package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import ox.{ExitCode, IO, Ox, OxApp}

object Endpoint extends OxApp with LazyLogging:
  override def run(args: Vector[String])(using Ox, IO): ExitCode =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    
    ExitCode.Success
