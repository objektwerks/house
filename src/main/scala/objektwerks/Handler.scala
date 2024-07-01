package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import EntityType.*

final class Handler(store: Store,
                    emailer: Emailer):
  val list = Map(
    Foundation -> listFoundations, Frame -> listFrames, Attic -> listAttics, Insulation -> listInsulations
  )
  val add = Map(
    House -> addHouse, Foundation -> addFoundation, Frame -> addFrame, Attic -> addAttic, Insulation -> addInsulation
  )
  val update = Map(
    House -> updateHouse, Foundation -> updateFoundation, Frame -> updateFrame, Attic -> updateAttic, Insulation -> updateInsulation
  )
  def isAuthorized(command: Command): Event =
    command match
      case license: License =>
        Try {
          Authorized( store.isAuthorized(license.license) )
        }.recover {
          case NonFatal(error) => Fault(s"Authorization failed: $error")
        }.get
      case Register(_) | Login(_, _) => Authorized(true)

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
    }.recover {
      case NonFatal(error) => Fault(s"Registration failed for: $email, because: ${error.getMessage}")
    }.get

  def login(email: String,
            pin: String): Event =
    Try {
      store.login(email, pin)
    }.fold(
      error => Fault(s"Login failed: ${error.getMessage()}"),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn( optionalAccount.get )
        else Fault(s"Login failed for email address: $email and pin: $pin")
    )

  def listHouses(accountId: Long): Event = HousesListed( store.listHouses(accountId) )

  def listFaults(): Event = FaultsListed( store.listFaults() )

  def addFault(fault: Fault): Event =
    store.addFault(fault)
    FaultAdded()

  def listEntities(typeof: EntityType, houseId: Long): Event =
    Try {
      val function = list(typeof)
      EntitiesListed( function(houseId) )
    }.recover {
      case NonFatal(error) => Fault(s"List entities for type [${typeof.toString}] failed!")
    }.get

  def addEntity(typeof: EntityType, entity: Entity): Event =
    Try {
      val function = add(typeof)
      EntityAdded( function(entity) )
    }.recover {
      case NonFatal(error) => Fault(s"Add entity for type [${typeof.toString}] failed! Entity: ${entity.toString}")
    }.get

  def updateEntity(typeof: EntityType, entity: Entity): Event =
    Try {
      val function = update(typeof)
      EntityUpdated( function(entity) )
    }.recover {
      case NonFatal(error) => Fault(s"Update entity for type [${typeof.toString}] failed! Entity: ${entity.toString}")
    }.get

  def addHouse(entity: Entity): Long = store.addHouse( entity.asInstanceOf[House] )

  def updateHouse(entity: Entity): Int = store.updateHouse( entity.asInstanceOf[House] )

  def listFoundations(houseId: Long): List[Foundation] = store.listFoundations(houseId)

  def addFoundation(entity: Entity): Long = store.addFoundation( entity.asInstanceOf[Foundation] )

  def updateFoundation(entity: Entity): Int = store.updateFoundation( entity.asInstanceOf[Foundation] )

  def listFrames(houseId: Long): List[Frame] = store.listFrames(houseId)

  def addFrame(entity: Entity): Long = store.addFrame( entity.asInstanceOf[Frame] )

  def updateFrame(entity: Entity): Int = store.updateFrame( entity.asInstanceOf[Frame] )

  def listAttics(houseId: Long): List[Attic] = store.listAttics(houseId)

  def addAttic(entity: Entity): Long = store.addAttic( entity.asInstanceOf[Attic] )

  def updateAttic(entity: Entity): Int = store.updateAttic( entity.asInstanceOf[Attic] )

  def listInsulations(houseId: Long): List[Insulation] = store.listInsulations(houseId)

  def addInsulation(entity: Entity): Long = store.addInsulation( entity.asInstanceOf[Insulation] )

  def updateInsulation(entity: Entity): Int = store.updateInsulation( entity.asInstanceOf[Insulation] )

  def listDuctworks(houseId: Long): List[Ductwork] = store.listDuctworks(houseId)

  def addDuctwork(entity: Entity): Long = store.addDuctwork( entity.asInstanceOf[Ductwork] )
