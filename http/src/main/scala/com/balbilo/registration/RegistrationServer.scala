package com.balbilo.registration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.concat
import com.balbilo.registration.config.ServerConfig
import com.balbilo.registration.domain.Domain
import com.balbilo.registration.routes.{Authenticate, Register}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

final class RegistrationServer(
    config: ServerConfig,
    services: Domain
)(implicit system: ActorSystem) extends LazyLogging{

  def createServer(): Future[Http.ServerBinding] = {
    logger.info("Server started......")
    Http().newServerAt(config.interface, config.port).bind(routes)
  }

  private def routes: Route = {
    services.handlingService.handler{
      concat(Authenticate(services.authentication, services.validation).route, Register(services.registration).route)
    }
  }
}
