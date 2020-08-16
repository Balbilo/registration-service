package balbilo.registration.model

import balbilo.registration.model.Entities._

trait RegistrationError {
  def message: String
}

object RegistrationError {

  final case class InvalidFullName(fullName: FullName) extends RegistrationError {
    val message = s"Invalid name: ${fullName.value}"
  }

  final case class InvalidEmail(email: Email) extends RegistrationError {
    val message = s"Invalid email: ${email.value}"
  }

  final case class InvalidPassword(password: Password) extends RegistrationError {
    val message = s"Invalid password: ${password.value}"
  }

  final case class InvalidDateOfBirth(dateOfBirth: DateOfBirth) extends RegistrationError {
    val message = s"Invalid date of birth: ${dateOfBirth.value}"
  }

}
