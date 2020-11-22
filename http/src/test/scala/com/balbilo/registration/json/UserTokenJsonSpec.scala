package com.balbilo.registration.json

import com.balbilo.registration.model.UserToken
import com.balbilo.registration.testkit.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class UserTokenJsonSpec extends AnyWordSpecBase with PropertySpecBase {

  "UserTokenJson" should {

    "encode and decode UserToken" in {
      forAll { userToken: UserToken =>
        val json = s"""{"token":"${userToken.token.value}","expireTimeStamp":${userToken.expireTimeStamp}}"""
        decoder[UserToken](json) shouldBe Right(userToken)
        encoder(userToken).noSpaces shouldBe json.trim
      }
    }

    "perform a roundTrip" in {
      forAll { userToken: UserToken =>
        decoder[UserToken](encoder(userToken).noSpaces) shouldBe Right(userToken)
      }
    }
  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)

}
