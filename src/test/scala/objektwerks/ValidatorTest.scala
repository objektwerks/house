package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

object Person:
  import Validator.*

  val nameField = Field("Name")
  val ageField = Field("Age")

  val nameMessage = Message("must be non empty.")
  val ageMessage = Message("must be greater than 1.")

  opaque type Name <: String = String
  object Name:
    def apply(value: String): Name = value

  opaque type Age <: Int = Int
  object Age:
    def apply(value: Int): Age = value

import Person.*

final case class Person(name: Name, age: Age)

extension (person: Person)
  def validate: Validator =
    Validator()
      .validate(person.name.nonEmpty)(nameField, nameMessage)
      .validate(person.age > 0)(ageField, ageMessage)

final class ValidatorTest extends AnyFunSuite with Matchers:
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