package objektwerks

import com.typesafe.scalalogging.Logger

import sttp.shared.Identity
import sttp.tapir.*
import sttp.tapir.json.jsoniter.*
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import Serializers.given
import Schemas.given

final class Endpoints(path: String,
                      dispatcher: Dispatcher,
                      logger: Logger):
  val commandEndpoint =
    endpoint
      .post
      .in(path)
      .in(jsonBody[Command])
      .out(jsonBody[Event])
      .handleSuccess { command =>
        logger.info(s"*** command: $command")
        val event = dispatcher.dispatch(command)
        logger.info(s"*** event: $event")
        event
      }

  val swaggerEndpoints =
    SwaggerInterpreter()
      .fromServerEndpoints[Identity](List(commandEndpoint), "House", "1.0")