package objektwerks

sealed trait Event

final case class Authorized(isAuthorized: Boolean) extends Event

final case class Registered(account: Account) extends Event

final case class LoggedIn(account: Account) extends Event

