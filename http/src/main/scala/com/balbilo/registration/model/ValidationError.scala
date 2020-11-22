package com.balbilo.registration.model

import com.balbilo.registration.model.ValueClasses._

sealed trait ValidationError

object ValidationError {

  final case class InvalidFullName(fullName: FullName) extends ValidationError {
    override def toString = "invalidFullName"
  }

  final case class InvalidEmail(email: Email) extends ValidationError {
    override def toString = "invalidEmail"
  }

  final case class InvalidPassword(password: Password) extends ValidationError {
    override def toString = "invalidPassword"
  }

  final case class InvalidDateOfBirth(dateOfBirth: DateOfBirth) extends ValidationError {
    override def toString = "invalidDateOfBirth"
  }

}
