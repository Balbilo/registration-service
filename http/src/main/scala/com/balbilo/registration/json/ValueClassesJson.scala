package com.balbilo.registration.json

import com.balbilo.registration.model.ValueClasses._
import io.circe.Decoder.{decodeLocalDate, decodeString}
import io.circe.Encoder.{encodeLocalDate, encodeString}
import io.circe.{Decoder, Encoder}

trait ValueClassesJson {

  implicit lazy val fullNameEncoder: Encoder[FullName] = encodeString.contramap(_.value)
  implicit lazy val fullNameDecoder: Decoder[FullName] = decodeString.map(FullName.apply)

  implicit lazy val emailEncoder: Encoder[Email] = encodeString.contramap(_.value)
  implicit lazy val emailDecoder: Decoder[Email] = decodeString.map(Email.apply)

  implicit lazy val passwordEncoder: Encoder[Password] = encodeString.contramap(_.value)
  implicit lazy val passwordDecoder: Decoder[Password] = decodeString.map(Password)

  implicit lazy val dateOfBirthEncoder: Encoder[DateOfBirth] = encodeLocalDate.contramap(_.value)
  implicit lazy val dateOfBirthDecoder: Decoder[DateOfBirth] = decodeLocalDate.map(DateOfBirth.apply)

}
