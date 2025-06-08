//> using dep org.typelevel::cats-core::2.13.0
//> using dep org.typelevel::cats-effect::3.6.1
//> using option -Xkind-projector
//> using dep dev.zio::zio::2.1.19

import cats.Monad
import cats.data.StateT
import cats.effect.{IO, IOApp, Temporal}
import cats.syntax.all._
import scala.concurrent.duration._

//#region interface
// Abstract interface over a monadic journey effect
// We only know the Journey will do some `F[_]` logic before returning a String
trait Journey[F[_]] {
  def nextStation: F[String]
  def travel: F[String]
}
//#endregion

//#region partial-implementation
// Partial implementation using StateT over a list of stations
class JourneyStateT[F[_]: Temporal] extends Journey[StateT[F, List[String], *]] {

  override def nextStation: StateT[F, List[String], String] =
    StateT { stationsList =>
      Temporal[F].sleep(1.second) *> {
        stationsList match {
          case Nil          => (Nil, "No more stations").pure[F]
          case head :: tail => (tail, s"Current station: $head").pure[F]
        }
      }
    }

  override def travel: StateT[F, List[String], String] = for {
    _            <- nextStation
    _            <- nextStation
    thirdStation <- nextStation
  } yield thirdStation
}
//#endregion

//#region app
object AbstractStateTApp extends IOApp.Simple {

  val stations: List[String] =
    List("Wrocław", "Kłodzko", "Międzylesie", "Lichkov", "Pardubice", "Praha")

  def run: IO[Unit] = {
    val journey = new JourneyStateT[IO]

    for {
      result <- journey.travel.run(stations)
      (remaining, current) = result
      _ <- IO.println(current)
      _ <- IO.println(s"Remaining stations: $remaining")
    } yield ()
  }
}
//#endregion

import zio._
import scala.concurrent.duration._

//#region zio-implementation
class JourneyZIO(stationsRef: Ref[List[String]]) extends Journey[Task] {

  override def nextStation: Task[String] = 
    stationsRef.modify {
      case Nil          => ("No more stations", Nil)
      case head :: tail => (s"Current station: $head", tail)
    }

  override def travel: Task[String] = for {
    _      <- nextStation
    _      <- nextStation
    result <- nextStation
  } yield result
}
object JourneyZIO {
  val stations = List("Wrocław", "Kłodzko", "Międzylesie", "Lichkov", "Pardubice", "Praha")
  def instance = Ref.make(stations).map(new JourneyZIO(_))
}
//#endregion

//#region zio-app
object ZIOApp extends ZIOAppDefault {
  def run = {
    for {
      journey <- JourneyZIO.instance
      result <- journey.travel
      _ <- Console.printLine(result)
    } yield ExitCode.success
  }
}
//#endregion