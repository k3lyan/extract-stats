import Presentation.Console.{printResults, reportErrors}
import Presentation.{Validator}
import cats.data.Validated.{Invalid, Valid}
import cats.effect.{ExitCode, IO, IOApp}
import com.typesafe.scalalogging.StrictLogging

object Main extends IOApp with StrictLogging {
  def run(args: List[String]): IO[ExitCode] = {
    IO { Validator(args).parseDocument }
      .flatMap {
        case Valid(document) => printResults(document, 5, args.tail.head)
        case Invalid(e) => reportErrors(e)
      }
  }
}
