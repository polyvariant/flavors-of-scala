package example

import org.scalatra.test.scalatest._

class ScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[CollaborationServlet], "/*")

  test("GET /collaboration") {
    get(
      "/collaboration",
      params = Map("artist1" -> "ArtistA", "artist2" -> "ArtistB")
    ) {
      status should equal(200)
      body should equal(
        "ArtistA and ArtistB could have collaborated between 1985 and 1992"
      )
    }
  }

}
