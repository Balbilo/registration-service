package com.balbilo.registration.http

import com.balbilo.registration.model.UserDetails

import scala.concurrent.Future

object VerifyUserService {

  def verifyUser(userDetails: UserDetails): Future[Boolean] = ???

}
