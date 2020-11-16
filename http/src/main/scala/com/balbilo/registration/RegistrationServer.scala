package com.balbilo.registration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.balbilo.registration.config.HttpConfig

final case class RegistrationServer(
    httpConfig: HttpConfig
) {

  def createServer[AC : ActorSystem]() =
    Http().newServerAt(httpConfig.serverConfig.interface, httpConfig.serverConfig.port).bind(routes)

  def routes: Route = ???
}
