package com.balbilo.registration.model

import java.time.LocalDate

object ValueClasses {

  final case class FullName private (value: String) extends AnyVal

  object FullName {
    def apply(value: String): FullName = new FullName(value.trim)
  }
  final case class Email private (value: String) extends AnyVal

  object Email {
    def apply(value: String): Email = new Email(value.trim.toLowerCase())
  }

  final case class Password(value: String) extends AnyVal

  final case class DateOfBirth(value: LocalDate) extends AnyVal

  final case class Token private (value: String) extends AnyVal

}
