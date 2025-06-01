//#region stateMonadClass
// A - computation result
// S - tracked state
final case class State[S, A](run: S => (A, S)) {

  def map[B](f: A => B): State[S, B] =
    State { s =>
      val (a, newState) = run(s)
      (f(a), newState)
    }

  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State { s =>
      val (a, intermediateState) = run(s)
      f(a).run(intermediateState)
    }

}
//#endregion

//#region stateMonadObject
object State {
  
  def get[S]: State[S, S] =
    State(s => (s, s))

  def set[S](s: S): State[S, Unit] =
    State(_ => ((), s))

}
//#endregion

@main
def stateMonadMain = {
  //#region stateMonadMain
  val increment: State[Int, Unit] =
    for {
      current <- State.get[Int]
      _       <- State.set(current + 1)
    } yield ()

  val program: State[Int, Int] =
    for {
      _      <- increment
      _      <- increment
      result <- State.get[Int]
    } yield result*2

  val (result, finalState) = program.run(0)
  println(s"result $result")
  println(s"finalState $finalState")
  //#endregion
}