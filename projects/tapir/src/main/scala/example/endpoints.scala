package example

import sttp.tapir._
import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import sttp.tapir.server.ServerEndpoint

// #region endpoint
val collaborationEndpoint: PublicEndpoint[(String, String), String, String, Any] = endpoint.get
  .in("collaboration")
  .in(query[String]("artist1"))
  .in(query[String]("artist2"))
  .out(stringBody)
  .errorOut(plainBody[String])
// #endregion

val collaborationServerEndpoint = collaborationEndpoint.serverLogic(collaboration)

// #region impl
private def collaboration(artistIn1: String, artistIn2: String) = {
  val result = for {
    artist1 <- findArtist[IO](artistIn1)
    artist2 <- findArtist[IO](artistIn2)
  } yield checkCollaboration(artist1, artist2)
  result.attempt.map(_.leftMap(_.getMessage()))
}
// #endregion
