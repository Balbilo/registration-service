package com.balbilo.user

import akka.actor.ActorSystem
import com.balbilo.user.clients.Mongo
import com.balbilo.user.config.HttpConfig
import com.balbilo.user.repository.{AuthorizationRepositoryMongo, RegistrationRepositoryMongo, Repositories}
import com.balbilo.user.service.{EncryptionImpl, Services, UserDetailsValidationImpl}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Main extends App {

  val config = ConfigSource.default.loadOrThrow[HttpConfig]

  implicit val system = ActorSystem("registration-service")
  implicit val ec     = system.dispatcher

  // Client
  val mongoClient = new Mongo(config.database).mongoClient

  // Repositories
  val authenticationRepository = new AuthorizationRepositoryMongo(mongoClient, config.database)
  val registrationRepository   = new RegistrationRepositoryMongo(mongoClient, config.database)
  val repositories             = Repositories(authenticationRepository, registrationRepository)

  //Services
  val validation = new UserDetailsValidationImpl(config.userDetails)
  val encryption = new EncryptionImpl()
  val services   = Services(validation, encryption)

  val server = new RegistrationServer(config.server, repositories, services)

  server.createServer()

}
