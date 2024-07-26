package objektwerks

import scala.collection.mutable
import scala.util.Random

object Pin:
  private val chars = "abcdefghijklmnopqrstuvwxyz"
  private val specialChars = "~!@#$%^&*-+=<>?/:;"
  private val random = Random

  private def newChar: Char = chars( random.nextInt(chars.length) )
  private def newSpecialChar: Char = specialChars( random.nextInt(specialChars.length) )

  def newInstance: String =
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