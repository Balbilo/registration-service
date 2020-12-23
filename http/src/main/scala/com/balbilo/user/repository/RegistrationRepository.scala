package com.balbilo.user.repository

import com.balbilo.user.config.DatabaseConfig
import com.balbilo.user.model.{ServerError, UserToken}

import scala.concurrent.Future

trait RegistrationRepository {

  def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]]

}

final class RegistrationRepositoryMongo(config: DatabaseConfig) extends RegistrationRepository {

  override def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]] = ???
}
