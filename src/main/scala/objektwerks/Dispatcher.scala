package objektwerks

import Validators.*

final class Dispatcher(handler: Handler):
  def dispatch(command: Command): Event =
    val commandValidator = command.validate
    commandValidator.isValid match
      case false => handler.addFault( Fault(s"${commandValidator.asString} for: $command") )
      case true =>
        handler.isAuthorized(command) match
          case Unauthorized(cause) => handler.addFault( Fault(cause) )
          case Authorized =>
            val event = command match
              case Register(emailAddress)           => handler.register(emailAddress)
              case Login(emailAddress, pin)         => handler.login(emailAddress, pin)
              case ListFaults(_)                    => handler.listFaults()
              case AddFault(_, fault)               => handler.addFault(fault)
              case ListEntities(_, typeof, houseId) => handler.listEntities(typeof, houseId)
              case AddEntity(_, typeof, entity)     => handler.addEntity(typeof, entity)
              case UpdateEntity(_, typeof, entity)  => handler.updateEntity(typeof, entity)
            val eventValidator = event.validate
            eventValidator.isValid match
              case false => handler.addFault( Fault(s"${eventValidator.asString} for: $event") )
              case true => event