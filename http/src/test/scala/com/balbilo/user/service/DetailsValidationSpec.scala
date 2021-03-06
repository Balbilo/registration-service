package com.balbilo.user.service

import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import com.balbilo.user.model.ValidationError
import com.balbilo.user.model.ValueClasses._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.time.LocalDate
import scala.util.matching.Regex

class DetailsValidationSpec extends AnyWordSpec with Matchers {

  val fullNameRegex: Regex = """^([a-zA-Z]{1}[a-zA-Z ,.'-]{0,100}\s{0,1}[a-zA-Z ,.'-]{0,100}[a-zA-Z0-9 ']$)""".r

  val emailRegex: Regex = """^[a-zA-Z0-9_.+-]{1,200}@[a-zA-Z0-9-]{1,200}\.[a-zA-Z0-9-.]{0,200}[a-zA-Z0-9]$""".r

  //At least 8 letters with one Capital and one Number
  val passwordRegex = """^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$""".r

  val maxYears = 100

  "ValidationService" when {

    "validating fullName" should {

      "return valid fullName with ." in {
        val validFullName = FullName("Chris P. Baton")
        UserDetailsValidation.validateFullName(validFullName, fullNameRegex) shouldBe Valid(validFullName)
      }

      "return valid fullName with '" in {
        val validFullName = FullName("W'einer And Mrs. Pit")
        UserDetailsValidation.validateFullName(validFullName, fullNameRegex) shouldBe Valid(validFullName)
      }

      "return valid fullName without special characters" in {
        val validFullName = FullName("Moe Lester")
        UserDetailsValidation.validateFullName(validFullName, fullNameRegex) shouldBe Valid(validFullName)
      }

      "return invalid fullName empty string" in {
        val invalidFullName = FullName("")
        UserDetailsValidation.validateFullName(invalidFullName, fullNameRegex) shouldBe invalid(
          ValidationError.InvalidFullName(invalidFullName)
        )
      }

      "return invalid fullName with not allowed characters" in {
        val invalidFullName = FullName("@#")
        UserDetailsValidation.validateFullName(invalidFullName, fullNameRegex) shouldBe invalid(
          ValidationError.InvalidFullName(invalidFullName)
        )
      }

    }

    "validating Email" should {

      "return valid email" in {
        val validEmail = Email("a@a.a")
        UserDetailsValidation.validateEmail(validEmail, emailRegex) shouldBe Valid(validEmail)
      }

      "return valid email with special characters" in {
        val validEmail = Email("a.a_r-r@a.a")
        UserDetailsValidation.validateEmail(validEmail, emailRegex) shouldBe Valid(validEmail)
      }

      "return invalid email for not @" in {
        val invalidEmail = Email("aa.a")
        UserDetailsValidation.validateEmail(invalidEmail, emailRegex) shouldBe invalid(ValidationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for starting with symbol" in {
        val invalidEmail = Email("@a.d")
        UserDetailsValidation.validateEmail(invalidEmail, emailRegex) shouldBe invalid(ValidationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for ending with symbol" in {
        val invalidEmail = Email("1@a.d.")
        UserDetailsValidation.validateEmail(invalidEmail, emailRegex) shouldBe invalid(ValidationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for ending with empty string" in {
        val invalidEmail = Email("")
        UserDetailsValidation.validateEmail(invalidEmail, emailRegex) shouldBe invalid(ValidationError.InvalidEmail(invalidEmail))
      }
    }

    "validating Password" should {

      "return valid Password with exactly 8 chars" in {
        val validPassword = Password("asdasdA1")
        UserDetailsValidation.validatePassword(validPassword, passwordRegex) shouldBe Valid(validPassword)
      }

      "return valid Password with strange chars" in {
        val validPassword = Password("@#!!!Andddds!'2'")
        UserDetailsValidation.validatePassword(validPassword, passwordRegex) shouldBe Valid(validPassword)
      }

      "return valid Password without special characters" in {
        val validPassword = Password(" Moe Lester1")
        UserDetailsValidation.validatePassword(validPassword, passwordRegex) shouldBe Valid(validPassword)
      }

      "return invalid Password empty string" in {
        val invalidPassword = Password("")
        UserDetailsValidation.validatePassword(invalidPassword, passwordRegex) shouldBe invalid(
          ValidationError.InvalidPassword(invalidPassword)
        )
      }

    }

    "validating DateOfBirth" should {

      "return valid date of birth" in {
        val validDataOfBirth = DateOfBirth(LocalDate.of(1996, 11, 2))
        UserDetailsValidation.validateDateOfBirth(validDataOfBirth, maxYears) shouldBe Valid(validDataOfBirth)
      }

      "return invalid date of birth when is before max" in {
        val invalidDataOfBirth = DateOfBirth(LocalDate.now.minusYears(maxYears).minusDays(1))
        UserDetailsValidation.validateDateOfBirth(invalidDataOfBirth, maxYears) shouldBe invalid(
          ValidationError.InvalidDateOfBirth(invalidDataOfBirth)
        )
      }

      "return invalid date of birth when is after min" in {
        val invalidDataOfBirth = DateOfBirth(LocalDate.now.plusDays(1))
        UserDetailsValidation.validateDateOfBirth(invalidDataOfBirth, maxYears) shouldBe invalid(
          ValidationError.InvalidDateOfBirth(invalidDataOfBirth)
        )
      }
    }
  }

  private def invalid[A](invalidObject: A) = Invalid(NonEmptyList.one(invalidObject))
}
