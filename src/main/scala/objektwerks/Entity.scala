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

final case class House(id: Long = 0,
                       accountId: Long,
                       typeof: HouseType = HouseType.primary,
                       location: String,
                       built: String = Entity.now) extends Entity

object House:
  given JsonValueCodec[House] = JsonCodecMaker.make[House]
  given Ordering[House] = Ordering.by[House, String](home => home.built).reverse

 // Structure

final case class Foundation(id: Long = 0,
                            houseId: Long,
                            typeof: FoundationType = FoundationType.slab,
                            built: String = Entity.now) extends Entity

object Foundation:
  given JsonValueCodec[Foundation] = JsonCodecMaker.make[Foundation]
  given Ordering[Foundation] = Ordering.by[Foundation, String](foundation => foundation.built).reverse

final case class Frame(id: Long = 0,
                       houseId: Long,
                       typeof: FrameType = FrameType.platform,
                       built: String = Entity.now) extends Entity

object Frame:
  given JsonValueCodec[Frame] = JsonCodecMaker.make[Frame]
  given Ordering[Frame] = Ordering.by[Frame, String](frame => frame.built).reverse

final case class Attic(id: Long = 0,
                       houseId: Long,
                       typeof: AtticType = AtticType.scuttle,
                       built: String = Entity.now) extends Entity

object Attic:
  given JsonValueCodec[Attic] = JsonCodecMaker.make[Attic]
  given Ordering[Attic] = Ordering.by[Attic, String](attic => attic.built).reverse

final case class Insulation(id: Long = 0,
                            houseId: Long,
                            typeof: InsulationType = InsulationType.blanket,
                            installed: String = Entity.now) extends Entity

object Insulation:
  given JsonValueCodec[Insulation] = JsonCodecMaker.make[Insulation]
  given Ordering[Insulation] = Ordering.by[Insulation, String](insulation => insulation.installed).reverse

final case class Ductwork(id: Long = 0,
                          houseId: Long,
                          typeof: DuctworkType = DuctworkType.airduct,
                          installed: String = Entity.now) extends Entity

object Ductwork:
  given JsonValueCodec[Ductwork] = JsonCodecMaker.make[Ductwork]
  given Ordering[Ductwork] = Ordering.by[Ductwork, String](insulation => insulation.installed).reverse

final case class Ventilation(id: Long = 0,
                             houseId: Long,
                             typeof: VentilationType = VentilationType.balanced,
                             installed: String = Entity.now) extends Entity

object Ventilation:
  given JsonValueCodec[Ventilation] = JsonCodecMaker.make[Ventilation]
  given Ordering[Ventilation] = Ordering.by[Ventilation, String](insulation => insulation.installed).reverse

final case class Roof(id: Long = 0,
                      houseId: Long,
                      typeof: RoofType = RoofType.shingle,
                      built: String = Entity.now) extends Entity

object Roof:
  given JsonValueCodec[Roof] = JsonCodecMaker.make[Roof]
  given Ordering[Roof] = Ordering.by[Roof, String](roof => roof.built).reverse

final case class Chimney(id: Long = 0,
                         houseId: Long,
                         typeof: ChimneyType = ChimneyType.masonry,
                         built: String = Entity.now) extends Entity

object Chimney:
  given JsonValueCodec[Chimney] = JsonCodecMaker.make[Chimney]
  given Ordering[Chimney] = Ordering.by[Chimney, String](chimney => chimney.built).reverse

final case class Balcony(id: Long = 0,
                         houseId: Long,
                         typeof: BalconyType = BalconyType.cantilevered,
                         built: String = Entity.now) extends Entity

object Balcony:
  given JsonValueCodec[Balcony] = JsonCodecMaker.make[Balcony]
  given Ordering[Balcony] = Ordering.by[Balcony, String](balcony => balcony.built).reverse

final case class Drywall(id: Long = 0,
                         houseId: Long,
                         typeof: DrywallType = DrywallType.standard,
                         built: String = Entity.now) extends Entity

object Drywall:
  given JsonValueCodec[Drywall] = JsonCodecMaker.make[Drywall]
  given Ordering[Drywall] = Ordering.by[Drywall, String](drywall => drywall.built).reverse

final case class Room(id: Long = 0,
                      houseId: Long,
                      typeof: RoomType = RoomType.bedroom,
                      built: String = Entity.now) extends Entity

object Room:
  given JsonValueCodec[Room] = JsonCodecMaker.make[Room]
  given Ordering[Room] = Ordering.by[Room, String](room => room.built).reverse

final case class Driveway(id: Long = 0,
                          houseId: Long,
                          typeof: DrivewayType = DrivewayType.concrete,
                          built: String = Entity.now) extends Entity

object Driveway:
  given JsonValueCodec[Driveway] = JsonCodecMaker.make[Driveway]
  given Ordering[Driveway] = Ordering.by[Driveway, String](driveway => driveway.built).reverse

final case class Garage(id: Long = 0,
                        houseId: Long,
                        typeof: GarageType = GarageType.attached,
                        built: String = Entity.now) extends Entity

object Garage:
  given JsonValueCodec[Garage] = JsonCodecMaker.make[Garage]
  given Ordering[Garage] = Ordering.by[Garage, String](garage => garage.built).reverse

// Integral

final case class Siding(id: Long = 0,
                        houseId: Long,
                        typeof: SidingType = SidingType.vinyl,
                        installed: String = Entity.now) extends Entity

object Siding:
  given JsonValueCodec[Siding] = JsonCodecMaker.make[Siding]
  given Ordering[Siding] = Ordering.by[Siding, String](siding => siding.installed).reverse

final case class Gutter(id: Long = 0,
                        houseId: Long,
                        typeof: GutterType = GutterType.kstyle,
                        installed: String = Entity.now) extends Entity

object Gutter:
  given JsonValueCodec[Gutter] = JsonCodecMaker.make[Gutter]
  given Ordering[Gutter] = Ordering.by[Gutter, String](gutter => gutter.installed).reverse

final case class Soffit(id: Long = 0,
                        houseId: Long,
                        typeof: SoffitType = SoffitType.aluminum,
                        installed: String = Entity.now) extends Entity

object Soffit:
  given JsonValueCodec[Soffit] = JsonCodecMaker.make[Soffit]
  given Ordering[Soffit] = Ordering.by[Soffit, String](soffit => soffit.installed).reverse

final case class Window(id: Long = 0,
                        houseId: Long,
                        typeof: WindowType = WindowType.single,
                        installed: String = Entity.now) extends Entity

object Window:
  given JsonValueCodec[Window] = JsonCodecMaker.make[Window]
  given Ordering[Window] = Ordering.by[Window, String](window => window.installed).reverse

final case class Door(id: Long = 0,
                      houseId: Long,
                      typeof: DoorType = DoorType.fiberglass,
                      installed: String = Entity.now) extends Entity

object Door:
  given JsonValueCodec[Door] = JsonCodecMaker.make[Door]
  given Ordering[Door] = Ordering.by[Door, String](door => door.installed).reverse

final case class Plumbing(id: Long = 0,
                          houseId: Long,
                          typeof: PlumbingType = PlumbingType.pvc,
                          installed: String = Entity.now) extends Entity

object Plumbing:
  given JsonValueCodec[Plumbing] = JsonCodecMaker.make[Plumbing]
  given Ordering[Plumbing] = Ordering.by[Plumbing, String](plumbing => plumbing.installed).reverse

final case class Electrical(id: Long = 0,
                            houseId: Long,
                            typeof: ElectricalType = ElectricalType.nm,
                            installed: String = Entity.now) extends Entity

object Electrical:
  given JsonValueCodec[Electrical] = JsonCodecMaker.make[Electrical]
  given Ordering[Electrical] = Ordering.by[Electrical, String](electrical => electrical.installed).reverse

final case class Fusebox(id: Long = 0,
                         houseId: Long,
                         typeof: FuseboxType = FuseboxType.circuitBreaker,
                         installed: String = Entity.now) extends Entity

object Fusebox:
  given JsonValueCodec[Fusebox] = JsonCodecMaker.make[Fusebox]
  given Ordering[Fusebox] = Ordering.by[Fusebox, String](fusebox => fusebox.installed).reverse

final case class Alarm(id: Long = 0,
                       houseId: Long,
                       typeof: AlarmType = AlarmType.wireless,
                       installed: String = Entity.now) extends Entity

object Alarm:
  given JsonValueCodec[Alarm] = JsonCodecMaker.make[Alarm]
  given Ordering[Alarm] = Ordering.by[Alarm, String](alarm => alarm.installed).reverse

final case class Heater(id: Long = 0,
                        houseId: Long,
                        typeof: HeaterType = HeaterType.furnace,
                        installed: String = Entity.now) extends Entity

object Heater:
  given JsonValueCodec[Heater] = JsonCodecMaker.make[Heater]
  given Ordering[Heater] = Ordering.by[Heater, String](heater => heater.installed).reverse

final case class AirConditioner(id: Long = 0,
                                houseId: Long,
                                typeof: AirConditionerType = AirConditionerType.central,
                                installed: String = Entity.now) extends Entity

object AirConditioner:
  given JsonValueCodec[AirConditioner] = JsonCodecMaker.make[AirConditioner]
  given Ordering[AirConditioner] = Ordering.by[AirConditioner, String](ac => ac.installed).reverse

final case class Floor(id: Long = 0,
                       houseId: Long,
                       typeof: FloorType = FloorType.tile,
                       installed: String = Entity.now) extends Entity

object Floor:
  given JsonValueCodec[Floor] = JsonCodecMaker.make[Floor]
  given Ordering[Floor] = Ordering.by[Floor, String](floor => floor.installed).reverse

final case class Lighting(id: Long = 0,
                          houseId: Long,
                          typeof: LightingType = LightingType.general,
                          installed: String = Entity.now) extends Entity

object Lighting:
  given JsonValueCodec[Lighting] = JsonCodecMaker.make[Lighting]
  given Ordering[Lighting] = Ordering.by[Lighting, String](lighting => lighting.installed).reverse

// External

final case class Sewage(id: Long = 0,
                        houseId: Long,
                        typeof: SewageType = SewageType.anaerobicSystem,
                        built: String = Entity.now) extends Entity

object Sewage:
  given JsonValueCodec[Sewage] = JsonCodecMaker.make[Sewage]
  given Ordering[Sewage] = Ordering.by[Sewage, String](sewage => sewage.built).reverse

final case class Well(id: Long = 0,
                      houseId: Long,
                      typeof: WellType = WellType.drilled,
                      built: String = Entity.now) extends Entity

object Well:
  given JsonValueCodec[Well] = JsonCodecMaker.make[Well]
  given Ordering[Well] = Ordering.by[Well, String](well => well.built).reverse

final case class Water(id: Long = 0,
                       houseId: Long,
                       typeof: WaterType = WaterType.well,
                       installed: String = Entity.now) extends Entity

object Water:
  given JsonValueCodec[Water] = JsonCodecMaker.make[Water]
  given Ordering[Water] = Ordering.by[Water, String](water => water.installed).reverse

final case class WaterHeater(id: Long = 0,
                             houseId: Long,
                             typeof: WaterHeaterType = WaterHeaterType.tank,
                             installed: String = Entity.now) extends Entity

object WaterHeater:
  given JsonValueCodec[WaterHeater] = JsonCodecMaker.make[WaterHeater]
  given Ordering[WaterHeater] = Ordering.by[WaterHeater, String](water => water.installed).reverse

final case class Lawn(id: Long = 0,
                      houseId: Long,
                      typeof: LawnType = LawnType.grass,
                      planted: String = Entity.now) extends Entity

object Lawn:
  given JsonValueCodec[Lawn] = JsonCodecMaker.make[Lawn]
  given Ordering[Lawn] = Ordering.by[Lawn, String](lawn => lawn.planted).reverse

final case class Garden(id: Long = 0,
                        houseId: Long,
                        typeof: GardenType = GardenType.vegetable,
                        planted: String = Entity.now) extends Entity

object Garden:
  given JsonValueCodec[Garden] = JsonCodecMaker.make[Garden]
  given Ordering[Garden] = Ordering.by[Garden, String](garden => garden.planted).reverse

final case class Sprinkler(id: Long = 0,
                            houseId: Long,
                            typeof: SprinklerType = SprinklerType.preAction,
                            installed: String = Entity.now) extends Entity

object Sprinkler:
  given JsonValueCodec[Sprinkler] = JsonCodecMaker.make[Sprinkler]
  given Ordering[Sprinkler] = Ordering.by[Sprinkler, String](irrigation => irrigation.installed).reverse

final case class Shed(id: Long = 0,
                      houseId: Long,
                      typeof: ShedType = ShedType.storage,
                      built: String = Entity.now) extends Entity

object Shed:
  given JsonValueCodec[Shed] = JsonCodecMaker.make[Shed]
  given Ordering[Shed] = Ordering.by[Shed, String](shed => shed.built).reverse

final case class SolarPanel(id: Long = 0,
                            houseId: Long,
                            number: Int,
                            typeof: SolarPanelType = SolarPanelType.monocrystalline,
                            installed: String = Entity.now) extends Entity

object SolarPanel:
  given JsonValueCodec[SolarPanel] = JsonCodecMaker.make[SolarPanel]
  given Ordering[SolarPanel] = Ordering.by[SolarPanel, String](solar => solar.installed).reverse

final case class Dock(id: Long = 0,
                      houseId: Long,
                      typeof: DockType = DockType.permanent,
                      built: String = Entity.now) extends Entity

object Dock:
  given JsonValueCodec[Dock] = JsonCodecMaker.make[Dock]
  given Ordering[Dock] = Ordering.by[Dock, String](dock => dock.built).reverse

final case class Porch(id: Long = 0,
                       houseId: Long,
                       typeof: PorchType = PorchType.back,
                       built: String = Entity.now) extends Entity

object Porch:
  given JsonValueCodec[Porch] = JsonCodecMaker.make[Porch]
  given Ordering[Porch] = Ordering.by[Porch, String](porch => porch.built).reverse

final case class Patio(id: Long = 0,
                       houseId: Long,
                       typeof: PatioType = PatioType.paver,
                       built: String = Entity.now) extends Entity

object Patio:
  given JsonValueCodec[Patio] = JsonCodecMaker.make[Patio]
  given Ordering[Patio] = Ordering.by[Patio, String](patio => patio.built).reverse

final case class Pool(id: Long = 0,
                      houseId: Long,
                      typeof: PoolType = PoolType.inground,
                      gallons: Int,
                      caged: Boolean,
                      built: String = Entity.now) extends Entity

object Pool:
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]
  given Ordering[Pool] = Ordering.by[Pool, String](pool => pool.built).reverse

final case class Mailbox(id: Long = 0,
                         houseId: Long,
                         typeof: MailboxType = MailboxType.postMount,
                         installed: String = Entity.now) extends Entity

object Mailbox:
  given JsonValueCodec[Mailbox] = JsonCodecMaker.make[Mailbox]
  given Ordering[Mailbox] = Ordering.by[Mailbox, String](mailbox => mailbox.installed).reverse