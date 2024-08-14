package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.sys.process.Process

import Validator.*

final class DispatcherTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d house -f ddl.sql").run().exitValue()
  exitCode shouldBe 0

  val config = ConfigFactory.load("test.conf")
  val store = Store(config)
  val emailer = Emailer(config)
  val handler = Handler(store, emailer)
  val dispatcher = Dispatcher(handler)

  var testAccount = Account()
  var testHouse = House(accountId = 0, location = "100 Rocky Way", label = "label", note = "note")
  var testIssue = Issue(houseId = 1)
  var testDrawing = Drawing(houseId = 1, url = "http://drawing/a", note = "drawing a")
  var testFoundation =  Foundation(houseId = 1)
  var testFrame = Frame(houseId = 1)
  var testAttic = Attic(houseId = 1)
  var testInsulation = Insulation(houseId = 1)
  var testDuctwork = Ductwork(houseId = 1)
  var testVentilation = Ventilation(houseId = 1)
  var testRoof = Roof(houseId = 1)
  var testChimney = Chimney(houseId = 1)
  var testBalcony = Balcony(houseId = 1)
  var testDrywall = Drywall(houseId = 1)
  var testRoom = Room(houseId = 1)
  var testDriveway = Driveway(houseId = 1)
  var testGarage = Garage(houseId = 1)
  var testSiding = Siding(houseId = 1)
  var testGutter = Gutter(houseId = 1)
  var testSoffit = Soffit(houseId = 1)
  var testWindow = Window(houseId = 1)
  var testDoor = Door(houseId = 1)
  var testPlumbing = Plumbing(houseId = 1)
  var testElectrical = Electrical(houseId = 1)
  var testFusebox = Fusebox(houseId = 1)
  var testAlarm = Alarm(houseId = 1)
  var testHeater = Heater(houseId = 1)
  var testAirConditioner = AirConditioner(houseId = 1)
  var testFloor = Floor(houseId = 1)
  var testLighting = Lighting(houseId = 1)
  var testSewage = Sewage(houseId = 1)
  var testWell = Well(houseId = 1)
  var testWater = Water(houseId = 1)
  var testWaterHeater = WaterHeater(houseId = 1)
  var testLawn = Lawn(houseId = 1)
  var testGarden = Garden(houseId = 1)
  var testSprinkler = Sprinkler(houseId = 1)
  var testShed = Shed(houseId = 1)
  var testSolarPanel = SolarPanel(houseId = 1)
  var testPorch = Porch(houseId = 1)
  var testPatio = Patio(houseId = 1)
  var testPool = Pool(houseId = 1, gallons = 9000)
  var testDock = Dock(houseId = 1)
  var testGazebo = Gazebo(houseId = 1)
  var testMailbox = Mailbox(houseId = 1)

  test("dispatcher"):
    register
    login
    
    addHouse
    updateHouse
    listHouses

    addDrawing
    updateDrawing
    listDrawings

    addFoundation
    updateFoundation
    listFoundations

    addFrame
    updateFrame
    listFrames

    addAttic
    updateAttic
    listAttics

    addInsulation
    updateInsulation
    listInsulations

    addDuctwork
    updateDuctwork
    listDuctworks

    addVentilation
    updateVentilation
    listVentilations

    addRoof
    updateRoof
    listRoofs

    addChimney
    updateChimney
    listChimneys

    addBalcony
    updateBalcony
    listBalconys

    addDrywall
    updateDrywall
    listDrywalls

    addRoom
    updateRoom
    listRooms

    addDriveway
    updateDriveway
    listDriveways

    addGarage
    updateGarage
    listGarages

    addSiding
    updateSiding
    listSidings

    addGutter
    updateGutter
    listGutters

    addSoffit
    updateSoffit
    listSoffits

    addWindow
    updateWindow
    listWindows

    addPlumbing
    updatePlumbing
    listPlumbings

    addElectrical
    updateElectrical
    listElectricals

    addFusebox
    updateFusebox
    listFuseboxes

    addAlarm
    updateAlarm
    listAlarms

    addHeater
    updateHeater
    listHeaters

    addAirConditioner
    updateAirConditioner
    listAirConditioners

    addFloor
    updateFloor
    listFloors

    addLighting
    updateLighting
    listLightings

    addSewage
    updateSewage
    listSewages

    addWell
    updateWell
    listWells

    addWater
    updateWater
    listWaters

    addWaterHeater
    updateWaterHeater
    listWaterHeaters

    addLawn
    updateLawn
    listLawns

    addGarden
    updateGarden
    listGardens

    addSprinkler
    updateSprinkler
    listSprinklers

    addShed
    updateShed
    listSheds

    addSolarPanel
    updateSolarPanel
    listSolarPanels

    addPorch
    updatePorch
    listPorches

    addPatio
    updatePatio
    listPatios

    addPool
    updatePool
    listPools

    addDock
    updateDock
    listDocks

    addGazebo
    updateGazebo
    listGazebos

    addMailbox
    updateMailbox
    listMailboxes

    fault

  def register: Unit =
    val register = Register(config.getString("email.sender"))
    dispatcher.dispatch(register) match
      case Registered(account) =>
        assert( account.isValid )
        testAccount = account
      case fault => fail(s"Invalid registered event: $fault")

  def login: Unit =
    val login = Login(testAccount.email, testAccount.pin)
    dispatcher.dispatch(login) match
      case LoggedIn(account) => account shouldBe testAccount
      case fault => fail(s"Invalid loggedin event: $fault")

  def addHouse: Unit =
    testHouse = testHouse.copy(accountId = testAccount.id)
    val addEntity = AddEntity(testAccount.license, EntityType.House, testHouse)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testHouse = testHouse.copy(id = id)
      case fault => fail(s"Invalid house added event: $fault")

  def updateHouse: Unit =
    testHouse = testHouse.copy(location = "200 Rocky Road")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.House, testHouse)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(id) => id shouldBe testHouse.id
      case fault => fail(s"Invalid house updated event: $fault")

  def listHouses: Unit =
    val list = ListEntities(testAccount.license, EntityType.House, testHouse.accountId)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testHouse
      case fault => fail(s"Invalid houses listed event: $fault")

  def addIssue: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Issue, testIssue)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testIssue = testIssue.copy(id = id)
      case fault => fail(s"Invalid issue added event: $fault")

  def addDrawing: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Drawing, testDrawing)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDrawing = testDrawing.copy(id = id)
      case fault => fail(s"Invalid drawing added event: $fault")

  def updateDrawing: Unit =
    testDrawing = testDrawing.copy(note = "drawing a update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Drawing, testDrawing)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid drawing updated event: $fault")

  def listDrawings: Unit =
    val list = ListEntities(testAccount.license, EntityType.Drawing, testDrawing.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDrawing
      case fault => fail(s"Invalid drawings listed event: $fault")

  def addFoundation: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Foundation, testFoundation)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testFoundation = testFoundation.copy(id = id)
      case fault => fail(s"Invalid foundation added event: $fault")

  def updateFoundation: Unit =
    testFoundation = testFoundation.copy(label = "foundation update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Foundation, testFoundation)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid foundation updated event: $fault")

  def listFoundations: Unit =
    val list = ListEntities(testAccount.license, EntityType.Foundation, testFoundation.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testFoundation
      case fault => fail(s"Invalid foundations listed event: $fault")

  def addFrame: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Frame, testFrame)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testFrame = testFrame.copy(id = id)
      case fault => fail(s"Invalid frame added event: $fault")

  def updateFrame: Unit =
    testFrame = testFrame.copy(label = "frame update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Frame, testFrame)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid frame updated event: $fault")

  def listFrames: Unit =
    val list = ListEntities(testAccount.license, EntityType.Frame, testFrame.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testFrame
      case fault => fail(s"Invalid frame listed event: $fault")

  def addAttic: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Attic, testAttic)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testAttic = testAttic.copy(id = id)
      case fault => fail(s"Invalid attic added event: $fault")

  def updateAttic: Unit =
    testAttic = testAttic.copy(label = "attic update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Attic, testAttic)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid attic updated event: $fault")

  def listAttics: Unit =
    val list = ListEntities(testAccount.license, EntityType.Attic, testAttic.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testAttic
      case fault => fail(s"Invalid attic listed event: $fault")

  def addInsulation: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Insulation, testInsulation)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testInsulation = testInsulation.copy(id = id)
      case fault => fail(s"Invalid insulation added event: $fault")

  def updateInsulation: Unit =
    testInsulation = testInsulation.copy(label = "insulation update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Insulation, testInsulation)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid insulation updated event: $fault")

  def listInsulations: Unit =
    val list = ListEntities(testAccount.license, EntityType.Insulation, testInsulation.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testInsulation
      case fault => fail(s"Invalid insulation listed event: $fault")

  def addDuctwork: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Ductwork, testDuctwork)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDuctwork = testDuctwork.copy(id = id)
      case fault => fail(s"Invalid ductwork added event: $fault")

  def updateDuctwork: Unit =
    testDuctwork = testDuctwork.copy(label = "ductwork update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Ductwork, testDuctwork)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid ductwork updated event: $fault")

  def listDuctworks: Unit =
    val list = ListEntities(testAccount.license, EntityType.Ductwork, testDuctwork.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDuctwork
      case fault => fail(s"Invalid ductwork listed event: $fault")

  def addVentilation: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Ventilation, testVentilation)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testVentilation = testVentilation.copy(id = id)
      case fault => fail(s"Invalid ventilation added event: $fault")

  def updateVentilation: Unit =
    testVentilation = testVentilation.copy(label = "ventilation update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Ventilation, testVentilation)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid ventialtion updated event: $fault")

  def listVentilations: Unit =
    val list = ListEntities(testAccount.license, EntityType.Ventilation, testVentilation.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testVentilation
      case fault => fail(s"Invalid ventilation listed event: $fault")

  def addRoof: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Roof, testRoof)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testRoof = testRoof.copy(id = id)
      case fault => fail(s"Invalid roof added event: $fault")

  def updateRoof: Unit =
    testRoof = testRoof.copy(label = "roof update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Roof, testRoof)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid roof updated event: $fault")

  def listRoofs: Unit =
    val list = ListEntities(testAccount.license, EntityType.Roof, testRoof.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testRoof
      case fault => fail(s"Invalid roof listed event: $fault")

  def addChimney: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Chimney, testChimney)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testChimney = testChimney.copy(id = id)
      case fault => fail(s"Invalid chimney added event: $fault")

  def updateChimney: Unit =
    testChimney = testChimney.copy(label = "chimney update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Chimney, testChimney)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid chimney updated event: $fault")

  def listChimneys: Unit =
    val list = ListEntities(testAccount.license, EntityType.Chimney, testChimney.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testChimney
      case fault => fail(s"Invalid chimney listed event: $fault")

  def addBalcony: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Balcony, testBalcony)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testBalcony = testBalcony.copy(id = id)
      case fault => fail(s"Invalid balcony added event: $fault")

  def updateBalcony: Unit =
    testBalcony = testBalcony.copy(label = "balcony update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Balcony, testBalcony)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid balcony updated event: $fault")

  def listBalconys: Unit =
    val list = ListEntities(testAccount.license, EntityType.Balcony, testBalcony.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testBalcony
      case fault => fail(s"Invalid balcony listed event: $fault")

  def addDrywall: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Drywall, testDrywall)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDrywall = testDrywall.copy(id = id)
      case fault => fail(s"Invalid drywall added event: $fault")

  def updateDrywall: Unit =
    testDrywall = testDrywall.copy(label = "drywall update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Drywall, testDrywall)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid drywall updated event: $fault")

  def listDrywalls: Unit =
    val list = ListEntities(testAccount.license, EntityType.Drywall, testDrywall.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDrywall
      case fault => fail(s"Invalid drywall listed event: $fault")

  def addRoom: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Room, testRoom)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testRoom = testRoom.copy(id = id)
      case fault => fail(s"Invalid room added event: $fault")

  def updateRoom: Unit =
    testRoom = testRoom.copy(label = "room update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Room, testRoom)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid room updated event: $fault")

  def listRooms: Unit =
    val list = ListEntities(testAccount.license, EntityType.Room, testRoom.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testRoom
      case fault => fail(s"Invalid room listed event: $fault")

  def addDriveway: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Driveway, testDriveway)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDriveway = testDriveway.copy(id = id)
      case fault => fail(s"Invalid driveway added event: $fault")

  def updateDriveway: Unit =
    testDriveway = testDriveway.copy(label = "driveway update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Driveway, testDriveway)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid driveway updated event: $fault")

  def listDriveways: Unit =
    val list = ListEntities(testAccount.license, EntityType.Driveway, testDriveway.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDriveway
      case fault => fail(s"Invalid driveway listed event: $fault")

  def addGarage: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Garage, testGarage)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testGarage = testGarage.copy(id = id)
      case fault => fail(s"Invalid garage added event: $fault")

  def updateGarage: Unit =
    testGarage = testGarage.copy(label = "garage update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Garage, testGarage)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid garage updated event: $fault")

  def listGarages: Unit =
    val list = ListEntities(testAccount.license, EntityType.Garage, testGarage.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testGarage
      case fault => fail(s"Invalid garage listed event: $fault")

  def addSiding: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Siding, testSiding)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testSiding = testSiding.copy(id = id)
      case fault => fail(s"Invalid siding added event: $fault")

  def updateSiding: Unit =
    testSiding = testSiding.copy(label = "siding update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Siding, testSiding)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid siding updated event: $fault")

  def listSidings: Unit =
    val list = ListEntities(testAccount.license, EntityType.Siding, testSiding.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testSiding
      case fault => fail(s"Invalid siding listed event: $fault")

  def addGutter: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Gutter, testGutter)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testGutter = testGutter.copy(id = id)
      case fault => fail(s"Invalid gutter added event: $fault")

  def updateGutter: Unit =
    testGutter = testGutter.copy(label = "gutter update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Gutter, testGutter)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid gutter updated event: $fault")

  def listGutters: Unit =
    val list = ListEntities(testAccount.license, EntityType.Gutter, testGutter.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testGutter
      case fault => fail(s"Invalid gutter listed event: $fault")

  def addSoffit: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Soffit, testSoffit)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testSoffit = testSoffit.copy(id = id)
      case fault => fail(s"Invalid soffit added event: $fault")

  def updateSoffit: Unit =
    testSoffit = testSoffit.copy(label = "soffit update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Soffit, testSoffit)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid soffit updated event: $fault")

  def listSoffits: Unit =
    val list = ListEntities(testAccount.license, EntityType.Soffit, testSoffit.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testSoffit
      case fault => fail(s"Invalid soffit listed event: $fault")

  def addWindow: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Window, testWindow)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testWindow = testWindow.copy(id = id)
      case fault => fail(s"Invalid window added event: $fault")

  def updateWindow: Unit =
    testWindow = testWindow.copy(label = "window update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Window, testWindow)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid window updated event: $fault")

  def listWindows: Unit =
    val list = ListEntities(testAccount.license, EntityType.Window, testWindow.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testWindow
      case fault => fail(s"Invalid window listed event: $fault")

  def addDoor: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Door, testDoor)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDoor = testDoor.copy(id = id)
      case fault => fail(s"Invalid door added event: $fault")

  def updateDoor: Unit =
    testWindow = testWindow.copy(label = "door update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Door, testDoor)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid door updated event: $fault")

  def listDoors: Unit =
    val list = ListEntities(testAccount.license, EntityType.Door, testDoor.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDoor
      case fault => fail(s"Invalid door listed event: $fault")

  def addPlumbing: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Plumbing, testPlumbing)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testPlumbing = testPlumbing.copy(id = id)
      case fault => fail(s"Invalid plumbing added event: $fault")

  def updatePlumbing: Unit =
    testPlumbing = testPlumbing.copy(label = "plumbing update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Plumbing, testPlumbing)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid plumbing updated event: $fault")

  def listPlumbings: Unit =
    val list = ListEntities(testAccount.license, EntityType.Plumbing, testPlumbing.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testPlumbing
      case fault => fail(s"Invalid plumbing listed event: $fault")

  def addElectrical: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Electrical, testElectrical)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testElectrical = testElectrical.copy(id = id)
      case fault => fail(s"Invalid electrical added event: $fault")

  def updateElectrical: Unit =
    testElectrical = testElectrical.copy(label = "electrical update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Electrical, testElectrical)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid electrical updated event: $fault")

  def listElectricals: Unit =
    val list = ListEntities(testAccount.license, EntityType.Electrical, testElectrical.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testElectrical
      case fault => fail(s"Invalid electrical listed event: $fault")

  def addFusebox: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Fusebox, testFusebox)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testFusebox = testFusebox.copy(id = id)
      case fault => fail(s"Invalid fusebox added event: $fault")

  def updateFusebox: Unit =
    testFusebox = testFusebox.copy(label = "fusebox update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Fusebox, testFusebox)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid fusebox updated event: $fault")

  def listFuseboxes: Unit =
    val list = ListEntities(testAccount.license, EntityType.Fusebox, testFusebox.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testFusebox
      case fault => fail(s"Invalid fusebox listed event: $fault")

  def addAlarm: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Alarm, testAlarm)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testAlarm = testAlarm.copy(id = id)
      case fault => fail(s"Invalid alarm added event: $fault")

  def updateAlarm: Unit =
    testAlarm = testAlarm.copy(label = "alarm update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Alarm, testAlarm)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid alarm updated event: $fault")

  def listAlarms: Unit =
    val list = ListEntities(testAccount.license, EntityType.Alarm, testAlarm.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testAlarm
      case fault => fail(s"Invalid alarm listed event: $fault")

  def addHeater: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Heater, testHeater)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testHeater = testHeater.copy(id = id)
      case fault => fail(s"Invalid heater added event: $fault")

  def updateHeater: Unit =
    testHeater = testHeater.copy(label = "heater update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Heater, testHeater)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid heater updated event: $fault")

  def listHeaters: Unit =
    val list = ListEntities(testAccount.license, EntityType.Heater, testHeater.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testHeater
      case fault => fail(s"Invalid heater listed event: $fault")

  def addAirConditioner: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.AirConditioner, testAirConditioner)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testAirConditioner = testAirConditioner.copy(id = id)
      case fault => fail(s"Invalid air conditioner added event: $fault")

  def updateAirConditioner: Unit =
    testAirConditioner = testAirConditioner.copy(label = "air conditioner update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.AirConditioner, testAirConditioner)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid air conditioner updated event: $fault")

  def listAirConditioners: Unit =
    val list = ListEntities(testAccount.license, EntityType.AirConditioner, testAirConditioner.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testAirConditioner
      case fault => fail(s"Invalid air conditioner listed event: $fault")

  def addFloor: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Floor, testFloor)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testFloor = testFloor.copy(id = id)
      case fault => fail(s"Invalid floor added event: $fault")

  def updateFloor: Unit =
    testFloor = testFloor.copy(label = "floor update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Floor, testFloor)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid floor updated event: $fault")

  def listFloors: Unit =
    val list = ListEntities(testAccount.license, EntityType.Floor, testFloor.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testFloor
      case fault => fail(s"Invalid floor listed event: $fault")

  def addLighting: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Lighting, testLighting)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testLighting = testLighting.copy(id = id)
      case fault => fail(s"Invalid lighting added event: $fault")

  def updateLighting: Unit =
    testLighting = testLighting.copy(label = "lighting update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Lighting, testLighting)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid lighting updated event: $fault")

  def listLightings: Unit =
    val list = ListEntities(testAccount.license, EntityType.Lighting, testLighting.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testLighting
      case fault => fail(s"Invalid lighting listed event: $fault")

  def addSewage: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Sewage, testSewage)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testSewage = testSewage.copy(id = id)
      case fault => fail(s"Invalid sewage added event: $fault")

  def updateSewage: Unit =
    testSewage = testSewage.copy(label = "sewage update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Sewage, testSewage)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid sewage updated event: $fault")

  def listSewages: Unit =
    val list = ListEntities(testAccount.license, EntityType.Sewage, testSewage.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testSewage
      case fault => fail(s"Invalid sewage listed event: $fault")

  def addWell: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Well, testWell)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testWell = testWell.copy(id = id)
      case fault => fail(s"Invalid well added event: $fault")

  def updateWell: Unit =
    testWell = testWell.copy(label = "well update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Well, testWell)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid well updated event: $fault")

  def listWells: Unit =
    val list = ListEntities(testAccount.license, EntityType.Well, testWell.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testWell
      case fault => fail(s"Invalid well listed event: $fault")

  def addWater: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Water, testWater)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testWater = testWater.copy(id = id)
      case fault => fail(s"Invalid water added event: $fault")

  def updateWater: Unit =
    testWater = testWater.copy(label = "water update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Water, testWater)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid water updated event: $fault")

  def listWaters: Unit =
    val list = ListEntities(testAccount.license, EntityType.Water, testWater.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testWater
      case fault => fail(s"Invalid water listed event: $fault")

  def addWaterHeater: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.WaterHeater, testWaterHeater)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testWaterHeater = testWaterHeater.copy(id = id)
      case fault => fail(s"Invalid water heater added event: $fault")

  def updateWaterHeater: Unit =
    testWaterHeater = testWaterHeater.copy(label = "water heater update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.WaterHeater, testWaterHeater)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid water heater updated event: $fault")

  def listWaterHeaters: Unit =
    val list = ListEntities(testAccount.license, EntityType.WaterHeater, testWaterHeater.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testWaterHeater
      case fault => fail(s"Invalid water heater listed event: $fault")

  def addLawn: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Lawn, testLawn)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testLawn = testLawn.copy(id = id)
      case fault => fail(s"Invalid lawn added event: $fault")

  def updateLawn: Unit =
    testLawn = testLawn.copy(label = "lawn update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Lawn, testLawn)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid lawn updated event: $fault")

  def listLawns: Unit =
    val list = ListEntities(testAccount.license, EntityType.Lawn, testLawn.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testLawn
      case fault => fail(s"Invalid lawn listed event: $fault")

  def addGarden: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Garden, testGarden)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testGarden = testGarden.copy(id = id)
      case fault => fail(s"Invalid garden added event: $fault")

  def updateGarden: Unit =
    testGarden = testGarden.copy(label = "garden update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Garden, testGarden)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid garden updated event: $fault")

  def listGardens: Unit =
    val list = ListEntities(testAccount.license, EntityType.Garden, testGarden.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testGarden
      case fault => fail(s"Invalid garden listed event: $fault")

  def addSprinkler: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Sprinkler, testSprinkler)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testSprinkler = testSprinkler.copy(id = id)
      case fault => fail(s"Invalid sprinkler added event: $fault")

  def updateSprinkler: Unit =
    testSprinkler = testSprinkler.copy(label = "sprinkler update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Sprinkler, testSprinkler)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid sprinkler updated event: $fault")

  def listSprinklers: Unit =
    val list = ListEntities(testAccount.license, EntityType.Sprinkler, testSprinkler.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testSprinkler
      case fault => fail(s"Invalid sprinkler listed event: $fault")

  def addShed: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Shed, testShed)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testShed = testShed.copy(id = id)
      case fault => fail(s"Invalid shed added event: $fault")

  def updateShed: Unit =
    testShed = testShed.copy(label = "shed update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Shed, testShed)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid shed updated event: $fault")

  def listSheds: Unit =
    val list = ListEntities(testAccount.license, EntityType.Shed, testShed.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testShed
      case fault => fail(s"Invalid shed listed event: $fault")

  def addSolarPanel: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.SolarPanel, testSolarPanel)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testSolarPanel = testSolarPanel.copy(id = id)
      case fault => fail(s"Invalid solar panel added event: $fault")

  def updateSolarPanel: Unit =
    testSolarPanel = testSolarPanel.copy(label = "solar panel update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.SolarPanel, testSolarPanel)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid solar panel updated event: $fault")

  def listSolarPanels: Unit =
    val list = ListEntities(testAccount.license, EntityType.SolarPanel, testSolarPanel.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testSolarPanel
      case fault => fail(s"Invalid solar panel listed event: $fault")

  def addPorch: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Porch, testPorch)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testPorch = testPorch.copy(id = id)
      case fault => fail(s"Invalid porch added event: $fault")

  def updatePorch: Unit =
    testPorch = testPorch.copy(label = "porch update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Porch, testPorch)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid porch updated event: $fault")

  def listPorches: Unit =
    val list = ListEntities(testAccount.license, EntityType.Porch, testPorch.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testPorch
      case fault => fail(s"Invalid porch listed event: $fault")

  def addPatio: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Patio, testPatio)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testPatio = testPatio.copy(id = id)
      case fault => fail(s"Invalid patio added event: $fault")

  def updatePatio: Unit =
    testPatio = testPatio.copy(label = "patio update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Patio, testPatio)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid patio updated event: $fault")

  def listPatios: Unit =
    val list = ListEntities(testAccount.license, EntityType.Patio, testPatio.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testPatio
      case fault => fail(s"Invalid patio listed event: $fault")

  def addPool: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Pool, testPool)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testPool = testPool.copy(id = id)
      case fault => fail(s"Invalid pool added event: $fault")

  def updatePool: Unit =
    testPool = testPool.copy(label = "pool update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Pool, testPool)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid pool updated event: $fault")

  def listPools: Unit =
    val list = ListEntities(testAccount.license, EntityType.Pool, testPool.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testPool
      case fault => fail(s"Invalid pool listed event: $fault")

  def addDock: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Dock, testDock)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testDock = testDock.copy(id = id)
      case fault => fail(s"Invalid dock added event: $fault")

  def updateDock: Unit =
    testDock = testDock.copy(label = "dock update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Dock, testDock)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid dock updated event: $fault")

  def listDocks: Unit =
    val list = ListEntities(testAccount.license, EntityType.Dock, testDock.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testDock
      case fault => fail(s"Invalid dock listed event: $fault")

  def addGazebo: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Gazebo, testGazebo)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testGazebo = testGazebo.copy(id = id)
      case fault => fail(s"Invalid gazebo added event: $fault")

  def updateGazebo: Unit =
    testGazebo = testGazebo.copy(label = "gazebo update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Gazebo, testGazebo)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid gazebo updated event: $fault")

  def listGazebos: Unit =
    val list = ListEntities(testAccount.license, EntityType.Gazebo, testGazebo.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testGazebo
      case fault => fail(s"Invalid gazebo listed event: $fault")

  def addMailbox: Unit =
    val addEntity = AddEntity(testAccount.license, EntityType.Mailbox, testMailbox)
    dispatcher.dispatch(addEntity) match
      case EntityAdded(id) =>
        id > 0 shouldBe true
        testMailbox = testMailbox.copy(id = id)
      case fault => fail(s"Invalid mailbox added event: $fault")

  def updateMailbox: Unit =
    testMailbox = testMailbox.copy(label = "mailbox update")
    val updateEntity = UpdateEntity(testAccount.license, EntityType.Mailbox, testMailbox)
    dispatcher.dispatch(updateEntity) match
      case EntityUpdated(count) => count shouldBe 1
      case fault => fail(s"Invalid mailbox updated event: $fault")

  def listMailboxes: Unit =
    val list = ListEntities(testAccount.license, EntityType.Mailbox, testMailbox.id)
    dispatcher.dispatch(list) match
      case EntitiesListed(list) =>
        list.length shouldBe 1
        list.head shouldBe testMailbox
      case fault => fail(s"Invalid mailbox listed event: $fault")

  def fault: Unit =
    val fault = Fault("test error message")
    store.addFault(fault) shouldBe fault
    store.listFaults().length shouldBe 1