package com.balbilo.registration.domain

import com.balbilo.registration.model.{UserDetails, UserToken}

import scala.concurrent.Future

trait AuthenticateService {
  def authenticate(userDetails: UserDetails): Future[Either[String, UserToken]]
}

final class AuthenticateServiceImpl(validation: UserDetailsValidation) extends AuthenticateService {
  override def authenticate(userDetails: UserDetails): Future[Either[String, UserToken]] = ???
}
