package com.balbilo.registration.model

import cats.data.NonEmptyList

sealed trait ServerError {
  def code: String
  def message: String
}

sealed trait AuthenticationError extends ServerError

object AuthenticationError {

  final case class InvalidDetailsError(invalidFields: NonEmptyList[ValidationError]) extends AuthenticationError {
    val code: String    = "invalidDetails"
    val message: String = s"[${invalidFields.toList.mkString(", ")}]"
  }
}

sealed trait HttpError extends ServerError

object HttpError {

  final case class NotFound(path: String) extends HttpError {
    val code: String    = "notFound"
    val message: String = s"Not found: [$path]"
  }

  final case class MethodNotAllowedError(requestMethod: String, allowedMethods: Seq[String]) extends HttpError {
    val code: String    = "methodNotAllowed"
    val message: String =
      s"Request method: [$requestMethod]. Allowed Methods: [${allowedMethods.mkString(", ")}]"
  }

  case object InternalServerError extends HttpError {
    val code: String    = "internalServerError"
    val message: String = s"Internal Server Error"
  }
}

sealed trait RegisterError extends ServerError

object RegisterError {

  case object UserRegistered extends RegisterError {
    val code: String    = "Registered"
    val message: String = "Registered: user already signed up"
  }
}
