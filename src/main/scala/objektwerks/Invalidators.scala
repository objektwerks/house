package objektwerks

import Invalidator.*

object Invalidators:
  extension (value: String)
    def isEmptyOrNonEmpty: Boolean = value.isEmpty || value.nonEmpty
    def isLicense: Boolean = value.length == 36
    def isPin: Boolean = value.length == 7
    def isEmail: Boolean = value.length >= 3 && value.contains("@")

  extension (account: Account)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(account.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .invalidate(account.license.isLicense)(Field("license"), Message("Must be 36 characters in length."))
        .invalidate(account.email.isEmail)(Field("email"), Message("Must be at least 3 characters in length and contain 1 @."))
        .invalidate(account.pin.isPin)(Field("pin"), Message("Must be 7 characters in length."))
        .invalidate(account.activated.nonEmpty)(Field("activated"), Message("Must be non empty."))

  extension (house: House)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(house.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .invalidate(house.accountId > 0)(Field("accountId"), Message("Must be greater than 0."))
        .invalidate(house.label.isEmptyOrNonEmpty)(Field("label"), Message("Must be empty or non empty."))
        .invalidate(house.note.isEmptyOrNonEmpty)(Field("note"), Message("Must be empty or non empty."))
        .invalidate(house.architect.isEmptyOrNonEmpty)(Field("architect"), Message("Must be empty or non empty."))
        .invalidate(house.builder.isEmptyOrNonEmpty)(Field("builder"), Message("Must be empty or non empty."))
        .invalidate(house.built.nonEmpty)(Field("built"), Message("Must be non empty."))

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

  extension (installed: Installed)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(installed.installed.nonEmpty)(Field("installed"), Message("Must be non empty."))

  extension (planted: Planted)
    def invalidate: Invalidator =
      Invalidator()
        .invalidate(planted.planted.nonEmpty)(Field("planted"), Message("Must be non empty."))
