package balbilo.registration.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import balbilo.registration.http.routes.{Live, VerifyUser}
import balbilo.registration.model.config.{ServerConfig, UserDetailsConfig}

import scala.concurrent.Future

final case class RegistrationServer(
    userDetailsConfig: UserDetailsConfig
)(implicit
    system: ActorSystem
) {
  import balbilo.registration.http.RegistrationServer._

  def bind(serverConfig: ServerConfig): Future[ServerBinding] = {
    Http().newServerAt(serverConfig.interface, serverConfig.port).bind(routes)
  }

  lazy val routes =
    handleExceptions(exceptionHandler) {
      concat(
        Live.route,
        VerifyUser.route(userDetailsConfig)
      )
    }
}

object RegistrationServer {

  lazy val exceptionHandler: ExceptionHandler = ExceptionHandler { _ =>
    extractUri { _ =>
      complete(StatusCodes.InternalServerError -> "InternalServerError")
    }
  }

}
