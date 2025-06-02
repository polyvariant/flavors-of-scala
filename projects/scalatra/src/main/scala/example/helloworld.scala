package example

// #region example
import org.scalatra._

class HelloWorld extends ScalatraServlet {
  get("/") {
    "Hello world"
  }
}
// #endregion
