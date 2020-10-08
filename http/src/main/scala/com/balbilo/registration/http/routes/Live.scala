package com.balbilo.registration.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

private[http] object Live {

  val route: Route =
    (path("live") and get) {
      complete(StatusCodes.OK)
    }
}
