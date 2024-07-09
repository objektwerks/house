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
  val cache = Store.cache(config)
  val datasource = Store.datasource(config)
  val store = Store(cache, datasource)
  val emailer = Emailer(config)
  val handler = Handler(store, emailer)
  val dispatcher = Dispatcher(handler)

  var testAccount = Account()
  var testHouse = House(accountId = 0, location = "100 Rocky Way")
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

  test("store"):
    register
    login

    addHouse
    updateHouse
    listHouses

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
    testHouse = testHouse.copy(location = "200 Softy Road")
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
