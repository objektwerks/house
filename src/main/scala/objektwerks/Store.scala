package objektwerks

import com.github.blemale.scaffeine.{Cache, Scaffeine}
import com.typesafe.config.Config
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import scalikejdbc.*

import scala.concurrent.duration.FiniteDuration

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