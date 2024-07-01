package objektwerks

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.sys.process.Process

final class DispatcherTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d house -f ddl.sql").run().exitValue()
  exitCode shouldBe 0

  val config = ConfigFactory.load("test.conf")
  val cache = Store.cache(config)
  val datasource = Store.datasource(config)
  val store = Store(cache, datasource)

  var testAccount = Account(email = "your@email.com")
  var testHouse = House(accountId = 0, location = "100 Rocky Way")

  test("store"):
    true
