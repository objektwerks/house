package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import ox.{ExitCode, IO, Ox, OxApp}

import sttp.tapir.*

import Serializer.given

object Endpoint extends OxApp with LazyLogging:
  override def run(args: Vector[String])(using Ox, IO): ExitCode =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val path = config.getString("server.endpoint")

    val store = Store(config)
    val emailer = Emailer(config)
    val handler = Handler(store, emailer)
    val dispatcher = Dispatcher(handler)

    println(s"*** Press Control-C to shutdown House Http Server at: $host:$port$path")
    logger.info(s"*** House Http Server started at: $host:$port$path")

    endpoint
      .post
      .in(path)
      .in(stringBody)
      .out(stringBody)
      .handleSuccess { commandJson => 
        logger.info(s"*** command json: $commandJson")

        val command = readFromString[Command](commandJson)
        logger.info(s"*** command: $command")

        val event = dispatcher.dispatch(command)
        logger.info(s"*** event: $event")

        val eventJson = writeToString[Event](event)
        logger.info(s"*** event json: $eventJson")
        eventJson
      }

    ExitCode.Success