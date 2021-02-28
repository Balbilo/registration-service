package com.balbilo.user.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, entity, onComplete, path, post}
import akka.http.scaladsl.server.Route
import com.balbilo.user.json._
import com.balbilo.user.model.{RegistrationError, UserDetails, UserTokens}
import com.balbilo.user.repository.Repositories
import com.balbilo.user.service.Services
import com.balbilo.user.utils.ExtensionMethods.EitherTOps
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

final case class Register(services: Services, repositories: Repositories)(implicit system: ActorSystem) {

  private implicit val ec = system.dispatcher

  val route =
    (path("register") & post & entity(as[UserDetails])) { userDetails =>
      val registered = (for {
        validDetails     <- Future.successful(services.validation.validateUserDetails(userDetails)).toEitherT
        encryptedDetails <- services.encryption.encryptPassword(validDetails).toEitherT
        userId           <- repositories.registrationRepository.registerUser(encryptedDetails).toEitherT
        userToken        <- repositories.authorizationRepository.authorizeNonVerifiedUser(userId).toEitherT
      } yield userToken).value

      onComplete(registered) {
        handleResponse
      }
    }

  private def handleResponse(response: Try[Either[RegistrationError, UserTokens]]): Route =
    response match {
      case Failure(exception) => complete(exception)
      case Success(success)   => handleResponse(success)
    }

  private def handleResponse(response: Either[RegistrationError, UserTokens]): Route =
    response match {
      case Right(userTokens) => complete(StatusCodes.OK -> userTokens)
      case Left(error)       => handleError(error)
    }

  private def handleError(error: RegistrationError): Route =
    error match {
      case e: RegistrationError => complete(StatusCodes.BadRequest -> e)
    }
}
