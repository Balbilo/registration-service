package com.balbilo.registration.client

import com.balbilo.registration.config.AuthorizationConfig
import com.balbilo.registration.model.{AuthenticationError, UserDetails, UserToken}

import scala.concurrent.Future

trait Authorization {
  def authorizeUser(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]]
}

final class AuthorizationImpl(config: AuthorizationConfig) extends Authorization {
  override def authorizeUser(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]] = ???
}

