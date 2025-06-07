import org.apache.pekko.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

// #region route
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
// #endregion

// #region asyncRoute
def asyncRoute(implicit ec: ExecutionContext) =
  path("collaboration") {
    get {
      parameters("artist1", "artist2") { (artistIn1, artistIn2) =>
        val resultF = for {
          maybeArtist1 <- findArtistAsync(artistIn1)
          maybeArtist2 <- findArtistAsync(artistIn2)
        } yield for {
          artist1 <- maybeArtist1
          artist2 <- maybeArtist2
        } yield checkCollaboration(artist1, artist2)

        onComplete(resultF) {
          case Failure(exception)            => complete(500, exception)
          case Success(Left(error))          => complete(404, error)
          case Success(Right(collaboration)) => complete(collaboration)
        }
      }
    }
  }
// #endregion

// #region asyncRouteMTL
def asyncRouteMTL(implicit ec: ExecutionContext) =
  path("collaboration") {
    get {
      parameters("artist1", "artist2") { (a1, a2) =>
        val result = for {
          artist1 <- findArtistAsyncMTL(a1) // TOOD: EitherT explain
          artist2 <- findArtistAsyncMTL(a2)
        } yield checkCollaboration(artist1, artist2)

        onComplete(result.value) {
          case Failure(exception)            => complete(500, exception)
          case Success(Left(error))          => complete(404, error)
          case Success(Right(collaboration)) => complete(collaboration)
        }
      }
    }
  }
// #endregion
