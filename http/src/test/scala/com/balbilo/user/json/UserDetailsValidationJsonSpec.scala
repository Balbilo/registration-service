package com.balbilo.user.json

import com.balbilo.user.model.UserDetails
import com.balbilo.user.testkit.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class UserDetailsValidationJsonSpec extends AnyWordSpecBase with PropertySpecBase {

  "UserDetailsJson" should {

    "encode and decode UserDetails" in forAll { userDetails: UserDetails =>
      val json =
        s"""{"fullName":"${userDetails.fullName.value}","email":"${userDetails.email.value}","password":"${userDetails.password.value}","dateOfBirth":"${userDetails.dateOfBirth.value}"}"""
      s"""{"fullName":"Andreas Rigas","email":"as@.as.com","password":"asdjasdjA12","dateOfBirth":"02/03/2020"}"""
      decoder[UserDetails](json) shouldBe Right(userDetails)
      encoder(userDetails).noSpaces shouldBe json.trim
    }

    "perform a roundTrip" in forAll { userDetails: UserDetails =>
      decoder[UserDetails](encoder(userDetails).noSpaces) shouldBe Right(userDetails)
    }
  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)
}
