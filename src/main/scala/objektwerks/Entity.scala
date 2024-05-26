package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import java.time.LocalDate
import java.util.UUID

sealed trait Entity:
  val id: Long

object Entity:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity](CodecMakerConfig.withDiscriminatorFieldName(None))

  def now: String = LocalDate.now.toString
  def localDate(now: String): LocalDate = if now.nonEmpty then LocalDate.parse(now) else LocalDate.now

final case class Account(id: Long = 0,
                         license: String = UUID.randomUUID.toString,
                         email: String = "",
                         pin: String = Pin.newInstance,
                         activated: String = Entity.now) extends Entity

object Account:
  val empty = Account(license = "", email = "", pin = "")