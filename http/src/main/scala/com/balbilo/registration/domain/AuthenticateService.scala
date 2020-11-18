package com.balbilo.registration.domain

import com.balbilo.registration.model.{ServerError, UserDetails, UserToken}
import org.mongodb.scala.MongoClient

import scala.concurrent.Future

trait AuthenticateService {
  def authenticate(userDetails: UserDetails): Future[Either[ServerError, UserToken]]
}

final class AuthenticateServiceImpl(dataBaseDriver: MongoClient) extends AuthenticateService {
  override def authenticate(userDetails: UserDetails): Future[Either[ServerError, UserToken]] = ???
}
