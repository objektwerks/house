package objektwerks

final class Handler(store: Store):
  def addHouse(entity: Entity): Event =
    EntityAdded( store.addHouse( entity.asInstanceOf[House] ) )
