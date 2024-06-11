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

    isAuthorized()
    updateHouse()

    isAuthorized()
    listHouses()

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
    val count = store.updateHouse(testHouse)
    count == 1 shouldBe true

  def listHouses(): Unit =
    store.listHouses(testHouse.accountId).length shouldBe 1

  def addFoundation(): Foundation =
    val foundation = Foundation(houseId = testHouse.id)
    val id = store.addFoundation(foundation)
    id > 0 shouldBe true
    foundation

  def updateFoundation(foundation: Foundation): Unit =
    val update = foundation.copy(typeof = FoundationType.crawl)
    val count = store.updateFoundation(update)
    count == 1 shouldBe true

  def listFoundations(): Unit =
    store.listFoundations(testHouse.id).length shouldBe 1