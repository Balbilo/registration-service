package balbilo.registration.http.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, path, post}
import balbilo.registration.model.UserDetails

private[http] object VerifyUserDetails {

  def route(userDetails: UserDetails) =
    path("/v1/verify") {
      post {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

}
