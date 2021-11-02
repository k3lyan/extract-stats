package Parser

import advxml.core.data.{ValidatedNelEx, XmlDecoder}
import advxml.implicits.{$, AnyConverterSyntaxOps, XmlContentZoomSyntaxForId, _}
import cats.syntax.all._

sealed trait TeamParser

final case class TeamData(id: String,
                          side: String,
                          playersStats: List[PlayerStat]) extends TeamParser

object TeamData extends ConvertersInstances {
  implicit def teamDataXmlDecoder(name: String): XmlDecoder[TeamData] = XmlDecoder.of[TeamData] { compet =>
    (
      compet.attr("TeamRef").asValidated[String],
      compet.attr("Side").asValidated[String],
      $(compet).PlayerLineUp.MatchPlayer.run[ValidatedNelEx].andThen { stat =>
        stat
          .map { stat =>
            stat.asValidated[PlayerStat](name)
          }
          .sequence
          .map(_.toList)
      }
      ).mapN(TeamData.apply)
  }
}

final case class TeamCompo(id: String,
                           name: String,
                           players: List[PlayerIdentity]) extends TeamParser
object TeamCompo extends ConvertersInstances {

  implicit val teamCompoXmlDecoder: XmlDecoder[TeamCompo] = XmlDecoder.of[TeamCompo] { compo =>
    (
      compo.attr("uID").asValidated[String],
      (compo \ "Name").content.asValidated[String],
      $(compo).Player.run[ValidatedNelEx].andThen { player =>
        player
          .map { player =>
            player.asValidated[PlayerIdentity]
          }
          .sequence
          .map(_.toList)
      }
      ).mapN(TeamCompo.apply)
  }
}
