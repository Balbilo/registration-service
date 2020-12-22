package com.balbilo.registration.model

import com.balbilo.registration.model.ValueClasses.{Expiration, Token}

final case class UserToken(token: Token, expiration: Expiration, Roles: Seq[String])
