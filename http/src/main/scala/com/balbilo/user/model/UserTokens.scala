package com.balbilo.user.model

import com.balbilo.user.model.ValueClasses.{AccessToken, RefreshToken}

final case class UserTokens(accessToken: AccessToken, refreshToken: RefreshToken)
