//> using dep org.typelevel::cats-effect::3.6.1
import cats.effect.{IO, IOApp, Ref}
import cats.syntax.all._
import scala.concurrent.duration._

object IORefExample extends IOApp.Simple {
  //#region example
  def doAsyncComputation: IO[Int] = IO.sleep(10.millis) *> IO.pure(21)

  def program(ref: Ref[IO, Int]): IO[String] = for {
    value    <- doAsyncComputation // perform the async effect
    _        <- ref.set(value * 2) // update state to value * 2
    res      <- ref.get            // get current state
    _        <- ref.set(res * 10)  // update state to res * 10
    finalRes <- ref.get            // get final state
  } yield s"The meaning of life is $finalRes"

  def run: IO[Unit] = for {
    ref    <- Ref.of[IO, Int](0) // initialize state with 0
    result <- program(ref)
    _      <- IO.println(result)
  } yield ()
  //#endregion
}
