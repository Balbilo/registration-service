package com.balbilo.user.utils

import cats.data.EitherT

object ExtensionMethods {

  implicit class EitherTOps[F[_], A, B](private val FEither: F[Either[A, B]]) {

    def toEitherT: EitherT[F, A, B] = EitherT(FEither)
  }

}
