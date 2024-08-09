package objektwerks

enum EntityType:
  case House, Foundation, Frame, Attic, Insulation, Ductwork, Ventilation, Roof, Chimney, Balcony, Drywall, Room, Driveway,
  Garage, Siding, Gutter, Soffit, Window, Door, Plumbing, Electrical, Fusebox, Alarm, Heater, AirConditioner, Floor, Lighting,
  Sewage, Well, Water, WaterHeater, Lawn, Garden, Sprinkler, Shed, SolarPanel, Porch, Patio, Pool, Dock, Gazebo, Mailbox

enum HouseType:
  case primary, secondary, vacation

// Structure

enum DrawingType:
  case architectural, structural, electrical, plumbing, finishing, singleLine

enum FoundationType:
  case slab, basement, wood, crawl

enum FrameType:
  case platform, steel

enum AtticType:
  case finished, unfinished, scuttle

enum InsulationType:
  case blanket, blown, foam

enum DuctworkType:
  case airduct, supply, `return`, rigid, flexible, insulated

enum VentilationType:
  case balanced, exhaust, supply, heat

enum BalconyType:
  case cantilevered, hung, stacked, juliet, mezzanine, loggia

enum DrywallType:
  case standard, specialty

enum RoomType:
  case kitchen, bathroom, bedroom, living, dining, den, study, laundry, office, closet

enum RoofType:
  case alluminum, galvalume, tile, shingle

enum ChimneyType:
  case masonry, metal

enum CulvertType:
  case concrete, steel, pvc, none

enum DrivewayType:
  case asphalt, concrete, gravel, paver

enum GarageType:
  case attached, detached

// Integral

enum SidingType:
  case vinyl, wood, metal, cement, brick, stone, composite, clapboard

enum GutterType:
  case kstyle, halfround, fascia, seamless, vinyl, aluminum, steel, copper

enum SoffitType:
  case wood, aluminum, cement, vinyl, steel

enum WindowType:
  case single, double, bay, slide

enum DoorType:
  case wood, fiberglass, steel, glass

enum PlumbingType:
  case pex, pvc, copper, abs

enum ElectricalType:
  case nm, romex, armored, undergroundFeeder, lowVoltage, thhn, thwn, phone, data

enum FuseboxType:
  case typeS, typeT, circuitBreaker, mainBreaker

enum AlarmType:
  case wireless, monitored, smart, passiveInfrared, dome, wirelessMotionDetector, windowSensor, doorSensor, dummy

enum HeaterType:
  case furnace, boiler, heatPump, inFloorRadiant, electricResistance, baseboardHeater, solar

enum AirConditionerType:
  case central, ductless, window, portable, floor, airSourceHeatPump

enum FloorType:
  case carpet, concrete, tile, wood, synthetic

enum LightingType:
  case general, task, accent

// External

enum SewageType:
  case sanitarySewer, subsurfaceDrainageSystem, septicSystem, aerobicTreatmentUnit, anaerobicSystem

enum WellType:
  case dug, drilled, driven

enum WaterType:
  case city, well, direct, indirect, singleStack

enum WaterHeaterType:
  case tank, tankless, hybrid, pointOfUse, solar

enum LawnType:
  case grass, rock, sand

enum GardenType:
  case flower, vegetable

enum SprinklerType:
  case multiUse, wetPipe, deluge, preAction

enum ShedType:
  case storage, workshop

enum SolarPanelType:
  case monocrystalline, polycrystalline, thinFilm

enum PorchType:
  case open, front, back, deck, screened, detached

enum PatioType:
  case concrete, paver, tile, wood

enum PoolType:
  case aboveground, inground, spa

enum DockType:
  case floating, stationary, combination, permanent

enum GazeboType:
  case steel, wood

enum MailboxType:
  case postMount, wallMount
