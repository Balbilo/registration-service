package com.balbilo.registration.domain

import scala.util.matching.Regex

private[domain] object StringValidators {

  val emailRegex: Regex = """^[a-zA-Z0-9_.+-]{1,200}@[a-zA-Z0-9-]{1,200}\.[a-zA-Z0-9-.]{0,200}[a-zA-Z0-9]$""".r

  val fullNameRegex: Regex = """^([a-zA-Z]{1}[a-zA-Z ,.'-]{0,100}\s{0,1}[a-zA-Z ,.'-]{0,100}[a-zA-Z0-9 ']$)""".r

  //At least 8 letters with one Capital and one Number
  val passwordRegex = """^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$""".r

}
