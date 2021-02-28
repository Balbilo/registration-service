package com.balbilo.user.json

import com.balbilo.user.model.UserTokens
import com.balbilo.user.testkit.{AnyWordSpecBase, PropertySpecBase}
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}

class UserTokenJsonSpec extends AnyWordSpecBase with PropertySpecBase {

  "UserTokenJson" should {

    "encode and decode UserToken" in {
      forAll { userToken: UserTokens =>
        val json = s"""{"accessToken":"${userToken.accessToken.value}","refreshToken":${userToken.refreshToken.value}}"""
        decoder[UserTokens](json) shouldBe Right(userToken)
        encoder(userToken).noSpaces shouldBe json.trim
      }
    }

    "perform a roundTrip" in {
      forAll { userToken: UserTokens =>
        decoder[UserTokens](encoder(userToken).noSpaces) shouldBe Right(userToken)
      }
    }
  }

  private def encoder[A : Encoder](value: A) = value.asJson

  private def decoder[A : Decoder](json: String) = decode[A](json)

}
