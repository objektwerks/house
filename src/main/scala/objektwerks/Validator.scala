package objektwerks

object Validator:
  extension (value: String)
    def isEmptyOrNonEmpty: Boolean = value.isEmpty || value.nonEmpty
    def isLicense: Boolean = if value.nonEmpty && value.length == 36 then true else false
    def isPin: Boolean = value.length == 7
    def isEmail: Boolean = value.nonEmpty && value.length >= 3 && value.contains("@")

  extension (common: Common)
    def isCommon: Boolean =
      common.id >= 0
      common.houseId > 0 &&
      common.label.isEmptyOrNonEmpty &&
      common.note.isEmptyOrNonEmpty

  extension (built: Built)
    def isBuilt: Boolean =
      built.isCommon &&
      built.built.nonEmpty

  extension (installed: Installed)
    def isInstalled: Boolean =
      installed.isCommon &&
      installed.installed.nonEmpty

  extension (planted: Planted)
    def isPlanted: Boolean =
      planted.isCommon &&
      planted.planted.nonEmpty

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
      house.location.nonEmpty &&
      house.label.isEmptyOrNonEmpty &&
      house.note.isEmptyOrNonEmpty &&
      house.built.nonEmpty

  extension (drawing: Drawing)
    def isValid: Boolean =
      drawing.id >= 0 &&
      drawing.houseId > 0 &&
      drawing.url.nonEmpty &&
      drawing.note.isEmptyOrNonEmpty &&
      drawing.added.nonEmpty

  extension (issue: Issue)
    def isValid: Boolean =
      issue.id >= 0 &&
      issue.houseId > 0 &&
      issue.report.nonEmpty &&
      issue.resolution.isEmptyOrNonEmpty &&
      issue.reported.nonEmpty &&
      issue.resolved.nonEmpty

  extension (foundation: Foundation)
    def isValid: Boolean = foundation.isBuilt

  extension (frame: Frame)
    def isValid: Boolean = frame.isBuilt

  extension (attic: Attic)
    def isValid: Boolean = attic.isBuilt

  extension (insulation: Insulation)
    def isValid: Boolean = insulation.isInstalled

  extension (ductwork: Ductwork)
    def isValid: Boolean = ductwork.isInstalled

  extension (ventilation: Ventilation)
    def isValid: Boolean = ventilation.isInstalled

  extension (roof: Roof)
    def isValid: Boolean = roof.isBuilt

  extension (chimney: Chimney)
    def isValid: Boolean = chimney.isBuilt

  extension (balcony: Balcony)
    def isValid: Boolean = balcony.isBuilt

  extension (drywall: Drywall)
    def isValid: Boolean = drywall.isBuilt

  extension (room: Room)
    def isValid: Boolean = room.isBuilt

  extension (driveway: Driveway)
    def isValid: Boolean = driveway.isBuilt

  extension (garage: Garage)
    def isValid: Boolean = garage.isBuilt

  extension (siding: Siding)
    def isValid: Boolean = siding.isInstalled

  extension (gutter: Gutter)
    def isValid: Boolean = gutter.isInstalled

  extension (soffit: Soffit)
    def isValid: Boolean = soffit.isInstalled

  extension (window: Window)
    def isValid: Boolean = window.isInstalled

  extension (door: Door)
    def isValid: Boolean = door.isInstalled

  extension (plumbing: Plumbing)
    def isValid: Boolean = plumbing.isInstalled

  extension (electrical: Electrical)
    def isValid: Boolean = electrical.isInstalled

  extension (fusebox: Fusebox)
    def isValid: Boolean = fusebox.isInstalled

  extension (alarm: Alarm)
    def isValid: Boolean = alarm.isInstalled

  extension (heater: Heater)
    def isValid: Boolean = heater.isInstalled

  extension (ac: AirConditioner)
    def isValid: Boolean = ac.isInstalled

  extension (floor: Floor)
    def isValid: Boolean = floor.isInstalled

  extension (lighting: Lighting)
    def isValid: Boolean = lighting.isInstalled

  extension (sewage: Sewage)
    def isValid: Boolean = sewage.isBuilt

  extension (well: Well)
    def isValid: Boolean = well.isBuilt

  extension (water: Water)
    def isValid: Boolean = water.isInstalled

  extension (waterHeater: WaterHeater)
    def isValid: Boolean = waterHeater.isInstalled

  extension (lawn: Lawn)
    def isValid: Boolean = lawn.isPlanted

  extension (garden: Garden)
    def isValid: Boolean = garden.isPlanted

  extension (sprinkler: Sprinkler)
    def isValid: Boolean = sprinkler.isInstalled

  extension (shed: Shed)
    def isValid: Boolean = shed.isBuilt

  extension (solarPanel: SolarPanel)
    def isValid: Boolean = solarPanel.isInstalled

  extension (porch: Porch)
    def isValid: Boolean = porch.isBuilt

  extension (patio: Patio)
    def isValid: Boolean = patio.isBuilt

  extension (pool: Pool)
    def isValid: Boolean = pool.isBuilt

  extension (dock: Dock)
    def isValid: Boolean = dock.isBuilt

  extension (gazebo: Gazebo)
    def isValid: Boolean = gazebo.isBuilt

  extension (mailbox: Mailbox)
    def isValid: Boolean = mailbox.isInstalled

  extension (license: License)
    def isLicense: Boolean = license.license.isLicense

  extension (register: Register)
    def isValid: Boolean = register.email.isEmail

  extension (login: Login)
    def isValid: Boolean =
      login.email.isEmail &&
      login.pin.isPin

  extension (listEntities: ListEntities)
    def isValid: Boolean =
      listEntities.license.isLicense 
      listEntities.parentId > 0

  extension (addEntity: AddEntity)
    def isValid: Boolean =
      addEntity.license.isLicense &&
      validate(addEntity.entity)

  extension (updateEntity: UpdateEntity)
    def isValid: Boolean =
      updateEntity.license.isLicense &&
      validate(updateEntity.entity)

  extension (fault: Fault)
    def isValid: Boolean =
      fault.cause.nonEmpty &&
      fault.occurred.nonEmpty

  extension (listFaults: ListFaults)
    def isValid: Boolean = listFaults.license.isLicense

  extension (addFault: AddFault)
    def isValid: Boolean =
      addFault.license.isLicense &&
      addFault.fault.isValid

  extension (command: Command)
    def isValid: Boolean =
      command match
        case register: Register         => register.isValid
        case login: Login               => login.isValid
        case listEntities: ListEntities => listEntities.isValid
        case addEntity: AddEntity       => addEntity.isValid
        case updateEntity: UpdateEntity => updateEntity.isValid
        case listFaults: ListFaults     => listFaults.isValid
        case addFault: AddFault         => addFault.isValid

  def validate(entity: Entity): Boolean =
    entity match
      case account: Account => account.isValid
      case house: House => house.isValid
      case issue: Issue => issue.isValid
      case drawing: Drawing => drawing.isValid
      case foundation: Foundation => foundation.isValid
      case frame: Frame => frame.isValid
      case attic: Attic => attic.isValid
      case insulation: Insulation => insulation.isValid
      case ductwork: Ductwork => ductwork.isValid
      case ventilation: Ventilation => ventilation.isValid
      case roof: Roof => roof.isValid
      case chimney: Chimney => chimney.isValid
      case balcony: Balcony => balcony.isValid
      case drywall: Drywall => drywall.isValid
      case room: Room => room.isValid
      case driveway: Driveway => driveway.isValid
      case garage: Garage => garage.isValid
      case siding: Siding => siding.isValid
      case gutter: Gutter => gutter.isValid
      case soffit: Soffit => soffit.isValid
      case window: Window => window.isValid
      case door: Door => door.isValid
      case plumbing: Plumbing => plumbing.isValid
      case electrical: Electrical => electrical.isValid
      case fusebox: Fusebox => fusebox.isValid
      case alarm: Alarm => alarm.isValid
      case heater: Heater => heater.isValid
      case ac: AirConditioner => ac.isValid
      case floor: Floor => floor.isValid
      case lighting: Lighting => lighting.isValid
      case sewage: Sewage => sewage.isValid
      case well: Well => well.isValid
      case water: Water => water.isValid
      case watermelon: WaterHeater => watermelon.isValid
      case lawn: Lawn => lawn.isValid
      case garden: Garden => garden.isValid
      case sprinkler: Sprinkler => sprinkler.isValid
      case shed: Shed => shed.isValid
      case solarPanel: SolarPanel => solarPanel.isValid
      case porch: Porch => porch.isValid
      case patio: Patio => patio.isValid
      case pool: Pool => pool.isValid
      case dock: Dock => dock.isValid
      case gazebo: Gazebo => gazebo.isValid
      case mailbox: Mailbox => mailbox.isValid