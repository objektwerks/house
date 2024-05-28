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

 // Structure

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

final case class Balcony(id: Long = 0,
                         homeId: Long,
                         kind: String,
                         built: String = Entity.now) extends Entity

object Balcony:
  given JsonValueCodec[Balcony] = JsonCodecMaker.make[Balcony]
  given Ordering[Balcony] = Ordering.by[Balcony, String](balcony => balcony.built).reverse

final case class Drywall(id: Long = 0,
                         homeId: Long,
                         kind: String,
                         built: String = Entity.now) extends Entity

object Drywall:
  given JsonValueCodec[Drywall] = JsonCodecMaker.make[Drywall]
  given Ordering[Drywall] = Ordering.by[Drywall, String](drywall => drywall.built).reverse

final case class Room(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity

object Room:
  given JsonValueCodec[Room] = JsonCodecMaker.make[Room]
  given Ordering[Room] = Ordering.by[Room, String](room => room.built).reverse

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

object Patio:
  given JsonValueCodec[Patio] = JsonCodecMaker.make[Patio]
  given Ordering[Patio] = Ordering.by[Patio, String](patio => patio.built).reverse

final case class Pool(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity

object Pool:
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]
  given Ordering[Pool] = Ordering.by[Pool, String](pool => pool.built).reverse

// Internal

final case class Siding(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Siding:
  given JsonValueCodec[Siding] = JsonCodecMaker.make[Siding]
  given Ordering[Siding] = Ordering.by[Siding, String](siding => siding.built).reverse

final case class Gutter(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Gutter:
  given JsonValueCodec[Gutter] = JsonCodecMaker.make[Gutter]
  given Ordering[Gutter] = Ordering.by[Gutter, String](gutter => gutter.built).reverse

final case class Soffit(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Soffit:
  given JsonValueCodec[Soffit] = JsonCodecMaker.make[Soffit]
  given Ordering[Soffit] = Ordering.by[Soffit, String](soffit => soffit.built).reverse

final case class Window(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Window:
  given JsonValueCodec[Window] = JsonCodecMaker.make[Window]
  given Ordering[Window] = Ordering.by[Window, String](window => window.built).reverse

final case class Door(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity

object Door:
  given JsonValueCodec[Door] = JsonCodecMaker.make[Door]
  given Ordering[Door] = Ordering.by[Door, String](door => door.built).reverse

final case class Plumbing(id: Long = 0,
                          homeId: Long,
                          kind: String,
                          installed: String = Entity.now) extends Entity

object Plumbing:
  given JsonValueCodec[Plumbing] = JsonCodecMaker.make[Plumbing]
  given Ordering[Plumbing] = Ordering.by[Plumbing, String](plumbing => plumbing.installed).reverse

final case class Electrical(id: Long = 0,
                            homeId: Long,
                            kind: String,
                            installed: String = Entity.now) extends Entity

object Electrical:
  given JsonValueCodec[Electrical] = JsonCodecMaker.make[Electrical]
  given Ordering[Electrical] = Ordering.by[Electrical, String](electrical => electrical.installed).reverse

final case class Fusebox(id: Long = 0,
                         homeId: Long,
                         kind: String,
                         installed: String = Entity.now) extends Entity

object Fusebox:
  given JsonValueCodec[Fusebox] = JsonCodecMaker.make[Fusebox]
  given Ordering[Fusebox] = Ordering.by[Fusebox, String](fusebox => fusebox.installed).reverse

final case class Alarm(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       installed: String = Entity.now) extends Entity

object Alarm:
  given JsonValueCodec[Alarm] = JsonCodecMaker.make[Alarm]
  given Ordering[Alarm] = Ordering.by[Alarm, String](alarm => alarm.installed).reverse

final case class Heater(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        installed: String = Entity.now) extends Entity

object Heater:
  given JsonValueCodec[Heater] = JsonCodecMaker.make[Heater]
  given Ordering[Heater] = Ordering.by[Heater, String](heater => heater.installed).reverse

final case class AirConditioner(id: Long = 0,
                                homeId: Long,
                                kind: String,
                                installed: String = Entity.now) extends Entity

object AirConditioner:
  given JsonValueCodec[AirConditioner] = JsonCodecMaker.make[AirConditioner]
  given Ordering[AirConditioner] = Ordering.by[AirConditioner, String](ac => ac.installed).reverse

final case class Floor(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       installed: String = Entity.now) extends Entity

object Floor:
  given JsonValueCodec[Floor] = JsonCodecMaker.make[Floor]
  given Ordering[Floor] = Ordering.by[Floor, String](floor => floor.installed).reverse

final case class Lighting(id: Long = 0,
                          homeId: Long,
                          kind: String,
                          installed: String = Entity.now) extends Entity

object Lighting:
  given JsonValueCodec[Lighting] = JsonCodecMaker.make[Lighting]
  given Ordering[Lighting] = Ordering.by[Lighting, String](lighting => lighting.installed).reverse

// External

final case class Sewage(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Sewage:
  given JsonValueCodec[Sewage] = JsonCodecMaker.make[Sewage]
  given Ordering[Sewage] = Ordering.by[Sewage, String](sewage => sewage.built).reverse

final case class Septic(id: Long = 0,
                        homeId: Long,
                        kind: String,
                        built: String = Entity.now) extends Entity

object Septic:
  given JsonValueCodec[Septic] = JsonCodecMaker.make[Septic]
  given Ordering[Septic] = Ordering.by[Septic, String](septic => septic.built).reverse

final case class Well(id: Long = 0,
                      homeId: Long,
                      kind: String,
                      built: String = Entity.now) extends Entity

object Well:
  given JsonValueCodec[Well] = JsonCodecMaker.make[Well]
  given Ordering[Well] = Ordering.by[Well, String](well => well.built).reverse

final case class Water(id: Long = 0,
                       homeId: Long,
                       kind: String,
                       installed: String = Entity.now) extends Entity

object Water:
  given JsonValueCodec[Water] = JsonCodecMaker.make[Water]
  given Ordering[Water] = Ordering.by[Water, String](water => water.installed).reverse

final case class WaterMain(id: Long = 0,
                           homeId: Long,
                           kind: String,
                           installed: String = Entity.now) extends Entity

object WaterMain:
  given JsonValueCodec[WaterMain] = JsonCodecMaker.make[WaterMain]
  given Ordering[WaterMain] = Ordering.by[WaterMain, String](main => main.installed).reverse

final case class WaterHeater(id: Long = 0,
                             homeId: Long,
                             kind: String,
                             installed: String = Entity.now) extends Entity