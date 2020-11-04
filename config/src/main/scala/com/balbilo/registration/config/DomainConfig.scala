package com.balbilo.registration.config

final case class DomainConfig(userDetailsConfig: UserDetailsConfig)

final case class UserDetailsConfig(fullNameRegex: String, emailRegex: String, passwordRegex: String, maxYears: Int)
