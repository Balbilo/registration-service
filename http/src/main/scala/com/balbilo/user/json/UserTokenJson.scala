package com.balbilo.user.json

import com.balbilo.user.model.UserToken
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

trait UserTokenJson extends ValueClassesJson {

  implicit lazy val userTokenEncoder: Encoder[UserToken] = deriveEncoder[UserToken]
  implicit lazy val userTokenDecoder: Decoder[UserToken] = deriveDecoder[UserToken]
}
