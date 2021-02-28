package com.balbilo.user.repository

final case class Repositories(
    authorizationRepository: AuthorizationRepository,
    registrationRepository: RegistrationRepository
)
