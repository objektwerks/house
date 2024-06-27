package objektwerks

sealed trait Event

final case class Authorized(isAuthorized: Boolean) extends Event
