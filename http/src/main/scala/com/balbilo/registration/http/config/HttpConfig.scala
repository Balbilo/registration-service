package com.balbilo.registration.http.config

final case class HttpConfig(serverConfig: ServerConfig, verifyConfig: VerifyConfig)

final case class ServerConfig(scheme: String, interface: String, port: Int)

final case class VerifyConfig(usersMaxAge: Int)
