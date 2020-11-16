package com.balbilo.registration.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.balbilo.registration.domain.AuthenticateService
import com.balbilo.registration.json.userDetailsDecoder
import com.balbilo.registration.model.{UserDetails, UserToken}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.util.{Failure, Success, Try}

final case class Authenticate(service: AuthenticateService)(implicit system: ActorSystem) {

  implicit val ec = system.dispatcher

  val route =
    (path("auth") & post & entity(as[UserDetails])) { userDetails =>
      onComplete(service.authenticate(userDetails)) {
        handleResponse
      }
    }

  private def handleResponse(response: Try[Either[String, UserToken]]): Route =
    response match {
      case Failure(exception) => complete(exception)
      case Success(success)   => ???
    }
}
