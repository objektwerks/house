package objektwerks

object Validator:
  extension (value: String)
    def isEmptyOrNonEmpty: Boolean = value.isEmpty || value.nonEmpty
    def isLicense: Boolean = if value.nonEmpty && value.length == 36 then true else false
    def isPin: Boolean = value.length == 7
    def isEmail: Boolean = value.nonEmpty && value.length >= 3 && value.contains("@")

  extension (account: Account)
    def isValid: Boolean =
      account.id >= 0 &&
      account.license.isLicense &&
      account.email.isEmail &&
      account.pin.isPin &&
      account.activated.nonEmpty

  extension (house: House)
    def isValid: Boolean =
      house.id >= 0 &&
      house.accountId > 0 &&
      house.typeof == HouseType &&
      house.location.nonEmpty &&
      house.label.isEmptyOrNonEmpty &&
      house.note.isEmptyOrNonEmpty &&
      house.built.nonEmpty