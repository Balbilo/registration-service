package com.balbilo.registration.json

import java.time.LocalDate

import com.balbilo.registration.model.ServerError._
import com.balbilo.registration.model.ValidationError._
import com.balbilo.registration.model.ValueClasses._
import com.balbilo.registration.model.{ServerError, ValidationError}
import io.circe.Decoder
import org.scalatest.Assertions.fail

trait FromJson {

  implicit val registrationErrorDecoder: Decoder[ValidationError] = cursor =>
    for {
      code    <- cursor.downField("code").as[String]
      message <- cursor.downField("message").as[String]
    } yield registrationError(code, message)

  implicit val serverErrorDecoder: Decoder[ServerError] = cursor =>
    for {
      code    <- cursor.downField("code").as[String]
      message <- cursor.downField("message").as[String]
    } yield serverError(code, message)

  def registrationError(code: String, message: String): ValidationError = {
    val betweenBrackets = """\[(.*?)\]""".r
    val msg             = betweenBrackets.findFirstIn(message).getOrElse("").replaceAll("""\[|\]""", "")
    (code, msg) match {
      case ("invalidFullName", value)    => InvalidFullName(FullName(value))
      case ("invalidEmail", value)       => InvalidEmail(Email(value))
      case ("invalidPassword", value)    => InvalidPassword(Password(value))
      case ("invalidDateOfBirth", value) => InvalidDateOfBirth(DateOfBirth(LocalDate.parse(value)))
      case _                             => fail(s"Failed to parse json registration error: $code, $message")
    }
  }

  def serverError(code: String, message: String): ServerError = {
    val betweenBrackets = """\[(.*?)\]""".r
    val msg             = betweenBrackets.findAllIn(message).toList.map(_.replaceAll("""\[|\]""", ""))
    (code, msg) match {
      case ("methodNotAllowed", method :: allowed :: Nil) =>
        MethodNotAllowedError(method, if (allowed.nonEmpty) allowed.split(", ", -1).toList else Nil)
      case ("internalServerError", Nil)                   => InternalServerError
      case _                                              => fail(s"Failed to parse json server error: $code, $message, ${msg.size}")
    }
  }
}
