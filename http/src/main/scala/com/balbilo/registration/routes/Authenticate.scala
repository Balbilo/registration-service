package com.balbilo.registration.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.data.EitherT
import com.balbilo.registration.domain.{AuthenticateService, UserDetailsValidation}
import com.balbilo.registration.json.userDetailsDecoder
import com.balbilo.registration.model.{ServerError, UserDetails, UserToken}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

final case class Authenticate(service: AuthenticateService, validation: UserDetailsValidation)(implicit system: ActorSystem) {

  implicit val ec = system.dispatcher

  val route =
    (path("auth") & post & entity(as[UserDetails])) { userDetails =>
      val authenticate = (for {
        validUserDetails <- EitherT(Future.successful(validation.validateUserDetails(userDetails)))
        userToken        <- EitherT(service.authenticate(validUserDetails))
      } yield userToken).value

      onComplete(authenticate) {
        handleResponse
      }
    }

  private def handleResponse(response: Try[Either[ServerError, UserToken]]): Route =
    response match {
      case Failure(exception) => complete(exception)
      case Success(success)   => ???
    }
}
