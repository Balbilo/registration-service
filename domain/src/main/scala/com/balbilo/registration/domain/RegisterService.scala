package com.balbilo.registration.domain

import com.balbilo.registration.model.UserDetails

import scala.concurrent.Future

trait RegisterService {

  def authenticate(userDetails: UserDetails): Future[Either[DomainError, Boolean]]

  def register(userDetails: UserDetails): Future[Either[DomainError, String]]

}