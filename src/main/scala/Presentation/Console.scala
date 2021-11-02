package Presentation

import Parser.SoccerDocument
import Transformer.Scoring.{playerScoring, teamScoring}
import cats.data.NonEmptyList
import cats.effect.{ExitCode, IO}

object Console {
  def reportErrors(err: NonEmptyList[ArgumentError]): IO[ExitCode] = IO {
    err.toList.foreach(error => println(s"\n${error.errorType}: ${error.value}"))
  }.as(ExitCode.Error)

  def printResults(data: SoccerDocument, rankLimit: Int, statName: String): IO[ExitCode] = for {
    _     <- IO {printPlayerScore(data, rankLimit)}
    _     <- IO {printTeamScore(data, statName)}
  } yield ExitCode.Success

  def printPlayerScore(data: SoccerDocument, rankLimit: Int): Unit =
    playerScoring(data.matchStats, data.compositions)
    .sortBy(_.stat)(Ordering.Float.TotalOrdering.reverse)
    .take(rankLimit)
    .zip(1 until (rankLimit + 1))
    .foreach(score => println(s"${score._2}. ${score._1.firstname} ${score._1.lastname} - ${score._1.stat}"))

  def printTeamScore(data: SoccerDocument, statName: String): Unit =
    teamScoring(data.matchStats, data.compositions).foreach { score =>
      score.stat match {
        case Some(value) => println(s"${score.side}; ${score.name} - ${value}")
        case None => println(s"No statistic ${statName} have been found for team ${score.name} ")
      }
    }
}
