package example

import org.scalatra.test.scalatest._

class ScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[CollaborationServlet], "/*")

  test("GET /collaboration") {
    get(
      "/collaboration",
      params = Map("artist1" -> "Frank Sinatra", "artist2" -> "Aretha Franklin")
    ) {
      status should equal(200)
      body should equal(
        "Frank Sinatra and Aretha Franklin could have collaborated between 1954 and 1995"
      )
    }
  }

}
