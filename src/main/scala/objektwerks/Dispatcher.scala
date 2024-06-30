package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

final class Dispatcher(emailer: Emailer,
                       store: Store,
                       handler: Handler):
  def dispatch[E <: Event](command: Command): Event =
    if !command.isValid then store.addFault( Fault(s"Command is invalid: $command") )

    isAuthorized(command) match
      case Authorized(isAuthorized) => if !isAuthorized then store.addFault( Fault(s"License is unauthorized: $command") )
      case fault @ Fault(_, _) => store.addFault(fault)
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
      case fault @ Fault(_, _) => store.addFault(fault)
      case _ => event

  private def isAuthorized(command: Command): Event =
    command match
      case license: License =>
        Try {
          Authorized( store.isAuthorized(license.license) )
        }.recover { case NonFatal(error) => Fault(s"Authorization failed: $error") }
         .get
      case Register(_) | Login(_, _) => Authorized(true)
