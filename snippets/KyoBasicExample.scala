//> using dep io.getkyo::kyo-core:0.19.0
//> using scala 3

//#region example-app
import kyo.*

object MyKyoApp extends KyoApp {
  val program: String < (IO & Check) = 
    for {
      _            <- Console.printLine(s"Main args: $args")
      currentTime  <- Clock.now
      _            <- Console.printLine(s"Current time is: $currentTime")
      randomNumber <- Random.nextInt(100)
      _            <- Check.require(randomNumber < 100, "Random was naughty")
      _            <- Console.printLine(s"Generated random number: $randomNumber")
    } yield "example"
  
  val programIgnoringErrors: String < IO = Check.runDiscard(program)

  run(programIgnoringErrors)
}
//#endregion
