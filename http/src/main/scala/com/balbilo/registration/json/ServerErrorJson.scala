package com.balbilo.registration.json

import com.balbilo.registration.model.ServerError
import io.circe.{Encoder, Json}

trait ServerErrorJson {

  implicit def serverErrorEncoder[E <: ServerError]: Encoder[E] = error =>
    Json.obj(
      ("code", Json.fromString(error.code)),
      ("message", Json.fromString(error.message))
    )

}
