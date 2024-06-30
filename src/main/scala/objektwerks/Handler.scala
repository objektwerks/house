package objektwerks

final class Handler(store: Store):
  def listEntities(typeof: EntityType, houseId: Long): Event = ???

  def addEntity(typeof: EntityType, entity: Entity): Event = ???

  def updateEntity(typeof: EntityType, entity: Entity): Event = ???

  def listHouses(accountId: Long): Event =
    HousesListed( store.listHouses(accountId) )

  def addHouse(entity: Entity): Event =
    EntityAdded( store.addHouse( entity.asInstanceOf[House] ) )

  def listFaults(): Event =
    FaultsListed( store.listFaults() )

  def addFault(fault: Fault): Event =
    store.addFault(fault)
    FaultAdded()
