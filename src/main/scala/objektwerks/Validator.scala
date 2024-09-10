package objektwerks

import scala.collection.mutable

object Validator:
  opaque type Field = String
  object Field:
    def apply(value: String): Field = value

  opaque type Message = String
  object Message:
    def apply(value: String): Message = value

final class Validator:
  import Validator.*

  private val invalidations = mutable.Map[Field, Message]()

  private def add(field: Field, message: Message): Validator =
    invalidations += field -> message
    this

  def isValid: Boolean = invalidations.isEmpty

  def isInvalid: Boolean = !invalidations.isEmpty

  def count: Int = invalidations.size

  def asList: List[String] = invalidations.map { (field, message) => s"[$field] $message" }.toList

  def asMap: Map[Field, Message] = invalidations.toMap

  def asString(separator: String = ","): String = asList.mkString(separator)

  def validate(expr: Boolean)(field: Field, message: Message): Validator =
    if expr then this
    else add(field, message)

  def validate(fn: () => Boolean)(field: Field, message: Message): Validator =
    if fn() then this
    else add(field, message)

  def validate(validator: Validator): Validator =
    invalidations ++= validator.asMap
    this