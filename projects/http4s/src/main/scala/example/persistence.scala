package example

import cats.effect.IO
import scala.io.Source
import cats.implicits._
import io.circe.parser.decode
import io.circe.generic.auto._
import cats.MonadThrow

case class ArtistNotFound(name: String) extends Exception(s"Artist $name not found")

def findArtistIOEither(name: String): IO[Either[ArtistNotFound, Artist]] = {
  val json = Source.fromResource("artists.json").mkString
  decode[List[Artist]](json) match {
    case Right(artists) =>
      artists.find(_.name == name) match {
        case Some(artist) => Right(artist).pure[IO]
        case None         => Left(ArtistNotFound(name)).pure[IO]
      }
    case Left(error) => IO.raiseError(error)
  }
}

def findArtistIO(name: String): IO[Artist] = {
  val json = Source.fromResource("artists.json").mkString
  decode[List[Artist]](json) match {
    case Right(artists) =>
      artists.find(_.name == name) match {
        case Some(artist) => artist.pure[IO]
        case None         => IO.raiseError(ArtistNotFound(name))
      }
    case Left(error) => IO.raiseError(error)
  }
}

def findArtist[F[_]](
    name: String
)(implicit ME: MonadThrow[F]): F[Artist] = {
  val json = Source.fromResource("artists.json").mkString
  decode[List[Artist]](json) match {
    case Right(artists) =>
      artists.find(_.name == name) match {
        case Some(artist) => artist.pure[F]
        case None         => ME.raiseError(ArtistNotFound(name))
      }
    case Left(error) => ME.raiseError(error)
  }
}
