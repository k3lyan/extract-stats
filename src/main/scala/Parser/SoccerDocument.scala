package Parser

import advxml.core.data.{ValidatedNelEx, XmlDecoder}
import advxml.implicits._
import cats.syntax.all._

import java.io.File
import scala.xml.XML

final case class SoccerDocument(matchStats: List[TeamData], compositions: List[TeamCompo])

object SoccerDocument {
  implicit def xmlCompanionDecoder(name: String): XmlDecoder[SoccerDocument] = XmlDecoder.of  { xml =>
    (
        $(xml).SoccerDocument.MatchData.TeamData.run[ValidatedNelEx].andThen { data =>
          data
            .map { data =>
              data.asValidated[TeamData](name)
            }
            .sequence
            .map(_.toList)
        },
       $(xml).SoccerDocument.Team.run[ValidatedNelEx].andThen { compo =>
         compo
           .map { compo =>
             compo.asValidated[TeamCompo]
           }
           .sequence
           .map(_.toList)
       }
      ).mapN(SoccerDocument.apply)
  }

  def parse(xmlFile: File, name: String): ValidatedNelEx[SoccerDocument] =
    XML.loadFile(xmlFile).decode[SoccerDocument](name)
}