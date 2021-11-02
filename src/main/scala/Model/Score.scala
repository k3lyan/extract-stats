package Model

sealed trait Score

object Score {
  final case object Empty extends Score
  final case class TeamScore(id: String, side: String, name: String, stat: Option[Float]) extends Score
  final case class PlayerScore(id: String, firstname: String, lastname: String, stat: Float) extends Score
}
