package Parser

import advxml.core.data.{Converter, ValidatedConverter, ValidatedNelEx}
import advxml.core.transform.XmlContentZoomRunner
import advxml.implicits.AnyConverterSyntaxOps
import cats.data.Validated.{Invalid, Valid}
import cats.implicits.{catsSyntaxOptionId, catsSyntaxValidatedId}

import scala.xml.NodeSeq

trait ConvertersInstances {

  implicit val floatValidatedConverter: ValidatedConverter[XmlContentZoomRunner, Float] =
    Converter.of[XmlContentZoomRunner, ValidatedNelEx[Float]] { zoomRunner =>
      zoomRunner.validated match {
        case Valid(str) =>
          str.toFloatOption match {
            case Some(number) =>
              number.valid
            case None =>
              new Exception(s"'$str' is not a number").invalidNel
          }
        case Invalid(error) =>
          new Exception(s"Could not read property '$zoomRunner' \n $error").invalidNel
      }
    }

  implicit def optionFromNodeSeqConverter[T](implicit converter: ValidatedConverter[NodeSeq, T]): ValidatedConverter[NodeSeq, Option[T]] =
    Converter.of { nodeSeq =>
      if (nodeSeq.isEmpty) {
        Option.empty[T].valid
      } else {
        nodeSeq.asValidated[T].map(_.some)
      }
    }
}
