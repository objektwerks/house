package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

sealed trait Typeof

object Typeof:
  given JsonValueCodec[Typeof] = JsonCodecMaker.make[Typeof](CodecMakerConfig.withDiscriminatorFieldName(None))

enum HouseType extends Typeof:
  case primary, secondary, vacation
  given JsonValueCodec[HouseType] = JsonCodecMaker.make[HouseType]

// Structure

enum FoundationType extends Typeof:
  case slab, basement, wood, crawl
  given JsonValueCodec[FoundationType] = JsonCodecMaker.make[FoundationType]

enum FrameType extends Typeof:
  case platform, steel
  given JsonValueCodec[FrameType] = JsonCodecMaker.make[FrameType]

enum AtticType extends Typeof:
  case finished, unfinished, scuttle
  given JsonValueCodec[AtticType] = JsonCodecMaker.make[AtticType]

enum InsulationType extends Typeof:
  case blanket, blown, foam
  given JsonValueCodec[InsulationType] = JsonCodecMaker.make[InsulationType]

enum DuctworkType extends Typeof:
  case airduct, supply, `return`, rigid, flexible, insulated

enum VentilationType extends Typeof:
  case balanced, exhaust, supply, heat
  given JsonValueCodec[VentilationType] = JsonCodecMaker.make[VentilationType]

enum BalconyType extends Typeof:
  case cantilevered, hung, stacked, juliet, mezzanine, loggia
  given JsonValueCodec[BalconyType] = JsonCodecMaker.make[BalconyType]

enum DrywallType extends Typeof:
  case standard, specialty
  given JsonValueCodec[DrywallType] = JsonCodecMaker.make[DrywallType]

enum RoomType extends Typeof:
  case kitchen, bathroom, bedroom, living, dining, den, study, laundry, office, closet
  given JsonValueCodec[RoomType] = JsonCodecMaker.make[RoomType]

enum RoofType extends Typeof:
  case alluminum, galvalume, tile, shingle
  given JsonValueCodec[RoofType] = JsonCodecMaker.make[RoofType]

enum ChimneyType extends Typeof:
  case masonry, metal
  given JsonValueCodec[ChimneyType] = JsonCodecMaker.make[ChimneyType]

enum DrivewayType extends Typeof:
  case asphalt, concrete, gravel, paver
  given JsonValueCodec[DrivewayType] = JsonCodecMaker.make[DrivewayType]

enum GarageType extends Typeof:
  case attached, detached
  given JsonValueCodec[GarageType] = JsonCodecMaker.make[GarageType]

enum PorchType extends Typeof:
  case open, front, back, deck, screened, detached
  given JsonValueCodec[PorchType] = JsonCodecMaker.make[PorchType]

enum PatioType extends Typeof:
  case concrete, paver, tile, wood
  given JsonValueCodec[PatioType] = JsonCodecMaker.make[PatioType]

enum PoolType extends Typeof:
  case aboveground, inground
  given JsonValueCodec[PoolType] = JsonCodecMaker.make[PoolType]

// Integral

enum SidingType extends Typeof:
  case vinyl, wood, metal, cement, brick, stone, composite, clapboard
  given JsonValueCodec[SidingType] = JsonCodecMaker.make[SidingType]

enum GutterType extends Typeof:
  case kstyle, halfround, fascia, seamless, vinyl, aluminum, steel, copper
  given JsonValueCodec[GutterType] = JsonCodecMaker.make[GutterType]

enum SoffitType extends Typeof:
  case wood, aluminum, cement, vinyl, steel
  given JsonValueCodec[SoffitType] = JsonCodecMaker.make[SoffitType]

enum WindowType extends Typeof:
  case single, double, bay, slide
  given JsonValueCodec[WindowType] = JsonCodecMaker.make[WindowType]

enum DoorType extends Typeof:
  case wood, fiberglass, steel, glass
  given JsonValueCodec[DoorType] = JsonCodecMaker.make[DoorType]

enum PlumbingType extends Typeof:
  case pex, pvc, copper, abs
  given JsonValueCodec[PlumbingType] = JsonCodecMaker.make[PlumbingType]

enum ElectricalType extends Typeof:
  case nm, romex, armored, undergroundFeeder, lowVoltage, thhn, thwn, phone, data
  given JsonValueCodec[ElectricalType] = JsonCodecMaker.make[ElectricalType]

enum FuseboxType extends Typeof:
  case typeS, typeT, circuitBreaker, mainBreaker
  given JsonValueCodec[FuseboxType] = JsonCodecMaker.make[FuseboxType]

enum AlarmType extends Typeof:
  case wireless, monitored, smart, passiveInfrared, dome, wirelessMotionDetector, windowSensor, doorSensor, dummy
  given JsonValueCodec[AlarmType] = JsonCodecMaker.make[AlarmType]

enum HeaterType extends Typeof:
  case furnace, boiler, heatPump, inFloorRadiant, electricResistance, baseboardHeater, solar

enum AirConditionerType extends Typeof:
  case central, ductless, window, portable, floor, airSourceHeatPump
  given JsonValueCodec[AirConditionerType] = JsonCodecMaker.make[AirConditionerType]

enum FloorType extends Typeof:
  case carpet, concrete, tile, wood, synthetic
  given JsonValueCodec[FloorType] = JsonCodecMaker.make[FloorType]

enum LightingType extends Typeof:
  case general, task, accent
  given JsonValueCodec[LightingType] = JsonCodecMaker.make[LightingType]

// External

enum SewageType extends Typeof:
  case sanitarySewer, subsurfaceDrainageSystem, septicSystem, aerobicTreatmentUnit, anaerobicSystem
  given JsonValueCodec[SewageType] = JsonCodecMaker.make[SewageType]

enum WellType extends Typeof:
  case dug, drilled, driven
  given JsonValueCodec[WellType] = JsonCodecMaker.make[WellType]

enum WaterType extends Typeof:
  case city, well, direct, indirect, singleStack
  given JsonValueCodec[WaterType] = JsonCodecMaker.make[WaterType]

enum WaterHeaterType extends Typeof:
  case tank, tankless, hybrid, pointOfUse, solar
  given JsonValueCodec[WaterHeaterType] = JsonCodecMaker.make[WaterHeaterType]

enum LawnType extends Typeof:
  case grass, rock, sand
  given JsonValueCodec[LawnType] = JsonCodecMaker.make[LawnType]

enum GardenType extends Typeof:
  case flower, vegetable
  given JsonValueCodec[GardenType] = JsonCodecMaker.make[GardenType]

enum SprinklerType extends Typeof:
  case multiUse, wetPipe, deluge, preAction
  given JsonValueCodec[SprinklerType] = JsonCodecMaker.make[SprinklerType]

enum ShedType extends Typeof:
  case storage, workshop
  given JsonValueCodec[ShedType] = JsonCodecMaker.make[ShedType]

enum SolarPanelType extends Typeof:
  case monocrystalline, polycrystalline, thinFilm
  given JsonValueCodec[SolarPanelType] = JsonCodecMaker.make[SolarPanelType]

enum DockType extends Typeof:
  case floating, stationary, combination, permanent
  given JsonValueCodec[DockType] = JsonCodecMaker.make[DockType]

enum MailboxType extends Typeof:
  case postMount, wallMount
  given JsonValueCodec[MailboxType] = JsonCodecMaker.make[MailboxType]
