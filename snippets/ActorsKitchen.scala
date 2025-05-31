//> using scala 3
//> using dep "org.apache.pekko::pekko-actor-typed:1.1.3"

import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.actor.typed.{ActorRef, ActorSystem, Behavior}
import scala.concurrent.duration._

// ADT messages
object RestaurantMessages {

  // Messages the Client can receive
  sealed trait ClientCommand
  case class Serve(dish: String) extends ClientCommand

  // Messages the Waiter can receive
  sealed trait WaiterCommand
  case class Order(dish: String, client: ActorRef[ClientCommand]) extends WaiterCommand
  case class Ready(dish: String, client: ActorRef[ClientCommand]) extends WaiterCommand

  // Messages the Kitchen can receive
  sealed trait KitchenCommand
  case class Cook(
    dish: String,
    waiter: ActorRef[WaiterCommand],
    client: ActorRef[ClientCommand]
  ) extends KitchenCommand
}


// Actor implementations
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

object WaiterActor {
  import RestaurantMessages._

  def apply(kitchen: ActorRef[KitchenCommand]): Behavior[WaiterCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Order(dish, client) =>
        println(s"Waiter: Received order for $dish from client.")
        kitchen ! Cook(dish, context.self, client)
        Behaviors.same

      case Ready(dish, client) =>
        println(s"Waiter: Received $dish from kitchen. Serving client.")
        client ! Serve(dish)
        Behaviors.same
    }
  }
}

object KitchenActor {
  import RestaurantMessages._

  def apply(): Behavior[KitchenCommand] = Behaviors.receive { (context, message) =>
    message match {
      case Cook(dish, waiter, client) =>
        println(s"Kitchen: Cooking $dish for client $client ...")
        context.scheduleOnce(1.second, waiter, Ready(dish, client))
        Behaviors.same
    }
  }
}


// Main method to start everything
@main def singleCLientRestaurantSimulation(): Unit = {
  import RestaurantMessages._

  val system = ActorSystem[Nothing](Behaviors.setup[Nothing] { context =>
    val kitchen = context.spawn(KitchenActor(), "kitchen")
    val waiter = context.spawn(WaiterActor(kitchen), "waiter")
    val client = context.spawn(ClientActor(), "client")

    // Kick off the interaction
    waiter ! Order("Pasta", client)

    Behaviors.empty
  }, "RestaurantSystem")
}

// Main method with random client simulation
@main def randomClientsRestaurantSimulation(): Unit = {
  import RestaurantMessages._

  val system = ActorSystem[Nothing](Behaviors.setup[Nothing] { context =>
    val kitchen = context.spawn(KitchenActor(), "kitchen")
    val waiter = context.spawn(WaiterActor(kitchen), "waiter")

    val menu = List("Pasta", "Pizza", "Panacotta")
    val random = new scala.util.Random()

    def spawnRandomClient(): Unit = {
      val delayMs = random.between(500, 2000)
      Thread.sleep(delayMs)
      val dish = menu(random.nextInt(menu.size))
      val client = context.spawn(ClientActor(), s"client-${System.currentTimeMillis()}")
      context.log.info(s"New client arrived and orders $dish.")
      waiter ! Order(dish, client)
      spawnRandomClient() // Schedule the next client
    }

    spawnRandomClient() // Start the first client

    Behaviors.empty
  }, "RestaurantSystem")
}
