package com.balbilo.registration.json

import com.balbilo.registration.model.RegistrationError
import io.circe.{Decoder, Encoder, Json}

trait RegistrationErrorJson {

  implicit val registrationErrorEncoder: Encoder[RegistrationError] = error =>
    Json.obj(
      ("code", Json.fromString(error.code)),
      ("message", Json.fromString(error.message))
    )

}
