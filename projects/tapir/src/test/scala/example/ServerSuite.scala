package example

import weaver._
import sttp.client4._
import sttp.client4.testing._
import sttp.tapir.integ.cats.effect.CatsMonadError
import sttp.model.StatusCode
import sttp.tapir.server.stub4.TapirStubInterpreter
import cats.effect.IO

// #region test
object ServerSuite extends SimpleIOSuite:
  val backend: Backend[IO] = TapirStubInterpreter(BackendStub(CatsMonadError[IO]))
    .whenServerEndpoint(collaborationServerEndpoint)
    .thenRunLogic()
    .backend()

  test("The /collaboration endpoint should return OK for valid artists") {
    basicRequest
      .get(uri"http://example.org/collaboration?artist1=Frank%20Sinatra&artist2=Aretha%20Franklin")
      .send(backend)
      .map { response =>
        expect.all(
          response.code == StatusCode.Ok,
          response.body == Right("Frank Sinatra and Aretha Franklin could have collaborated between 1954 and 1995")
        )
      }
  }
// #endregion

  test("The /collaboration endpoint should return NotFound for non-existent artists") {
    basicRequest
      .get(uri"http://example.org/collaboration?artist1=NonExistent&artist2=ArtistB")
      .send(backend)
      .map { response =>
        println(response)
        expect(response.code == StatusCode.BadRequest)
      }
  }
