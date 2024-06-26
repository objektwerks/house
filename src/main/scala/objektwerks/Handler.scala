package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import EntityType.*

final class Handler(store: Store,
                    emailer: Emailer):
  val list = Map(
    Foundation -> listFoundations, Frame -> listFrames, Attic -> listAttics, Insulation -> listInsulations,
    Ductwork -> listDuctworks, Ventilation -> listVentilations, Roof -> listRoofs, Chimney -> listChimneys,
    Balcony -> listBalconys, Drywall -> listDrywalls, Room -> listRooms, Driveway -> listDriveways, Garage -> listGarages
  )
  val add = Map(
    House -> addHouse, Foundation -> addFoundation, Frame -> addFrame, Attic -> addAttic, Insulation -> addInsulation,
    Ductwork -> addDuctwork, Ventilation -> addVentilation, Roof -> addRoof, Chimney -> addChimney, Balcony -> addBalcony,
    Drywall -> addDrywall, Room -> addRoom, Driveway -> addDriveway, Garage -> addGarage
  )
  val update = Map(
    House -> updateHouse, Foundation -> updateFoundation, Frame -> updateFrame, Attic -> updateAttic,
    Insulation -> updateInsulation, Ductwork -> updateDuctwork, Ventilation -> updateVentilation, Roof -> updateRoof,
    Chimney -> updateRoof, Balcony -> updateBalcony, Drywall -> updateDrywall, Room -> updateRoom, Driveway -> updateDriveway,
    Garage -> updateGarage
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

  def updateDuctwork(entity: Entity): Int = store.updateDuctwork( entity.asInstanceOf[Ductwork] )

  def listVentilations(houseId: Long): List[Ventilation] = store.listVentilations(houseId)

  def addVentilation(entity: Entity): Long = store.addVentilation( entity.asInstanceOf[Ventilation] )

  def updateVentilation(entity: Entity): Int = store.updateVentilation( entity.asInstanceOf[Ventilation] )

  def listRoofs(houseId: Long): List[Roof] = store.listRoofs(houseId)

  def addRoof(entity: Entity): Long = store.addRoof( entity.asInstanceOf[Roof] )

  def updateRoof(entity: Entity): Int = store.updateRoof( entity.asInstanceOf[Roof] )

  def listChimneys(houseId: Long): List[Chimney] = store.listChimneys(houseId)

  def addChimney(entity: Entity): Long = store.addChimney( entity.asInstanceOf[Chimney] )

  def updateChimney(entity: Entity): Int = store.updateChimney( entity.asInstanceOf[Chimney] )

  def listBalconys(houseId: Long): List[Balcony] = store.listBalconys(houseId)

  def addBalcony(entity: Entity): Long = store.addBalcony( entity.asInstanceOf[Balcony] )

  def updateBalcony(entity: Entity): Int = store.updateBalcony( entity.asInstanceOf[Balcony] )

  def listDrywalls(houseId: Long): List[Drywall] = store.listDrywalls(houseId)

  def addDrywall(entity: Entity): Long = store.addDrywall( entity.asInstanceOf[Drywall] )

  def updateDrywall(entity: Entity): Int = store.updateDrywall( entity.asInstanceOf[Drywall] )

  def listRooms(houseId: Long): List[Room] = store.listRooms(houseId)

  def addRoom(entity: Entity): Long = store.addRoom( entity.asInstanceOf[Room] )

  def updateRoom(entity: Entity): Int = store.updateRoom( entity.asInstanceOf[Room] )

  def listDriveways(houseId: Long): List[Driveway] = store.listDriveways(houseId)

  def addDriveway(entity: Entity): Long = store.addDriveway( entity.asInstanceOf[Driveway] )

  def updateDriveway(entity: Entity): Int = store.updateDriveway( entity.asInstanceOf[Driveway] )

  def listGarages(houseId: Long): List[Garage] = store.listGarages(houseId)

  def addGarage(entity: Entity): Long = store.addGarage( entity.asInstanceOf[Garage] )

  def updateGarage(entity: Entity): Int = store.updateGarage( entity.asInstanceOf[Garage] )
