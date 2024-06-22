package objektwerks

object Sorter:
  given Ordering[Account] = Ordering.by[Account, String](account => account.activated).reverse
  given Ordering[House] = Ordering.by[House, String](home => home.built).reverse
  given Ordering[Foundation] = Ordering.by[Foundation, String](foundation => foundation.built).reverse
