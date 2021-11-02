package Transformer

import Model.Score.{PlayerScore, TeamScore}
import Parser.{TeamCompo, TeamData}

object Scoring {
  val addOptions = (o1: Option[Float], o2: Option[Float]) =>
    o1.fold(o2)(v1 => o2.fold(o1)(v2 => Some(v1 + v2)))

  def teamScoring(data: List[TeamData], compo: List[TeamCompo]): List[TeamScore] = {
    data
      .flatMap{ d =>
        compo
          .filter(_.id == d.id)
          .map(c => TeamScore(d.id,
            d.side,
            c.name,
            d.playersStats
              .foldLeft(None: Option[Float]) { (acc, item) => addOptions(acc, item.value)}
          ))
      }
  }

  def playerScoring(matchStats: List[TeamData], compositions: List[TeamCompo]): List[PlayerScore] = {
    matchStats.flatMap { data =>
      compositions
        .filter(_.id == data.id).flatMap { c =>
        data.playersStats
          .filter(_.value.isDefined)
          .flatMap { d =>
            c.players
              .filter(_.id == d.id)
              .map(c => PlayerScore(d.id, c.firstName, c.lastName, d.value.get))
          }
      }
    }
  }
}
