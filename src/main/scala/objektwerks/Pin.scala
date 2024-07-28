package objektwerks

import java.util.concurrent.ThreadLocalRandom

import scala.collection.mutable
import scala.util.Random

object Pin:
  private val numbers = "0123456789"
  private val chars = "abcdefghijklmnopqrstuvwxyz"
  private val specialChars = "~!@#$%^&*-+=<>?/:;"
  private val shuffler = Random

  private def newNumber(random: ThreadLocalRandom): Char = numbers( random.nextInt(numbers.length).toChar )
  private def newChar(random: ThreadLocalRandom): Char = chars( random.nextInt(chars.length) )
  private def newSpecialChar(random: ThreadLocalRandom): Char = specialChars( random.nextInt(specialChars.length) )

  def newInstance: String =
    val random = ThreadLocalRandom.current()
    val pin = mutable.ArrayBuffer[Char]()

    pin += newNumber(random)
    pin += newChar(random).toUpper
    pin += newChar(random).toLower
    pin += newSpecialChar(random)
    pin += newChar(random).toLower
    pin += newChar(random).toUpper
    pin += newSpecialChar(random)

    shuffler.shuffle(pin).mkString