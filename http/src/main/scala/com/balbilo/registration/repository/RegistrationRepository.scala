package com.balbilo.registration.repository

import com.balbilo.registration.config.DatabaseConfig
import com.balbilo.registration.model.{ServerError, UserToken}

import scala.concurrent.Future

trait RegistrationRepository {

  def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]]

}

final class RegistrationRepositoryMongo(config: DatabaseConfig) extends RegistrationRepository {

  override def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]] = ???
}
