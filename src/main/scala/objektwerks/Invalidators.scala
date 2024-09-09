package objektwerks

import Invalidator.*
import Validators.*

object Invalidators:
  extension (common: Common)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(common.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .invalidate(common.houseId > 0)(Field("houseId"), Message("Must be great than 0."))
        .invalidate(common.label.isEmptyOrNonEmpty)(Field("label"), Message("Must be empty or non empty."))
        .invalidate(common.note.isEmptyOrNonEmpty)(Field("note"), Message("Must be empty or non empty."))

  extension (built: Built)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(built.built.nonEmpty)(Field("built"), Message("Must be non empty."))
