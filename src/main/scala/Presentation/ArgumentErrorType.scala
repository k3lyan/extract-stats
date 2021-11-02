package Presentation

sealed trait ArgumentErrorType

case object ParseXMLInvalid extends ArgumentErrorType
case object XMLFileInvalid extends ArgumentErrorType
case object ArgumentsNumberInvalid extends ArgumentErrorType

final case class ArgumentError(value: String, errorType: ArgumentErrorType) extends Throwable with Product