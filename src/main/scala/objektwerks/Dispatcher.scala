package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

final class Dispatcher(emailer: Emailer,
                       store: Store):
  def dispatch[E <: Event](command: Command): Event =
    if !command.isValid then store.addFault( Fault(s"Command is invalid: $command") )

    isAuthorized(command) match
      case Authorized(isAuthorized) => if !isAuthorized then store.addFault( Fault(s"License is unauthorized: $command") )
      case fault @ Fault(_, _) => store.addFault(fault)
      case _ =>

    val event = command match
      case Register(emailAddress)           => register(emailAddress)
      case Login(emailAddress, pin)         => login(emailAddress, pin)
      case ListHouses(_, accountId)         => listHouses(accountId)
      case ListEntities(_, typeof, houseId) => listEntities(typeof, houseId)
      case AddEntity(_, typeof, entity)     => addEntity(typeof, entity)
      case UpdateEntity(_, typeof, entity)  => updateEntity(typeof, entity)
      case ListFaults(_)                    => listFaults()
      case AddFault(_, fault)               => addFault(fault)

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

  private def send(email: String,
                   message: String): Unit =
    val recipients = List(email)
    emailer.send(recipients, message)

  private def register(email: String): Event =
    Try {
      val account = Account(email = email)
      val message = s"<p><b>Account Registration:</b> Your new pin is: <b>${account.pin}</b> Welcome aboard!</p>"
      send(account.email, message)
      val id = store.register(account)
      Registered( account.copy(id = id) )
    }.recover { case NonFatal(error) => Fault(s"Registration failed for: $email, because: ${error.getMessage}") }
     .get

  private def login(email: String,
                    pin: String): Event =
    Try { store.login(email, pin) }.fold(
      error => Fault(s"Login failed: ${error.getMessage()}"),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
        else Fault(s"Login failed for email address: $email and pin: $pin")
    )

  private def listEntities(typeof: EntityType, houseId: Long): Event = ???

  private def addEntity(typeof: EntityType, entity: Entity): Event = ???

  private def updateEntity(typeof: EntityType, entity: Entity): Event = ???

  private def listFaults(): Event =
    FaultsListed( store.listFaults() )

  private def addFault(fault: Fault): Event =
    store.addFault(fault)
    FaultAdded()
