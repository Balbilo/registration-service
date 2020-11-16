package com.balbilo.registration.model

import cats.data.NonEmptyList

sealed trait ServerError {
  def code: String
  def message: String
}

object ServerError {

  final case class InvalidDetailsError(invalidFields: NonEmptyList[ValidationError]) extends ServerError {
    override def code: String    = "invalidDetails"
    override def message: String = s"Invalid details: [${invalidFields.toList.mkString(", ")}]"
  }

  final case class MethodNotAllowedError(requestMethod: String, allowedMethods: List[String]) extends ServerError {
    override def code: String    = "methodNotAllowed"
    override def message: String =
      s"Request method: [$requestMethod]. Allowed Methods: [${allowedMethods.mkString(", ")}]"
  }

  case object InternalServerError extends ServerError {
    override def code: String    = "internalServerError"
    override def message: String = s"Internal Server Error"
  }

}
