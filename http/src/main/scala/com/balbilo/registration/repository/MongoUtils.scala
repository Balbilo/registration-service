package com.balbilo.registration.repository

import com.balbilo.registration.config.DatabaseConfig
import org.mongodb.scala.MongoCredential._
import org.mongodb.scala.connection.NettyStreamFactoryFactory
import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoCredential}

object MongoUtils {

  def createSession(config: DatabaseConfig) = {
    val credentials: MongoCredential = createCredential(config.username, config.databaseName, config.password.toCharArray)

    val settings: MongoClientSettings = MongoClientSettings
      .builder()
      .streamFactoryFactory(NettyStreamFactoryFactory())
      .applyToSslSettings(b => b.enabled(true))
      .credential(credentials)
      .build()

    MongoClient(settings)
  }

}
