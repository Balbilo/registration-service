package com.balbilo.user.json

import com.balbilo.user.model.ValueClasses._
import io.circe.Decoder.{decodeLocalDate, decodeString}
import io.circe.Encoder.{encodeLocalDate, encodeString}
import io.circe.{Decoder, Encoder}

private[json] trait ValueClassesJson {

  implicit lazy val fullNameEncoder: Encoder[FullName] = encodeString.contramap(_.value)
  implicit lazy val fullNameDecoder: Decoder[FullName] = decodeString.map(FullName.apply)

  implicit lazy val emailEncoder: Encoder[Email] = encodeString.contramap(_.value)
  implicit lazy val emailDecoder: Decoder[Email] = decodeString.map(Email.apply)

  implicit lazy val passwordEncoder: Encoder[Password] = encodeString.contramap(_.value)
  implicit lazy val passwordDecoder: Decoder[Password] = decodeString.map(Password)

  implicit lazy val encryptedPasswordEncoder: Encoder[EncryptedPassword] = encodeString.contramap(_.value)
  implicit lazy val encryptedPasswordDecoder: Decoder[EncryptedPassword] = decodeString.map(EncryptedPassword)

  implicit lazy val saltEncoder: Encoder[Salt] = encodeString.contramap(_.value)
  implicit lazy val saltDecoder: Decoder[Salt] = decodeString.map(Salt)

  implicit lazy val dateOfBirthEncoder: Encoder[DateOfBirth] = encodeLocalDate.contramap(_.value)
  implicit lazy val dateOfBirthDecoder: Decoder[DateOfBirth] = decodeLocalDate.map(DateOfBirth.apply)

  implicit lazy val accessTokenEncoder: Encoder[AccessToken] = encodeString.contramap(_.value)
  implicit lazy val accessTokenDecoder: Decoder[AccessToken] = decodeString.map(AccessToken)

  implicit lazy val refreshTokenEncoder: Encoder[RefreshToken] = encodeString.contramap(_.value)
  implicit lazy val refreshTokenDecoder: Decoder[RefreshToken] = decodeString.map(RefreshToken)

}
