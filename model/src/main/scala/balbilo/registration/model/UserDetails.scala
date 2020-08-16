package balbilo.registration.model

import balbilo.registration.model.Entities._

final case class UserDetails(
    fullName: FullName,
    email: Email,
    password: Password,
    dateOfBirth: DateOfBirth
)
