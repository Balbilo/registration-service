package com.balbilo.registration.domain

import java.time.OffsetDateTime
import java.util.UUID

import com.balbilo.registration.model.ValueClasses.Token
import com.balbilo.registration.model.{AuthenticationError, UserDetails, UserToken}
import org.mongodb.scala.MongoClient

import scala.concurrent.Future

trait AuthenticateService {
  def authenticate(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]]
}

final class AuthenticateServiceImpl(mongoClient: MongoClient) extends AuthenticateService {
  override def authenticate(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]] =
    Future.successful(Right(UserToken(Token(UUID.randomUUID().toString), OffsetDateTime.now.toEpochSecond)))
}
