package com.balbilo.registration.domain

import cats.implicits.catsSyntaxTuple4Semigroupal
import com.balbilo.registration.config.UserDetailsConfig
import com.balbilo.registration.model.{AuthenticationError, UserDetails}

trait UserDetailsValidation {
  def validateUserDetails(userDetails: UserDetails): Either[AuthenticationError, UserDetails]
}

final class UserDetailsValidationImpl(userDetailsConfig: UserDetailsConfig) extends UserDetailsValidation {

  def validateUserDetails(userDetails: UserDetails): Either[AuthenticationError, UserDetails] =
    (
      DetailsValidation.validateFullName(userDetails.fullName, userDetailsConfig.fullNameRegex),
      DetailsValidation.validateEmail(userDetails.email, userDetailsConfig.emailRegex),
      DetailsValidation.validatePassword(userDetails.password, userDetailsConfig.passwordRegex),
      DetailsValidation.validateDateOfBirth(userDetails.dateOfBirth, userDetailsConfig.maxYears)
    ).mapN(UserDetails).leftMap(AuthenticationError.InvalidDetailsError).toEither

}
