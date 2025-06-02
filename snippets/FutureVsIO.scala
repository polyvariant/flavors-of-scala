//> using dep org.typelevel::cats-effect::3.6.1
import scala.concurrent.Future
import cats.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.unsafe.implicits.global

@main def main() = {
  // #region example
  // Future: eager, starts running immediately
  val future = Future {
    println("Running Future")
    42
  }

  // IO: just a value, lazy, describes the computation but does not run it
  val io = IO {
    println("Running IO")
    42
  }

  // Nothing printed until we explicitly run IO
  io.unsafeRunSync()
  // #endregion
}

