//> using dep org.typelevel::cats-mtl::1.5.0
//> using dep org.typelevel::cats-core::2.13.0
//> using dep org.typelevel::cats-effect::3.6.1
//> using option -Xkind-projector
//> using dep dev.zio::zio::2.1.19
package finaltaglessartists


import cats.Monad
import cats.effect.{IO, IOApp, Temporal}
import cats.syntax.all._
import cats.data.EitherT
import scala.concurrent.duration._
import scala.io.Source


case class ArtistNotFound(name: String) extends Exception(s"Artist $name not found")
case class Artist(name: String, startYear: Int, endYear: Int)
def jsonParse[T](content: String): T = ???


//#region interface
// Abstract interface over a monadic effect
trait Artists[F[_]] {
  def findArtist(name: String): F[Artist]
}
//#endregion

//#region mtl-implementation
class ArtistT extends Artists[EitherT[IO, ArtistNotFound, *]] {

  def findArtist(name: String): EitherT[IO, ArtistNotFound, Artist] = 
    ??? // you know it already

}
//#endregion


import zio._
import scala.concurrent.duration._

//#region zio-implementation
class ArtistsZIO(artistsRef: Ref[Map[String, Artist]]) 
  extends Artists[zio.IO[ArtistNotFound, *]] {

  def findArtist(name: String): zio.IO[ArtistNotFound, Artist] = 
    artistsRef.get.map{ ref => 
      ref.get(name).toRight(ArtistNotFound(name))
    }.flatMap(ZIO.fromEither)

}
//#endregion