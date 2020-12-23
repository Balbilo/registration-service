package com.balbilo.user

import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, extractRequest, handleExceptions, handleRejections}
import akka.http.scaladsl.server.{Directive, ExceptionHandler, MethodRejection, RejectionHandler}
import com.balbilo.user.model.HttpError
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import com.balbilo.user.json._

object HttpHandler extends LazyLogging {

  def exceptionsRejections: Directive[Unit] = handleExceptions(exceptionHandler) & handleRejections(rejectionHandler)

  private def exceptionHandler: ExceptionHandler = ExceptionHandler { throwable =>
    extractRequest { request =>
      handlingLogger(s"Exception: ${throwable.getClass.getName}", request, logger.error(_, throwable))
      complete(StatusCodes.InternalServerError -> HttpError.InternalServerError)
    }
  }

  private def rejectionHandler =
    RejectionHandler
      .newBuilder()
      .handleAll[MethodRejection](methodNotAllowedHandler)
      .handleNotFound(notFoundHandler)
      .result()

  private lazy val methodNotAllowedHandler = (methodRejections: Seq[MethodRejection]) =>
    extractRequest { request =>
      val supportedMethods = methodRejections.map(_.supported.name)
      handlingLogger(s"MethodNotAllowed: ${request.method.name}, supportedMethods: [${supportedMethods.mkString(", ")}]",
                     request,
                     logger.warn(_))
      complete(StatusCodes.MethodNotAllowed -> HttpError.MethodNotAllowedError(request.method.name, supportedMethods))
    }

  private lazy val notFoundHandler =
    extractRequest { request =>
      val notFound = HttpError.NotFound(request.uri.path.toString())
      handlingLogger(s"Not found", request, logger.warn(_))
      complete(StatusCodes.NotFound -> notFound)
    }

  private def handlingLogger(message: String, request: HttpRequest, log: String => Unit): Unit =
    log(s"$message, path: ${request.uri.path}, method: ${request.method}, content-type: ${request.entity.contentType}")
}
