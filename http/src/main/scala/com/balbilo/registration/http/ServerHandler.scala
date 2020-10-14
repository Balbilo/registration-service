package com.balbilo.registration.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, extractUri}
import akka.http.scaladsl.server.{ExceptionHandler, MethodRejection, RejectionHandler}
import com.balbilo.registration.model.MethodNotAllowedError

object ServerHandler {

  private lazy val methodHandler = (methodRejections: Seq[MethodRejection]) => {
    val methodsAllowed = methodRejections.map(_.supported.name())
    complete(MethodNotAllowedError("method", methodsAllowed), "")
  }

  lazy val exceptionHandler: ExceptionHandler = ExceptionHandler { _ =>
    extractUri { _ =>
      complete(StatusCodes.InternalServerError -> "InternalServerError")
    }
  }

  val rejectionHandler = RejectionHandler
    .newBuilder()
    .handleAll[MethodRejection](methodHandler)

}
