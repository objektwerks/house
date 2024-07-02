package objektwerks

sealed trait Command

sealed trait License:
  val license: String

final case class Register(email: String) extends Command

final case class Login(email: String, pin: String) extends Command

final case class ListEntities(license: String, typeof: EntityType, parentId: Long) extends Command with License

final case class AddEntity(license: String, typeof: EntityType, entity: Entity) extends Command with License

final case class UpdateEntity(license: String, typeof: EntityType, entity: Entity) extends Command with License

final case class ListFaults(license: String) extends Command with License

final case class AddFault(license: String, fault: Fault) extends Command with License
