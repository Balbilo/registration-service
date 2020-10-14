package com.balbilo.registration.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, path, post}
import akka.http.scaladsl.server.Route
import com.balbilo.registration.http.config.VerifyConfig

private[http] object VerifyUser {

  def route(userDetailsConfig: VerifyConfig): Route =
    path("/verify") {
      post {
        complete(StatusCodes.OK -> s"Well Done: $userDetailsConfig")
      }
    }

}
