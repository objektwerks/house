package objektwerks

import ox.supervised
import ox.resilience.retry
import ox.scheduling.Schedule

import scala.concurrent.duration.*
import scala.util.Try
import scala.util.control.NonFatal

import EntityType.*

final class Handler(store: Store, emailer: Emailer):
  private val list = Map(
    House -> listHouses, Issue -> listIssues, Drawing -> listDrawings, Foundation -> listFoundations, Frame -> listFrames,
    Attic -> listAttics, Insulation -> listInsulations, Ductwork -> listDuctworks, Ventilation -> listVentilations, Roof -> listRoofs,
    Chimney -> listChimneys, Balcony -> listBalconys, Drywall -> listDrywalls, Room -> listRooms, Driveway -> listDriveways,
    Garage -> listGarages, Siding -> listSidings, Gutter -> listGutters, Soffit -> listSoffits, Window -> listWindows,
    Door -> listDoors, Plumbing -> listPlumbings, Electrical -> listElectricals, Fusebox -> listFuseboxes, Alarm -> listAlarms,
    Heater -> listHeaters, AirConditioner -> listAirConditioners, Floor -> listFloors, Lighting -> listLightings, Sewage -> listSewages,
    Well -> listWells, Water -> listWaters, WaterHeater -> listWaterHeaters, Lawn -> listLawns, Garden -> listGardens,
    Sprinkler -> listSprinklers, Shed -> listSheds, SolarPanel -> listSolarPanels, Porch -> listPorches, Patio -> listPatios,
    Pool -> listPools, Dock -> listDocks, Gazebo -> listGazebos, Mailbox -> listMailboxes
  )
  private val add = Map(
    House -> addHouse, Issue -> addIssue, Drawing -> addDrawing, Foundation -> addFoundation, Frame -> addFrame, Attic -> addAttic,
    Insulation -> addInsulation, Ductwork -> addDuctwork, Ventilation -> addVentilation, Roof -> addRoof, Chimney -> addChimney,
    Balcony -> addBalcony, Drywall -> addDrywall, Room -> addRoom, Driveway -> addDriveway, Garage -> addGarage, Siding -> addSiding,
    Gutter -> addGutter, Soffit -> addSoffit, Window -> addWindow, Door -> addDoor, Plumbing -> addPlumbing,
    Electrical -> addElectrical, Fusebox -> addFusebox, Alarm -> addAlarm, Heater -> addHeater, AirConditioner -> addAirConditioner,
    Floor -> addFloor, Lighting -> addLighting, Sewage -> addSewage, Well -> addWell, Water -> addWater,
    WaterHeater -> addWaterHeater, Lawn -> addLawn, Garden -> addGarden, Sprinkler -> addSprinkler, Shed -> addShed,
    SolarPanel -> addSolarPanel, Porch -> addPorch, Patio -> addPatio, Pool -> addPool, Dock -> addDock, Gazebo -> addGazebo,
    Mailbox -> addMailbox
  )
  private val update = Map(
    House -> updateHouse, Issue -> updateIssue, Drawing ->updateDrawing, Foundation -> updateFoundation, Frame -> updateFrame,
    Attic -> updateAttic, Insulation -> updateInsulation, Ductwork -> updateDuctwork, Ventilation -> updateVentilation, Roof -> updateRoof,
    Chimney -> updateChimney, Balcony -> updateBalcony, Drywall -> updateDrywall, Room -> updateRoom, Driveway -> updateDriveway,
    Garage -> updateGarage, Siding -> updateSiding, Gutter -> updateGutter, Soffit -> updateSoffit, Window -> updateWindow,
    Door -> updateDoor, Plumbing -> updatePlumbing, Electrical -> updateElectrical, Fusebox -> updateFusebox, Alarm -> updateAlarm,
    Heater -> updateHeater, AirConditioner -> updateAirConditioner, Floor -> updateFloor, Lighting -> updateLighting,
    Sewage -> updateSewage, Well -> updateWell, Water -> updateWater, WaterHeater -> updateWaterHeater, Lawn -> updateLawn,
    Garden -> updateGarden, Sprinkler -> updateSprinkler, Shed -> updateShed, SolarPanel -> updateSolarPanel, Porch -> updatePorch,
    Patio -> updatePatio, Pool -> updatePool, Dock -> updateDock, Gazebo -> updateGazebo, Mailbox -> updateMailbox
  )

  def isAuthorized(command: Command): Security =
    command match
      case license: License =>
        try
          supervised:
            retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )(
              if store.isAuthorized(license.license) then Authorized
              else Unauthorized(s"Unauthorized: $command")
            )
        catch
          case NonFatal(error) => Unauthorized(s"Unauthorized: $command, cause: $error")
      case Register(_) | Login(_, _) => Authorized

  def sendEmail(email: String, message: String): Boolean =
    val recipients = List(email)
    emailer.send(recipients, message)

  def register(email: String): Event =
    try
      supervised:
        val account = Account(email = email)
        val message = s"Your new pin is: ${account.pin}\n\nWelcome aboard!"
        val result = retry( Schedule.fixedInterval(600.millis).maxRepeats(1) )( sendEmail(account.email, message) )
        if result then
          val id = store.register(account)
          Registered( account.copy(id = id) )
        else
          throw IllegalArgumentException("Invalid email address.")
    catch
      case NonFatal(error) => addFault( Fault(s"Registration failed for: $email, because: ${error.getMessage}") )

  def login(email: String, pin: String): Event =
    Try:
      supervised:
        retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( store.login(email, pin) )
    .fold(
      error => addFault( Fault(s"Login failed: ${error.getMessage}") ),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn( optionalAccount.get )
        else addFault( Fault(s"Login failed for email address: $email and pin: $pin") ) )

  def listFaults(): Event =
    try
      FaultsListed(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( store.listFaults() )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"List faults failed: ${error.getMessage}") )

  def addFault(fault: String): Event =
    try
      FaultAdded(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( store.addFault( Fault(fault) ) )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"Add fault failed: ${error.getMessage}") )

  def addFault(fault: Fault): Event =
    try
      FaultAdded(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( store.addFault(fault) )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"Add fault failed: ${error.getMessage}") )

  def listEntities(typeof: EntityType, parentId: Long): Event =
    try
      val function = list(typeof)
      EntitiesListed(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( function(parentId) )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"List entities [$typeof]{$parentId} failed: ${error.getMessage}") )

  def addEntity(typeof: EntityType, entity: Entity): Event =
    try
      val function = add(typeof)
      EntityAdded(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( function(entity) )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"Add entity [$typeof] failed: ${error.getMessage} for: $entity") )

  def updateEntity(typeof: EntityType, entity: Entity): Event =
    try
      val function = update(typeof)
      EntityUpdated(
        supervised:
          retry( Schedule.fixedInterval(100.millis).maxRepeats(1) )( function(entity) )
      )
    catch
      case NonFatal(error) => addFault( Fault(s"Update entity [$typeof] failed: ${error.getMessage} for: $entity") )

  def listHouses(accountId: Long): List[House] = store.listHouses(accountId)

  def addHouse(entity: Entity): Long = store.addHouse( entity.asInstanceOf[House] )

  def updateHouse(entity: Entity): Int = store.updateHouse( entity.asInstanceOf[House] )

  def listIssues(houseId: Long): List[Issue] = store.listIssues(houseId)

  def addIssue(entity: Entity): Long = store.addIssue( entity.asInstanceOf[Issue] )

  def updateIssue(entity: Entity): Int = store.updateIssue( entity.asInstanceOf[Issue] )

  def listDrawings(houseId: Long): List[Drawing] = store.listDrawings(houseId)

  def addDrawing(entity: Entity): Long = store.addDrawing( entity.asInstanceOf[Drawing] )

  def updateDrawing(entity: Entity): Int = store.updateDrawing( entity.asInstanceOf[Drawing] )

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

  def updateFusebox(entity: Entity): Int = store.updateFusebox( entity.asInstanceOf[Fusebox] )

  def listAlarms(houseId: Long): List[Alarm] = store.listAlarms(houseId)

  def addAlarm(entity: Entity): Long = store.addAlarm( entity.asInstanceOf[Alarm] )

  def updateAlarm(entity: Entity): Int = store.updateAlarm( entity.asInstanceOf[Alarm] )

  def listHeaters(houseId: Long): List[Heater] = store.listHeaters(houseId)

  def addHeater(entity: Entity): Long = store.addHeater( entity.asInstanceOf[Heater] )

  def updateHeater(entity: Entity): Int = store.updateHeater( entity.asInstanceOf[Heater] )

  def listAirConditioners(houseId: Long): List[AirConditioner] = store.listAirConditioners(houseId)

  def addAirConditioner(entity: Entity): Long = store.addAirConditioner( entity.asInstanceOf[AirConditioner] )

  def updateAirConditioner(entity: Entity): Int = store.updateAirConditioner( entity.asInstanceOf[AirConditioner] )

  def listFloors(houseId: Long): List[Floor] = store.listFloors(houseId)

  def addFloor(entity: Entity): Long = store.addFloor( entity.asInstanceOf[Floor] )

  def updateFloor(entity: Entity): Int = store.updateFloor( entity.asInstanceOf[Floor] )

  def listLightings(houseId: Long): List[Lighting] = store.listLightings(houseId)

  def addLighting(entity: Entity): Long = store.addLighting( entity.asInstanceOf[Lighting] )

  def updateLighting(entity: Entity): Int = store.updateLighting( entity.asInstanceOf[Lighting] )

  def listSewages(houseId: Long): List[Sewage] = store.listSewages(houseId)

  def addSewage(entity: Entity): Long = store.addSewage( entity.asInstanceOf[Sewage] )

  def updateSewage(entity: Entity): Int = store.updateSewage( entity.asInstanceOf[Sewage] )

  def listWells(houseId: Long): List[Well] = store.listWells(houseId)

  def addWell(entity: Entity): Long = store.addWell( entity.asInstanceOf[Well] )

  def updateWell(entity: Entity): Int = store.updateWell( entity.asInstanceOf[Well] )

  def listWaters(houseId: Long): List[Water] = store.listWaters(houseId)

  def addWater(entity: Entity): Long = store.addWater( entity.asInstanceOf[Water] )

  def updateWater(entity: Entity): Int = store.updateWater( entity.asInstanceOf[Water] )

  def listWaterHeaters(houseId: Long): List[WaterHeater] = store.listWaterHeaters(houseId)

  def addWaterHeater(entity: Entity): Long = store.addWaterHeater( entity.asInstanceOf[WaterHeater] )

  def updateWaterHeater(entity: Entity): Int = store.updateWaterHeater( entity.asInstanceOf[WaterHeater] )

  def listLawns(houseId: Long): List[Lawn] = store.listLawns(houseId)

  def addLawn(entity: Entity): Long = store.addLawn( entity.asInstanceOf[Lawn] )

  def updateLawn(entity: Entity): Int = store.updateLawn( entity.asInstanceOf[Lawn] )

  def listGardens(houseId: Long): List[Garden] = store.listGardens(houseId)

  def addGarden(entity: Entity): Long = store.addGarden( entity.asInstanceOf[Garden] )

  def updateGarden(entity: Entity): Int = store.updateGarden( entity.asInstanceOf[Garden] )

  def listSprinklers(houseId: Long): List[Sprinkler] = store.listSprinklers(houseId)

  def addSprinkler(entity: Entity): Long = store.addSprinkler( entity.asInstanceOf[Sprinkler] )

  def updateSprinkler(entity: Entity): Int = store.updateSprinkler( entity.asInstanceOf[Sprinkler] )

  def listSheds(houseId: Long): List[Shed] = store.listSheds(houseId)

  def addShed(entity: Entity): Long = store.addShed( entity.asInstanceOf[Shed] )

  def updateShed(entity: Entity): Int = store.updateShed( entity.asInstanceOf[Shed] )

  def listSolarPanels(houseId: Long): List[SolarPanel] = store.listSolarPanels(houseId)

  def addSolarPanel(entity: Entity): Long = store.addSolarPanel( entity.asInstanceOf[SolarPanel] )

  def updateSolarPanel(entity: Entity): Int = store.updateSolarPanel( entity.asInstanceOf[SolarPanel] )

  def listPorches(houseId: Long): List[Porch] = store.listPorches(houseId)

  def addPorch(entity: Entity): Long = store.addPorch( entity.asInstanceOf[Porch] )

  def updatePorch(entity: Entity): Int = store.updatePorch( entity.asInstanceOf[Porch] )

  def listPatios(houseId: Long): List[Patio] = store.listPatios(houseId)

  def addPatio(entity: Entity): Long = store.addPatio( entity.asInstanceOf[Patio] )

  def updatePatio(entity: Entity): Int = store.updatePatio( entity.asInstanceOf[Patio] )

  def listPools(houseId: Long): List[Pool] = store.listPools(houseId)

  def addPool(entity: Entity): Long = store.addPool( entity.asInstanceOf[Pool] )

  def updatePool(entity: Entity): Int = store.updatePool( entity.asInstanceOf[Pool] )

  def listDocks(houseId: Long): List[Dock] = store.listDocks(houseId)

  def addDock(entity: Entity): Long = store.addDock( entity.asInstanceOf[Dock] )

  def updateDock(entity: Entity): Int = store.updateDock( entity.asInstanceOf[Dock] )

  def listGazebos(houseId: Long): List[Gazebo] = store.listGazebos(houseId)

  def addGazebo(entity: Entity): Long = store.addGazebo( entity.asInstanceOf[Gazebo] )

  def updateGazebo(entity: Entity): Int = store.updateGazebo( entity.asInstanceOf[Gazebo] )

  def listMailboxes(houseId: Long): List[Mailbox] = store.listMailboxes(houseId)

  def addMailbox(entity: Entity): Long = store.addMailbox( entity.asInstanceOf[Mailbox] )

  def updateMailbox(entity: Entity): Int = store.updateMailbox( entity.asInstanceOf[Mailbox] )
