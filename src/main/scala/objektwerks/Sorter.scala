package objektwerks

object Sorter:
  given Ordering[Account] = Ordering.by[Account, String](account => account.activated).reverse
  given Ordering[House] = Ordering.by[House, String](home => home.built).reverse
  given Ordering[Foundation] = Ordering.by[Foundation, String](foundation => foundation.built).reverse
  given Ordering[Frame] = Ordering.by[Frame, String](frame => frame.built).reverse
  given Ordering[Attic] = Ordering.by[Attic, String](attic => attic.built).reverse
  given Ordering[Insulation] = Ordering.by[Insulation, String](insulation => insulation.installed).reverse
  given Ordering[Ductwork] = Ordering.by[Ductwork, String](insulation => insulation.installed).reverse
  given Ordering[Ventilation] = Ordering.by[Ventilation, String](insulation => insulation.installed).reverse
  given Ordering[Roof] = Ordering.by[Roof, String](roof => roof.built).reverse
  given Ordering[Chimney] = Ordering.by[Chimney, String](chimney => chimney.built).reverse
  given Ordering[Balcony] = Ordering.by[Balcony, String](balcony => balcony.built).reverse
  given Ordering[Drywall] = Ordering.by[Drywall, String](drywall => drywall.built).reverse
  given Ordering[Room] = Ordering.by[Room, String](room => room.built).reverse
  given Ordering[Driveway] = Ordering.by[Driveway, String](driveway => driveway.built).reverse
  given Ordering[Garage] = Ordering.by[Garage, String](garage => garage.built).reverse
  given Ordering[Siding] = Ordering.by[Siding, String](siding => siding.installed).reverse
  given Ordering[Gutter] = Ordering.by[Gutter, String](gutter => gutter.installed).reverse
  given Ordering[Soffit] = Ordering.by[Soffit, String](soffit => soffit.installed).reverse
  given Ordering[Window] = Ordering.by[Window, String](window => window.installed).reverse
  given Ordering[Door] = Ordering.by[Door, String](door => door.installed).reverse
  given Ordering[Plumbing] = Ordering.by[Plumbing, String](plumbing => plumbing.installed).reverse
  given Ordering[Electrical] = Ordering.by[Electrical, String](electrical => electrical.installed).reverse
  given Ordering[Fusebox] = Ordering.by[Fusebox, String](fusebox => fusebox.installed).reverse
  given Ordering[Alarm] = Ordering.by[Alarm, String](alarm => alarm.installed).reverse
  given Ordering[Heater] = Ordering.by[Heater, String](heater => heater.installed).reverse
  given Ordering[AirConditioner] = Ordering.by[AirConditioner, String](ac => ac.installed).reverse
  given Ordering[Floor] = Ordering.by[Floor, String](floor => floor.installed).reverse
  given Ordering[Lighting] = Ordering.by[Lighting, String](lighting => lighting.installed).reverse
