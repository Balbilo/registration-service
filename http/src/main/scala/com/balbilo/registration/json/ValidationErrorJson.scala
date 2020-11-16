package com.balbilo.registration.json

import com.balbilo.registration.domain.ValidationError
import io.circe.{Encoder, Json}

trait ValidationErrorJson {

  implicit lazy val registrationErrorEncoder: Encoder[ValidationError] = error =>
    Json.obj(
      ("code", Json.fromString(error.code)),
      ("message", Json.fromString(error.message))
    )

}
