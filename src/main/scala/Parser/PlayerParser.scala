package Parser

import advxml.core.data.{ValidatedNelEx, XmlDecoder}
import advxml.implicits.{$, AnyConverterSyntaxOps, XmlContentZoomSyntaxForId}
import advxml.implicits._
import cats.syntax.all._



sealed trait PlayerParser

final case class PlayerStat(id: String, value: Option[Float]) extends PlayerParser

object PlayerStat extends ConvertersInstances {
  implicit def playerXmlDecoder(name: String): XmlDecoder[PlayerStat] = XmlDecoder.of[PlayerStat] { player =>
    (
      player.attr("PlayerRef").asValidated[String],
      $(player).Stat.run[ValidatedNelEx].andThen { stat =>
        stat
          .map { stat =>
            stat.asValidated[Stat]
          }
          .sequence
          .map(_.toList.filter(stat => stat.name == name))
        .map {
          case List(stat) => Some(stat.value)
          case Nil => None
        }
      }
      ).mapN(PlayerStat.apply)
  }
}

final case class PlayerIdentity(id: String, firstName: String, lastName: String) extends PlayerParser

object PlayerIdentity extends ConvertersInstances {
  implicit val playerConverter: XmlDecoder[PlayerIdentity] = XmlDecoder.of { player =>
    (
      player.attr("uID").asValidated[String],
      (player \ "PersonName" \ "First").content.asValidated[String],
      (player \ "PersonName" \ "Last").content.asValidated[String]
      ).mapN(PlayerIdentity.apply)
  }
}