//> using dep org.typelevel::cats-core::2.13.0
import cats.data.State

@main
def stateMain() = {
  //#region example
  // State instance to get the next station and update the state to the tail of the list
  val nextStation: State[List[String], String] = State { stationsList =>
    stationsList match {
      case Nil          => (Nil, "No more stations")
      case head :: tail => (tail, s"Current station: $head")
    }
  }
  // Initial state is not known yet
  val result = for {
    _            <- nextStation
    _            <- nextStation
    thirdStation <- nextStation
  } yield thirdStation
  // List of stations from Wrocław to Prague
  val stations: List[String] = List("Wrocław", "Kłodzko", "Międzylesie", "Lichkov", "Pardubice", "Praha")
  // Run the state manipulation
  val (remainingStations, currentStation) = result.run(stations).value
  println(s"$currentStation")
  println(s"Remaining stations: ${remainingStations}")
  //#endregion
}
