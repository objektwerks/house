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