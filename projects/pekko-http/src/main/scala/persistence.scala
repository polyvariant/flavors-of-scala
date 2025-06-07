import scala.io.Source
import org.apache.pekko.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json._
import scala.concurrent.ExecutionContext
import cats.data.EitherT
import cats.implicits._
import scala.concurrent.Future

implicit val artistFormat: JsonFormat[Artist] = jsonFormat3(Artist.apply)

// #region findArtist
def findArtist(name: String): Either[String, Artist] = {
  val artists: List[Artist] = {
    val json = Source.fromResource("artists.json").mkString
    json.parseJson.convertTo[List[Artist]]
  }

  artists.find(_.name == name).toRight(s"Artist $name not found")
}
// #endregion

def findArtistAsync(
    name: String
)(implicit ec: ExecutionContext): Future[Either[String, Artist]] =
  Future(findArtist(name))

def findArtistAsyncMTL(
    name: String
)(implicit ec: ExecutionContext): EitherT[Future, String, Artist] = {
  EitherT(findArtistAsync(name))
}
