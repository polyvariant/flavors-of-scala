//> using scala 3
//> using dep "org.apache.pekko::pekko-actor-typed:1.1.3"

import org.apache.pekko.actor.typed.*
import org.apache.pekko.actor.typed.scaladsl.*

//#region CounterActor
object CounterActor {
  sealed trait Message
  case object Increment extends Message
  case object Reset extends Message

  private def behavior(count: Int): Behavior[Message] = 
    Behaviors.receive { (ctx, msg) =>
      msg match {
        case Increment => behavior(count + 1)
        case Reset     => behavior(0)
      }
    }

  def makeActor(): Behavior[Message] = behavior(0)
}
//#endregion
