package com.balbilo.registration.repository

import akka.Done
import com.balbilo.registration.config.DatabaseConfig
import com.balbilo.registration.model.{AuthenticationError, UserDetails}
import com.balbilo.registration.repository.AuthenticationRepositoryMongo.configureDriver
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

import scala.concurrent.Future

trait AuthenticationRepository {

  def authenticateUser(userDetails: UserDetails): Future[Either[AuthenticationError, Done]]
}

final class AuthenticationRepositoryMongo(config: DatabaseConfig) extends AuthenticationRepository {

  val mongoClient   = configureDriver(config)
  val codecRegistry = fromRegistries(fromProviders(classOf[UserDetails]), DEFAULT_CODEC_REGISTRY)
  val database      = mongoClient.getDatabase(config.databaseName).withCodecRegistry(codecRegistry)
  val collection    = database.getCollection(config.authenticateCollection)

  override def authenticateUser(userDetails: UserDetails): Future[Either[AuthenticationError, Done]] = ???
}

object AuthenticationRepositoryMongo {

  def configureDriver(config: DatabaseConfig): MongoClient = MongoClient(config.hosts)

}
