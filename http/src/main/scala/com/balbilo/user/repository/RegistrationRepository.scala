package com.balbilo.user.repository

import com.balbilo.user.config.DatabaseConfig
import com.balbilo.user.model.{EncryptedUserDetails, RegistrationError}
import org.mongodb.scala.MongoClient
import io.circe.syntax._
import com.balbilo.user.json.encryptedUserDetailsEncoder
import org.mongodb.scala.bson.Document

import scala.concurrent.{ExecutionContext, Future}

trait RegistrationRepository {

  def registerUser(encryptedUserDetails: EncryptedUserDetails): Future[Either[RegistrationError, String]]

}

final class RegistrationRepositoryMongo(client: MongoClient, config: DatabaseConfig)(implicit ec: ExecutionContext)
    extends RegistrationRepository {

  private val database   = client.getDatabase(config.databaseName)
  private val collection = database.getCollection(config.userRegistrationCollection)

  override def registerUser(encryptedUserDetails: EncryptedUserDetails): Future[Either[RegistrationError, String]] = {
    val document = Document(encryptedUserDetails.asJson.noSpaces)
    collection.insertOne(document).toFuture().map(_ => Right(""))
  }

}
