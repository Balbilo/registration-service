package com.balbilo.user.service

import akka.actor.ActorSystem
import cats.implicits.catsSyntaxEitherId
import com.balbilo.user.model.RegistrationError.PasswordEncryptionError
import com.balbilo.user.model.ValueClasses.{EncryptedPassword, Salt}
import com.balbilo.user.model.{EncryptedUserDetails, RegistrationError, UserDetails}
import com.github.t3hnar.bcrypt._

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait Encryption {

  def encryptPassword(userDetails: UserDetails): Future[Either[PasswordEncryptionError.type, EncryptedUserDetails]]

}

class EncryptionImpl()(implicit system: ActorSystem) extends Encryption {

  implicit val ec = system.dispatcher

  override def encryptPasqsword(userDetails: UserDetails): Future[Either[PasswordEncryptionError.type, EncryptedUserDetails]] =
    Future {
      val salt = Salt(generateSalt)
      userDetails.password.value.bcryptSafeBounded(salt.value) match {
        case Success(encryptedPassword) =>
          EncryptedUserDetails(
            None,
            userDetails.fullName,
            userDetails.email,
            EncryptedPassword(encryptedPassword),
            salt,
            userDetails.dateOfBirth
          ).asRight
        case Failure(_)                 => RegistrationError.PasswordEncryptionError.asLeft
      }
    }
}
