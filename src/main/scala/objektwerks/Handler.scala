package objektwerks

final class Handler(store: Store):
  def listHouses(accountId: Long): Event =
    HousesListed( store.listHouses(accountId) )

  def addHouse(entity: Entity): Event =
    EntityAdded( store.addHouse( entity.asInstanceOf[House] ) )
