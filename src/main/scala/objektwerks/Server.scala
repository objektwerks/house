package objektwerks

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import io.helidon.webserver.WebServer
import io.helidon.webserver.http.HttpRouting

object Server extends LazyLogging:
  @main def main(): Unit =
    val config = ConfigFactory.load("server.conf")
    val host = config.getString("server.host")
    val port = config.getInt("server.port")
    val endpoint = config.getString("server.endpoint")

    val store = Store(config)
    val emailer = Emailer(config)
    val handler = Handler(store, emailer)
    val dispatcher = Dispatcher(handler)

    val exchanger = Exchanger(dispatcher)

    val builder = HttpRouting
      .builder
      .post(endpoint, exchanger)

    WebServer
      .builder
      .port(port)
      .routing(builder)
      .build
      .start

    println(s"*** Press Control-C to shutdown House Http Server at: $host:$port$endpoint")
    logger.info(s"*** House Http Server started at: $host:$port$endpoint")

    Thread.currentThread().join()
