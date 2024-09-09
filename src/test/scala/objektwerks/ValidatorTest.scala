package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

object Person:
  import Validator.*

  val nameField = Field("Name")
  val ageField = Field("Age")

  val nameMessage = Message("Name must be non empty.")
  val ageMessage = Message("Age must be greater than 1.")

  opaque type Name <: String = String
  object Name:
    def apply(value: String): Name = value

  opaque type Age <: Int = Int
  object Age:
    def apply(value: Int): Age = value

final case class Person(name: String, age: Int)

import Person.*
extension (person: Person)
  def validate: Validator =
    Validator()
      .validate(person.name.isEmpty)(nameField, nameMessage)
      .validate(person.age < 1)(ageField, ageMessage)

final class InvalidatorTest extends AnyFunSuite with Matchers:
  import Person.*

  test("valid"):
    val person = Person(Name("Fred Flintsone"), Age(28))
    val validator = person.validate
    validator.isValid shouldBe true

  test("invalid"):
    val person = Person(Name(""), Age(0))
    val validator = person.validate
    validator.isInvalid shouldBe true
    validator.count shouldBe 2

    println( validator.asList )
    println( validator.asMap )
    println( validator.asString )