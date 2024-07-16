package objektwerks

sealed trait Security
case object Authorized extends Security
final case class Unauthorized(cause: String) extends Security
