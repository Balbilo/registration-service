package com.balbilo.registration.json

import com.balbilo.registration.model.{HttpError, ServerError}
import io.circe.Decoder
import org.scalatest.Assertions.fail

trait FromJson {

  implicit val serverErrorDecoder: Decoder[ServerError] = cursor =>
    for {
      code    <- cursor.downField("code").as[String]
      message <- cursor.downField("message").as[String]
    } yield serverError(code, message)

  def serverError(code: String, message: String): ServerError = {
    val betweenBrackets = """\[(.*?)\]""".r
    val msg             = betweenBrackets.findAllIn(message).toList.map(_.replaceAll("""\[|\]""", ""))
    (code, msg) match {
      case ("methodNotAllowed", method :: allowed :: Nil) =>
        HttpError.MethodNotAllowedError(method, if (allowed.nonEmpty) allowed.split(", ", -1).toList else Nil)
      case ("internalServerError", Nil)                   => HttpError.InternalServerError
      case ("notFound", path :: Nil)                      => HttpError.NotFound(path)
      case _                                              => fail(s"Failed to parse json server error: $code, $message, ${msg.size}")
    }
  }
}
