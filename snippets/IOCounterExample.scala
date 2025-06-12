//> using dep org.typelevel::cats-effect::3.6.1
import cats.effect.{IO, IOApp, Ref}
import cats.syntax.all._

//#region example
class Counter(ref: Ref[IO, Int]) {
  def increment: IO[Unit] =
    ref.update(_ + 1)

  def reset: IO[Unit] =
    ref.set(0)
}

object CounterApp extends IOApp.Simple {
  def run: IO[Unit] = for {
    ref    <- Ref.of[IO, Int](0) // initialize Ref 0
    counter = new Counter(ref)
    _      <- counter.increment
    _      <- counter.increment
    _      <- counter.reset
  } yield ()
}
//#endregion