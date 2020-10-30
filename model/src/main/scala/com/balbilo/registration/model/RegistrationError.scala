package com.balbilo.registration.model

import com.balbilo.registration.model.ValueClasses._

sealed trait RegistrationError extends ModelError {
  def code: String
  def message: String
}

object RegistrationError {

  final case class InvalidFullName(fullName: FullName) extends RegistrationError {
    val code    = "invalidFullName"
    val message = s"Invalid name: [${fullName.value}]"
  }

  final case class InvalidEmail(email: Email) extends RegistrationError {
    val code    = "invalidEmail"
    val message = s"Invalid email: [${email.value}]"
  }

  final case class InvalidPassword(password: Password) extends RegistrationError {
    val code    = "invalidPassword"
    val message = s"Invalid password: [${password.value}]"
  }

  final case class InvalidDateOfBirth(dateOfBirth: DateOfBirth) extends RegistrationError {
    val code    = "invalidDateOfBirth"
    val message = s"Invalid date of birth: [${dateOfBirth.value}]"
  }

}
