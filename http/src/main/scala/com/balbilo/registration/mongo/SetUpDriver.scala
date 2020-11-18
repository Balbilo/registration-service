package com.balbilo.registration.mongo

import com.mongodb.MongoClientSettings
import org.mongodb.scala.MongoClient

object SetUpDriver {

  def configureDriver(settings: Map[String, String]): MongoClient = {
    val settings = MongoClientSettings.builder().applyToClusterSettings()

    MongoClient()
  }

}
