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

  extension (roof: Roof)
    def isValid: Boolean =
      roof.id >= 0 &&
      roof.houseId > 0 &&
      roof.typeof == RoofType &&
      roof.label.isEmptyOrNonEmpty &&
      roof.note.isEmptyOrNonEmpty &&
      roof.built.nonEmpty

  extension (chimney: Chimney)
    def isValid: Boolean =
      chimney.id >= 0 &&
      chimney.houseId > 0 &&
      chimney.typeof == ChimneyType &&
      chimney.label.isEmptyOrNonEmpty &&
      chimney.note.isEmptyOrNonEmpty &&
      chimney.built.nonEmpty

  extension (balcony: Balcony)
    def isValid: Boolean =
      balcony.id >= 0 &&
      balcony.houseId > 0 &&
      balcony.typeof == BalconyType &&
      balcony.label.isEmptyOrNonEmpty &&
      balcony.note.isEmptyOrNonEmpty &&
      balcony.built.nonEmpty

  extension (drywall: Drywall)
    def isValid: Boolean =
      drywall.id >= 0 &&
      drywall.houseId > 0 &&
      drywall.typeof == DrywallType &&
      drywall.label.isEmptyOrNonEmpty &&
      drywall.note.isEmptyOrNonEmpty &&
      drywall.built.nonEmpty

  extension (room: Room)
    def isValid: Boolean =
      room.id >= 0 &&
      room.houseId > 0 &&
      room.typeof == RoomType &&
      room.label.isEmptyOrNonEmpty &&
      room.note.isEmptyOrNonEmpty &&
      room.built.nonEmpty

  extension (driveway: Driveway)
    def isValid: Boolean =
      driveway.id >= 0 &&
      driveway.houseId > 0 &&
      driveway.typeof == DrivewayType &&
      driveway.label.isEmptyOrNonEmpty &&
      driveway.note.isEmptyOrNonEmpty &&
      driveway.built.nonEmpty

  extension (garage: Garage)
    def isValid: Boolean =
      garage.id >= 0 &&
      garage.houseId > 0 &&
      garage.typeof == GarageType &&
      garage.label.isEmptyOrNonEmpty &&
      garage.note.isEmptyOrNonEmpty &&
      garage.built.nonEmpty

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
      listEntity.entityType == EntityType &&
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
