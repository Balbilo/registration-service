package com.balbilo.user.model

import com.balbilo.user.model.ValueClasses.{DateOfBirth, Email, EncryptedPassword, FullName, Salt}

final case class EncryptedUserDetails(
    id: String,
    fullName: FullName,
    email: Email,
    password: EncryptedPassword,
    salt: Salt,
    dateOfBirth: DateOfBirth,
    EmailVerified: Boolean = false
)
