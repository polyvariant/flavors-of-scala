//> using dep org.typelevel::cats-mtl::1.5.0
import cats.data.StateT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.Monad

//#region example
type FutureWithInt[A] = StateT[Future, Int, A]

def doLiftedAsyncComputation() = {
  val work = Future {
    Thread.sleep(10) // simulate doing the work
    21
  }
  StateT.liftF[Future, Int, Int](work)
}
def program: FutureWithInt[String] =
  for {
    value <- doLiftedAsyncComputation()
    _     <- StateT.set(value * 2)
    res   <- StateT.get
    _     <- StateT.set(res * 10)
  } yield s"The meaning of life is $res"

@main def run(): Unit = {
  val result = program.run(0)
  result.foreach { case (state, result) =>
    println(s"Result: $result\nState: $state")
  }
  Thread.sleep(100)
}
//#endregion