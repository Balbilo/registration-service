package com.balbilo.registration.json

import io.circe.{Decoder, Encoder}

trait UserDetails extends ValueClasses {

  implicit val userDetailsEncoder: Encoder[UserDetails] = deriveEncoder
  implicit val userDetailsDecoder: Decoder[UserDetails] =

}
