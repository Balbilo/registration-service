package com.balbilo.user.json

import com.balbilo.user.model.UserTokens
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

trait UserTokenJson extends ValueClassesJson {

  implicit lazy val userTokenEncoder: Encoder[UserTokens] = deriveEncoder[UserTokens]
  implicit lazy val userTokenDecoder: Decoder[UserTokens] = deriveDecoder[UserTokens]
}
