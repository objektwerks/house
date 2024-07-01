package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.typesafe.scalalogging.LazyLogging

import io.helidon.webserver.http.{Handler => WebHandler, ServerRequest, ServerResponse}

import Serializer.given

final class Exchanger(dispatcher: Dispatcher,
                      handler: Handler) extends WebHandler with LazyLogging:
  override def handle(request: ServerRequest,
                      response: ServerResponse): Unit =
    val commandJson = request.content.as(classOf[String])
    logger.info(s"*** Exchanger command json: $commandJson")

    val command = readFromString[Command](commandJson)
    logger.info(s"*** Exchanger command: $command")

    val event = dispatcher.dispatch(command)
    logger.info(s"*** Exchanger event: $event")
    event match
      case fault @ Fault(_, _) =>
        logger.error(s"*** Handler fault: $fault")
        handler.addFault(fault)
      case _ =>
    val eventJson = writeToString[Event](event)
    logger.info(s"*** Exchanger event json: $eventJson")

    response
      .status(200)
      .header("Content-Type", "application/json; charset=UTF-8")
      .send(eventJson)
