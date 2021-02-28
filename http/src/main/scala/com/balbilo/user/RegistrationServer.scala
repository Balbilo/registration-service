package com.balbilo.user

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.concat
import akka.http.scaladsl.server.Route
import com.balbilo.user.config.ServerConfig
import com.balbilo.user.repository.Repositories
import com.balbilo.user.routes.Register
import com.balbilo.user.service.Services
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

final class RegistrationServer(
    config: ServerConfig,
    repositories: Repositories,
    services: Services
)(implicit system: ActorSystem)
    extends LazyLogging {

  def createServer(): Future[Http.ServerBinding] = {
    logger.info("Server started......")
    Http().newServerAt(config.interface, config.port).bind(routes)
  }

  private val register = Register(services, repositories)

  private def routes: Route =
    HttpHandler.exceptionsRejections {
      concat(register.route)
    }
}
