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
      sql"select * from house where account_id = $accountId order by built desc"
        .map(rs =>
          House(
            rs.long("id"),
            rs.long("account_id"),
            HouseType.valueOf( rs.string("typeof") ),
            rs.string("location"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addHouse(house: House): Long =
    DB localTx { implicit session =>
      sql"""
        insert into house(account_id, typeof, location, note, built)
        values(${house.accountId}, ${house.typeof.toString}, ${house.location}, ${house.note}, ${house.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateHouse(house: House): Int =
    DB localTx { implicit session =>
      sql"""
        update house set typeof = ${house.typeof.toString}, location = ${house.location}, note = ${house.note}, built = ${house.built}
        where id = ${house.id}
        """
        .update()
    }

  def listFoundations(houseId: Long): List[Foundation] =
    DB readOnly { implicit session =>
      sql"select * from foundation where house_id = $houseId order by built desc"
        .map(rs =>
          Foundation(
            rs.long("id"),
            rs.long("house_id"),
            FoundationType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addFoundation(foundation: Foundation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into foundation(house_id, typeof, label, note, built)
        values(${foundation.houseId}, ${foundation.typeof.toString}, ${foundation.label}), ${foundation.note}), ${foundation.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFoundation(foundation: Foundation): Int =
    DB localTx { implicit session =>
      sql"""
        update foundation set typeof = ${foundation.typeof.toString}, label = ${foundation.label}, note = ${foundation.note},
        built = ${foundation.built} where id = ${foundation.id}
        """
        .update()
    }

  def listFrames(houseId: Long): List[Frame] =
    DB readOnly { implicit session =>
      sql"select * from frame where house_id = $houseId order by built desc"
        .map(rs =>
          Frame(
            rs.long("id"),
            rs.long("house_id"),
            FrameType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addFrame(frame: Frame): Long =
    DB localTx { implicit session =>
      sql"""
        insert into frame(house_id, typeof, label, note, built)
        values(${frame.houseId}, ${frame.typeof.toString},  ${frame.label}),  ${frame.note}), ${frame.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFrame(frame: Frame): Int =
    DB localTx { implicit session =>
      sql"""
        update frame set typeof = ${frame.typeof.toString}, label = ${frame.label}, note = ${frame.note}, built = ${frame.built}
        where id = ${frame.id}
        """
        .update()
    }

  def listAttics(houseId: Long): List[Attic] =
    DB readOnly { implicit session =>
      sql"select * from attic where house_id = $houseId order by built desc"
        .map(rs =>
          Attic(
            rs.long("id"),
            rs.long("house_id"),
            AtticType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addAttic(attic: Attic): Long =
    DB localTx { implicit session =>
      sql"""
        insert into attic(house_id, typeof, label, note, built)
        values(${attic.houseId}, ${attic.typeof.toString}, ${attic.label}, ${attic.note}, ${attic.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateAttic(attic: Attic): Int =
    DB localTx { implicit session =>
      sql"""
        update attic set typeof = ${attic.typeof.toString},  label = ${attic.label},  note = ${attic.note}, built = ${attic.built}
        where id = ${attic.id}
        """
        .update()
    }

  def listInsulations(houseId: Long): List[Insulation] =
    DB readOnly { implicit session =>
      sql"select * from insulation where house_id = $houseId order by installed desc"
        .map(rs =>
          Insulation(
            rs.long("id"),
            rs.long("house_id"),
            InsulationType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addInsulation(insulation: Insulation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into insulation(house_id, typeof, label, note, installed)
        values(${insulation.houseId}, ${insulation.typeof.toString}, ${insulation.label}, ${insulation.note}, ${insulation.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateInsulation(insulation: Insulation): Int =
    DB localTx { implicit session =>
      sql"""
        update insulation set typeof = ${insulation.typeof.toString}, label = ${insulation.label}, note = ${insulation.note},
        installed = ${insulation.installed} where id = ${insulation.id}
        """
        .update()
    }

  def listDuctworks(houseId: Long): List[Ductwork] =
    DB readOnly { implicit session =>
      sql"select * from ductwork where house_id = $houseId order by installed desc"
        .map(rs =>
          Ductwork(
            rs.long("id"),
            rs.long("house_id"),
            DuctworkType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addDuctwork(ductwork: Ductwork): Long =
    DB localTx { implicit session =>
      sql"""
        insert into ductwork(house_id, typeof, label, note, installed)
        values(${ductwork.houseId}, ${ductwork.typeof.toString}, ${ductwork.label}, ${ductwork.note}, ${ductwork.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDuctwork(ductwork: Ductwork): Int =
    DB localTx { implicit session =>
      sql"""
        update ductwork set typeof = ${ductwork.typeof.toString}, label = ${ductwork.label}, note = ${ductwork.note},
        installed = ${ductwork.installed} where id = ${ductwork.id}
        """
        .update()
    }

  def listVentilations(houseId: Long): List[Ventilation] =
    DB readOnly { implicit session =>
      sql"select * from ventilation where house_id = $houseId order by installed desc"
        .map(rs =>
          Ventilation(
            rs.long("id"),
            rs.long("house_id"),
            VentilationType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addVentilation(ventilation: Ventilation): Long =
    DB localTx { implicit session =>
      sql"""
        insert into ventilation(house_id, typeof, label, note, installed)
        values(${ventilation.houseId}, ${ventilation.typeof.toString}, ${ventilation.label}, ${ventilation.note}, ${ventilation.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateVentilation(ventilation: Ventilation): Int =
    DB localTx { implicit session =>
      sql"""
        update ventilation set typeof = ${ventilation.typeof.toString}, label = ${ventilation.label}, note = ${ventilation.note},
        installed = ${ventilation.installed} where id = ${ventilation.id}
        """
        .update()
    }

  def listRoofs(houseId: Long): List[Roof] =
    DB readOnly { implicit session =>
      sql"select * from roof where house_id = $houseId order by built desc"
        .map(rs =>
          Roof(
            rs.long("id"),
            rs.long("house_id"),
            RoofType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addRoof(roof: Roof): Long =
    DB localTx { implicit session =>
      sql"""
        insert into roof(house_id, typeof, label, note, built)
        values(${roof.houseId}, ${roof.typeof.toString}, ${roof.label}, ${roof.note}, ${roof.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateRoof(roof: Roof): Int =
    DB localTx { implicit session =>
      sql"""
        update roof set typeof = ${roof.typeof.toString}, label = ${roof.label}, note = ${roof.note}, built = ${roof.built}
        where id = ${roof.id}
        """
        .update()
    }

  def listChimneys(houseId: Long): List[Chimney] =
    DB readOnly { implicit session =>
      sql"select * from chimney where house_id = $houseId order by built desc"
        .map(rs =>
          Chimney(
            rs.long("id"),
            rs.long("house_id"),
            ChimneyType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addChimney(chimney: Chimney): Long =
    DB localTx { implicit session =>
      sql"""
        insert into chimney(house_id, typeof, label, note, built)
        values(${chimney.houseId}, ${chimney.typeof.toString}, ${chimney.label}, ${chimney.note}, ${chimney.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateChimney(chimney: Chimney): Int =
    DB localTx { implicit session =>
      sql"""
        update chimney set typeof = ${chimney.typeof.toString}, label = ${chimney.label}, note = ${chimney.note}, built = ${chimney.built}
        where id = ${chimney.id}
        """
        .update()
    }

  def listBalconys(houseId: Long): List[Balcony] =
    DB readOnly { implicit session =>
      sql"select * from balcony where house_id = $houseId order by built desc"
        .map(rs =>
          Balcony(
            rs.long("id"),
            rs.long("house_id"),
            BalconyType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addBalcony(balcony: Balcony): Long =
    DB localTx { implicit session =>
      sql"""
        insert into balcony(house_id, typeof, label, note, built)
        values(${balcony.houseId}, ${balcony.typeof.toString}, ${balcony.label}, ${balcony.note}, ${balcony.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateBalcony(balcony: Balcony): Int =
    DB localTx { implicit session =>
      sql"""
        update balcony set typeof = ${balcony.typeof.toString}, label = ${balcony.label}, note = ${balcony.note}, built = ${balcony.built}
        where id = ${balcony.id}
        """
        .update()
    }

  def listDrywalls(houseId: Long): List[Drywall] =
    DB readOnly { implicit session =>
      sql"select * from drywall where house_id = $houseId order by built desc"
        .map(rs =>
          Drywall(
            rs.long("id"),
            rs.long("house_id"),
            DrywallType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addDrywall(drywall: Drywall): Long =
    DB localTx { implicit session =>
      sql"""
        insert into drywall(house_id, typeof, label, note, built)
        values(${drywall.houseId}, ${drywall.typeof.toString}, ${drywall.label}, ${drywall.note}, ${drywall.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDrywall(drywall: Drywall): Int =
    DB localTx { implicit session =>
      sql"""
        update drywall set typeof = ${drywall.typeof.toString}, label = ${drywall.label}, note = ${drywall.note}, built = ${drywall.built}
        where id = ${drywall.id}
        """
        .update()
    }

  def listRooms(houseId: Long): List[Room] =
    DB readOnly { implicit session =>
      sql"select * from room where house_id = $houseId order by built desc"
        .map(rs =>
          Room(
            rs.long("id"),
            rs.long("house_id"),
            RoomType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addRoom(room: Room): Long =
    DB localTx { implicit session =>
      sql"""
        insert into room(house_id, typeof, label, note, built)
        values(${room.houseId}, ${room.typeof.toString}, ${room.label}, ${room.note}, ${room.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateRoom(room: Room): Int =
    DB localTx { implicit session =>
      sql"""
        update room set typeof = ${room.typeof.toString}, label = ${room.label}, note = ${room.note}, built = ${room.built}
        where id = ${room.id}
        """
        .update()
    }

  def listDriveways(houseId: Long): List[Driveway] =
    DB readOnly { implicit session =>
      sql"select * from driveway where house_id = $houseId order by built desc"
        .map(rs =>
          Driveway(
            rs.long("id"),
            rs.long("house_id"),
            DrivewayType.valueOf( rs.string("typeof") ),
            CulvertType.valueOf( rs.string("culvert_typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addDriveway(driveway: Driveway): Long =
    DB localTx { implicit session =>
      sql"""
        insert into driveway(house_id, typeof, culvert_typeof, label, note, built)
        values(${driveway.houseId}, ${driveway.typeof.toString}, ${driveway.culvertTypeof.toString}, ${driveway.label}, ${driveway.note},
        ${driveway.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDriveway(driveway: Driveway): Int =
    DB localTx { implicit session =>
      sql"""
        update driveway set typeof = ${driveway.typeof.toString}, culvert_typeof = ${driveway.culvertTypeof.toString},
        label = ${driveway.label}, note = ${driveway.note}, built = ${driveway.built}
        where id = ${driveway.id}
        """
        .update()
    }

  def listGarages(houseId: Long): List[Garage] =
    DB readOnly { implicit session =>
      sql"select * from garage where house_id = $houseId order by built desc"
        .map(rs =>
          Garage(
            rs.long("id"),
            rs.long("house_id"),
            GarageType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("built")
          )
        )
        .list()
    }

  def addGarage(garage: Garage): Long =
    DB localTx { implicit session =>
      sql"""
        insert into garage(house_id, typeof, label, note, built)
        values(${garage.houseId}, ${garage.typeof.toString}, ${garage.label}, ${garage.note},${garage.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGarage(garage: Garage): Int =
    DB localTx { implicit session =>
      sql"""
        update garage set typeof = ${garage.typeof.toString}, label = ${garage.label}, note = ${garage.note}, built = ${garage.built}
        where id = ${garage.id}
        """
        .update()
    }

  def listSidings(houseId: Long): List[Siding] =
    DB readOnly { implicit session =>
      sql"select * from siding where house_id = $houseId order by installed desc"
        .map(rs =>
          Siding(
            rs.long("id"),
            rs.long("house_id"),
            SidingType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSiding(siding: Siding): Long =
    DB localTx { implicit session =>
      sql"""
        insert into siding(house_id, typeof, label, note,installed)
        values(${siding.houseId}, ${siding.typeof.toString}, ${siding.label}, ${siding.note}, ${siding.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSiding(siding: Siding): Int =
    DB localTx { implicit session =>
      sql"""
        update siding set typeof = ${siding.typeof.toString}, label = ${siding.label}, note = ${siding.note},
        installed = ${siding.installed} where id = ${siding.id}
        """
        .update()
    }

  def listGutters(houseId: Long): List[Gutter] =
    DB readOnly { implicit session =>
      sql"select * from gutter where house_id = $houseId order by installed desc"
        .map(rs =>
          Gutter(
            rs.long("id"),
            rs.long("house_id"),
            GutterType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addGutter(gutter: Gutter): Long =
    DB localTx { implicit session =>
      sql"""
        insert into gutter(house_id, typeof, label, note, installed)
        values(${gutter.houseId}, ${gutter.typeof.toString}, ${gutter.label}, ${gutter.note}, ${gutter.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGutter(gutter: Gutter): Int =
    DB localTx { implicit session =>
      sql"""
        update gutter set typeof = ${gutter.typeof.toString}, label = ${gutter.label}, note = ${gutter.note},
        installed = ${gutter.installed} where id = ${gutter.id}
        """
        .update()
    }

  def listSoffits(houseId: Long): List[Soffit] =
    DB readOnly { implicit session =>
      sql"select * from soffit where house_id = $houseId order by installed desc"
        .map(rs =>
          Soffit(
            rs.long("id"),
            rs.long("house_id"),
            SoffitType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSoffit(soffit: Soffit): Long =
    DB localTx { implicit session =>
      sql"""
        insert into soffit(house_id, typeof, label, note, installed)
        values(${soffit.houseId}, ${soffit.typeof.toString}, ${soffit.label}, ${soffit.note}, ${soffit.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSoffit(soffit: Soffit): Int =
    DB localTx { implicit session =>
      sql"""
        update soffit set typeof = ${soffit.typeof.toString}, label = ${soffit.label}, note = ${soffit.note},
        installed = ${soffit.installed} where id = ${soffit.id}
        """
        .update()
    }

  def listWindows(houseId: Long): List[Window] =
    DB readOnly { implicit session =>
      sql"select * from wndow where house_id = $houseId order by installed desc"
        .map(rs =>
          Window(
            rs.long("id"),
            rs.long("house_id"),
            WindowType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addWindow(window: Window): Long =
    DB localTx { implicit session =>
      sql"""
        insert into wndow(house_id, typeof, label, note, installed)
        values(${window.houseId}, ${window.typeof.toString}, ${window.label}, ${window.note}, ${window.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateWindow(window: Window): Int =
    DB localTx { implicit session =>
      sql"""
        update wndow set typeof = ${window.typeof.toString}, label = ${window.label}, note = ${window.note},
        installed = ${window.installed} where id = ${window.id}
        """
        .update()
    }

  def listDoors(houseId: Long): List[Door] =
    DB readOnly { implicit session =>
      sql"select * from door where house_id = $houseId order by installed desc"
        .map(rs =>
          Door(
            rs.long("id"),
            rs.long("house_id"),
            DoorType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addDoor(door: Door): Long =
    DB localTx { implicit session =>
      sql"""
        insert into door(house_id, typeof, label, note, installed)
        values(${door.houseId}, ${door.typeof.toString}, ${door.label}, ${door.note}, ${door.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDoor(door: Door): Int =
    DB localTx { implicit session =>
      sql"""
        update door set typeof = ${door.typeof.toString}, label = ${door.label}, note = ${door.note},
        installed = ${door.installed} where id = ${door.id}
        """
        .update()
    }

  def listPlumbings(houseId: Long): List[Plumbing] =
    DB readOnly { implicit session =>
      sql"select * from plumbing where house_id = $houseId order by installed desc"
        .map(rs =>
          Plumbing(
            rs.long("id"),
            rs.long("house_id"),
            PlumbingType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addPlumbing(plumbing: Plumbing): Long =
    DB localTx { implicit session =>
      sql"""
        insert into plumbing(house_id, typeof, label, note, installed)
        values(${plumbing.houseId}, ${plumbing.typeof.toString}, ${plumbing.label}, ${plumbing.note}, ${plumbing.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updatePlumbing(plumbing: Plumbing): Int =
    DB localTx { implicit session =>
      sql"""
        update plumbing set typeof = ${plumbing.typeof.toString}, label = ${plumbing.label}, note = ${plumbing.note},
        installed = ${plumbing.installed} where id = ${plumbing.id}
        """
        .update()
    }

  def listElectricals(houseId: Long): List[Electrical] =
    DB readOnly { implicit session =>
      sql"select * from electrical where house_id = $houseId order by installed desc"
        .map(rs =>
          Electrical(
            rs.long("id"),
            rs.long("house_id"),
            ElectricalType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addElectrical(electrical: Electrical): Long =
    DB localTx { implicit session =>
      sql"""
        insert into electrical(house_id, typeof, label, note, installed)
        values(${electrical.houseId}, ${electrical.typeof.toString}, ${electrical.label}, ${electrical.note}, ${electrical.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateElectrical(electrical: Electrical): Int =
    DB localTx { implicit session =>
      sql"""
        update electrical set typeof = ${electrical.typeof.toString}, label = ${electrical.label}, note = ${electrical.note},
        installed = ${electrical.installed} where id = ${electrical.id}
        """
        .update()
    }

  def listFuseboxes(houseId: Long): List[Fusebox] =
    DB readOnly { implicit session =>
      sql"select * from fusebox where house_id = $houseId order by installed desc"
        .map(rs =>
          Fusebox(
            rs.long("id"),
            rs.long("house_id"),
            FuseboxType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addFusebox(fusebox: Fusebox): Long =
    DB localTx { implicit session =>
      sql"""
        insert into fusebox(house_id, typeof, label, note, installed)
        values(${fusebox.houseId}, ${fusebox.typeof.toString}, ${fusebox.label}, ${fusebox.note}, ${fusebox.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFusebox(fusebox: Fusebox): Int =
    DB localTx { implicit session =>
      sql"""
        update fusebox set typeof = ${fusebox.typeof.toString}, label = ${fusebox.label}, note = ${fusebox.note},
        installed = ${fusebox.installed} where id = ${fusebox.id}
        """
        .update()
    }

  def listAlarms(houseId: Long): List[Alarm] =
    DB readOnly { implicit session =>
      sql"select * from alarm where house_id = $houseId order by installed desc"
        .map(rs =>
          Alarm(
            rs.long("id"),
            rs.long("house_id"),
            AlarmType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addAlarm(alarm: Alarm): Long =
    DB localTx { implicit session =>
      sql"""
        insert into alarm(house_id, typeof, label, note, installed)
        values(${alarm.houseId}, ${alarm.typeof.toString}, ${alarm.label}, ${alarm.note}, ${alarm.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateAlarm(alarm: Alarm): Int =
    DB localTx { implicit session =>
      sql"""
        update alarm set typeof = ${alarm.typeof.toString}, label = ${alarm.label}, note = ${alarm.note},
        installed = ${alarm.installed} where id = ${alarm.id}
        """
        .update()
    }

  def listHeaters(houseId: Long): List[Heater] =
    DB readOnly { implicit session =>
      sql"select * from heater where house_id = $houseId order by installed desc"
        .map(rs =>
          Heater(
            rs.long("id"),
            rs.long("house_id"),
            HeaterType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addHeater(heater: Heater): Long =
    DB localTx { implicit session =>
      sql"""
        insert into heater(house_id, typeof, label, note, installed)
        values(${heater.houseId}, ${heater.typeof.toString}, ${heater.label}, ${heater.note}, ${heater.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateHeater(heater: Heater): Int =
    DB localTx { implicit session =>
      sql"""
        update heater set typeof = ${heater.typeof.toString}, label = ${heater.label}, note = ${heater.note},
        installed = ${heater.installed} where id = ${heater.id}
        """
        .update()
    }

  def listAirConditioners(houseId: Long): List[AirConditioner] =
    DB readOnly { implicit session =>
      sql"select * from ac where house_id = $houseId order by installed desc"
        .map(rs =>
          AirConditioner(
            rs.long("id"),
            rs.long("house_id"),
            AirConditionerType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addAirConditioner(ac: AirConditioner): Long =
    DB localTx { implicit session =>
      sql"""
        insert into ac(house_id, typeof, label, note, installed)
        values(${ac.houseId}, ${ac.typeof.toString}, ${ac.label}, ${ac.note}, ${ac.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateAirConditioner(ac: AirConditioner): Int =
    DB localTx { implicit session =>
      sql"""
        update ac set typeof = ${ac.typeof.toString}, label = ${ac.label}, note = ${ac.note},installed = ${ac.installed}
        where id = ${ac.id}
        """
        .update()
    }

  def listFloors(houseId: Long): List[Floor] =
    DB readOnly { implicit session =>
      sql"select * from floor where house_id = $houseId order by installed desc"
        .map(rs =>
          Floor(
            rs.long("id"),
            rs.long("house_id"),
            FloorType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addFloor(floor: Floor): Long =
    DB localTx { implicit session =>
      sql"""
        insert into floor(house_id, typeof, label, note, installed)
        values(${floor.houseId}, ${floor.typeof.toString}, ${floor.label}, ${floor.note}, ${floor.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateFloor(floor: Floor): Int =
    DB localTx { implicit session =>
      sql"""
        update floor set typeof = ${floor.typeof.toString}, label = ${floor.label}, note = ${floor.note},
        installed = ${floor.installed} where id = ${floor.id}
        """
        .update()
    }

  def listLightings(houseId: Long): List[Lighting] =
    DB readOnly { implicit session =>
      sql"select * from lighting where house_id = $houseId order by installed desc"
        .map(rs =>
          Lighting(
            rs.long("id"),
            rs.long("house_id"),
            LightingType.valueOf( rs.string("typeof") ),
            rs.string("label"),
            rs.string("note"),
            rs.string("installed")
          )
        )
        .list()
    }

  def addLighting(lighting: Lighting): Long =
    DB localTx { implicit session =>
      sql"""
        insert into lighting(house_id, typeof, label, note, installed)
        values(${lighting.houseId}, ${lighting.typeof.toString}, ${lighting.label}, ${lighting.note}, ${lighting.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateLighting(lighting: Lighting): Int =
    DB localTx { implicit session =>
      sql"""
        update lighting set typeof = ${lighting.typeof.toString}, installed = ${lighting.installed}
        where id = ${lighting.id}
        """
        .update()
    }

  def listSewages(houseId: Long): List[Sewage] =
    DB readOnly { implicit session =>
      sql"select * from sewage where house_id = $houseId order by built desc"
        .map(rs =>
          Sewage(
            rs.long("id"),
            rs.long("house_id"),
            SewageType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addSewage(sewage: Sewage): Long =
    DB localTx { implicit session =>
      sql"""
        insert into sewage(house_id, typeof, built)
        values(${sewage.houseId}, ${sewage.typeof.toString}, ${sewage.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSewage(sewage: Sewage): Int =
    DB localTx { implicit session =>
      sql"""
        update sewage set typeof = ${sewage.typeof.toString}, built = ${sewage.built}
        where id = ${sewage.id}
        """
        .update()
    }

  def listWells(houseId: Long): List[Well] =
    DB readOnly { implicit session =>
      sql"select * from well where house_id = $houseId order by built desc"
        .map(rs =>
          Well(
            rs.long("id"),
            rs.long("house_id"),
            WellType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addWell(well: Well): Long =
    DB localTx { implicit session =>
      sql"""
        insert into well(house_id, typeof, built)
        values(${well.houseId}, ${well.typeof.toString}, ${well.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateWell(well: Well): Int =
    DB localTx { implicit session =>
      sql"""
        update well set typeof = ${well.typeof.toString}, built = ${well.built}
        where id = ${well.id}
        """
        .update()
    }

  def listWaters(houseId: Long): List[Water] =
    DB readOnly { implicit session =>
      sql"select * from water where house_id = $houseId order by installed desc"
        .map(rs =>
          Water(
            rs.long("id"),
            rs.long("house_id"),
            WaterType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addWater(water: Water): Long =
    DB localTx { implicit session =>
      sql"""
        insert into water(house_id, typeof, installed)
        values(${water.houseId}, ${water.typeof.toString}, ${water.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateWater(water: Water): Int =
    DB localTx { implicit session =>
      sql"""
        update water set typeof = ${water.typeof.toString}, installed = ${water.installed}
        where id = ${water.id}
        """
        .update()
    }

  def listWaterHeaters(houseId: Long): List[WaterHeater] =
    DB readOnly { implicit session =>
      sql"select * from water_heater where house_id = $houseId order by installed desc"
        .map(rs =>
          WaterHeater(
            rs.long("id"),
            rs.long("house_id"),
            WaterHeaterType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addWaterHeater(waterHeater: WaterHeater): Long =
    DB localTx { implicit session =>
      sql"""
        insert into water_heater(house_id, typeof, installed)
        values(${waterHeater.houseId}, ${waterHeater.typeof.toString}, ${waterHeater.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateWaterHeater(waterHeater: WaterHeater): Int =
    DB localTx { implicit session =>
      sql"""
        update water_heater set typeof = ${waterHeater.typeof.toString}, installed = ${waterHeater.installed}
        where id = ${waterHeater.id}
        """
        .update()
    }

  def listLawns(houseId: Long): List[Lawn] =
    DB readOnly { implicit session =>
      sql"select * from lawn where house_id = $houseId order by planted desc"
        .map(rs =>
          Lawn(
            rs.long("id"),
            rs.long("house_id"),
            LawnType.valueOf( rs.string("typeof") ),
            rs.string("planted")
          )
        )
        .list()
    }

  def addLawn(lawn: Lawn): Long =
    DB localTx { implicit session =>
      sql"""
        insert into lawn(house_id, typeof, planted)
        values(${lawn.houseId}, ${lawn.typeof.toString}, ${lawn.planted})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateLawn(lawn: Lawn): Int =
    DB localTx { implicit session =>
      sql"""
        update lawn set typeof = ${lawn.typeof.toString}, planted = ${lawn.planted}
        where id = ${lawn.id}
        """
        .update()
    }

  def listGardens(houseId: Long): List[Garden] =
    DB readOnly { implicit session =>
      sql"select * from garden where house_id = $houseId order by planted desc"
        .map(rs =>
          Garden(
            rs.long("id"),
            rs.long("house_id"),
            GardenType.valueOf( rs.string("typeof") ),
            rs.string("planted")
          )
        )
        .list()
    }

  def addGarden(garden: Garden): Long =
    DB localTx { implicit session =>
      sql"""
        insert into garden(house_id, typeof, planted)
        values(${garden.houseId}, ${garden.typeof.toString}, ${garden.planted})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGarden(garden: Garden): Int =
    DB localTx { implicit session =>
      sql"""
        update garden set typeof = ${garden.typeof.toString}, planted = ${garden.planted}
        where id = ${garden.id}
        """
        .update()
    }

  def listSprinklers(houseId: Long): List[Sprinkler] =
    DB readOnly { implicit session =>
      sql"select * from sprinkler where house_id = $houseId order by installed desc"
        .map(rs =>
          Sprinkler(
            rs.long("id"),
            rs.long("house_id"),
            SprinklerType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSprinkler(sprinkler: Sprinkler): Long =
    DB localTx { implicit session =>
      sql"""
        insert into sprinkler(house_id, typeof, installed)
        values(${sprinkler.houseId}, ${sprinkler.typeof.toString}, ${sprinkler.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSprinkler(sprinkler: Sprinkler): Int =
    DB localTx { implicit session =>
      sql"""
        update sprinkler set typeof = ${sprinkler.typeof.toString}, installed = ${sprinkler.installed}
        where id = ${sprinkler.id}
        """
        .update()
    }

  def listSheds(houseId: Long): List[Shed] =
    DB readOnly { implicit session =>
      sql"select * from shed where house_id = $houseId order by built desc"
        .map(rs =>
          Shed(
            rs.long("id"),
            rs.long("house_id"),
            ShedType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addShed(shed: Shed): Long =
    DB localTx { implicit session =>
      sql"""
        insert into shed(house_id, typeof, built)
        values(${shed.houseId}, ${shed.typeof.toString}, ${shed.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateShed(shed: Shed): Int =
    DB localTx { implicit session =>
      sql"""
        update shed set typeof = ${shed.typeof.toString}, built = ${shed.built}
        where id = ${shed.id}
        """
        .update()
    }

  def listSolarPanels(houseId: Long): List[SolarPanel] =
    DB readOnly { implicit session =>
      sql"select * from solarpanel where house_id = $houseId order by installed desc"
        .map(rs =>
          SolarPanel(
            rs.long("id"),
            rs.long("house_id"),
            SolarPanelType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addSolarPanel(solarpanel: SolarPanel): Long =
    DB localTx { implicit session =>
      sql"""
        insert into solarpanel(house_id, typeof, installed)
        values(${solarpanel.houseId}, ${solarpanel.typeof.toString}, ${solarpanel.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateSolarPanel(solarpanel: SolarPanel): Int =
    DB localTx { implicit session =>
      sql"""
        update solarpanel set typeof = ${solarpanel.typeof.toString}, installed = ${solarpanel.installed}
        where id = ${solarpanel.id}
        """
        .update()
    }

  def listPorches(houseId: Long): List[Porch] =
    DB readOnly { implicit session =>
      sql"select * from porch where house_id = $houseId order by built desc"
        .map(rs =>
          Porch(
            rs.long("id"),
            rs.long("house_id"),
            PorchType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addPorch(porch: Porch): Long =
    DB localTx { implicit session =>
      sql"""
        insert into porch(house_id, typeof, built)
        values(${porch.houseId}, ${porch.typeof.toString}, ${porch.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updatePorch(porch: Porch): Int =
    DB localTx { implicit session =>
      sql"""
        update porch set typeof = ${porch.typeof.toString}, built = ${porch.built}
        where id = ${porch.id}
        """
        .update()
    }

  def listPatios(houseId: Long): List[Patio] =
    DB readOnly { implicit session =>
      sql"select * from patio where house_id = $houseId order by built desc"
        .map(rs =>
          Patio(
            rs.long("id"),
            rs.long("house_id"),
            PatioType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addPatio(patio: Patio): Long =
    DB localTx { implicit session =>
      sql"""
        insert into patio(house_id, typeof, built)
        values(${patio.houseId}, ${patio.typeof.toString}, ${patio.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updatePatio(patio: Patio): Int =
    DB localTx { implicit session =>
      sql"""
        update patio set typeof = ${patio.typeof.toString}, built = ${patio.built}
        where id = ${patio.id}
        """
        .update()
    }

  def listPools(houseId: Long): List[Pool] =
    DB readOnly { implicit session =>
      sql"select * from pool where house_id = $houseId order by built desc"
        .map(rs =>
          Pool(
            rs.long("id"),
            rs.long("house_id"),
            PoolType.valueOf( rs.string("typeof") ),
            rs.int("gallons"),
            rs.boolean("caged"),
            rs.string("built")
          )
        )
        .list()
    }

  def addPool(pool: Pool): Long =
    DB localTx { implicit session =>
      sql"""
        insert into pool(house_id, typeof, gallons, caged, built)
        values(${pool.houseId}, ${pool.typeof.toString}, ${pool.gallons}, ${pool.caged}, ${pool.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updatePool(pool: Pool): Int =
    DB localTx { implicit session =>
      sql"""
        update pool set typeof = ${pool.typeof.toString}, gallons = ${pool.gallons}, caged = ${pool.caged}, built = ${pool.built}
        where id = ${pool.id}
        """
        .update()
    }

  def listDocks(houseId: Long): List[Dock] =
    DB readOnly { implicit session =>
      sql"select * from dock where house_id = $houseId order by built desc"
        .map(rs =>
          Dock(
            rs.long("id"),
            rs.long("house_id"),
            DockType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addDock(dock: Dock): Long =
    DB localTx { implicit session =>
      sql"""
        insert into dock(house_id, typeof, built)
        values(${dock.houseId}, ${dock.typeof.toString}, ${dock.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateDock(dock: Dock): Int =
    DB localTx { implicit session =>
      sql"""
        update dock set typeof = ${dock.typeof.toString}, built = ${dock.built}
        where id = ${dock.id}
        """
        .update()
    }

  def listGazebos(houseId: Long): List[Gazebo] =
    DB readOnly { implicit session =>
      sql"select * from gazebo where house_id = $houseId order by built desc"
        .map(rs =>
          Gazebo(
            rs.long("id"),
            rs.long("house_id"),
            GazeboType.valueOf( rs.string("typeof") ),
            rs.string("built")
          )
        )
        .list()
    }

  def addGazebo(gazebo: Gazebo): Long =
    DB localTx { implicit session =>
      sql"""
        insert into gazebo(house_id, typeof, built)
        values(${gazebo.houseId}, ${gazebo.typeof.toString}, ${gazebo.built})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateGazebo(gazebo: Gazebo): Int =
    DB localTx { implicit session =>
      sql"""
        update gazebo set typeof = ${gazebo.typeof.toString}, built = ${gazebo.built}
        where id = ${gazebo.id}
        """
        .update()
    }

  def listMailboxes(houseId: Long): List[Mailbox] =
    DB readOnly { implicit session =>
      sql"select * from mailbox where house_id = $houseId order by installed desc"
        .map(rs =>
          Mailbox(
            rs.long("id"),
            rs.long("house_id"),
            MailboxType.valueOf( rs.string("typeof") ),
            rs.string("installed")
          )
        )
        .list()
    }

  def addMailbox(mailbox: Mailbox): Long =
    DB localTx { implicit session =>
      sql"""
        insert into mailbox(house_id, typeof, installed)
        values(${mailbox.houseId}, ${mailbox.typeof.toString}, ${mailbox.installed})
        """
        .updateAndReturnGeneratedKey()
    }

  def updateMailbox(mailbox: Mailbox): Int =
    DB localTx { implicit session =>
      sql"""
        update mailbox set typeof = ${mailbox.typeof.toString}, installed = ${mailbox.installed}
        where id = ${mailbox.id}
        """
        .update()
    }