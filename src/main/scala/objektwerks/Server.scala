package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import java.util.concurrent.Executors

import ox.{ExitCode, IO, never, Ox, OxApp, releaseAfterScope}

import sttp.tapir.*
import sttp.tapir.server.jdkhttp.JdkHttpServer

object Server extends OxApp with LazyLogging:
  override def run(args: Vector[String])(using Ox, IO): ExitCode =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val path = config.getString("server.path")

    val store = Store(config)
    val emailer = Emailer(config)
    val handler = Handler(store, emailer)
    val dispatcher = Dispatcher(handler)
    val endpoints = Endpoints(path, dispatcher, logger)

    val jdkHttpServer = JdkHttpServer()
      .executor( Executors.newVirtualThreadPerTaskExecutor() )
      .host(host)
      .port(port)
      .addEndpoint(endpoints.commandEndpoint)
      .addEndpoints(endpoints.swaggerEndpoints)
      .start()

    println(s"*** House Endpoint: ${endpoints.commandEndpoint.show}")
    println(s"*** Press Control-C to shutdown House Http Server at: $host:$port/$path")
    logger.info(s"*** House Endpoint: ${endpoints.commandEndpoint.show}")
    logger.info(s"*** House Http Server started at: $host:$port/$path")

    releaseAfterScope:
      println(s"*** House Http Server shutdown at: $host:$port")
      logger.info(s"*** House Http Server shutdown at: $host:$port")
      jdkHttpServer.stop(10)

    never

    ExitCode.Success