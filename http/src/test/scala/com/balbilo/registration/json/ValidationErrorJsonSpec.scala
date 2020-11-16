package com.balbilo.registration.json

import com.balbilo.registration.model.ValidationError
import com.balbilo.registration.testkit.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class ValidationErrorJsonSpec extends AnyWordSpecBase with PropertySpecBase with FromJson {

  "RegistrationJson" should {

    "encode and decode Registration error with code and message" in {
      forAll { registrationError: ValidationError =>
        val json = s"""{"code":"${registrationError.code}","message":"${registrationError.message}"}"""
        decoder[ValidationError](json) shouldBe Right(registrationError)
        encoder(registrationError).noSpaces shouldBe json.trim
      }
    }

    "perform a roundTrip" in {
      forAll { registrationError: ValidationError =>
        decoder[ValidationError](encoder(registrationError).noSpaces) shouldBe Right(registrationError)
      }
    }

  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)

}
