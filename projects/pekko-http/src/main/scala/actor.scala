import org.apache.pekko.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import language.postfixOps
import scala.concurrent.duration._

case class CheckCollaboration(artist1: String, artist2: String)

sealed trait CollaborationResponse
case class CollaborationResult(summary: String) extends CollaborationResponse
case class CollaborationProblem(exception: Throwable)
    extends CollaborationResponse

class GuildActor extends Actor {
  def receive = { case check: CheckCollaboration =>
    println(s"${self.path} received $check")
    (for {
      artist1 <- findArtist(check.artist1)
      artist2 <- findArtist(check.artist2)
    } yield checkCollaboration(artist1, artist2)) match {
      case Left(notFound) => sender() ! CollaborationProblem(notFound)
      case Right(summary) => sender() ! CollaborationResult(summary)
    }
  }
}
