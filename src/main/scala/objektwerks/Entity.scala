package objektwerks

import java.time.LocalDate

sealed trait Entity:
  val id: Long

object Entity:
  def now: String = LocalDate.now.toString
  def localDate(now: String): LocalDate = if now.nonEmpty then LocalDate.parse(now) else LocalDate.now