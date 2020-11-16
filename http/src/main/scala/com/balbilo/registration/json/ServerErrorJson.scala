package com.balbilo.registration.json

import com.balbilo.registration.ServerError
import io.circe.{Encoder, Json}

trait ServerErrorJson {

  implicit val serverErrorEncoder: Encoder[ServerError] = error =>
    Json.obj(
      ("code", Json.fromString(error.code)),
      ("message", Json.fromString(error.message))
    )

}
