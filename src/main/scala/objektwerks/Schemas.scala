package objektwerks

import sttp.tapir.*

object Schemas:
  given Schema[EntityType] = Schema.derived
  given Schema[HouseType] = Schema.derived
  given Schema[DrawingType] = Schema.derived
  given Schema[FoundationType] = Schema.derived
  given Schema[FrameType] = Schema.derived
  given Schema[AtticType] = Schema.derived
  given Schema[InsulationType] = Schema.derived
  given Schema[DuctworkType] = Schema.derived
  given Schema[VentilationType] = Schema.derived
  given Schema[BalconyType] = Schema.derived
  given Schema[DrywallType] = Schema.derived
  given Schema[RoomType] = Schema.derived
  given Schema[RoofType] = Schema.derived
  given Schema[ChimneyType] = Schema.derived
  given Schema[CulvertType] = Schema.derived
  given Schema[DrivewayType] = Schema.derived
  given Schema[GarageType] = Schema.derived
  given Schema[SidingType] = Schema.derived
  given Schema[GutterType] = Schema.derived
  given Schema[SoffitType] = Schema.derived
  given Schema[WindowType] = Schema.derived
  given Schema[DoorType] = Schema.derived
  given Schema[PlumbingType] = Schema.derived
  given Schema[ElectricalType] = Schema.derived
  given Schema[FuseboxType] = Schema.derived
  given Schema[AlarmType] = Schema.derived
  given Schema[HeaterType] = Schema.derived
  given Schema[AirConditionerType] = Schema.derived
  given Schema[FloorType] = Schema.derived
  given Schema[LightingType] = Schema.derived
  given Schema[SewageType] = Schema.derived
  given Schema[WellType] = Schema.derived
  given Schema[WaterType] = Schema.derived
  given Schema[WaterHeaterType] = Schema.derived
  given Schema[LawnType] = Schema.derived
  given Schema[GardenType] = Schema.derived
  given Schema[SprinklerType] = Schema.derived
  given Schema[ShedType] = Schema.derived
  given Schema[SolarPanelType] = Schema.derived
  given Schema[PorchType] = Schema.derived
  given Schema[PatioType] = Schema.derived
  given Schema[PoolType] = Schema.derived
  given Schema[DockType] = Schema.derived
  given Schema[GazeboType] = Schema.derived
  given Schema[MailboxType] = Schema.derived