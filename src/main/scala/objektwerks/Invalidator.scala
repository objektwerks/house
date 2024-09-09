package objektwerks

import scala.collection.mutable

opaque type Field = String
object Field:
  def apply(value: String): Field = value

opaque type Message = String
object Message:
  def apply(value: String): Message = value

final class Invalidator:
  private val invalidations = mutable.Map[Field, Message]()

  private def add(field: Field, message: Message): Invalidator =
    invalidations += field -> message
    this

  def isValid: Boolean = invalidations.isEmpty

  def isInvalid: Boolean = !invalidations.isEmpty

  def count: Int = invalidations.size

  def asList: List[String] = invalidations.map { (_, message) => s"$message" }.toList

  def asMap: Map[Field, Message] = invalidations.toMap

  def asString(separator: String = ","): String = asList.mkString(separator)

  def invalidate(isInvalidExpr: Boolean)(field: Field, message: Message): Invalidator =
    if isInvalidExpr then add(field, message)
    else this

  def invalidate(isInvalidFn: () => Boolean)(field: Field, message: Message): Invalidator =
    if isInvalidFn() then add(field, message)
    else this