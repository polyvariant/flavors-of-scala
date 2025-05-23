import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}


object FutureExample extends App {

  // #region examples
  def findUserName(id: Int): Future[String] = Future.successful(s"User$id")

  val userIdFuture: Future[Int] = Future {
    Thread.sleep(1000)
    42
  }

  // Using flatMap to chain another Future
  val userNameFuture: Future[String] = 
    userIdFuture.flatMap(findUserName)

  userNameFuture.onComplete {
    case Success(value) => println(s"The result is $value")
    case Failure(e)     => println(s"Something went wrong: ${e.getMessage}")
  }
  // #endregion
}