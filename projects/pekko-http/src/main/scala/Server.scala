import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.stream.ActorMaterializer
import org.apache.pekko.stream.Materializer

object Server extends App {
  implicit val system: ActorSystem = ActorSystem("pekko-http-system")
  implicit val materializer: Materializer = ActorMaterializer()

  Http().newServerAt("localhost", 8080).bind(route)

  println("Server online at http://localhost:8080/\nPress RETURN to stop...")
  scala.io.StdIn.readLine() // Keep the server running until user presses RETURN
  system.terminate()
}
