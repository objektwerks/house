package objektwerks

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.{Failure, Random, Success, Try}

object Pin:
  private val chars = "abcdefghijklmnopqrstuvwxyz"
  private val specialChars = "~!@#$%^&*-+=<>?/:;"
  private val random = Random

  private def newChar: Char = chars( random.nextInt(chars.length) )
  private def newSpecialChar: Char = specialChars( random.nextInt(specialChars.length) )

  @tailrec
  private def retry[T](attempts: Int)(fn: => T): T =
    Try( fn ) match
      case Success(result) => result
      case Failure(error) => if attempts >= 1 then retry(attempts - 1)(fn) else throw error

  def build: String =
    val buffer = mutable.ArrayBuffer.empty[Char]

    buffer += newSpecialChar
    buffer += newChar.toUpper
    buffer += newChar.toLower
    buffer += newChar.toUpper
    buffer += newChar.toLower
    buffer += newChar.toUpper
    buffer += newSpecialChar

    val pin = buffer.mkString
    if pin.length < 7 then throw Exception(pin) else pin

  def newInstance: String = retry(3)(build)