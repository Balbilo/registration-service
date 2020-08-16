package balbilo.registration.domain

import balbilo.registration.model.{RegistrationError, UserDetails}
import balbilo.registration.model.config.UserDetailsConfig
import cats.data.ValidatedNel
import cats.implicits.catsSyntaxTuple4Semigroupal

final class UserDetailsValidation(userDetailsConfig: UserDetailsConfig) {

  def validateUserDetails(userDetails: UserDetails): ValidatedNel[RegistrationError, UserDetails] = {
    (EntitiesValidation.validateFullName(userDetails.fullName),
      EntitiesValidation.validateEmail(userDetails.email),
    EntitiesValidation.validatePassword(userDetails.password),
    EntitiesValidation.validateDateOfBirth(userDetails.dateOfBirth,userDetailsConfig.maxYears)).mapN(UserDetails)
  }

}
