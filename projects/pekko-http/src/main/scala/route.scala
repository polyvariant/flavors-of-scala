import org.apache.pekko.http.scaladsl.server.Directives._

val route =
  path("collaboration") {
    get {
      parameters("artist1", "artist2") { (a1, a2) =>
        val result = for {
          artist1 <- findArtist(a1)
          artist2 <- findArtist(a2)
        } yield checkCollaboration(artist1, artist2)
        result match {
          case Left(error)          => complete(404, error)
          case Right(collaboration) => complete(collaboration)
        }
      }
    }
  }
