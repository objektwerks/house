package objektwerks

import scala.collection.mutable
import scala.util.Random

object Pin:
  private val numbers = "0123456789"
  private val chars = "abcdefghijklmnopqrstuvwxyz"
  private val specialChars = "~!@#$%^&*-+=<>?/:;"
  private val random = Random

  private def newNumber: Char = numbers( random.nextInt(numbers.length).toChar )
  private def newChar: Char = chars( random.nextInt(chars.length) )
  private def newSpecialChar: Char = specialChars( random.nextInt(specialChars.length) )

  def newInstance: String =
    val pin = mutable.ArrayBuffer[Char]()

    pin += newNumber
    pin += newChar.toUpper
    pin += newChar.toLower
    pin += newChar.toUpper
    pin += newChar.toLower
    pin += newChar.toUpper
    pin += newSpecialChar

    random.shuffle(pin).mkString