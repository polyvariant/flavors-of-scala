package example

import cats.effect.IO
import org.http4s.*
import org.http4s.implicits.*
import munit.CatsEffectSuite

class CollaborationSpec extends CatsEffectSuite {

  test("respond to the /collaboration request with valid artists") {
    assertIO(
      collaborationReq("Frank Sinatra", "Aretha Franklin").flatMap(_.as[String]),
      "Frank Sinatra and Aretha Franklin could have collaborated between 1954 and 1995"
    )
  }

  test("return 404 for non-existent artists") {
    assertIO(
      collaborationReq("NonExistent", "ArtistB").map(_.status),
      Status.NotFound
    )
  }

  private[this] def collaborationReq(
      artist1: String,
      artist2: String
  ): IO[Response[IO]] =
    val req = Request[IO](
      Method.GET,
      uri"/collaboration"
        .withQueryParam("artist1", artist1)
        .withQueryParam("artist2", artist2)
    )
    Routes.routes[IO](Collaboration.impl[IO]).orNotFound(req)
}
