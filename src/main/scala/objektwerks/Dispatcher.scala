package objektwerks

import Validator.*

final class Dispatcher(handler: Handler):
  def dispatch[E <: Event](command: Command): Event =
    if !command.isValid then handler.addFault( Fault(s"Command is invalid: $command") )

    handler.isAuthorized(command) match
      case Authorized(isAuthorized) => if !isAuthorized then handler.addFault( Fault(s"License is unauthorized: $command") )
      case fault @ Fault(_, _) => handler.addFault(fault)
      case _ =>

    val event = command match
      case Register(emailAddress)           => handler.register(emailAddress)
      case Login(emailAddress, pin)         => handler.login(emailAddress, pin)
      case ListHouses(_, accountId)         => handler.listHouses(accountId)
      case ListEntities(_, typeof, houseId) => handler.listEntities(typeof, houseId)
      case AddEntity(_, typeof, entity)     => handler.addEntity(typeof, entity)
      case UpdateEntity(_, typeof, entity)  => handler.updateEntity(typeof, entity)
      case ListFaults(_)                    => handler.listFaults()
      case AddFault(_, fault)               => handler.addFault(fault)

    event match
      case fault @ Fault(_, _) => handler.addFault(fault)
      case _ => event
