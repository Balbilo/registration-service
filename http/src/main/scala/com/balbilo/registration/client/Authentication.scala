package com.balbilo.registration.client

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import com.balbilo.registration.config.AuthenticationConfig
import com.balbilo.registration.model.{AuthenticationError, UserToken}
import com.balbilo.registration.model.ValueClasses.Email

import scala.concurrent.Future

trait Authentication {
  def authenticateUserEmail(userToken: UserToken, email: Email): Future[Either[AuthenticationError, Done]]
}

final class AuthenticationImpl(config: AuthenticationConfig)(implicit val system: ActorSystem) extends Authentication {

  override def authenticateUserEmail(userToken: UserToken, email: Email): Future[Either[AuthenticationError, Done]] =
    Http().singleRequest(HttpRequest(Uri()))
}
