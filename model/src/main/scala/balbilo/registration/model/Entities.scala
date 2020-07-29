package balbilo.registration.model

import java.util.Date

object Entities {

  final case class FirstName(value: String) extends AnyVal

  final case class LastName(value: String) extends AnyVal

  final case class Email(value: String) extends AnyVal

  final case class Username(value: String) extends AnyVal

  final case class Password(value: String) extends AnyVal

  final case class DateOfBirth(value:  Date) extends AnyVal

}
