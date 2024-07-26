package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

final class PinTest extends AnyFunSuite with Matchers:
  test("pin"):
    val pins = mutable.Set.empty[String]
    for i <- 1 to 10_000
    do
      val pin = Pin.newInstance
      pin.length shouldBe 7
      pins += pin
    pins.size shouldBe 10_000