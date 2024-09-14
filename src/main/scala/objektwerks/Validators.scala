package objektwerks

import Validator.*

object Validators:
  extension (value: String)
    def isEmptyOrNonEmpty: Boolean = value.isEmpty || value.nonEmpty
    def isLicense: Boolean = value.length == 36
    def isPin: Boolean = value.length == 7
    def isEmail: Boolean = value.length >= 3 && value.contains("@")

  extension (account: Account)
    def validate: Validator =
      Validator()
        .validate(account.id >= 0)(Field("Id"), Message("must be greater than or equal to 0."))
        .validate(account.license.isLicense)(Field("License"), Message("must be 36 characters in length."))
        .validate(account.email.isEmail)(Field("Email"), Message("must be at least 3 characters in length and contain 1 @ symbol."))
        .validate(account.pin.isPin)(Field("Pin"), Message("must be 7 characters in length."))
        .validate(account.activated.nonEmpty)(Field("Activated"), Message("must be non empty."))

  extension (house: House)
    def validate: Validator =
      Validator()
        .validate(house.id >= 0)(Field("Id"), Message("must be greater than or equal to 0."))
        .validate(house.accountId > 0)(Field("AccountId"), Message("must be greater than 0."))
        .validate(house.label.isEmptyOrNonEmpty)(Field("Label"), Message("must be empty or non empty."))
        .validate(house.note.isEmptyOrNonEmpty)(Field("Note"), Message("must be empty or non empty."))
        .validate(house.architect.isEmptyOrNonEmpty)(Field("Architect"), Message("must be empty or non empty."))
        .validate(house.builder.isEmptyOrNonEmpty)(Field("Builder"), Message("must be empty or non empty."))
        .validate(house.built.nonEmpty)(Field("Built"), Message("must be non empty."))

  extension (drawing: Drawing)
    def validate: Validator =
      Validator()
        .validate(drawing.id >= 0)(Field("Id"), Message("must be greater than or equal to 0."))
        .validate(drawing.houseId > 0)(Field("House Id"), Message("must be greater than 0."))
        .validate(drawing.url.nonEmpty)(Field("Url"), Message("must be non empty."))
        .validate(drawing.note.isEmptyOrNonEmpty)(Field("Note"), Message("must be empty or non empty."))
        .validate(drawing.added.nonEmpty)(Field("Added"), Message("must be non empty."))

  extension (issue: Issue)
    def validate: Validator =
      Validator()
        .validate(issue.id >= 0)(Field("Id"), Message("must be greater than or equal to 0."))
        .validate(issue.houseId > 0)(Field("House Id"), Message("must be greater than 0."))
        .validate(issue.report.nonEmpty)(Field("Report"), Message("must be non empty."))
        .validate(issue.resolution.isEmptyOrNonEmpty)(Field("Resolution"), Message("must be empty or non empty."))
        .validate(issue.reported.nonEmpty)(Field("Reported"), Message("must be non empty."))
        .validate(issue.resolved.nonEmpty)(Field("Resolved"), Message("must be non empty."))

  extension (common: Common)
    def validateCommon: Validator =
      Validator()
        .validate(common.id >= 0)(Field("Id"), Message("must be greater than or equal to 0."))
        .validate(common.houseId > 0)(Field("House Id"), Message("must be great than 0."))
        .validate(common.label.isEmptyOrNonEmpty)(Field("Label"), Message("must be empty or non empty."))
        .validate(common.note.isEmptyOrNonEmpty)(Field("Note"), Message("must be empty or non empty."))

  extension (built: Built)
    def validateBuilt: Validator =
      Validator()
        .validate(built.validateCommon)
        .validate(built.built.nonEmpty)(Field("Built"), Message("must be non empty."))

  extension (installed: Installed)
    def validateInstalled: Validator =
      Validator()
        .validate(installed.validateCommon)
        .validate(installed.installed.nonEmpty)(Field("Installed"), Message("must be non empty."))

  extension (planted: Planted)
    def validatePlanted: Validator =
      Validator()
        .validate(planted.validateCommon)
        .validate(planted.planted.nonEmpty)(Field("Planted"), Message("must be non empty."))

  extension (register: Register)
    def validate: Validator =
      Validator()
        .validate(register.email.isEmail)(Field("Email"), Message("must be at least 3 characters in length and contain 1 @ symbol."))

  extension (login: Login)
    def validate: Validator =
      Validator()
        .validate(login.email.isEmail)(Field("Email"), Message("must be at least 3 characters in length and contain 1 @ symbol."))
        .validate(login.pin.isPin)(Field("Pin"), Message("must be 7 characters in length."))

  extension (listEntities: ListEntities)
    def validate: Validator =
      Validator()
        .validate(listEntities.license.isLicense)(Field("License"), Message("must be 36 characters in length."))
        .validate(listEntities.parentId > 0)(Field("Parent Id"), Message("must be greater than 0."))

  extension (addEntity: AddEntity)
    def validate: Validator =
      Validator()
        .validate(addEntity.license.isLicense)(Field("License"), Message("must be 36 characters in length."))
        .validate(validateEntity(addEntity.entity))

  extension (updateEntity: UpdateEntity)
    def validate: Validator =
      Validator()
        .validate(updateEntity.license.isLicense)(Field("License"), Message("must be 36 characters in length."))
        .validate(validateEntity(updateEntity.entity))

  extension (listFaults: ListFaults)
    def validate: Validator =
      Validator()
        .validate(listFaults.license.isLicense)(Field("License"), Message("must be 36 characters in length."))

  extension (addFault: AddFault)
    def validate: Validator =
      Validator()
        .validate(addFault.license.isLicense)(Field("License"), Message("must be 36 characters in length."))
        .validate(addFault.fault.nonEmpty)(Field("Fault"), Message("must be non empty."))

  def validateEntity(entity: Entity): Validator =
    entity match
      case account: Account => account.validate
      case house: House => house.validate
      case issue: Issue => issue.validate
      case drawing: Drawing => drawing.validate
      case built: Built => built.validateBuilt
      case installed: Installed => installed.validateInstalled
      case planted: Planted => planted.validatePlanted

  extension (command: Command)
    def validate: Validator =
      command match
        case register: Register         => register.validate
        case login: Login               => login.validate
        case listEntities: ListEntities => listEntities.validate
        case addEntity: AddEntity       => addEntity.validate
        case updateEntity: UpdateEntity => updateEntity.validate
        case listFaults: ListFaults     => listFaults.validate
        case addFault: AddFault         => addFault.validate

  extension (registered: Registered)
    def validate: Validator =
      Validator()
        .validate(registered.account.validate)

  extension (loggedIn: LoggedIn)
    def validate: Validator =
      Validator()
        .validate(loggedIn.account.validate)

  extension (entities: EntitiesListed)
    def validate: Validator =
      Validator()
        .validate(entities.entities.size >= 0)(Field("Entities listed"), Message("must be zero or more."))

  extension (entityAdded: EntityAdded)
    def validate: Validator =
      Validator()
        .validate(entityAdded.id > 0)(Field("Entity added"), Message("must be zero or more."))

  extension (EntityUpdated: EntityUpdated)
    def validate: Validator =
      Validator()
        .validate(EntityUpdated.count > 0)(Field("Entity updated"), Message("must be greater than zero."))

  extension (fault: Fault)
    def validate: Validator =
      Validator()
        .validate(fault.cause.nonEmpty)(Field("Fault"), Message("must be non empty"))

  extension (FaultAdded: FaultAdded)
    def validate: Validator =
      Validator()
        .validate(FaultAdded.fault.validate)

  extension (event: Event)
    def validate: Validator =
      event match
        case registered: Registered         => registered.validate
        case loggedIn: LoggedIn             => loggedIn.validate
        case entitiesListed: EntitiesListed => entitiesListed.validate
        case entityAdded: EntityAdded       => entityAdded.validate
        case entityUpdated: EntityUpdated   => entityUpdated.validate
        case faultsListed: FaultsListed     => faultsListed.validate
        case faultAdded: FaultAdded         => faultAdded.validate
        case fault: Fault                   => fault.validate