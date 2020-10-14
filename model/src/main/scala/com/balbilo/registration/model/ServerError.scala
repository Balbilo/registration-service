package com.balbilo.registration.model

sealed trait ServerError extends ModelError {
  def code: String
  def message: String
}

final case class MethodNotAllowedError(requestMethod: String, allowedMethods: Seq[String]) extends ServerError {
  override def code: String    = "methodNotAllowed"
  override def message: String =
    s"Request method: ${requestMethod.toUpperCase}. Allowed Methods: ${allowedMethods.map(_.toUpperCase()).mkString(", ")}"
}
