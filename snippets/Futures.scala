package futureexample

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.io.Source

case class Artist(name: String, startYear: Int, endYear: Int)

def jsonParse[T](content: String): T = ???

object FutureExample extends App {

  case class Artist(name: String, startYear: Int, endYear: Int)

  // #region examples
  def findArtist(name: String)(implicit ec: ExecutionContext): Future[Artist] = 
    Future {
      val artists: List[Artist] = {
        val jsonText = Source.fromResource("artists.json").mkString
        jsonParse[List[Artist]](jsonText)
      }
      artists.find(_.name == name).head
    }
  
  def calculateActiveYears(artist: Artist): Int =
    artist.endYear - artist.startYear

  // Using flatMap to chain another Future
  val artistFuture: Future[Int] =
    findArtist("Frank Sinatra")
      .map(calculateActiveYears)

  artistFuture.onComplete {
    case Success(value) => println(s"The result is $value")
    case Failure(e)     => println(s"Something went wrong: ${e.getMessage}")
  }
  // #endregion
}

