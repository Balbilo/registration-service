package com.balbilo.user.json

import com.balbilo.user.model.UserDetails
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

trait UserDetailsJson extends ValueClassesJson {

  implicit lazy val userDetailsEncoder: Encoder[UserDetails] = deriveEncoder[UserDetails]
  implicit lazy val userDetailsDecoder: Decoder[UserDetails] = deriveDecoder[UserDetails]

}
