package com.balbilo.registration

import akka.actor.ActorSystem
import com.balbilo.registration.client.{AuthenticationImpl, AuthorizationImpl, Clients}
import com.balbilo.registration.config.HttpConfig
import com.balbilo.registration.repository.{AuthenticationRepositoryMongo, RegistrationRepositoryMongo, Repositories}
import com.balbilo.registration.service.{Services, UserDetailsValidationImpl}
import pureconfig.ConfigSource

object Main extends App {

  val config = ConfigSource.default.loadOrThrow[HttpConfig]

  implicit val system = ActorSystem("registration-service")

  // Repositories
  val authenticationRepository = new AuthenticationRepositoryMongo(config.database)
  val registrationRepository   = new RegistrationRepositoryMongo(config.database)
  val repositories             = Repositories(authenticationRepository, registrationRepository)

  //Services
  val validation = new UserDetailsValidationImpl(config.userDetails)
  val services   = Services(validation)

  //Clients
  val authorization  = new AuthorizationImpl(config.authorization)
  val authentication = new AuthenticationImpl(config.authentication)
  val clients        = Clients(authorization, authentication)

  val server = new RegistrationServer(config.server, repositories, clients, services)

  server.createServer()

}
