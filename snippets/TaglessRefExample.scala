//> using dep org.typelevel::cats-effect::3.6.1
package taglessrefexample // keep this to avoid overlaps with other files

import cats.effect.{IO, IOApp, Ref}
import cats.syntax.all._
import scala.concurrent.duration._
import cats.effect.kernel.Sync


object MutableRef {
  def of[F[_]: Sync, A](initial: A): F[MutableRef[F, A]] = Ref.of[F, A](initial).map(fromRef(_))

  def fromRef[F[_], A](ref: Ref[F, A]): MutableRef[F, A] = new MutableRef[F, A] {
    def get: F[A]          = ref.get
    def set(a: A): F[Unit] = ref.set(a)
  }
}

//#region tagless
trait MutableRef[F[_], A] {
  def get: F[A]
  def set(a: A): F[Unit]
}

trait Program[F[_]] {
  def doAsyncComputation: F[Int]
  def program(ref: MutableRef[F, Int]): F[String]
}

object Program {
  def apply[F[_]: Sync]: Program[F] = new Program[F] {
    def doAsyncComputation: F[Int] = Sync[F].delay(Thread.sleep(10)) *> Sync[F].pure(21)

    def program(ref: MutableRef[F, Int]): F[String] = for {
      value <- doAsyncComputation
      _     <- ref.set(value * 2)
      res   <- ref.get
      _     <- ref.set(res * 10)
      state <- ref.get
    } yield s"The meaning of life is $res, state: $state"
  }
}
//#endregion

import cats.effect.{IO, IOApp}

//#region ce-implementation
object MainIO extends IOApp.Simple {
  def run: IO[Unit] = for {
    ref    <- MutableRef.of[IO, Int](0)
    result <- Program[IO].program(ref)
    _      <- IO.println(result)
  } yield ()
}
//#endregion


//#region other-implementation
trait MyMonad[+A] 
given Sync[MyMonad] = ???
def safePrint(value: Any): MyMonad[Unit] = ???

object MyMonadMain {
  def run: MyMonad[Unit] = for {
    ref    <- MutableRef.of[MyMonad, Int](0)
    result <- Program[MyMonad].program(ref)
    _      <- safePrint(result)
  } yield ()
}
//#endregion

// TODO add ZIO based implementation