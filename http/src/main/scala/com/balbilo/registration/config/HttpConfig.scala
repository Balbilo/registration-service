package com.balbilo.registration.config

import scala.util.matching.Regex

final case class HttpConfig(userDetailsConfig: UserDetailsConfig, serverConfig: ServerConfig)

final case class UserDetailsConfig(fullNameRegex: Regex, emailRegex: Regex, passwordRegex: Regex, maxYears: Int)

object UserDetailsConfig {
  def apply(fullNameRegex: String, emailRegex: String, passwordRegex: String, maxYears: Int): UserDetailsConfig =
    new UserDetailsConfig(fullNameRegex.r, emailRegex.r, passwordRegex.r, maxYears)
}

final case class ServerConfig(interface: String, port: Int)

final case class DatabaseConfig(connection: String, ssl: Boolean)
