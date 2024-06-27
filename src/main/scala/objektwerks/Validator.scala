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

  // TODO - Entity Validators

  extension  (license: License)
    def isLicense: Boolean =
      license.license.isLicense

  extension (register: Register)
    def isValid: Boolean =
      register.email.isEmail

  extension (login: Login)
    def isValid: Boolean =
      login.email.isEmail &&
      login.pin.isPin

  extension (listEntity: ListEntity)
    def isValid: Boolean =
      listEntity.license.isLicense &&
      listEntity.entityClass.nonEmpty &&
      listEntity.accountId > 0

  extension (addEntity: AddEntity)
    def isValid: Boolean =
      addEntity.license.isLicense &&
      addEntity.entity != null

  extension (updateEntity: UpdateEntity)
    def isValid: Boolean =
      updateEntity.license.isLicense &&
      updateEntity.entity != null
