import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.stream.ActorMaterializer
import org.apache.pekko.stream.Materializer
import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Server extends App {
  implicit val system: ActorSystem = ActorSystem("pekko-http-system")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

  println("Server now online. Press RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
