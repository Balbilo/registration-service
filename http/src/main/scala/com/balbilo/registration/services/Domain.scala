package com.balbilo.registration.services

final case class Domain(
    validation: UserDetailsValidation,
    authentication: AuthenticateService,
    registration: RegisterService,
)
