package com.balbilo.registration.domain

import java.time.LocalDate

import cats.data.ValidatedNel
import cats.implicits.catsSyntaxValidatedId
import com.balbilo.registration.model.ValidationError
import com.balbilo.registration.model.ValueClasses._

import scala.util.matching.Regex

private[domain] object DetailsValidation {

  def validateFullName(fullName: FullName, fullNameRegex: Regex): ValidatedNel[ValidationError.InvalidFullName, FullName] =
    fullName.value match {
      case str if str.matches(fullNameRegex.regex) => fullName.validNel
      case _                                       => ValidationError.InvalidFullName(fullName).invalidNel
    }

  def validateEmail(email: Email, emailRegex: Regex): ValidatedNel[ValidationError.InvalidEmail, Email] =
    email.value match {
      case str if str.matches(emailRegex.regex) => email.validNel
      case _                                    => ValidationError.InvalidEmail(email).invalidNel
    }

  def validatePassword(password: Password, passwordRegex: Regex): ValidatedNel[ValidationError.InvalidPassword, Password] =
    password.value match {
      case str if str.matches(passwordRegex.regex) => password.validNel
      case _                                       => ValidationError.InvalidPassword(password).invalidNel
    }

  def validateDateOfBirth(
      dateOfBirth: DateOfBirth,
      maxYears: Int
  ): ValidatedNel[ValidationError.InvalidDateOfBirth, DateOfBirth] = {
    val minDate = LocalDate.now
    val maxDate = minDate.minusYears(maxYears)
    dateOfBirth.value match {
      case date if date.isAfter(minDate) || date.isBefore(maxDate) =>
        ValidationError.InvalidDateOfBirth(dateOfBirth).invalidNel
      case _                                                       => dateOfBirth.validNel
    }
  }

}
