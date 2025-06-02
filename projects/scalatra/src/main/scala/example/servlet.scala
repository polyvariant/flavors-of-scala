package example

import org.scalatra._
import org.json4s.jackson.JsonMethods._
import scala.io.Source
import org.json4s.DefaultFormats
import org.json4s.Formats
import org.scalatra.json._
import org.json4s.jvalue2extractable

class CollaborationServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats =
    DefaultFormats.withBigDecimal

  get("/collaboration") {
    val artist1 = findArtist(
      params.getOrElse("artist1", halt(400, "Missing parameter: artist1"))
    )
    val artist2 = findArtist(
      params.getOrElse("artist2", halt(400, "Missing parameter: artist2"))
    )

    checkCollaboration(artist1, artist2)
  }

  // this simulates a side-effectful call: example of an API or a DB call
  def findArtist(name: String): Artist = {
    val artists: List[Artist] = {
      val json =
        Source
          .fromResource("artists.json")
          .mkString
      parse(json).extract[List[Artist]]
    }

    artists
      .find(_.name == name)
      .getOrElse(halt(404, s"Artist $name not found"))
  }
}
