package com.balbilo.registration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.concat
import com.balbilo.registration.client.Clients
import com.balbilo.registration.config.ServerConfig
import com.balbilo.registration.repository.Repositories
import com.balbilo.registration.service.Services
import com.balbilo.registration.routes.{Authenticate, Register}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

final class RegistrationServer(
    config: ServerConfig,
    repositories: Repositories,
    clients: Clients,
    services: Services
)(implicit system: ActorSystem)
    extends LazyLogging {

  def createServer(): Future[Http.ServerBinding] = {
    logger.info("Server started......")
    Http().newServerAt(config.interface, config.port).bind(routes)
  }

  private val authenticate = Authenticate(services.validation, repositories.authenticationRepository, clients)

  private val register = Register(repositories.registrationRepository)

  private def routes: Route =
    HttpHandler.exceptionsRejections {
      concat(authenticate.route, register.route)
    }
}
