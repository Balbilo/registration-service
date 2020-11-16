package com.balbilo.registration.testkit

import java.time.LocalDate

import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.anyvals.PosInt
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait PropertySpecBase extends ScalaCheckDrivenPropertyChecks {

  val minSuccessful: PosInt = PosInt(10)

  implicit override val generatorDrivenConfig = PropertyCheckConfiguration(minSuccessful = minSuccessful)

  implicit lazy val arbFullName = Arbitrary(Gen.alphaNumStr.map(FullName(_)))

  implicit lazy val arbEmail = Arbitrary(Gen.alphaNumStr.map(Email(_)))

  implicit lazy val arbPassword = Arbitrary(Gen.alphaNumStr.map(Password))

  implicit lazy val arbDateOfBirth = Arbitrary(
    Gen.chooseNum(LocalDate.MIN.toEpochDay, LocalDate.MAX.toEpochDay).map(day => DateOfBirth(LocalDate.ofEpochDay(day)))
  )

  implicit lazy val arbUserDetails = Arbitrary(Gen.resultOf(UserDetails))

  implicit lazy val arbInvalidFullName    = Arbitrary(Gen.resultOf(InvalidFullName))
  implicit lazy val arbInvalidEmail       = Arbitrary(Gen.resultOf(InvalidEmail))
  implicit lazy val arbInvalidPassword    = Arbitrary(Gen.resultOf(InvalidPassword))
  implicit lazy val arbInvalidDateOfBirth = Arbitrary(Gen.resultOf(InvalidDateOfBirth))

  implicit lazy val arbRegistrationError: Arbitrary[RegistrationError] = Arbitrary {
    for {
      invalidFullName    <- arbInvalidFullName.arbitrary
      invalidEmail       <- arbInvalidEmail.arbitrary
      arbInvalidPassword <- arbInvalidPassword.arbitrary
      invalidDateOfBirth <- arbInvalidDateOfBirth.arbitrary
      registrationError  <- Gen.oneOf(invalidFullName, invalidEmail, arbInvalidPassword, invalidDateOfBirth)
    } yield registrationError
  }

  implicit lazy val arbMethodNotAllowed: Arbitrary[MethodNotAllowedError] = Arbitrary {
    for {
      method         <- Gen.alphaNumStr
      allowedMethods <- Gen.listOf(Gen.alphaNumStr)
    } yield MethodNotAllowedError(method, allowedMethods)
  }

  implicit lazy val arbServerError: Arbitrary[ServerError] = Arbitrary {
    for {
      methodNotAllowed <- arbMethodNotAllowed.arbitrary
      serverError      <- Gen.oneOf(methodNotAllowed, InternalServerError)
    } yield serverError
  }
}
