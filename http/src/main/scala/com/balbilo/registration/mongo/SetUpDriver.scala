package com.balbilo.registration.mongo

import com.balbilo.registration.config.DatabaseConfig
import com.mongodb.MongoClientSettings
import org.mongodb.scala.MongoClient
import org.mongodb.scala.connection.NettyStreamFactoryFactory

object SetUpDriver {

  def configureDriver(config: DatabaseConfig): MongoClient = {
    if (config.ssl)
        MongoClientSettings.builder().streamFactoryFactory(NettyStreamFactoryFactory()).build()
      else MongoClientSettings.builder().applyToClusterSettings(???)

    MongoClient()
  }

}
