package objektwerks

sealed trait Security
case object Authorized extends Security
final case class Unauthorized(cause: String = "Authorization failed. Invalid license.") extends Security
