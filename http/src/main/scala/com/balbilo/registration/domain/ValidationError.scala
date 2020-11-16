package com.balbilo.registration.domain

import com.balbilo.registration.model.ValueClasses._

sealed trait ValidationError {
  def code: String
  def message: String
}

object ValidationError {

  final case class InvalidFullName(fullName: FullName) extends ValidationError {
    val code    = "invalidFullName"
    val message = s"Invalid name: [${fullName.value}]"
  }

  final case class InvalidEmail(email: Email) extends ValidationError {
    val code    = "invalidEmail"
    val message = s"Invalid email: [${email.value}]"
  }

  final case class InvalidPassword(password: Password) extends ValidationError {
    val code    = "invalidPassword"
    val message = s"Invalid password: [${password.value}]"
  }

  final case class InvalidDateOfBirth(dateOfBirth: DateOfBirth) extends ValidationError {
    val code    = "invalidDateOfBirth"
    val message = s"Invalid date of birth: [${dateOfBirth.value}]"
  }

}
