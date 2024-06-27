package objektwerks

sealed trait Command

sealed trait License

final case class ListEntity(license: String, clazz: String) extends Command with License