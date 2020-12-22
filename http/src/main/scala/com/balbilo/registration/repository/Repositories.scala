package com.balbilo.registration.repository

final case class Repositories(
    authenticationRepository: AuthenticationRepository,
    registrationRepository: RegistrationRepository
)
