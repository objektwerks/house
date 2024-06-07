package objektwerks

sealed trait Typeof

enum HouseType extends Typeof:
  case primary, secondary, vacation

// Structure

enum FoundationType extends Typeof:
  case slab, basement, wood, crawl

enum FrameType extends Typeof:
  case platform, steel

enum AtticType extends Typeof:
  case finished, unfinished, scuttle

enum InsulationType extends Typeof:
  case blanket, blown, foam

enum DuctworkType extends Typeof:
  case airduct, supply, `return`, rigid, flexible, insulated

enum VentilationType extends Typeof:
  case balanced, exhaust, supply, heat

enum BalconyType extends Typeof:
  case cantilevered, hung, stacked, juliet, mezzanine, loggia

enum DrywallType extends Typeof:
  case standard, specialty

enum RoomType extends Typeof:
  case kitchen, bathroom, bedroom, living, dining, den, study, laundry, office, closet

enum RoofType extends Typeof:
  case alluminum, galvalume, tile, shingle

enum ChimneyType extends Typeof:
  case masonry, metal

enum DrivewayType extends Typeof:
  case asphalt, concrete, gravel, paver

enum GarageType extends Typeof:
  case attached, detached

enum PorchType extends Typeof:
  case open, front, back, deck, screened, detached

enum PatioType extends Typeof:
  case concrete, paver, tile, wood

enum PoolType extends Typeof:
  case aboveground, inground

// Integral

enum SidingType extends Typeof:
  case vinyl, wood, metal, cement, brick, stone, composite, clapboard

enum GutterType extends Typeof:
  case kstyle, halfround, fascia, seamless, vinyl, aluminum, steel, copper

enum SoffitType extends Typeof:
  case wood, aluminum, cement, vinyl, steel

enum WindowType extends Typeof:
  case single, double, bay, slide

enum DoorType extends Typeof:
  case wood, fiberglass, steel, glass

enum PlumbingType extends Typeof:
  case pex, pvc, copper, abs

enum ElectricalType extends Typeof:
  case nm, romex, armored, undergroundFeeder, lowVoltage, thhn, thwn, phone, data

enum FuseboxType extends Typeof:
  case typeS, typeT, circuitBreaker, mainBreaker

enum AlarmType extends Typeof:
  case wireless, monitored, smart, passiveInfrared, dome, wirelessMotionDetector, windowSensor, doorSensor, dummy

enum HeaterType extends Typeof:
  case furnace, boiler, heatPump, inFloorRadiant, electricResistance, baseboardHeater, solar

enum AirConditionerType extends Typeof:
  case central, ductless, window, portable, floor, airSourceHeatPump

enum FloorType extends Typeof:
  case carpet, concrete, tile, wood, synthetic

enum LightingType extends Typeof:
  case general, task, accent

// External

enum SewageType extends Typeof:
  case sanitarySewer, subsurfaceDrainageSystem, septicSystem, aerobicTreatmentUnit, anaerobicSystem

enum WellType extends Typeof:
  case dug, drilled, driven

enum WaterType extends Typeof:
  case city, well, direct, indirect, singleStack

enum WaterHeaterType extends Typeof:
  case tank, tankless, hybrid, pointOfUse, solar

enum LawnType extends Typeof:
  case grass, rock, sand

enum GardenType extends Typeof:
  case flower, vegetable

enum SprinklerType extends Typeof:
  case multiUse, wetPipe, deluge, preAction

enum ShedType extends Typeof:
  case storage, workshop

enum SolarPanelType extends Typeof:
  case monocrystalline, polycrystalline, thinFilm

enum DockType extends Typeof:
  case floating, stationary, combination, permanent

enum MailboxType extends Typeof:
  case postMount, wallMount