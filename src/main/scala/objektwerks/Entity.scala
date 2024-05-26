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
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]

  val empty = Account(license = "", email = "", pin = "")

final case class Home(id: Long = 0,
                      accountId: Long,
                      location: String,
                      built: String = Entity.now) extends Entity

object Home:
  given JsonValueCodec[Home] = JsonCodecMaker.make[Home]
  given Ordering[Home] = Ordering.by[Home, String](home => home.built).reverse

final case class Driveway(id: Long = 0,
                          homeId: Long,
                          kind: String,
                          built: String = Entity.now) extends Entity

object Driveway:
  given JsonValueCodec[Driveway] = JsonCodecMaker.make[Driveway]
  given Ordering[Driveway] = Ordering.by[Driveway, String](driveway => driveway.built).reverse