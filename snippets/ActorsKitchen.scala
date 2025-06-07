//> using scala 3
//> using dep "org.apache.pekko::pekko-actor-typed:1.1.3"

import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.{ActorRef, ActorSystem, Behavior}
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.Random

//#region messages
object RestaurantMessages {
  // Messages the Client can receive
  sealed trait ClientCommand
  case class Serve(dish: String) extends ClientCommand

  // Messages the Restaurant can receive
  sealed trait RestaurantCommand
  case class Order(dish: String, client: ActorRef[ClientCommand]) extends RestaurantCommand
  case class Cook(dish: String, client: ActorRef[ClientCommand]) extends RestaurantCommand
}
//#endregion

//#region actors
object RestaurantActor {
  import RestaurantMessages._

  def apply(): Behavior[RestaurantCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Order(dish, client) =>
        println(s"Restaurant: Received order for $dish.")
        context.self ! Cook(dish, client)
        Behaviors.same

      case Cook(dish, client) =>
        println(s"Restaurant: Cooking $dish for client $client ...")
        context.scheduleOnce(1.second, client, Serve(dish)) // simulate cooking
        Behaviors.same
    }
  }
}

object ClientActor {
  import RestaurantMessages._

  def apply(): Behavior[ClientCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Serve(dish) =>
        println(s"Client: Received $dish. Yum!")
        Behaviors.stopped
    }
  }
}
//#endregion

// Main method to run a single client simulation
@main def singleClientRestaurant(): Unit = {
  import RestaurantMessages._

  val system = ActorSystem[Nothing](Behaviors.setup[Nothing] { context =>
    val restaurant = context.spawn(RestaurantActor(), "restaurant")
    val client = context.spawn(ClientActor(), "client")

    restaurant ! Order("Pasta", client)

    Behaviors.empty
  }, "SimpleRestaurantSystem")
}

// Main method to run random client simulation
@main def randomClientsRestaurant(): Unit = {
  import RestaurantMessages._

  val system = ActorSystem[Nothing](Behaviors.setup[Nothing] { context =>
    val restaurant = context.spawn(RestaurantActor(), "restaurant")
    val menu = List("Pasta", "Pizza", "Panacotta")
    val random = new scala.util.Random()

    def spawnRandomClient(): Unit = {
      val delayMs = random.between(500, 2000)
      Thread.sleep(delayMs)
      val dish = menu(random.nextInt(menu.size))
      val client = context.spawn(ClientActor(), s"client-${System.currentTimeMillis()}")
      context.log.info(s"New client arrived and orders $dish.")
      restaurant ! Order(dish, client)
      spawnRandomClient() // Keep them coming
    }

    spawnRandomClient()
    Behaviors.empty
  }, "RandomClientsRestaurantSystem")
}
