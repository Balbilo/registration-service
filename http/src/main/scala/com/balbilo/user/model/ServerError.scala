package com.balbilo.user.model

import cats.data.NonEmptyList

sealed trait ServerError {
  def code: String
  def message: String
}

sealed trait AuthenticationError extends ServerError

object AuthenticationError {

  final case class UnknownError(error: String) extends AuthenticationError {
    val code: String = "unexpectedError"
    val message      = s"Unexpected Error: ${error}"
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

sealed trait RegistrationError extends ServerError

object RegistrationError {

  final case class InvalidDetailsError(invalidFields: NonEmptyList[ValidationError]) extends RegistrationError {
    val code: String    = "invalidDetails"
    val message: String = s"[${invalidFields.toList.mkString(", ")}]"
  }

  case object UserRegistered extends RegistrationError {
    val code: String    = "Registered"
    val message: String = "Registered: user already signed up"
  }

  case object PasswordEncryptionError extends RegistrationError {
    val code: String    = "PasswordEncryptionError"
    val message: String = "PasswordEncryptionError: user's password failed to encrypt"

  }
}
