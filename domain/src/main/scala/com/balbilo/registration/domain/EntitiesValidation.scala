package com.balbilo.registration.domain

import java.time.LocalDate

import cats.data._
import cats.implicits._
import com.balbilo.registration.model.RegistrationError
import com.balbilo.registration.model.ValueClasses._

private[domain] object EntitiesValidation {

  def validateFullName(fullName: FullName): ValidatedNel[RegistrationError.InvalidFullName, FullName] =
    fullName.value match {
      case str if str.matches(StringValidators.fullNameRegex.regex) => fullName.validNel
      case _                                                        => RegistrationError.InvalidFullName(fullName).invalidNel
    }

  def validateEmail(email: Email): ValidatedNel[RegistrationError.InvalidEmail, Email] =
    email.value match {
      case str if str.matches(StringValidators.emailRegex.regex) => email.validNel
      case _                                                     => RegistrationError.InvalidEmail(email).invalidNel
    }

  def validatePassword(password: Password): ValidatedNel[RegistrationError.InvalidPassword, Password] =
    password.value match {
      case str if str.matches(StringValidators.passwordRegex.regex) => password.validNel
      case _                                                        => RegistrationError.InvalidPassword(password).invalidNel
    }

  def validateDateOfBirth(
      dateOfBirth: DateOfBirth,
      maxYears: Int
  ): ValidatedNel[RegistrationError.InvalidDateOfBirth, DateOfBirth] = {
    val minDate = LocalDate.now
    val maxDate = minDate.minusYears(maxYears)
    dateOfBirth.value match {
      case date if (date.isAfter(minDate) || date.isBefore(maxDate)) =>
        RegistrationError.InvalidDateOfBirth(dateOfBirth).invalidNel
      case _                                                         => dateOfBirth.validNel
    }
  }

}
