package com.balbilo.user.repository

final case class Repositories(
    authenticationRepository: AuthenticationRepository,
    registrationRepository: RegistrationRepository
)
