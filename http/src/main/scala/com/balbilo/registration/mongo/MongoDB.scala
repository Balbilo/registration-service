package com.balbilo.registration.mongo

import com.balbilo.registration.config.DatabaseConfig
import org.mongodb.scala.MongoClient

object MongoDB {

  def configureDriver(config: DatabaseConfig): MongoClient = MongoClient(config.hosts)

}
