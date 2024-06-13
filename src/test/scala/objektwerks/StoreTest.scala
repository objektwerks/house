package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt
import scala.sys.process.Process

final class StoreTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d house -f ddl.sql").run().exitValue()
  exitCode shouldBe 0

  val config = ConfigFactory.load("test.conf")

  val store = Store(config, Store.cache(initialSize = 1, maxSize = 1, expireAfter = 1.hour))

  var testAccount = Account(email = "your@email.com")
  var testHouse = House(accountId = 0, location = "100 Rocky Way")

  test("store"):
    register()
    login()
    isAuthorized()

    addHouse()
    updateHouse()
    listHouses()

    updateFoundation( addFoundation() )
    listFoundations()

    updateFrame( addFrame() )
    listFrames()

    updateAttic( addAttic() )
    listAttics()

    updateInsulation( addInsulation() )
    listInsulations()

    updateDuctwork( addDuctwork() )
    listDuctworks()

    updateVentilation( addVentilation() )
    listVentilations()

    updateRoof( addRoof() )
    listRoofs()

    updateChimney( addChimney() )
    listChimneys()

    updateBalcony( addBalcony() )
    listBalconys()

    updateDrywall( addDrywall() )
    listDrywalls()

    updateRoom( addRoom() )
    listRooms()

    updateDriveway( addDriveway() )
    listDriveways()

    updateGarage( addGarage() )
    listGarages()

    updateSiding( addSiding() )
    listSidings()

    updateGutter( addGutter() )
    listGutters()

    updateSoffit( addSoffit() )
    listSoffits()

    updateWindow( addWindow() )
    listWindows()

    updateDoor( addDoor() )
    listDoors()

    updatePlumbing( addPlumbing() )
    listPlumbings()

    updateElectrical( addElectrical() )
    listElectricals()

  def register(): Unit =
    val id = store.register(testAccount)
    id > 0 shouldBe true
    testAccount = testAccount.copy(id = id)

  def login(): Unit =
    val optionalAccount = store.login(testAccount.email, testAccount.pin)
    optionalAccount shouldBe Some(testAccount)

  def isAuthorized(): Unit =
    store.isAuthorized(testAccount.license) shouldBe true

  def addHouse(): Unit =
    testHouse = testHouse.copy(accountId = testAccount.id)
    val id = store.addHouse(testHouse)
    id > 0 shouldBe true
    testHouse = testHouse.copy(id = id)

  def updateHouse(): Unit =
    testHouse = testHouse.copy(location = "100 Rocky Road")
    store.updateHouse(testHouse) shouldBe 1

  def listHouses(): Unit =
    store.listHouses(testHouse.accountId).length shouldBe 1

  def addFoundation(): Foundation =
    val add = Foundation(houseId = testHouse.id)
    val id = store.addFoundation(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateFoundation(foundation: Foundation): Unit =
    store.updateFoundation( foundation.copy(typeof = FoundationType.crawl) ) shouldBe 1

  def listFoundations(): Unit =
    store.listFoundations(testHouse.id).length shouldBe 1

  def addFrame(): Frame =
    val add = Frame(houseId = testHouse.id)
    val id = store.addFrame(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateFrame(update: Frame): Unit =
    store.updateFrame( update.copy(typeof = FrameType.steel) ) shouldBe 1

  def listFrames(): Unit =
    store.listFrames(testHouse.id).length shouldBe 1

  def addAttic(): Attic =
    val add = Attic(houseId = testHouse.id)
    val id = store.addAttic(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateAttic(update: Attic): Unit =
    store.updateAttic( update.copy(typeof = AtticType.unfinished) ) shouldBe 1

  def listAttics(): Unit =
    store.listAttics(testHouse.id).length shouldBe 1

  def addInsulation(): Insulation =
    val add = Insulation(houseId = testHouse.id)
    val id = store.addInsulation(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateInsulation(update: Insulation): Unit =
    store.updateInsulation( update.copy(typeof = InsulationType.foam) ) shouldBe 1

  def listInsulations(): Unit =
    store.listInsulations(testHouse.id).length shouldBe 1

  def addDuctwork(): Ductwork =
    val add = Ductwork(houseId = testHouse.id)
    val id = store.addDuctwork(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateDuctwork(update: Ductwork): Unit =
    store.updateDuctwork( update.copy(typeof = DuctworkType.flexible) ) shouldBe 1

  def listDuctworks(): Unit =
    store.listDuctworks(testHouse.id).length shouldBe 1

  def addVentilation(): Ventilation =
    val add = Ventilation(houseId = testHouse.id)
    val id = store.addVentilation(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateVentilation(update: Ventilation): Unit =
    store.updateVentilation( update.copy(typeof = VentilationType.exhaust) ) shouldBe 1

  def listVentilations(): Unit =
    store.listVentilations(testHouse.id).length shouldBe 1

  def addRoof(): Roof =
    val add = Roof(houseId = testHouse.id)
    val id = store.addRoof(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateRoof(update: Roof): Unit =
    store.updateRoof( update.copy(typeof = RoofType.shingle) ) shouldBe 1

  def listRoofs(): Unit =
    store.listRoofs(testHouse.id).length shouldBe 1

  def addChimney(): Chimney =
    val add = Chimney(houseId = testHouse.id)
    val id = store.addChimney(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateChimney(update: Chimney): Unit =
    store.updateChimney( update.copy(typeof = ChimneyType.metal) ) shouldBe 1

  def listChimneys(): Unit =
    store.listChimneys(testHouse.id).length shouldBe 1

  def addBalcony(): Balcony =
    val add = Balcony(houseId = testHouse.id)
    val id = store.addBalcony(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateBalcony(update: Balcony): Unit =
    store.updateBalcony( update.copy(typeof = BalconyType.juliet) ) shouldBe 1

  def listBalconys(): Unit =
    store.listBalconys(testHouse.id).length shouldBe 1

  def addDrywall(): Drywall =
    val add = Drywall(houseId = testHouse.id)
    val id = store.addDrywall(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateDrywall(update: Drywall): Unit =
    store.updateDrywall( update.copy(typeof = DrywallType.specialty) ) shouldBe 1

  def listDrywalls(): Unit =
    store.listDrywalls(testHouse.id).length shouldBe 1

  def addRoom(): Room =
    val add = Room(houseId = testHouse.id)
    val id = store.addRoom(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateRoom(update: Room): Unit =
    store.updateRoom( update.copy(typeof = RoomType.bathroom) ) shouldBe 1

  def listRooms(): Unit =
    store.listRooms(testHouse.id).length shouldBe 1

  def addDriveway(): Driveway =
    val add = Driveway(houseId = testHouse.id)
    val id = store.addDriveway(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateDriveway(update: Driveway): Unit =
    store.updateDriveway( update.copy(typeof = DrivewayType.asphalt) ) shouldBe 1

  def listDriveways(): Unit =
    store.listDriveways(testHouse.id).length shouldBe 1

  def addGarage(): Garage =
    val add = Garage(houseId = testHouse.id)
    val id = store.addGarage(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateGarage(update: Garage): Unit =
    store.updateGarage( update.copy(typeof = GarageType.detached) ) shouldBe 1

  def listGarages(): Unit =
    store.listGarages(testHouse.id).length shouldBe 1

  def addSiding(): Siding =
    val add = Siding(houseId = testHouse.id)
    val id = store.addSiding(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateSiding(update: Siding): Unit =
    store.updateSiding( update.copy(typeof = SidingType.clapboard) ) shouldBe 1

  def listSidings(): Unit =
    store.listSidings(testHouse.id).length shouldBe 1

  def addGutter(): Gutter =
    val add = Gutter(houseId = testHouse.id)
    val id = store.addGutter(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateGutter(update: Gutter): Unit =
    store.updateGutter( update.copy(typeof = GutterType.aluminum) ) shouldBe 1

  def listGutters(): Unit =
    store.listGutters(testHouse.id).length shouldBe 1

  def addSoffit(): Soffit =
    val add = Soffit(houseId = testHouse.id)
    val id = store.addSoffit(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateSoffit(update: Soffit): Unit =
    store.updateSoffit( update.copy(typeof = SoffitType.aluminum) ) shouldBe 1

  def listSoffits(): Unit =
    store.listSoffits(testHouse.id).length shouldBe 1

  def addWindow(): Window =
    val add = Window(houseId = testHouse.id)
    val id = store.addWindow(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateWindow(update: Window): Unit =
    store.updateWindow( update.copy(typeof = WindowType.bay) ) shouldBe 1

  def listWindows(): Unit =
    store.listWindows(testHouse.id).length shouldBe 1

  def addDoor(): Door =
    val add = Door(houseId = testHouse.id)
    val id = store.addDoor(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateDoor(update: Door): Unit =
    store.updateDoor( update.copy(typeof = DoorType.glass) ) shouldBe 1

  def listDoors(): Unit =
    store.listDoors(testHouse.id).length shouldBe 1

  def addPlumbing(): Plumbing =
    val add = Plumbing(houseId = testHouse.id)
    val id = store.addPlumbing(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updatePlumbing(update: Plumbing): Unit =
    store.updatePlumbing( update.copy(typeof = PlumbingType.pex) ) shouldBe 1

  def listPlumbings(): Unit =
    store.listPlumbings(testHouse.id).length shouldBe 1

  def addElectrical(): Electrical =
    val add = Electrical(houseId = testHouse.id)
    val id = store.addElectrical(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateElectrical(update: Electrical): Unit =
    store.updateElectrical( update.copy(typeof = ElectricalType.romex) ) shouldBe 1

  def listElectricals(): Unit =
    store.listElectricals(testHouse.id).length shouldBe 1

  def addFusebox(): Fusebox =
    val add = Fusebox(houseId = testHouse.id)
    val id = store.addFusebox(add)
    id > 0 shouldBe true
    add.copy(id = id)

  def updateFusebox(update: Fusebox): Unit =
    store.updateFusebox( update.copy(typeof = FuseboxType.typeS) ) shouldBe 1