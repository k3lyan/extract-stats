package Presentation

import Parser.SoccerDocument
import cats.data.Validated.{Invalid, Valid}
import cats.data.ValidatedNel
import cats.implicits._

import java.io.File

import java.nio.file.{Files, Paths}

final case class Validator(args: List[String]) {
  type AllErrorsOr[A] = ValidatedNel[ArgumentError, A]

  val validateArguments: AllErrorsOr[List[String]] =
    if (args.length == 2) args.validNel
    else ArgumentError(s"Wrong number of arguments: ${args.length}", ArgumentsNumberInvalid).invalidNel

  def validateXMLFile(args: List[String]): AllErrorsOr[File] =
    if (Files.isRegularFile(Paths.get(args.head))) new File(args.head).validNel
    else ArgumentError(s"${args.head} is not a valid file path.", XMLFileInvalid).invalidNel

  val parseDocument: AllErrorsOr[SoccerDocument] =
    validateArguments
      .andThen(args => validateXMLFile(args))
      .andThen(file =>
        SoccerDocument.parse(file, args.tail.head) match {
          case Valid(suppliers) => suppliers.validNel
          case Invalid(_) => ArgumentError(s"Could not load XML properly.", ParseXMLInvalid).invalidNel
    })
}
