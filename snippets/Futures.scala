import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
// #region examples
// Future declaration
val future1: Future[Int] = Future {
  Thread.sleep(1000)
  42
}

def double(input: Int) = input * 2

// Using flatMap to chain another Future
val future3: Future[String] = 
  future1
    .map(double)
    .flatMap(findUserName)

val _ = future3.foreach(println)

def findUserName(id: Int): Future[String] =
  Future.successful("User1")
// #endregion