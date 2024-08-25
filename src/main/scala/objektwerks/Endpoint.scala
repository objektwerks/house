package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import ox.{ExitCode, IO, Ox, OxApp}

import sttp.tapir.*
import sttp.tapir.json.jsoniter.*

import Serializer.given

object Endpoint extends OxApp with LazyLogging:
  override def run(args: Vector[String])(using Ox, IO): ExitCode =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val path = config.getString("server.endpoint")

    println(s"*** Press Control-C to shutdown House Http Server at: $host:$port$path")
    logger.info(s"*** House Http Server started at: $host:$port$path")

    endpoint
      .post
      .in(jsonBody[Command])
      .out(jsonBody[Event])
      .handle { command => 
        logger.info(s"*** Endpoint command: $command")
        val event = dispatcher.dispatch(command)
        logger.info(s"*** Dispatcher event: $event")
        event
      }

    ExitCode.Success