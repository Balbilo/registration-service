package com.balbilo.registration.model

import ValueClasses._

final case class UserDetails(
    fullName: FullName,
    email: Email,
    password: Password,
    dateOfBirth: DateOfBirth
)
