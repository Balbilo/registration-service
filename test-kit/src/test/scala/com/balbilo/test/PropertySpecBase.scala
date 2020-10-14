package com.balbilo.test

import java.time.LocalDate

import com.balbilo.registration.model.UserDetails
import com.balbilo.registration.model.ValueClasses.{DateOfBirth, Email, FullName, Password}
import org.scalacheck.{Arbitrary, Gen}
import org.scalactic.anyvals.PosInt
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

trait PropertySpecBase extends ScalaCheckDrivenPropertyChecks {

  val minSuccessful: PosInt = PosInt(100)

  implicit override val generatorDrivenConfig = PropertyCheckConfiguration(minSuccessful = minSuccessful)

  implicit lazy val arbFullName = Arbitrary(Gen.alphaNumStr.map(FullName(_)))

  implicit lazy val arbEmail = Arbitrary(Gen.alphaNumStr.map(Email(_)))

  implicit lazy val arbPassword = Arbitrary(Gen.alphaNumStr.map(Password))

  implicit lazy val arbDateOfBirth = Arbitrary{
    for{
    num <- Gen.chooseNum(Long.MinValue,Long.MaxValue)

    } yield DateOfBirth(LocalDate.ofEpochDay(num))
  }

  implicit lazy val arbUserDetails = Arbitrary{Gen.resultOf(UserDetails)}

}
