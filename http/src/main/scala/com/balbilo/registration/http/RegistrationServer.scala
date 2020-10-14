package com.balbilo.registration.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import com.balbilo.registration.http.config.HttpConfig
import com.balbilo.registration.http.routes.{Live, VerifyUser}

import scala.concurrent.Future

final case class RegistrationServer(
    httpConfig: HttpConfig
)(implicit
    system: ActorSystem
) {
  import RegistrationServer._

  def bind(): Future[ServerBinding] =
    Http().newServerAt(httpConfig.serverConfig.interface, httpConfig.serverConfig.port).bind(routes)

  lazy val routes =
    handleExceptions(exceptionHandler) {
      concat(
        Live.route,
        VerifyUser.route(httpConfig.verifyConfig)
      )
    }
}
