package com.balbilo.registration.http

import akka.actor.ActorSystem
import com.balbilo.registration.http.config.HttpConfig

final case class RegistrationServer(
    httpConfig: HttpConfig
)(implicit
    system: ActorSystem
) {

//  def bind(): Future[ServerBinding] =
//    Http().newServerAt(httpConfig.serverConfig.interface, httpConfig.serverConfig.port).bind(routes)
//
//  lazy val routes =
//    handleExceptions(exceptionHandler) {
//      concat(
//        Live.route,
//        VerifyUser.route(httpConfig.verifyConfig)
//      )
//    }
}
