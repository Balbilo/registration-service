package com.balbilo.registration.json

import com.balbilo.registration.model.UserDetails
import com.balbilo.registration.testkit.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class ServerErrorJsonSpec extends AnyWordSpecBase with PropertySpecBase with FromJson {

  "RegistrationJson" should {

    "encode and decode Server error with code and message" in {
      forAll { userDetails: UserDetails =>
        val json =
          s"""{"fullName":"${userDetails.fullName.value}","email":"${userDetails.email.value}","password":"${userDetails.password.value}","dateOfBirth":"${userDetails.dateOfBirth.value}"}"""
        decoder[UserDetails](json) shouldBe Right(userDetails)
        encoder(userDetails).noSpaces shouldBe json.trim
      }
    }

    "perform a roundTrip" in {
      forAll { userDetails: UserDetails =>
        decoder[UserDetails](encoder(userDetails).noSpaces) shouldBe Right(userDetails)
      }
    }

  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)
}
