package com.balbilo.registration.domain

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.balbilo.registration.json._
import com.balbilo.registration.model.HttpError
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._


final class HttpHandler extends LazyLogging {

  def handler: Directive[Unit] = handleExceptions(exceptionHandler) & handleRejections(rejectionHandler)

  private def exceptionHandler: ExceptionHandler = ExceptionHandler { throwable =>
    extractRequest { request =>
    val logError: String => Unit = logger.error(_,throwable)
      handlingLogger(s"Exception: ${throwable.getClass.getName}",request,logError)
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
      val logWarn: String => Unit = logger.warn(_)
      handlingLogger(s"MethodNotAllowed: ${request.method.name}, supportedMethods: [${supportedMethods.mkString(", ")}]",request,logWarn)
      complete(StatusCodes.MethodNotAllowed -> HttpError.MethodNotAllowedError(request.method.name, supportedMethods))
    }

  private lazy val notFoundHandler =
    extractRequest { request =>
      val notFound = HttpError.NotFound(request.uri.path.toString())
      val logWarn: String => Unit = logger.warn(_)
      handlingLogger(s"Not found", request, logWarn)
      complete(StatusCodes.NotFound -> notFound)
    }

  private def handlingLogger(message: String, request: HttpRequest,log: String => Unit): Unit ={
    log(s"$message, path: ${request.uri.path}, method: ${request.method}, content-type: ${request.entity.contentType}")
  }
}
