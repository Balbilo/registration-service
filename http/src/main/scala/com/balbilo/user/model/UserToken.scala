package com.balbilo.user.model

import com.balbilo.user.model.ValueClasses.{Expiration, Token}

final case class UserToken(token: Token, expiration: Expiration, Roles: Seq[String])
