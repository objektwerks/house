package objektwerks

enum HouseType:
  case primary, secondary, vacation

enum FoundationType:
  case slab, basement, wood, crawl

enum FrameType:
  case platform, steel

enum AtticType:
  case finished, unfinished, scuttle

enum InsulationType:
  case blanket, blown, foam

enum VentilationType:
  case balanced, exhaust, supply, heat

enum BalconyType:
  case cantilevered, hung, stacked, juliet, mezzanine, loggia

enum DrywallType:
  case standard, specialty

enum RoofType:
  case alluminum, galvalume, tile, shingle

enum ChimneyType:
  case masonry, metal

enum DrivewayType:
  case asphalt, concrete, gravel, paver

enum GarageType:
  case attached, detached

enum PorchType:
  case open, front, back, deck, screened, detached

enum PatioType:
  case concrete, paver, tile, wood

enum PoolType:
  case aboveground, inground

enum SidingType:
  case vinyl, wood, metal, cement, brick, stone, composite, clapboard

enum RoomType:
  case kitchen, bathroom, bedroom, living, dining, den, study, laundry, office, closet

enum WindowType:
  case single, double, bay, slide

enum DoorType:
  case wood, fiberglass, steel, glass

enum FloorType:
  case carpet, concrete, tile, wood, synthetic