package com.balbilo.registration.config

final case class HttpConfig(userDetailsConfig: UserDetailsConfig, serverConfig: ServerConfig)

final case class UserDetailsConfig(fullNameRegex: String, emailRegex: String, passwordRegex: String, maxYears: Int)

final case class ServerConfig(interface: String, port: Int)

final case class DatabaseConfig()
