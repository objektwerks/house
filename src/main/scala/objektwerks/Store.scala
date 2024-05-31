package objektwerks

import com.typesafe.config.Config

import scala.collection.concurrent.TrieMap

import scalikejdbc.*

final class Store(config: Config):
  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val password = config.getString("db.password")

  val cache = TrieMap.empty[String, String]

  ConnectionPool.singleton(url, user, password)

  def register(account: Account): Account = addAccount(account)

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
    cache.get(license) match
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

  def addAccount(account: Account): Account =
    val id = DB localTx { implicit session =>
      sql"""
        insert into account(license, email, pin, activated)
        values(${account.license}, ${account.email}, ${account.pin}, ${account.activated})
      """
      .updateAndReturnGeneratedKey()
    }
    account.copy(id = id)

  def listHouses(accountId: Long): List[House] = DB readOnly { implicit session =>
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