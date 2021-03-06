package com.balbilo.user.config

import scala.util.matching.Regex

final case class HttpConfig(userDetails: UserDetailsConfig, server: ServerConfig, database: DatabaseConfig)

final case class UserDetailsConfig private (fullNameRegex: Regex, emailRegex: Regex, passwordRegex: Regex, maxYears: Int)

object UserDetailsConfig {
  def apply(fullNameRegex: String, emailRegex: String, passwordRegex: String, maxYears: Int): UserDetailsConfig =
    new UserDetailsConfig(fullNameRegex.r, emailRegex.r, passwordRegex.r, maxYears)
}

final case class ServerConfig(interface: String, port: Int)

final case class DatabaseConfig(
    hosts: List[String],
    databaseName: String,
    userAuthenticationCollection: String,
    userRegistrationCollection: String,
    ssl: Boolean,
    username: String,
    password: String
)
