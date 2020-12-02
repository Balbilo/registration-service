package com.balbilo.registration.domain

import java.time.OffsetDateTime
import java.util.UUID

import com.balbilo.registration.model.ValueClasses.Token
import com.balbilo.registration.model.{RegisterError, UserToken}
import org.mongodb.scala.MongoClient

import scala.concurrent.Future

trait RegisterService {
  def registerUser(userToken: UserToken): Future[Either[RegisterError, UserToken]]
}

final class RegisterServiceImpl(mongoClient: MongoClient) extends RegisterService {
  override def registerUser(userToken: UserToken): Future[Either[RegisterError, UserToken]] =
    Future.successful(Right(UserToken(Token(UUID.randomUUID().toString), OffsetDateTime.now.toEpochSecond)))
}
