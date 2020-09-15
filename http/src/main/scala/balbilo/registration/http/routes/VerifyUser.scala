package balbilo.registration.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, path, post}
import akka.http.scaladsl.server.Route
import balbilo.registration.model.config.UserDetailsConfig

private[http] object VerifyUser {

  def route(userDetailsConfig: UserDetailsConfig): Route =
    path("/verify") {
      post {
        complete(StatusCodes.OK -> s"Well Done: $userDetailsConfig")
      }
    }

}
