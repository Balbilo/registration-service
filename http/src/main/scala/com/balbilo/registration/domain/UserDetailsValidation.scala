package com.balbilo.registration.domain

import cats.data.NonEmptyList
import cats.implicits.catsSyntaxTuple4Semigroupal
import com.balbilo.registration.config.UserDetailsConfig
import com.balbilo.registration.model.{UserDetails, ValidationError}

final class UserDetailsValidation(userDetailsConfig: UserDetailsConfig) {

  def validateUserDetails(userDetails: UserDetails): Either[NonEmptyList[ValidationError], UserDetails] =
    (
      DetailsValidation.validateFullName(userDetails.fullName, userDetailsConfig.fullNameRegex.r),
      DetailsValidation.validateEmail(userDetails.email, userDetailsConfig.emailRegex.r),
      DetailsValidation.validatePassword(userDetails.password, userDetailsConfig.passwordRegex.r),
      DetailsValidation.validateDateOfBirth(userDetails.dateOfBirth, userDetailsConfig.maxYears)
    ).mapN(UserDetails).toEither

}
