package com.balbilo.registration.domain

import com.balbilo.registration.model.{ServerError, UserToken}
import org.mongodb.scala.MongoClient

import scala.concurrent.Future

trait RegisterService {
  def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]]
}

class RegisterServiceImpl(dataBaseDriver: MongoClient) extends RegisterService {
  override def registerUser(userToken: UserToken): Future[Either[ServerError, UserToken]] = ???
}
