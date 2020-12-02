package com.balbilo.registration.mongo

import com.balbilo.registration.config.DatabaseConfig
import com.mongodb.MongoClientSettings
import org.mongodb.scala.MongoClient

object MongoDB {

  def configureDriver(config: DatabaseConfig): MongoClient = {
    val sslSettings = MongoClientSettings
      .builder()
      .build()

    val settings = MongoClientSettings.builder(sslSettings)

    MongoClient()
  }

}
