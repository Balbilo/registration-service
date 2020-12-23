package com.balbilo.user.client

import com.balbilo.user.config.AuthorizationConfig
import com.balbilo.user.model.{AuthenticationError, UserDetails, UserToken}

import scala.concurrent.Future

trait Authorization {
  def authorizeUser(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]]
}

final class AuthorizationImpl(config: AuthorizationConfig) extends Authorization {
  override def authorizeUser(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]] = ???
}

