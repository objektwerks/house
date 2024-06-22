package objektwerks

object Sorter:
  given Ordering[House] = Ordering.by[House, String](home => home.built).reverse