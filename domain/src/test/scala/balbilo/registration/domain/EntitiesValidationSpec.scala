package balbilo.registration.domain

import java.time.LocalDate

import balbilo.registration.model.Entities.{DateOfBirth, Email, FullName, Password}
import balbilo.registration.model.RegistrationError
import cats.data.NonEmptyList
import cats.data.Validated.{Invalid, Valid}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EntitiesValidationSpec extends AnyWordSpec with Matchers {

  "ValidationService" when {

    "validating fullName" should {

      "return valid fullName with ." in {
        val validFullName = FullName("Chris P. Baton")
        EntitiesValidation.validateFullName(validFullName) shouldBe Valid(validFullName)
      }

      "return valid fullName with '" in {
        val validFullName = FullName("W'einer And Mrs. Pit")
        EntitiesValidation.validateFullName(validFullName) shouldBe Valid(validFullName)
      }

      "return valid fullName without special characters" in {
        val validFullName = FullName("Moe Lester")
        EntitiesValidation.validateFullName(validFullName) shouldBe Valid(validFullName)
      }

      "return invalid fullName empty string" in {
        val invalidFullName = FullName("")
        EntitiesValidation.validateFullName(invalidFullName) shouldBe invalid(RegistrationError.InvalidFullName(invalidFullName))
      }

      "return invalid fullName with not allowed characters" in {
        val invalidFullName = FullName("@#")
        EntitiesValidation.validateFullName(invalidFullName) shouldBe invalid(RegistrationError.InvalidFullName(invalidFullName))
      }

    }

    "validating Email" should {

      "return valid email" in {
        val validEmail = Email("a@a.a")
        EntitiesValidation.validateEmail(validEmail) shouldBe Valid(validEmail)
      }

      "return valid email with special characters" in {
        val validEmail = Email("a.a_r-r@a.a")
        EntitiesValidation.validateEmail(validEmail) shouldBe Valid(validEmail)
      }

      "return invalid email for not @" in {
        val invalidEmail = Email("aa.a")
        EntitiesValidation.validateEmail(invalidEmail) shouldBe invalid(RegistrationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for starting with symbol" in {
        val invalidEmail = Email("@a.d")
        EntitiesValidation.validateEmail(invalidEmail) shouldBe invalid(RegistrationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for ending with symbol" in {
        val invalidEmail = Email("1@a.d.")
        EntitiesValidation.validateEmail(invalidEmail) shouldBe invalid(RegistrationError.InvalidEmail(invalidEmail))
      }

      "return invalid email for ending with empty string" in {
        val invalidEmail = Email("")
        EntitiesValidation.validateEmail(invalidEmail) shouldBe invalid(RegistrationError.InvalidEmail(invalidEmail))
      }
    }

    "validating Password" should {

      "return valid Password with exactly 8 chars" in {
        val validPassword = Password("asdasdA1")
        EntitiesValidation.validatePassword(validPassword) shouldBe Valid(validPassword)
      }

      "return valid Password with strange chars" in {
        val validPassword = Password("@#!!!Andddds!'2'")
        EntitiesValidation.validatePassword(validPassword) shouldBe Valid(validPassword)
      }

      "return valid Password without special characters" in {
        val validPassword = Password(" Moe Lester1")
        EntitiesValidation.validatePassword(validPassword) shouldBe Valid(validPassword)
      }

      "return invalid Password empty string" in {
        val invalidPassword = Password("")
        EntitiesValidation.validatePassword(invalidPassword) shouldBe invalid(RegistrationError.InvalidPassword(invalidPassword))
      }

    }

    "validating DateOfBirth" should {

      "return valid date of birth" in {
        val maxYears = 100
        val validDataOfBirth = DateOfBirth(LocalDate.of(1996, 11, 2))
        EntitiesValidation.validateDateOfBirth(validDataOfBirth, maxYears) shouldBe Valid(validDataOfBirth)
      }

      "return invalid date of birth when is before max" in {
        val maxYears = 100
        val invalidDataOfBirth = DateOfBirth(LocalDate.now.minusYears(maxYears).minusDays(1))
        EntitiesValidation.validateDateOfBirth(invalidDataOfBirth, maxYears) shouldBe invalid(
          RegistrationError.InvalidDateOfBirth(invalidDataOfBirth))
      }

      "return invalid date of birth when is after min" in {
        val maxYears = 100
        val invalidDataOfBirth = DateOfBirth(LocalDate.now.plusDays(1))
        EntitiesValidation.validateDateOfBirth(invalidDataOfBirth, maxYears) shouldBe invalid(
          RegistrationError.InvalidDateOfBirth(invalidDataOfBirth))
      }
    }
  }

  private def invalid[A](invalidObject: A) = Invalid(NonEmptyList.one(invalidObject))
}
