package com.balbilo.registration.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, entity, onComplete, path, post}
import akka.http.scaladsl.server.Route
import com.balbilo.registration.domain.RegisterService
import com.balbilo.registration.json.{userTokenDecoder, userTokenEncoder}
import com.balbilo.registration.model.{RegisterError, UserToken}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._

import scala.util.{Failure, Success, Try}

final case class Register(service: RegisterService)(implicit system: ActorSystem) {

  private implicit val dispatcher = system.dispatcher

  val route =
    (path("register") & post & entity(as[UserToken])) { userToken =>
      val register = service.registerUser(userToken)
      onComplete(register)(handleResponse)
    }

  private def handleResponse(register: Try[Either[RegisterError, UserToken]]): Route =
    register match {
      case Success(success)   => handleResponse(success)
      case Failure(exception) => complete(exception)
    }

  private def handleResponse(register: Either[RegisterError, UserToken]): Route =
    register match {
      case Right(userToken) => complete(StatusCodes.OK, userToken)
      case Left(userToken)  => ???
    }
}
