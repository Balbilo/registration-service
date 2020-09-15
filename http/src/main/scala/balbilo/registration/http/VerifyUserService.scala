package balbilo.registration.http

import balbilo.registration.model.UserDetails

import scala.concurrent.Future

object VerifyUserService {

  def verifyUser(userDetails: UserDetails): Future[Boolean] = ???

}
