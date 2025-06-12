package example

import cats.syntax.all.*
import cats.MonadThrow

// #region interface
trait Collaboration[F[_]] {
  def collaboration(artist1: String, artist2: String): F[String]
}
// #endregion

object Collaboration {
// #region impl
  def impl[F[_]: MonadThrow]: Collaboration[F] = new Collaboration[F] {
    def collaboration(artist1: String, artist2: String): F[String] =
      for {
        a1 <- findArtist(artist1)
        a2 <- findArtist(artist2)
      } yield checkCollaboration(a1, a2)
  }
// #endregion
}
