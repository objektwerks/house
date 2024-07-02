package objektwerks

import scala.util.Try
import scala.util.control.NonFatal

import EntityType.*

final class Handler(store: Store,
                    emailer: Emailer):
  val list = Map(
    Foundation -> listFoundations, Frame -> listFrames, Attic -> listAttics, Insulation -> listInsulations,
    Ductwork -> listDuctworks, Ventilation -> listVentilations, Roof -> listRoofs, Chimney -> listChimneys,
    Balcony -> listBalconys, Drywall -> listDrywalls, Room -> listRooms, Driveway -> listDriveways, Garage -> listGarages,
    Siding -> listSidings, Gutter -> listGutters, Soffit -> listSoffits, Window -> listWindows, Door -> listDoors,
    Plumbing -> listPlumbings, Electrical -> listElectricals
  )
  val add = Map(
    House -> addHouse, Foundation -> addFoundation, Frame -> addFrame, Attic -> addAttic, Insulation -> addInsulation,
    Ductwork -> addDuctwork, Ventilation -> addVentilation, Roof -> addRoof, Chimney -> addChimney, Balcony -> addBalcony,
    Drywall -> addDrywall, Room -> addRoom, Driveway -> addDriveway, Garage -> addGarage, Siding -> addSiding,
    Gutter -> addGutter, Soffit -> addSoffit, Window -> addWindow, Door -> addDoor, Plumbing -> addPlumbing,
    Electrical -> addElectrical
  )
  val update = Map(
    House -> updateHouse, Foundation -> updateFoundation, Frame -> updateFrame, Attic -> updateAttic,
    Insulation -> updateInsulation, Ductwork -> updateDuctwork, Ventilation -> updateVentilation, Roof -> updateRoof,
    Chimney -> updateRoof, Balcony -> updateBalcony, Drywall -> updateDrywall, Room -> updateRoom, Driveway -> updateDriveway,
    Garage -> updateGarage, Siding -> updateSiding, Gutter -> updateGutter, Soffit -> updateSoffit, Window -> updateWindow,
    Door -> updateDoor, Plumbing -> updatePlumbing, Electrical -> updateElectrical
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
      case NonFatal(error) => Fault(s"List entities [${typeof.toString}] failed: ${error.getMessage}")
    }.get

  def addEntity(typeof: EntityType, entity: Entity): Event =
    Try {
      val function = add(typeof)
      EntityAdded( function(entity) )
    }.recover {
      case NonFatal(error) => Fault(s"Add entity [${typeof.toString}] failed: ${error.getMessage} for: ${entity.toString}")
    }.get

  def updateEntity(typeof: EntityType, entity: Entity): Event =
    Try {
      val function = update(typeof)
      EntityUpdated( function(entity) )
    }.recover {
      case NonFatal(error) => Fault(s"Update entity [${typeof.toString}] failed: ${error.getMessage} for: ${entity.toString}")
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

  def listSidings(houseId: Long): List[Siding] = store.listSidings(houseId)

  def addSiding(entity: Entity): Long = store.addSiding( entity.asInstanceOf[Siding] )

  def updateSiding(entity: Entity): Int = store.updateSiding( entity.asInstanceOf[Siding] )

  def listGutters(houseId: Long): List[Gutter] = store.listGutters(houseId)

  def addGutter(entity: Entity): Long = store.addGutter( entity.asInstanceOf[Gutter] )

  def updateGutter(entity: Entity): Int = store.updateGutter( entity.asInstanceOf[Gutter] )

  def listSoffits(houseId: Long): List[Soffit] = store.listSoffits(houseId)

  def addSoffit(entity: Entity): Long = store.addSoffit( entity.asInstanceOf[Soffit] )

  def updateSoffit(entity: Entity): Int = store.updateSoffit( entity.asInstanceOf[Soffit] )

  def listWindows(houseId: Long): List[Window] = store.listWindows(houseId)

  def addWindow(entity: Entity): Long = store.addWindow( entity.asInstanceOf[Window] )

  def updateWindow(entity: Entity): Int = store.updateWindow( entity.asInstanceOf[Window] )

  def listDoors(houseId: Long): List[Door] = store.listDoors(houseId)

  def addDoor(entity: Entity): Long = store.addDoor( entity.asInstanceOf[Door] )

  def updateDoor(entity: Entity): Int = store.updateDoor( entity.asInstanceOf[Door] )

  def listPlumbings(houseId: Long): List[Plumbing] = store.listPlumbings(houseId)

  def addPlumbing(entity: Entity): Long = store.addPlumbing( entity.asInstanceOf[Plumbing] )

  def updatePlumbing(entity: Entity): Int = store.updatePlumbing( entity.asInstanceOf[Plumbing] )

  def listElectricals(houseId: Long): List[Electrical] = store.listElectricals(houseId)

  def addElectrical(entity: Entity): Long = store.addElectrical( entity.asInstanceOf[Electrical] )

  def updateElectrical(entity: Entity): Int = store.updateElectrical( entity.asInstanceOf[Electrical] )

  def listFuseboxes(houseId: Long): List[Fusebox] = store.listFuseboxes(houseId)

  def addFusebox(entity: Entity): Long = store.addFusebox( entity.asInstanceOf[Fusebox] )
