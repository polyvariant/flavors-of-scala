//> using scala 3
//> using dep "org.apache.pekko::pekko-actor-typed:1.1.3"

import org.apache.pekko.actor.typed.*
import org.apache.pekko.actor.typed.scaladsl.*

//#region CounterActor
object CounterActor {
  enum Message {
    case Increment
    case Reset
  }

  private def behavior(count: Int): Behavior[Message] = 
    Behaviors.receive { (ctx, msg) =>
      msg match {
        case Message.Increment => behavior(count + 1)
        case Message.Reset     => behavior(0)
      }
    }

  def makeActor(): Behavior[Message] = behavior(0)
}
//#endregion
