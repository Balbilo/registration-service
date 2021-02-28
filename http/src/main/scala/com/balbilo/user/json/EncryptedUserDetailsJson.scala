package com.balbilo.user.json

import com.balbilo.user.model.EncryptedUserDetails
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

trait EncryptedUserDetailsJson extends ValueClassesJson {

  implicit val encryptedUserDetailsEncoder: Encoder[EncryptedUserDetails] = deriveEncoder
  implicit val encryptedUserDetailsDecoder: Decoder[EncryptedUserDetails] = deriveDecoder

}
