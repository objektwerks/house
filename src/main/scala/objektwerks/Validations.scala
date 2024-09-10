package objektwerks

import Validator.*

object Validations:
  extension (value: String)
    def isEmptyOrNonEmpty: Boolean = value.isEmpty || value.nonEmpty
    def isLicense: Boolean = value.length == 36
    def isPin: Boolean = value.length == 7
    def isEmail: Boolean = value.length >= 3 && value.contains("@")

  extension (account: Account)
    def validate: Validator =
      Validator()
        .validate(account.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .validate(account.license.isLicense)(Field("license"), Message("Must be 36 characters in length."))
        .validate(account.email.isEmail)(Field("email"), Message("Must be at least 3 characters in length and contain 1 @ symbol."))
        .validate(account.pin.isPin)(Field("pin"), Message("Must be 7 characters in length."))
        .validate(account.activated.nonEmpty)(Field("activated"), Message("Must be non empty."))

  extension (house: House)
    def validate: Validator =
      Validator()
        .validate(house.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .validate(house.accountId > 0)(Field("accountId"), Message("Must be greater than 0."))
        .validate(house.label.isEmptyOrNonEmpty)(Field("label"), Message("Must be empty or non empty."))
        .validate(house.note.isEmptyOrNonEmpty)(Field("note"), Message("Must be empty or non empty."))
        .validate(house.architect.isEmptyOrNonEmpty)(Field("architect"), Message("Must be empty or non empty."))
        .validate(house.builder.isEmptyOrNonEmpty)(Field("builder"), Message("Must be empty or non empty."))
        .validate(house.built.nonEmpty)(Field("built"), Message("Must be non empty."))

  extension (drawing: Drawing)
    def validate: Validator =
      Validator()
        .validate(drawing.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .validate(drawing.houseId > 0)(Field("houseId"), Message("Must be greater than 0."))
        .validate(drawing.url.nonEmpty)(Field("url"), Message("Must be non empty."))
        .validate(drawing.note.isEmptyOrNonEmpty)(Field("note"), Message("Must be empty or non empty."))
        .validate(drawing.added.nonEmpty)(Field("added"), Message("Must be non empty."))

  extension (issue: Issue)
    def validate: Validator =
      Validator()
        .validate(issue.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .validate(issue.houseId > 0)(Field("houseId"), Message("Must be greater than 0."))
        .validate(issue.report.nonEmpty)(Field("report"), Message("Must be non empty."))
        .validate(issue.resolution.isEmptyOrNonEmpty)(Field("resolution"), Message("Must be empty or non empty."))
        .validate(issue.reported.nonEmpty)(Field("reported"), Message("Must be non empty."))
        .validate(issue.resolved.nonEmpty)(Field("resolved"), Message("Must be non empty."))

  extension (common: Common)
    def validateCommon: Validator =
      Validator()
        .validate(common.id >= 0)(Field("id"), Message("Must be greater than or equal to 0."))
        .validate(common.houseId > 0)(Field("houseId"), Message("Must be great than 0."))
        .validate(common.label.isEmptyOrNonEmpty)(Field("label"), Message("Must be empty or non empty."))
        .validate(common.note.isEmptyOrNonEmpty)(Field("note"), Message("Must be empty or non empty."))

  extension (built: Built)
    def validateBuilt: Validator =
      Validator()
        .validate(built.validateCommon)
        .validate(built.built.nonEmpty)(Field("built"), Message("Must be non empty."))

  extension (installed: Installed)
    def validateInstalled: Validator =
      Validator()
        .validate(installed.validateCommon)
        .validate(installed.installed.nonEmpty)(Field("installed"), Message("Must be non empty."))

  extension (planted: Planted)
    def validatePlanted: Validator =
      Validator()
        .validate(planted.validateCommon)
        .validate(planted.planted.nonEmpty)(Field("planted"), Message("Must be non empty."))

  extension (register: Register)
    def validate: Validator =
      Validator()
        .validate(register.email.isEmail)(Field("email"), Message("Must be at least 3 characters in length and contain 1 @ symbol."))

  extension (login: Login)
    def validate: Validator =
      Validator()
        .validate(login.email.isEmail)(Field("email"), Message("Must be at least 3 characters in length and contain 1 @ symbol."))
        .validate(login.pin.isPin)(Field("pin"), Message("Must be 7 characters in length."))

  extension (listEntities: ListEntities)
    def validate: Validator =
      Validator()
        .validate(listEntities.license.isLicense)(Field("license"), Message("Must be 36 characters in length."))
        .validate(listEntities.parentId > 0)(Field("parentId"), Message("Must be greater than 0."))

  extension (addEntity: AddEntity)
    def validate: Validator =
      Validator()
        .validate(addEntity.license.isLicense)(Field("license"), Message("Must be 36 characters in length."))
        .validate(validateEntity(addEntity.entity))(Field("entity"), Message("Entity must be valid."))

  def validateEntity(entity: Entity): Validator =
    entity match
      case account: Account => account.validate
      case house: House => house.validate
      case issue: Issue => issue.validate
      case drawing: Drawing => drawing.validate
      case built: Built => built.validateBuilt
      case installed: Installed => installed.validateInstalled
      case planted: Planted => planted.validatePlanted