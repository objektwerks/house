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

  extension (foundation: Foundation)
    def isValid: Boolean =
      foundation.id >= 0 &&
      foundation.houseId > 0 &&
      foundation.typeof == FoundationType &&
      foundation.label.isEmptyOrNonEmpty &&
      foundation.note.isEmptyOrNonEmpty &&
      foundation.built.nonEmpty

  extension (frame: Frame)
    def isValid: Boolean =
      frame.id >= 0 &&
      frame.houseId > 0 &&
      frame.typeof == FrameType &&
      frame.label.isEmptyOrNonEmpty &&
      frame.note.isEmptyOrNonEmpty &&
      frame.built.nonEmpty

  extension (attic: Attic)
    def isValid: Boolean =
      attic.id >= 0 &&
      attic.houseId > 0 &&
      attic.typeof == AtticType &&
      attic.label.isEmptyOrNonEmpty &&
      attic.note.isEmptyOrNonEmpty &&
      attic.built.nonEmpty

  extension (insulation: Insulation)
    def isValid: Boolean =
      insulation.id >= 0 &&
      insulation.houseId > 0 &&
      insulation.typeof == InsulationType &&
      insulation.label.isEmptyOrNonEmpty &&
      insulation.note.isEmptyOrNonEmpty &&
      insulation.installed.nonEmpty

  extension (ductwork: Ductwork)
    def isValid: Boolean =
      ductwork.id >= 0 &&
      ductwork.houseId > 0 &&
      ductwork.typeof == DuctworkType &&
      ductwork.label.isEmptyOrNonEmpty &&
      ductwork.note.isEmptyOrNonEmpty &&
      ductwork.installed.nonEmpty

  extension (ventilation: Ventilation)
    def isValid: Boolean =
      ventilation.id >= 0 &&
      ventilation.houseId > 0 &&
      ventilation.typeof == VentilationType &&
      ventilation.label.isEmptyOrNonEmpty &&
      ventilation.note.isEmptyOrNonEmpty &&
      ventilation.installed.nonEmpty

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

  extension (command: Command)
    def isValid: Boolean =
      command match
        case register: Register         => register.isValid
        case login: Login               => login.isValid
        case listEntity: ListEntity     => listEntity.isValid
        case addEntity: AddEntity       => addEntity.isValid
        case updateEntity: UpdateEntity => updateEntity.isValid
