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

final case class Foundation(id: Long = 0,
                            homeId: Long,
                            kind: String,
                            built: String = Entity.now) extends Entity

object Foundation:
  given JsonValueCodec[Foundation] = JsonCodecMaker.make[Foundation]
  given Ordering[Foundation] = Ordering.by[Foundation, String](foundation => foundation.built).reverse

final case class Frame(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       built: String = Entity.now) extends Entity

object Frame:
  given JsonValueCodec[Frame] = JsonCodecMaker.make[Frame]
  given Ordering[Frame] = Ordering.by[Frame, String](frame => frame.built).reverse

final case class Attic(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       built: String = Entity.now) extends Entity

object Attic:
  given JsonValueCodec[Attic] = JsonCodecMaker.make[Attic]
  given Ordering[Attic] = Ordering.by[Attic, String](attic => attic.built).reverse

final case class Insulation(id: Long = 0,
                            homeId: Long,
                            kind: String,
                            installed: String = Entity.now) extends Entity

object Insulation:
  given JsonValueCodec[Insulation] = JsonCodecMaker.make[Insulation]
  given Ordering[Insulation] = Ordering.by[Insulation, String](insulation => insulation.installed).reverse

final case class Ventilation(id: Long = 0,
                             homeId: Long,
                             kind: String,
                             installed: String = Entity.now) extends Entity

object Ventilation:
  given JsonValueCodec[Ventilation] = JsonCodecMaker.make[Ventilation]
  given Ordering[Ventilation] = Ordering.by[Ventilation, String](insulation => insulation.installed).reverse

final case class Roof(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity

object Roof:
  given JsonValueCodec[Roof] = JsonCodecMaker.make[Roof]
  given Ordering[Roof] = Ordering.by[Roof, String](roof => roof.built).reverse

final case class Chimney(id: Long = 0,
                         homeId: Long,
                         kind: String,
                         built: String = Entity.now) extends Entity

object Chimney:
  given JsonValueCodec[Chimney] = JsonCodecMaker.make[Chimney]
  given Ordering[Chimney] = Ordering.by[Chimney, String](chimney => chimney.built).reverse

final case class Driveway(id: Long = 0,
                          homeId: Long,
                          kind: String,
                          built: String = Entity.now) extends Entity

object Driveway:
  given JsonValueCodec[Driveway] = JsonCodecMaker.make[Driveway]
  given Ordering[Driveway] = Ordering.by[Driveway, String](driveway => driveway.built).reverse

final case class Garage(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Garage:
  given JsonValueCodec[Garage] = JsonCodecMaker.make[Garage]
  given Ordering[Garage] = Ordering.by[Garage, String](garage => garage.built).reverse

final case class Porch(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       built: String = Entity.now) extends Entity

object Porch:
  given JsonValueCodec[Porch] = JsonCodecMaker.make[Porch]
  given Ordering[Porch] = Ordering.by[Porch, String](porch => porch.built).reverse

final case class Patio(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       built: String = Entity.now) extends Entity


final case class Pool(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity