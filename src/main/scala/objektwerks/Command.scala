package objektwerks

sealed trait Command

sealed trait License:
  val license: String

final case class Register(email: String) extends Command

final case class Login(email: String, pin: String) extends Command

final case class ListEntity(license: String, entityType: EntityType, accountId: Long) extends Command with License

final case class AddEntity(license: String, entity: Entity) extends Command with License

final case class UpdateEntity(license: String, entity: Entity) extends Command with License
