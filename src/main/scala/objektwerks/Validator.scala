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
    def isValid: Boolean =
      foundation.id >= 0 &&
      foundation.houseId > 0 &&
      foundation.label.isEmptyOrNonEmpty &&
      foundation.note.isEmptyOrNonEmpty &&
      foundation.built.nonEmpty

  extension (frame: Frame)
    def isValid: Boolean =
      frame.id >= 0 &&
      frame.houseId > 0 &&
      frame.label.isEmptyOrNonEmpty &&
      frame.note.isEmptyOrNonEmpty &&
      frame.built.nonEmpty

  extension (attic: Attic)
    def isValid: Boolean =
      attic.id >= 0 &&
      attic.houseId > 0 &&
      attic.label.isEmptyOrNonEmpty &&
      attic.note.isEmptyOrNonEmpty &&
      attic.built.nonEmpty

  extension (insulation: Insulation)
    def isValid: Boolean =
      insulation.id >= 0 &&
      insulation.houseId > 0 &&
      insulation.label.isEmptyOrNonEmpty &&
      insulation.note.isEmptyOrNonEmpty &&
      insulation.installed.nonEmpty

  extension (ductwork: Ductwork)
    def isValid: Boolean =
      ductwork.id >= 0 &&
      ductwork.houseId > 0 &&
      ductwork.label.isEmptyOrNonEmpty &&
      ductwork.note.isEmptyOrNonEmpty &&
      ductwork.installed.nonEmpty

  extension (ventilation: Ventilation)
    def isValid: Boolean =
      ventilation.id >= 0 &&
      ventilation.houseId > 0 &&
      ventilation.label.isEmptyOrNonEmpty &&
      ventilation.note.isEmptyOrNonEmpty &&
      ventilation.installed.nonEmpty

  extension (roof: Roof)
    def isValid: Boolean =
      roof.id >= 0 &&
      roof.houseId > 0 &&
      roof.label.isEmptyOrNonEmpty &&
      roof.note.isEmptyOrNonEmpty &&
      roof.built.nonEmpty

  extension (chimney: Chimney)
    def isValid: Boolean =
      chimney.id >= 0 &&
      chimney.houseId > 0 &&
      chimney.label.isEmptyOrNonEmpty &&
      chimney.note.isEmptyOrNonEmpty &&
      chimney.built.nonEmpty

  extension (balcony: Balcony)
    def isValid: Boolean =
      balcony.id >= 0 &&
      balcony.houseId > 0 &&
      balcony.label.isEmptyOrNonEmpty &&
      balcony.note.isEmptyOrNonEmpty &&
      balcony.built.nonEmpty

  extension (drywall: Drywall)
    def isValid: Boolean =
      drywall.id >= 0 &&
      drywall.houseId > 0 &&
      drywall.label.isEmptyOrNonEmpty &&
      drywall.note.isEmptyOrNonEmpty &&
      drywall.built.nonEmpty

  extension (room: Room)
    def isValid: Boolean =
      room.id >= 0 &&
      room.houseId > 0 &&
      room.label.isEmptyOrNonEmpty &&
      room.note.isEmptyOrNonEmpty &&
      room.built.nonEmpty

  extension (driveway: Driveway)
    def isValid: Boolean =
      driveway.id >= 0 &&
      driveway.houseId > 0 &&
      driveway.label.isEmptyOrNonEmpty &&
      driveway.note.isEmptyOrNonEmpty &&
      driveway.built.nonEmpty

  extension (garage: Garage)
    def isValid: Boolean =
      garage.id >= 0 &&
      garage.houseId > 0 &&
      garage.label.isEmptyOrNonEmpty &&
      garage.note.isEmptyOrNonEmpty &&
      garage.built.nonEmpty

  extension (siding: Siding)
    def isValid: Boolean =
      siding.id >= 0 &&
      siding.houseId > 0 &&
      siding.label.isEmptyOrNonEmpty &&
      siding.note.isEmptyOrNonEmpty &&
      siding.installed.nonEmpty

  extension (gutter: Gutter)
    def isValid: Boolean =
      gutter.id >= 0 &&
      gutter.houseId > 0 &&
      gutter.label.isEmptyOrNonEmpty &&
      gutter.note.isEmptyOrNonEmpty &&
      gutter.installed.nonEmpty

  extension (soffit: Soffit)
    def isValid: Boolean =
      soffit.id >= 0 &&
      soffit.houseId > 0 &&
      soffit.label.isEmptyOrNonEmpty &&
      soffit.note.isEmptyOrNonEmpty &&
      soffit.installed.nonEmpty

  extension (window: Window)
    def isValid: Boolean =
      window.id >= 0 &&
      window.houseId > 0 &&
      window.label.isEmptyOrNonEmpty &&
      window.note.isEmptyOrNonEmpty &&
      window.installed.nonEmpty

  extension (door: Door)
    def isValid: Boolean =
      door.id >= 0 &&
      door.houseId > 0 &&
      door.label.isEmptyOrNonEmpty &&
      door.note.isEmptyOrNonEmpty &&
      door.installed.nonEmpty

  extension (plumbing: Plumbing)
    def isValid: Boolean =
      plumbing.id >= 0 &&
      plumbing.houseId > 0 &&
      plumbing.label.isEmptyOrNonEmpty &&
      plumbing.note.isEmptyOrNonEmpty &&
      plumbing.installed.nonEmpty

  extension (electrical: Electrical)
    def isValid: Boolean =
      electrical.id >= 0 &&
      electrical.houseId > 0 &&
      electrical.label.isEmptyOrNonEmpty &&
      electrical.note.isEmptyOrNonEmpty &&
      electrical.installed.nonEmpty

  extension (fusebox: Fusebox)
    def isValid: Boolean =
      fusebox.id >= 0 &&
      fusebox.houseId > 0 &&
      fusebox.label.isEmptyOrNonEmpty &&
      fusebox.note.isEmptyOrNonEmpty &&
      fusebox.installed.nonEmpty

  extension (alarm: Alarm)
    def isValid: Boolean =
      alarm.id >= 0 &&
      alarm.houseId > 0 &&
      alarm.label.isEmptyOrNonEmpty &&
      alarm.note.isEmptyOrNonEmpty &&
      alarm.installed.nonEmpty

  extension (heater: Heater)
    def isValid: Boolean =
      heater.id >= 0 &&
      heater.houseId > 0 &&
      heater.label.isEmptyOrNonEmpty &&
      heater.note.isEmptyOrNonEmpty &&
      heater.installed.nonEmpty

  extension (ac: AirConditioner)
    def isValid: Boolean =
      ac.id >= 0 &&
      ac.houseId > 0 &&
      ac.label.isEmptyOrNonEmpty &&
      ac.note.isEmptyOrNonEmpty &&
      ac.installed.nonEmpty

  extension (floor: Floor)
    def isValid: Boolean =
      floor.id >= 0 &&
      floor.houseId > 0 &&
      floor.label.isEmptyOrNonEmpty &&
      floor.note.isEmptyOrNonEmpty &&
      floor.installed.nonEmpty

  extension (lighting: Lighting)
    def isValid: Boolean =
      lighting.id >= 0 &&
      lighting.houseId > 0 &&
      lighting.label.isEmptyOrNonEmpty &&
      lighting.note.isEmptyOrNonEmpty &&
      lighting.installed.nonEmpty

  extension (sewage: Sewage)
    def isValid: Boolean =
      sewage.id >= 0 &&
      sewage.houseId > 0 &&
      sewage.label.isEmptyOrNonEmpty &&
      sewage.note.isEmptyOrNonEmpty &&
      sewage.built.nonEmpty

  extension (well: Well)
    def isValid: Boolean =
      well.id >= 0 &&
      well.houseId > 0 &&
      well.label.isEmptyOrNonEmpty &&
      well.note.isEmptyOrNonEmpty &&
      well.built.nonEmpty

  extension (water: Water)
    def isValid: Boolean =
      water.id >= 0 &&
      water.houseId > 0 &&
      water.label.isEmptyOrNonEmpty &&
      water.note.isEmptyOrNonEmpty &&
      water.installed.nonEmpty

  extension (waterHeater: WaterHeater)
    def isValid: Boolean =
      waterHeater.id >= 0 &&
      waterHeater.houseId > 0 &&
      waterHeater.label.isEmptyOrNonEmpty &&
      waterHeater.note.isEmptyOrNonEmpty &&
      waterHeater.installed.nonEmpty

  extension (lawn: Lawn)
    def isValid: Boolean =
      lawn.id >= 0 &&
      lawn.houseId > 0 &&
      lawn.label.isEmptyOrNonEmpty &&
      lawn.note.isEmptyOrNonEmpty &&
      lawn.planted.nonEmpty

  extension (garden: Garden)
    def isValid: Boolean =
      garden.id >= 0 &&
      garden.houseId > 0 &&
      garden.label.isEmptyOrNonEmpty &&
      garden.note.isEmptyOrNonEmpty &&
      garden.planted.nonEmpty

  extension (sprinkler: Sprinkler)
    def isValid: Boolean =
      sprinkler.id >= 0 &&
      sprinkler.houseId > 0 &&
      sprinkler.label.isEmptyOrNonEmpty &&
      sprinkler.note.isEmptyOrNonEmpty &&
      sprinkler.installed.nonEmpty

  extension (shed: Shed)
    def isValid: Boolean =
      shed.id >= 0 &&
      shed.houseId > 0 &&
      shed.label.isEmptyOrNonEmpty &&
      shed.note.isEmptyOrNonEmpty &&
      shed.built.nonEmpty

  extension (solarPanel: SolarPanel)
    def isValid: Boolean =
      solarPanel.id >= 0 &&
      solarPanel.houseId > 0 &&
      solarPanel.label.isEmptyOrNonEmpty &&
      solarPanel.note.isEmptyOrNonEmpty &&
      solarPanel.installed.nonEmpty

  extension (porch: Porch)
    def isValid: Boolean =
      porch.id >= 0 &&
      porch.houseId > 0 &&
      porch.label.isEmptyOrNonEmpty &&
      porch.note.isEmptyOrNonEmpty &&
      porch.built.nonEmpty

  extension (patio: Patio)
    def isValid: Boolean =
      patio.id >= 0 &&
      patio.houseId > 0 &&
      patio.label.isEmptyOrNonEmpty &&
      patio.note.isEmptyOrNonEmpty &&
      patio.built.nonEmpty

  extension (pool: Pool)
    def isValid: Boolean =
      pool.id >= 0 &&
      pool.houseId > 0 &&
      pool.gallons >= 1000 &&
      pool.label.isEmptyOrNonEmpty &&
      pool.note.isEmptyOrNonEmpty &&
      pool.built.nonEmpty

  extension (dock: Dock)
    def isValid: Boolean =
      dock.id >= 0 &&
      dock.houseId > 0 &&
      dock.label.isEmptyOrNonEmpty &&
      dock.note.isEmptyOrNonEmpty &&
      dock.built.nonEmpty

  extension (gazebo: Gazebo)
    def isValid: Boolean =
      gazebo.id >= 0 &&
      gazebo.houseId > 0 &&
      gazebo.label.isEmptyOrNonEmpty &&
      gazebo.note.isEmptyOrNonEmpty &&
      gazebo.built.nonEmpty

  extension (mailbox: Mailbox)
    def isValid: Boolean =
      mailbox.id >= 0 &&
      mailbox.houseId > 0 &&
      mailbox.label.isEmptyOrNonEmpty &&
      mailbox.note.isEmptyOrNonEmpty &&
      mailbox.installed.nonEmpty

  extension (license: License)
    def isLicense: Boolean = license.license.isLicense

  extension (register: Register)
    def isValid: Boolean =
      register.email.isEmail

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