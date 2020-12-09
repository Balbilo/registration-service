package com.balbilo.registration.services

import com.balbilo.registration.config.DatabaseConfig
import com.balbilo.registration.model.{AuthenticationError, UserDetails, UserToken}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection}

import scala.concurrent.Future

trait AuthenticateService {
  def authenticate(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]]
}

final class AuthenticateServiceImpl(mongoClient: MongoClient, config: DatabaseConfig) extends AuthenticateService {

  val codecRegistry = fromRegistries(fromProviders(classOf[UserDetails]), DEFAULT_CODEC_REGISTRY)
  val database = mongoClient.getDatabase(config.databaseName).withCodecRegistry(codecRegistry)
  val collection: MongoCollection[UserDetails] = database.getCollection(config.authenticateCollection)

  override def authenticate(userDetails: UserDetails): Future[Either[AuthenticationError, UserToken]] = {
      collection.insertOne(userDetails).toFuture()
  }


  private def createToken()
}
