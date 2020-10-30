package com.balbilo.registration.model

sealed trait ServerError extends ModelError {
  def code: String
  def message: String
}

object ServerError {

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
