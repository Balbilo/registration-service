package com.balbilo.registration.json

import com.balbilo.registration.model.UserDetails
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

trait UserDetailsJson extends ValueClassesJson {

  implicit val userDetailsEncoder: Encoder[UserDetails] = deriveEncoder[UserDetails]
  implicit val userDetailsDecoder: Decoder[UserDetails] = deriveDecoder[UserDetails]

}
