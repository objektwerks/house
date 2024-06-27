package objektwerks

sealed trait Command

sealed trait License:
  val license: String

final case class Register(email: String) extends Command

final case class Login(email: String, pin: String) extends Command

final case class ListEntity(license: String, typeof: EntityType, accountId: Long) extends Command with License

final case class AddEntity(license: String, typeof: EntityType, entity: Entity) extends Command with License

final case class UpdateEntity(license: String, typeof: EntityType, entity: Entity) extends Command with License
