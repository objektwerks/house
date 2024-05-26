package objektwerks

import java.time.LocalDate
import java.util.UUID

sealed trait Entity:
  val id: Long

object Entity:
  def now: String = LocalDate.now.toString
  def localDate(now: String): LocalDate = if now.nonEmpty then LocalDate.parse(now) else LocalDate.now

final case class Account(id: Long = 0,
                         license: String = UUID.randomUUID.toString,
                         email: String = "",
                         pin: String = Pin.newInstance,
                         activated: String = Entity.now) extends Entity