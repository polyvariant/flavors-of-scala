import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object FuturesEagerEval extends App {

  // #region examples
  def doX: Future[Int] = Future {
    println("done x")
    42
  }

  def doY: Future[Int] = Future {
    println("done y")
    666
  }

  def decision(what: String): Future[Int] = {
    val (x, y) = (doX, doY)
    if (what == "x") x else y
  }

  decision("x").onComplete {
    case Success(value) => println(s"The result is $value")
    case Failure(e)     => println(s"Something went wrong: ${e.getMessage}")
  }
  // #endregion
}
