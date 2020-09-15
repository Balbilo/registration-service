package balbilo.registration.database

import balbilo.registration.database.config.DatabaseConfig
import com.mongodb.reactivestreams.client.MongoDatabase
import com.mongodb.reactivestreams.client._

trait DatabaseConnection {

  def connectToDatabase(config: DatabaseConfig): MongoDatabase
}

class ConnectToMongoDB(config: DatabaseConfig) extends DatabaseConnection {

  override def connectToDatabase(config: DatabaseConfig): MongoDatabase ={
    val mongoClient = MongoClients.create(config.host)
    mongoClient.getDatabase("balbilo_users")
  }

}
