package com.balbilo.registration.domain

final case class Domain(
    validation: UserDetailsValidation,
    authentication: AuthenticateService,
    registration: RegisterService,
    handlingService: HttpHandler
)
