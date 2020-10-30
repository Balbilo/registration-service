package com.balbilo.registration.json

import com.balbilo.registration.model.RegistrationError
import com.balbilo.test.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class RegistrationErrorJsonSpec extends AnyWordSpecBase with PropertySpecBase with FromJson {

  "RegistrationJson" should {

    "encode and decode Registration error with code and message" in {
      forAll { registrationError: RegistrationError =>
        val json = s"""{"code":"${registrationError.code}","message":"${registrationError.message}"}"""
        decoder[RegistrationError](json) shouldBe Right(registrationError)
        encoder(registrationError).noSpaces shouldBe json.trim
      }
    }

    "perform a roundTrip" in {
      forAll { registrationError: RegistrationError =>
        decoder[RegistrationError](encoder(registrationError).noSpaces) shouldBe Right(registrationError)
      }
    }

  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)

}
