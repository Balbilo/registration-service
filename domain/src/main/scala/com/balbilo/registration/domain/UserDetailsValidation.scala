package com.balbilo.registration.domain

import cats.data.ValidatedNel
import cats.implicits.catsSyntaxTuple4Semigroupal
import com.balbilo.registration.model.{RegistrationError, UserDetails}

final class UserDetailsValidation(maxYears: Int) {

  def validateUserDetails(userDetails: UserDetails): ValidatedNel[RegistrationError, UserDetails] =
    (
      EntitiesValidation.validateFullName(userDetails.fullName),
      EntitiesValidation.validateEmail(userDetails.email),
      EntitiesValidation.validatePassword(userDetails.password),
      EntitiesValidation.validateDateOfBirth(userDetails.dateOfBirth, maxYears)
    ).mapN(UserDetails)

}
