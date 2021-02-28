package com.balbilo.user

import com.balbilo.user.clients.Mongo
import com.balbilo.user.config.HttpConfig
import com.balbilo.user.json._
import com.balbilo.user.model.EncryptedUserDetails
import com.balbilo.user.model.ValueClasses._
import io.circe.syntax._
import org.mongodb.scala.SingleObservable
import org.mongodb.scala.bson.Document
import org.mongodb.scala.result.InsertOneResult
import pureconfig.ConfigSource
import scala.concurrent.ExecutionContext.Implicits.global
import pureconfig.generic.auto._

import java.time.LocalDate
object Experiment extends App {

  val config = ConfigSource.default.loadOrThrow[HttpConfig]

  val user = EncryptedUserDetails(
    "1",
    FullName("Andreas Rigas"),
    Email("andreas@asd.xom"),
    EncryptedPassword("1234"),
    Salt("123"),
    DateOfBirth(LocalDate.now().minusYears(10))
  )

  val x :: tail = List("")

  val json = user.asJson.noSpaces

  // Client
  val mongoClient = new Mongo(config.database).mongoClient

  mongoClient.listDatabaseNames().toFuture().foreach(println(_))

  val database   = mongoClient.getDatabase(config.database.databaseName)
  val collection = database.getCollection(config.database.userRegistrationCollection)

  val document = Document(user.asJson.noSpaces)

  val s: SingleObservable[InsertOneResult] = collection.insertOne(document)
  s.toFuture().map(_ => println("Inserted"))

  println(json)

  Thread.sleep(30000000)
}
