package com.balbilo.user.repository

import akka.actor.ActorSystem
import com.balbilo.user.config.DatabaseConfig
import com.balbilo.user.model.{RegistrationError, UserTokens}
import org.mongodb.scala.MongoClient

import scala.concurrent.{ExecutionContext, Future}

trait AuthorizationRepository {

  def authorizeUser(userId: String): Future[Either[RegistrationError, UserTokens]]

  def authorizeNonVerifiedUser(userId: String): Future[Either[RegistrationError, UserTokens]]

}

final class AuthorizationRepositoryMongo(client: MongoClient, config: DatabaseConfig)(implicit system: ActorSystem)
    extends AuthorizationRepository {

  implicit val ec: ExecutionContext = system.dispatcher

  override def authorizeUser(userId: String): Future[Either[RegistrationError, UserTokens]] = ???

  override def authorizeNonVerifiedUser(userId: String): Future[Either[RegistrationError, UserTokens]] = Future {
    ???
  }
}
