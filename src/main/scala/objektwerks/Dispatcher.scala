package objektwerks

import Validator.*

final class Dispatcher(handler: Handler):
  def dispatch(command: Command): Event =
    command.isValid match
      case false => handler.addFault( Fault(s"Command is invalid: $command") )
      case true =>
        handler.isAuthorized(command) match
          case Unauthorized => handler.addFault( Fault(s"License is unauthorized: $command") )
          case fault @ Fault(_, _) => handler.addFault(fault)
          case _ =>
            val event = command match
              case Register(emailAddress)           => handler.register(emailAddress)
              case Login(emailAddress, pin)         => handler.login(emailAddress, pin)
              case ListFaults(_)                    => handler.listFaults()
              case AddFault(_, fault)               => handler.addFault(fault)
              case ListEntities(_, typeof, houseId) => handler.listEntities(typeof, houseId)
              case AddEntity(_, typeof, entity)     => handler.addEntity(typeof, entity)
              case UpdateEntity(_, typeof, entity)  => handler.updateEntity(typeof, entity)
            event match
              case fault @ Fault(_, _) => handler.addFault(fault)
              case _ => event
