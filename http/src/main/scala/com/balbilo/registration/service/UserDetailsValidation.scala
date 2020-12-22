package com.balbilo.registration.service

import cats.data.ValidatedNel
import cats.implicits.{catsSyntaxTuple4Semigroupal, catsSyntaxValidatedId}
import com.balbilo.registration.config.UserDetailsConfig
import com.balbilo.registration.model.ValueClasses.{DateOfBirth, Email, FullName, Password}
import com.balbilo.registration.model.{AuthenticationError, UserDetails, ValidationError}
import com.balbilo.registration.service.UserDetailsValidation._

import java.time.LocalDate
import scala.util.matching.Regex

trait UserDetailsValidation {
  def validateUserDetails(userDetails: UserDetails): Either[AuthenticationError, UserDetails]
}

final class UserDetailsValidationImpl(userDetailsConfig: UserDetailsConfig) extends UserDetailsValidation {

  def validateUserDetails(userDetails: UserDetails): Either[AuthenticationError, UserDetails] =
    (
      validateFullName(userDetails.fullName, userDetailsConfig.fullNameRegex),
      validateEmail(userDetails.email, userDetailsConfig.emailRegex),
      validatePassword(userDetails.password, userDetailsConfig.passwordRegex),
      validateDateOfBirth(userDetails.dateOfBirth, userDetailsConfig.maxYears)
    ).mapN(UserDetails).leftMap(AuthenticationError.InvalidDetailsError).toEither

}

private[service] object UserDetailsValidation {

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
