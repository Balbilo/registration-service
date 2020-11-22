package com.balbilo.registration.model

import com.balbilo.registration.model.ValueClasses.Token

final case class UserToken(token: Token, expireTimeStamp: Long)
