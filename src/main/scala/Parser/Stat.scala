package Parser

import advxml.core.data.XmlDecoder
import advxml.implicits._
import cats.syntax.all._

final case class Stat(name: String, value: Float)

object Stat extends ConvertersInstances {
  implicit val converter: XmlDecoder[Stat] = XmlDecoder.of { stat => (
    stat.attr("Type").asValidated[String],
    stat.content.asValidated[Float]
    ).mapN(Stat.apply)
  }
}