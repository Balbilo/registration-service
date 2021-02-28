package com.balbilo.user.clients

import com.balbilo.user.config.DatabaseConfig
import org.mongodb.scala.MongoCredential.createCredential
import org.mongodb.scala.connection.NettyStreamFactoryFactory
import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoCredential, ServerAddress}
import scala.jdk.CollectionConverters._

final class Mongo(config: DatabaseConfig) {

  val mongoClient: MongoClient = {

    val credentials: MongoCredential = createCredential(config.username, config.databaseName, config.password.toCharArray)

    val settings: MongoClientSettings = MongoClientSettings
      .builder()
      .applyToClusterSettings(b => b.hosts(config.hosts.map(new ServerAddress(_)).asJava))
      .applyToSslSettings(b => b.enabled(config.ssl))
      .credential(credentials)
      .build()

    MongoClient(settings)
  }

}
