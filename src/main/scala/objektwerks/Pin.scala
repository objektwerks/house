package objektwerks

import scala.util.Random

object Pin:
  private val specialChars = "~!@#$%^&*-+=<>?/:;".toList
  private val random = Random

  private def newSpecialChar: Char = specialChars( random.nextInt(specialChars.length) )

  def newInstance: String =
    Random
      .alphanumeric
      .take(5)
      .mkString
      .prepended(newSpecialChar)
      .appended(newSpecialChar)
