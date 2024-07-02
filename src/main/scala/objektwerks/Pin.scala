package objektwerks

import scala.util.Random

object Pin:
  private val specialChars = "~!@#$%^&*-+=<>?/:;".toList
  private val random = Random

  private def newSpecialChar: Char = specialChars( random.nextInt(specialChars.length) )

  def newInstance: String =
    Random.shuffle(
      Random
        .alphanumeric
        .take(5)
        .prepended(newSpecialChar)
        .appended(newSpecialChar)
    ).mkString
