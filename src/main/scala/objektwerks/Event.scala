package objektwerks

import java.time.LocalDate

sealed trait Event

final case class Authorized(isAuthorized: Boolean) extends Event

final case class Registered(account: Account) extends Event

final case class LoggedIn(account: Account) extends Event

final case class HousesListed(houses: List[House]) extends Event

final case class EntitiesListed(entities: List[Entity]) extends Event

final case class EntityAdded(id: Long) extends Event

final case class EntityUpdated(count: Int) extends Event

final case class Fault(cause: String, occurred: String = LocalDate.now.toString) extends Event

final case class FaultsListed(faults: List[Fault]) extends Event

final case class FaultAdded() extends Event
