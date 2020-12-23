package com.balbilo.registration.routes

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import cats.data.EitherT
import com.balbilo.registration.client.{Authorization, Clients}
import com.balbilo.registration.json._
import com.balbilo.registration.model.{AuthenticationError, UserDetails, UserToken}
import com.balbilo.registration.repository.AuthenticationRepository
import com.balbilo.registration.service.UserDetailsValidation
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

final case class Authenticate(service: UserDetailsValidation, repository: AuthenticationRepository, client: Clients)(implicit
    system: ActorSystem
) {

  private implicit val ec = system.dispatcher

  val route =
    (path("register") & post & entity(as[UserDetails])) { userDetails =>
      val authenticate = (for {
        valid     <- EitherT(Future.successful(service.validateUserDetails(userDetails)))
        userToken <- EitherT(client.authorization.authorizeUser(valid))
        _         <- EitherT(client.authentication.authenticateUserEmail(userToken, valid.email))
        userToken <- EitherT(repository.authenticateUser(valid))
      } yield userToken).value

      onComplete(authenticate) {
        handleResponse
      }
    }

  private def handleResponse(response: Try[Either[AuthenticationError, Done]]): Route =
    response match {
      case Failure(exception) => complete(exception)
      case Success(success)   => handleResponse(success)
    }

  private def handleResponse(response: Either[AuthenticationError, Done]): Route =
    response match {
      case Right(_)    => complete(StatusCodes.OK)
      case Left(error) => handleError(error)
    }

  private def handleError(error: AuthenticationError): Route =
    error match {
      case e: AuthenticationError.InvalidDetailsError => complete(StatusCodes.BadRequest -> e)
    }
}
