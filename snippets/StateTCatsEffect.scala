//> using dep org.typelevel::cats-core::2.13.0
//> using dep org.typelevel::cats-effect::3.6.1

import cats.data.StateT
import cats.effect.{IO, IOApp}
import scala.concurrent.duration._

object StateTCatsEffect extends IOApp.Simple {

  //#region example
  // StateT[IO, List[String], String] - modifies a List[String] state and returns a String in IO
  val nextStation: StateT[IO, List[String], String] = StateT { stationsList =>
    IO.sleep(1.second) *> IO.pure {
      stationsList match {
        case Nil          => (Nil, "No more stations")
        case head :: tail => (tail, s"Current station: $head")
      }
    }
  }
  // Let's ride!
  val travel: StateT[IO, List[String], String] = for {
    _            <- nextStation
    _            <- nextStation
    thirdStation <- nextStation
  } yield thirdStation

  val stations: List[String] = List("Wrocław", "Kłodzko", "Międzylesie", "Lichkov", "Pardubice", "Praha")
  def run: IO[Unit] = for {
    result <- travel.run(stations)
    (remainingStations, currentStation) = result
    _ <- IO.println(s"$currentStation")
    _ <- IO.println(s"Remaining stations: $remainingStations")
  } yield ()
  //#endregion
}

