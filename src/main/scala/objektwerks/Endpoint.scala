package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import ox.{ExitCode, IO, Ox, OxApp}

//import Serializer.given

object Endpoint extends OxApp with LazyLogging:
  override def run(args: Vector[String])(using Ox, IO): ExitCode =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val path = config.getString("server.endpoint")

    println(s"*** Press Control-C to shutdown House Http Server at: $host:$port$path")
    logger.info(s"*** House Http Server started at: $host:$port$path")

    ExitCode.Success