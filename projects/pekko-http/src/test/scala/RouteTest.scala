import org.apache.pekko.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RouteTest extends AnyWordSpec with Matchers with ScalatestRouteTest {
  "HTTP routes" should {
    "respond to the /collaboration request with valid artists" in {
      Get(
        "/collaboration?artist1=Frank%20Sinatra&artist2=Aretha%20Franklin"
      ) ~> route ~> check {
        status.intValue() shouldEqual 200
        responseAs[String] shouldEqual "Frank Sinatra and Aretha Franklin could have collaborated between 1954 and 1995"
      }
    }

    "return 404 for non-existent artists" in {
      Get(
        "/collaboration?artist1=NonExistent&artist2=ArtistB"
      ) ~> route ~> check {
        status.intValue() shouldEqual 404
      }
    }
  }
}
