package objektwerks

import java.time.LocalDate
import java.util.UUID

sealed trait Entity:
  val id: Long

object Entity:
  def now: String = LocalDate.now.toString
  def localDate(now: String): LocalDate =
    if now.nonEmpty then LocalDate.parse(now) else LocalDate.now

final case class Fault(cause: String,
                       occurred: String = LocalDate.now.toString,
                       id: Long = 0) extends Entity

final case class Account(
    id: Long = 0,
    license: String = UUID.randomUUID.toString,
    email: String = "",
    pin: String = Pin.newInstance,
    activated: String = Entity.now
) extends Entity derives CanEqual

final case class House(
    id: Long = 0,
    accountId: Long,
    typeof: HouseType = HouseType.primary,
    location: String,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

// Structure

final case class Foundation(
    id: Long = 0,
    houseId: Long,
    typeof: FoundationType = FoundationType.slab,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Frame(
    id: Long = 0,
    houseId: Long,
    typeof: FrameType = FrameType.platform,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Attic(
    id: Long = 0,
    houseId: Long,
    typeof: AtticType = AtticType.scuttle,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Insulation(
    id: Long = 0,
    houseId: Long,
    typeof: InsulationType = InsulationType.blanket,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Ductwork(
    id: Long = 0,
    houseId: Long,
    typeof: DuctworkType = DuctworkType.airduct,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Ventilation(
    id: Long = 0,
    houseId: Long,
    typeof: VentilationType = VentilationType.balanced,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Roof(
    id: Long = 0,
    houseId: Long,
    typeof: RoofType = RoofType.shingle,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Chimney(
    id: Long = 0,
    houseId: Long,
    typeof: ChimneyType = ChimneyType.masonry,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Balcony(
    id: Long = 0,
    houseId: Long,
    typeof: BalconyType = BalconyType.cantilevered,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Drywall(
    id: Long = 0,
    houseId: Long,
    typeof: DrywallType = DrywallType.standard,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Room(
    id: Long = 0,
    houseId: Long,
    typeof: RoomType = RoomType.bedroom,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Driveway(
    id: Long = 0,
    houseId: Long,
    typeof: DrivewayType = DrivewayType.concrete,
    culvertTypeof: CulvertType = CulvertType.concrete,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Garage(
    id: Long = 0,
    houseId: Long,
    typeof: GarageType = GarageType.attached,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

// Integral

final case class Siding(
    id: Long = 0,
    houseId: Long,
    typeof: SidingType = SidingType.vinyl,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Gutter(
    id: Long = 0,
    houseId: Long,
    typeof: GutterType = GutterType.kstyle,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Soffit(
    id: Long = 0,
    houseId: Long,
    typeof: SoffitType = SoffitType.aluminum,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Window(
    id: Long = 0,
    houseId: Long,
    typeof: WindowType = WindowType.single,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Door(
    id: Long = 0,
    houseId: Long,
    typeof: DoorType = DoorType.fiberglass,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Plumbing(
    id: Long = 0,
    houseId: Long,
    typeof: PlumbingType = PlumbingType.pvc,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Electrical(
    id: Long = 0,
    houseId: Long,
    typeof: ElectricalType = ElectricalType.nm,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Fusebox(
    id: Long = 0,
    houseId: Long,
    typeof: FuseboxType = FuseboxType.circuitBreaker,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Alarm(
    id: Long = 0,
    houseId: Long,
    typeof: AlarmType = AlarmType.wireless,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Heater(
    id: Long = 0,
    houseId: Long,
    typeof: HeaterType = HeaterType.furnace,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class AirConditioner(
    id: Long = 0,
    houseId: Long,
    typeof: AirConditionerType = AirConditionerType.central,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Floor(
    id: Long = 0,
    houseId: Long,
    typeof: FloorType = FloorType.tile,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Lighting(
    id: Long = 0,
    houseId: Long,
    typeof: LightingType = LightingType.general,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

// External

final case class Sewage(
    id: Long = 0,
    houseId: Long,
    typeof: SewageType = SewageType.anaerobicSystem,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Well(
    id: Long = 0,
    houseId: Long,
    typeof: WellType = WellType.drilled,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Water(
    id: Long = 0,
    houseId: Long,
    typeof: WaterType = WaterType.well,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class WaterHeater(
    id: Long = 0,
    houseId: Long,
    typeof: WaterHeaterType = WaterHeaterType.tank,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Lawn(
    id: Long = 0,
    houseId: Long,
    typeof: LawnType = LawnType.grass,
    label: String = "",
    note: String = "",
    planted: String = Entity.now
) extends Entity derives CanEqual

final case class Garden(
    id: Long = 0,
    houseId: Long,
    typeof: GardenType = GardenType.vegetable,
    label: String = "",
    note: String = "",
    planted: String = Entity.now
) extends Entity derives CanEqual

final case class Sprinkler(
    id: Long = 0,
    houseId: Long,
    typeof: SprinklerType = SprinklerType.preAction,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Shed(
    id: Long = 0,
    houseId: Long,
    typeof: ShedType = ShedType.storage,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class SolarPanel(
    id: Long = 0,
    houseId: Long,
    typeof: SolarPanelType = SolarPanelType.monocrystalline,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity derives CanEqual

final case class Porch(
    id: Long = 0,
    houseId: Long,
    typeof: PorchType = PorchType.back,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Patio(
    id: Long = 0,
    houseId: Long,
    typeof: PatioType = PatioType.paver,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Pool(
    id: Long = 0,
    houseId: Long,
    typeof: PoolType = PoolType.inground,
    gallons: Int,
    caged: Boolean = false,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Dock(
    id: Long = 0,
    houseId: Long,
    typeof: DockType = DockType.permanent,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Gazebo(
    id: Long = 0,
    houseId: Long,
    typeof: GazeboType = GazeboType.wood,
    label: String = "",
    note: String = "",
    built: String = Entity.now
) extends Entity derives CanEqual

final case class Mailbox(
    id: Long = 0,
    houseId: Long,
    typeof: MailboxType = MailboxType.postMount,
    label: String = "",
    note: String = "",
    installed: String = Entity.now
) extends Entity
