package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

final class Handler(store: Store):
  def send(email: String,
                   message: String): Unit =
    val recipients = List(email)
    emailer.send(recipients, message)

  def register(email: String): Event =
    Try {
      val account = Account(email = email)
      val message = s"<p><b>Account Registration:</b> Your new pin is: <b>${account.pin}</b> Welcome aboard!</p>"
      send(account.email, message)
      val id = store.register(account)
      Registered( account.copy(id = id) )
    }.recover { case NonFatal(error) => Fault(s"Registration failed for: $email, because: ${error.getMessage}") }
     .get

  def login(email: String,
                    pin: String): Event =
    Try { store.login(email, pin) }.fold(
      error => Fault(s"Login failed: ${error.getMessage()}"),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
        else Fault(s"Login failed for email address: $email and pin: $pin")
    )

  def listEntities(typeof: EntityType, houseId: Long): Event = ???

  def addEntity(typeof: EntityType, entity: Entity): Event = ???

  def updateEntity(typeof: EntityType, entity: Entity): Event = ???

  def listHouses(accountId: Long): Event =
    HousesListed( store.listHouses(accountId) )

  def addHouse(entity: Entity): Event =
    EntityAdded( store.addHouse( entity.asInstanceOf[House] ) )

  def listFaults(): Event =
    FaultsListed( store.listFaults() )

  def addFault(fault: Fault): Event =
    store.addFault(fault)
    FaultAdded()
