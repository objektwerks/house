package objektwerks

import com.github.blemale.scaffeine.{Cache, Scaffeine}
import com.typesafe.config.Config
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import scala.concurrent.duration.FiniteDuration

import scalikejdbc.*

object Store:
  def cache(initialSize: Int,
            maxSize: Int,
            expireAfter: FiniteDuration): Cache[String, String] =
    Scaffeine()
      .initialCapacity(initialSize)
      .maximumSize(maxSize)
      .expireAfterWrite(expireAfter)
      .build[String, String]()

final class Store(config: Config,
                  cache: Cache[String, String]):
  private val dataSource: DataSource = {
    val ds = HikariDataSource()
    ds.setDataSourceClassName(config.getString("db.driver"))
    ds.addDataSourceProperty("url", config.getString("db.url"))
    ds.addDataSourceProperty("user", config.getString("db.user"))
    ds.addDataSourceProperty("password", config.getString("db.password"))
    ds
  }
  ConnectionPool.singleton( DataSourceConnectionPool(dataSource) )

  def register(account: Account): Long =
    addAccount(account)

  def login(email: String, pin: String): Option[Account] =
    DB readOnly { implicit session =>
      sql"select * from account where email = $email and pin = $pin"
        .map(rs =>
          Account(
            rs.long("id"),
            rs.string("license"),
            rs.string("email"),
            rs.string("pin"),
            rs.string("activated")
          )
        )
        .single()
    }

  def isAuthorized(license: String): Boolean =
    cache.getIfPresent(license) match
      case Some(_) =>
        true
      case None =>
        val optionalLicense = DB readOnly { implicit session =>
          sql"select license from account where license = $license"
            .map(rs => rs.string("license"))
            .single()
        }
        if optionalLicense.isDefined then
          cache.put(license, license)
          true
        else false

  def addAccount(account: Account): Long =
    DB localTx { implicit session =>
      sql"""
        insert into account(license, email, pin, activated)
        values(${account.license}, ${account.email}, ${account.pin}, ${account.activated})
      """
      .updateAndReturnGeneratedKey()
    }

  def listHouses(accountId: Long): List[House] =
    DB readOnly { implicit session =>
      sql"select * from house where account_id = $accountId order by built"
        .map(rs =>
          House(
            rs.long("id"),
            rs.long("account_id"),
            HouseType.valueOf( rs.string("typeof") ),
            rs.string("location"), 
            rs.string("built")
          )
        )
        .list()
    }

  def addHouse(house: House): Long =
    DB localTx { implicit session =>
      sql"""
        insert into house(account_id, typeof, location, built)
        values(${house.accountId}, ${house.typeof.toString}, ${house.location}, ${house.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateHouse(house: House): Int =
    DB localTx { implicit session =>
      sql"""
        update house set typeof = ${house.typeof.toString}, location = ${house.location}, built = ${house.built}
        where id = ${house.id}
        """
        .update()
    }

  def listFoundations(houseId: Long): List[Foundation] =
    DB readOnly { implicit session =>
      sql"select * from foundation where house_id = $houseId order by built"
        .map(rs =>
          Foundation(
            rs.long("id"),
            rs.long("house_id"),
            FoundationType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addFoundation(foundation: Foundation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into foundation(house_id, typeof, built)
        values(${foundation.homeId}, ${foundation.typeof.toString}, ${foundation.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFoundation(foundation: Foundation): Int =
    DB localTx { implicit session =>
      sql"""
        update foundation set typeof = ${foundation.typeof.toString}, built = ${foundation.built}
        where id = ${foundation.id}
        """
        .update()
    }

  def listFrames(houseId: Long): List[Frame] =
    DB readOnly { implicit session =>
      sql"select * from frame where house_id = $houseId order by built"
        .map(rs =>
          Frame(
            rs.long("id"),
            rs.long("house_id"),
            FrameType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addFrame(frame: Frame): Long =
    DB localTx { implicit session =>
      sql"""
        insert into frame(house_id, typeof, built)
        values(${frame.homeId}, ${frame.typeof.toString}, ${frame.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFrame(frame: Frame): Int =
    DB localTx { implicit session =>
      sql"""
        update frame set typeof = ${frame.typeof.toString}, built = ${frame.built}
        where id = ${frame.id}
        """
        .update()
    }

  def listAttics(houseId: Long): List[Attic] =
    DB readOnly { implicit session =>
      sql"select * from attic where house_id = $houseId order by built"
        .map(rs =>
          Attic(
            rs.long("id"),
            rs.long("house_id"),
            AtticType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addAttic(attic: Attic): Long =
    DB localTx { implicit session =>
      sql"""
        insert into attic(house_id, typeof, built)
        values(${attic.homeId}, ${attic.typeof.toString}, ${attic.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateAttic(attic: Attic): Int =
    DB localTx { implicit session =>
      sql"""
        update attic set typeof = ${attic.typeof.toString}, built = ${attic.built}
        where id = ${attic.id}
        """
        .update()
    }

  def listInsulations(houseId: Long): List[Insulation] =
    DB readOnly { implicit session =>
      sql"select * from insulation where house_id = $houseId order by installed"
        .map(rs =>
          Insulation(
            rs.long("id"),
            rs.long("house_id"),
            InsulationType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addInsulation(insulation: Insulation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into insulation(house_id, typeof, installed)
        values(${insulation.homeId}, ${insulation.typeof.toString}, ${insulation.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateInsulation(insulation: Insulation): Int =
    DB localTx { implicit session =>
      sql"""
        update insulation set typeof = ${insulation.typeof.toString}, installed = ${insulation.installed}
        where id = ${insulation.id}
        """
        .update()
    }

  def listVentilations(houseId: Long): List[Ventilation] =
    DB readOnly { implicit session =>
      sql"select * from ventilation where house_id = $houseId order by installed"
        .map(rs =>
          Ventilation(
            rs.long("id"),
            rs.long("house_id"),
            VentilationType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addVentilation(ventilation: Ventilation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into ventilation(house_id, typeof, installed)
        values(${ventilation.homeId}, ${ventilation.typeof.toString}, ${ventilation.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateVentilation(ventilation: Ventilation): Int =
    DB localTx { implicit session =>
      sql"""
        update ventilation set typeof = ${ventilation.typeof.toString}, installed = ${ventilation.installed}
        where id = ${ventilation.id}
        """
        .update()
    }

  def listRoofs(houseId: Long): List[Roof] =
    DB readOnly { implicit session =>
      sql"select * from roof where house_id = $houseId order by built"
        .map(rs =>
          Roof(
            rs.long("id"),
            rs.long("house_id"),
            RoofType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addRoof(roof: Roof): Long =
    DB localTx { implicit session =>
      sql"""
        insert into roof(house_id, typeof, built)
        values(${roof.homeId}, ${roof.typeof.toString}, ${roof.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateRoof(roof: Roof): Int =
    DB localTx { implicit session =>
      sql"""
        update roof set typeof = ${roof.typeof.toString}, built = ${roof.built}
        where id = ${roof.id}
        """
        .update()
    }

  def listChimneys(houseId: Long): List[Chimney] =
    DB readOnly { implicit session =>
      sql"select * from chimney where house_id = $houseId order by built"
        .map(rs =>
          Chimney(
            rs.long("id"),
            rs.long("house_id"),
            ChimneyType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addChimney(chimney: Chimney): Long =
    DB localTx { implicit session =>
      sql"""
        insert into chimney(house_id, typeof, built)
        values(${chimney.homeId}, ${chimney.typeof.toString}, ${chimney.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateChimney(chimney: Chimney): Int =
    DB localTx { implicit session =>
      sql"""
        update chimney set typeof = ${chimney.typeof.toString}, built = ${chimney.built}
        where id = ${chimney.id}
        """
        .update()
    }

  def listBalconys(houseId: Long): List[Balcony] =
    DB readOnly { implicit session =>
      sql"select * from balcony where house_id = $houseId order by built"
        .map(rs =>
          Balcony(
            rs.long("id"),
            rs.long("house_id"),
            BalconyType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addBalcony(balcony: Balcony): Long =
    DB localTx { implicit session =>
      sql"""
        insert into balcony(house_id, typeof, built)
        values(${balcony.homeId}, ${balcony.typeof.toString}, ${balcony.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateBalcony(balcony: Balcony): Int =
    DB localTx { implicit session =>
      sql"""
        update balcony set typeof = ${balcony.typeof.toString}, built = ${balcony.built}
        where id = ${balcony.id}
        """
        .update()
    }

  def listDrywalls(houseId: Long): List[Drywall] =
    DB readOnly { implicit session =>
      sql"select * from drywall where house_id = $houseId order by built"
        .map(rs =>
          Drywall(
            rs.long("id"),
            rs.long("house_id"),
            DrywallType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addDrywall(drywall: Drywall): Long =
    DB localTx { implicit session =>
      sql"""
        insert into drywall(house_id, typeof, built)
        values(${drywall.homeId}, ${drywall.typeof.toString}, ${drywall.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDrywall(drywall: Drywall): Int =
    DB localTx { implicit session =>
      sql"""
        update drywall set typeof = ${drywall.typeof.toString}, built = ${drywall.built}
        where id = ${drywall.id}
        """
        .update()
    }

  def listRooms(houseId: Long): List[Room] =
    DB readOnly { implicit session =>
      sql"select * from room where house_id = $houseId order by built"
        .map(rs =>
          Room(
            rs.long("id"),
            rs.long("house_id"),
            RoomType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addRoom(room: Room): Long =
    DB localTx { implicit session =>
      sql"""
        insert into room(house_id, typeof, built)
        values(${room.homeId}, ${room.typeof.toString}, ${room.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateRoom(room: Room): Int =
    DB localTx { implicit session =>
      sql"""
        update room set typeof = ${room.typeof.toString}, built = ${room.built}
        where id = ${room.id}
        """
        .update()
    }

  def listDriveways(houseId: Long): List[Driveway] =
    DB readOnly { implicit session =>
      sql"select * from driveway where house_id = $houseId order by built"
        .map(rs =>
          Driveway(
            rs.long("id"),
            rs.long("house_id"),
            DrivewayType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addDriveway(driveway: Driveway): Long =
    DB localTx { implicit session =>
      sql"""
        insert into driveway(house_id, typeof, built)
        values(${driveway.homeId}, ${driveway.typeof.toString}, ${driveway.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDriveway(driveway: Driveway): Int =
    DB localTx { implicit session =>
      sql"""
        update driveway set typeof = ${driveway.typeof.toString}, built = ${driveway.built}
        where id = ${driveway.id}
        """
        .update()
    }

  def listGarages(houseId: Long): List[Garage] =
    DB readOnly { implicit session =>
      sql"select * from garage where house_id = $houseId order by built"
        .map(rs =>
          Garage(
            rs.long("id"),
            rs.long("house_id"),
            GarageType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addGarage(garage: Garage): Long =
    DB localTx { implicit session =>
      sql"""
        insert into garage(house_id, typeof, built)
        values(${garage.homeId}, ${garage.typeof.toString}, ${garage.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGarage(garage: Garage): Int =
    DB localTx { implicit session =>
      sql"""
        update garage set typeof = ${garage.typeof.toString}, built = ${garage.built}
        where id = ${garage.id}
        """
        .update()
    }

  def listSidings(houseId: Long): List[Siding] =
    DB readOnly { implicit session =>
      sql"select * from siding where house_id = $houseId order by installed"
        .map(rs =>
          Siding(
            rs.long("id"),
            rs.long("house_id"),
            SidingType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSiding(siding: Siding): Long =
    DB localTx { implicit session =>
      sql"""
        insert into siding(house_id, typeof, installed)
        values(${siding.homeId}, ${siding.typeof.toString}, ${siding.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSiding(siding: Siding): Int =
    DB localTx { implicit session =>
      sql"""
        update siding set typeof = ${siding.typeof.toString}, installed = ${siding.installed}
        where id = ${siding.id}
        """
        .update()
    }

  def listGutters(houseId: Long): List[Gutter] =
    DB readOnly { implicit session =>
      sql"select * from gutter where house_id = $houseId order by installed"
        .map(rs =>
          Gutter(
            rs.long("id"),
            rs.long("house_id"),
            GutterType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addGutter(gutter: Gutter): Long =
    DB localTx { implicit session =>
      sql"""
        insert into gutter(house_id, typeof, installed)
        values(${gutter.homeId}, ${gutter.typeof.toString}, ${gutter.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGutter(gutter: Gutter): Int =
    DB localTx { implicit session =>
      sql"""
        update gutter set typeof = ${gutter.typeof.toString}, installed = ${gutter.installed}
        where id = ${gutter.id}
        """
        .update()
    }

  def listSoffits(houseId: Long): List[Soffit] =
    DB readOnly { implicit session =>
      sql"select * from soffit where house_id = $houseId order by installed"
        .map(rs =>
          Soffit(
            rs.long("id"),
            rs.long("house_id"),
            SoffitType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSoffit(soffit: Soffit): Long =
    DB localTx { implicit session =>
      sql"""
        insert into soffit(house_id, typeof, installed)
        values(${soffit.homeId}, ${soffit.typeof.toString}, ${soffit.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSoffit(soffit: Soffit): Int =
    DB localTx { implicit session =>
      sql"""
        update soffit set typeof = ${soffit.typeof.toString}, installed = ${soffit.installed}
        where id = ${soffit.id}
        """
        .update()
    }

  def listWindows(houseId: Long): List[Window] =
    DB readOnly { implicit session =>
      sql"select * from window where house_id = $houseId order by installed"
        .map(rs =>
          Window(
            rs.long("id"),
            rs.long("house_id"),
            WindowType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addWindow(window: Window): Long =
    DB localTx { implicit session =>
      sql"""
        insert into window(house_id, typeof, installed)
        values(${window.homeId}, ${window.typeof.toString}, ${window.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateWindow(window: Window): Int =
    DB localTx { implicit session =>
      sql"""
        update window set typeof = ${window.typeof.toString}, installed = ${window.installed}
        where id = ${window.id}
        """
        .update()
    }

  def listDoors(houseId: Long): List[Door] =
    DB readOnly { implicit session =>
      sql"select * from door where house_id = $houseId order by installed"
        .map(rs =>
          Door(
            rs.long("id"),
            rs.long("house_id"),
            DoorType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addDoor(door: Door): Long =
    DB localTx { implicit session =>
      sql"""
        insert into door(house_id, typeof, installed)
        values(${door.homeId}, ${door.typeof.toString}, ${door.installed})
        """
        .updateAndReturnGeneratedKey()
    }