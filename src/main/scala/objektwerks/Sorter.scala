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
