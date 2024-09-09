package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

object Person:
  import Invalidator.*

  val nameField = Field("Name")
  val ageField = Field("Age")

  val nameMessage = Message("Name is less than 1 character.")
  val ageMessage = Message("Age is less than 1.")

  opaque type Name <: String = String
  object Name:
    def apply(value: String): Name = value

  opaque type Age <: Int = Int
  object Age:
    def apply(value: Int): Age = value

final case class Person(name: String, age: Int)

import Person.*
extension (person: Person)
  def invalidate: Invalidator =
    Invalidator()
      .invalidate(person.name.isEmpty)(nameField, nameMessage)
      .invalidate(person.age < 1)(ageField, ageMessage)

final class InvalidatorTest extends AnyFunSuite with Matchers:
  import Person.*

  test("valid"):
    val person = Person(Name("Fred Flintsone"), Age(28))
    val invalidations = person.invalidate
    invalidations.isValid shouldBe true

  test("invalid"):
    val person = Person(Name(""), Age(0))
    val invalidations = person.invalidate
    invalidations.isInvalid shouldBe true
    invalidations.count shouldBe 2

    println( invalidations.asList )
    println( invalidations.asMap )
    println( invalidations.asString )