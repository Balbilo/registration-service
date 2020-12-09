package com.balbilo.registration

import akka.actor.ActorSystem
import pureconfig.generic.auto._
import com.balbilo.registration.config.HttpConfig
import com.balbilo.registration.services.{AuthenticateServiceImpl, Domain, RegisterServiceImpl, UserDetailsValidationImpl}
import com.balbilo.registration.mongo.MongoDB
import pureconfig.ConfigSource

object Main extends App {

  val config = ConfigSource.default.loadOrThrow[HttpConfig]

  implicit val system = ActorSystem("registration-service")

  val mongoClient = MongoDB.configureDriver(config.database)

  val validation = new UserDetailsValidationImpl(config.userDetails)

  val authentication = new AuthenticateServiceImpl(mongoClient, config.database)

  val registration = new RegisterServiceImpl(mongoClient, config.database)

  val services = Domain(validation,authentication,registration)

  val server = new RegistrationServer(config.server,services)

  server.createServer()

}
