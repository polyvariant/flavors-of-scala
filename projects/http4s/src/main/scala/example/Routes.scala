package example

import cats.effect.Sync
import cats.syntax.all.*
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher

object Routes:

  object Artist1Param extends QueryParamDecoderMatcher[String]("artist1")
  object Artist2Param extends QueryParamDecoderMatcher[String]("artist2")

  def routes[F[_]: Sync](C: Collaboration[F]): HttpRoutes[F] =
    val dsl = new Http4sDsl[F] {}
    import dsl.*
    HttpRoutes.of[F] {
      case GET -> Root / "collaboration" :?
          Artist1Param(artist1) +& Artist2Param(artist2) =>
        C.collaboration(artist1, artist2).flatMap(Ok(_)).handleErrorWith {
          case ex: ArtistNotFound => NotFound(ex.getMessage)
          case ex                 => InternalServerError(ex.getMessage)
        }
    }
