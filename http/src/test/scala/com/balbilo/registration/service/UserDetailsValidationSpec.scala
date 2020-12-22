package com.balbilo.registration.service

import java.time.LocalDate

import cats.data.NonEmptyList
import com.balbilo.registration.config.UserDetailsConfig
import com.balbilo.registration.model.ValueClasses._
import com.balbilo.registration.model.{AuthenticationError, UserDetails, ValidationError}
import com.balbilo.registration.testkit.{AnyWordSpecBase, PropertySpecBase}

import scala.util.matching.Regex

class UserDetailsValidationSpec extends AnyWordSpecBase with PropertySpecBase {

  val fullNameRegex: Regex = """^([a-zA-Z]{1}[a-zA-Z ,.'-]{0,100}\s{0,1}[a-zA-Z ,.'-]{0,100}[a-zA-Z0-9 ']$)""".r

  val emailRegex: Regex = """^[a-zA-Z0-9_.+-]{1,200}@[a-zA-Z0-9-]{1,200}\.[a-zA-Z0-9-.]{0,200}[a-zA-Z0-9]$""".r

  //At least 8 letters with one Capital and one Number
  val passwordRegex = """^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$""".r

  val maxYears = 100

  val config = UserDetailsConfig(fullNameRegex, emailRegex, passwordRegex, maxYears)

  val validation = new UserDetailsValidationImpl(config)

  "UserDetailsValidationSpec" should {

    "return UserDetails when details are valid" in {
      val userDetails = UserDetails(FullName("Great Developer"),
                                    Email("sd@dsd.com"),
                                    Password("wHatever123"),
                                    DateOfBirth(LocalDate.now().minusDays(maxYears - 1)))
      val result      = validation.validateUserDetails(userDetails)

      result shouldBe Right(userDetails)
    }

    "return a NonEmptyList of invalid details" in {
      val invalidEmail    = Email("sdsd.com")
      val invalidPassword = Password("whatever123")
      val userDetails     = UserDetails(FullName("Great Developer"),
                                    invalidEmail,
                                    invalidPassword,
                                    DateOfBirth(LocalDate.now().minusDays(maxYears - 1)))
      val result          = validation.validateUserDetails(userDetails)

      result shouldBe Left(
        AuthenticationError.InvalidDetailsError(
          NonEmptyList(ValidationError.InvalidEmail(invalidEmail), List(ValidationError.InvalidPassword(invalidPassword)))
        ))
    }
  }

}
