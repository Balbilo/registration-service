package com.balbilo.registration.client

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, StatusCodes, Uri}
import com.balbilo.registration.config.AuthenticationConfig
import com.balbilo.registration.model.{AuthenticationError, UserToken}
import com.balbilo.registration.model.ValueClasses.Email

import scala.concurrent.Future

trait Authentication {
  def authenticateUserEmail(userToken: UserToken, email: Email): Future[Either[AuthenticationError, Done]]
}

final class AuthenticationImpl(config: AuthenticationConfig)(implicit val system: ActorSystem) extends Authentication {

  private implicit val ec = system.dispatcher
  private val endPoint = "/validateEmail"

  override def authenticateUserEmail(userToken: UserToken, email: Email): Future[Either[AuthenticationError, Done]] = {
    val uri = Uri(config.url).withPath(Uri.Path(endPoint))
    val request = HttpRequest(method = HttpMethods.DELETE,uri = uri)
    handleResponse(Http().singleRequest(request))
  }

  private def handleResponse(response: Future[HttpResponse]): Future[Either[AuthenticationError,Done]] = {
    response.map { res =>
      res.status match {
        case StatusCodes.OK => Right(Done)
        case _ => Left(AuthenticationError.UnknownError("Authenticate Error"))
      }
    }
  }
}
