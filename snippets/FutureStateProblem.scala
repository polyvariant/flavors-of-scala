import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object FutureStateProblem extends App {
  // #region examples
  var counter = 0 // shared mutable state

  def increment(): Future[Unit] = Future {
    val current = counter
    Thread.sleep(10) // simulate some work
    counter = current + 1
  }

  val futures = (1 to 100).map(_ => increment())

  Future.sequence(futures).onComplete {
    case Success(_) => println(s"Final counter value: $counter")
    case Failure(e) => println(s"Error: ${e.getMessage}")
  }
  // #endregion

  Thread.sleep(2000) // wait for Futures to complete (demo only)
}
