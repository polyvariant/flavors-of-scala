package example

import sttp.tapir._
import sttp.tapir.server.netty.cats.NettyCatsServer
import cats.effect.{IO, IOApp}

object Server extends IOApp.Simple {

  override def run: IO[Unit] = {
    NettyCatsServer
      .io()
      .use { server =>
        server
          .host("localhost")
          .port(8080)
          .addEndpoint(collaborationEndpoint)
          .start()
          .flatTap(_ => IO.println("Server now online. Press RETURN to stop...").flatTap(_ => IO.readLine))
          .void
      }
  }
}
