package com.balbilo.registration.model

import scala.util.matching.Regex

case class ValidationRules(
    userNameRegex: Regex,
    passwordRegex: Regex,
    ameRegex: Regex
)
