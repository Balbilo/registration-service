package balbilo.registration.model

import balbilo.registration.model.Entities._

final case class UserDetails(
    firstName: FirstName,
    lastName: LastName,
    email: Email,
    username: Username,
    password: Password,
    dateOfBirth: DateOfBirth
)
