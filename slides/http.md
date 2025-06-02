---
layout: full
---

# Can I even use all of this in practice?

All these techniques had their impact on how Scala devs developed software. Let's go very quickly through some examples and see how the code looked like and what developers focused on.

---
layout: full
---

# HTTP

For every startup, the most important decision is what web framework to use (and we all know that if a startup fails it's 100% fault of the wrong web framework).

---
layout: full
---

# HTTP

We will not go through every possible web framework for Scala, just couple of them that had the most impact. And since we already talked about actors, the best way to start with HTTP is to mention a singer.

---
background: /sinatra.jpg
layout: center
---

# Sinatra

## or rather Scalatra

---
layout: full
---

# Scalatra

Scalatra is a simple, accessible and free web micro-framework.

It combines the power of the JVM with the beauty and brevity of Scala, helping you quickly build high-performance web sites and APIs.

Ported from Ruby.

<!--
Let's see how it works in action.
-->

---
layout: full
---

# Example: Artist collaborations

Implement an API which returns an information whether two given artists could have collaborated with each other.

E.g.

```
> curl http://localhost:8080/collaboration?artist1=Frank Sinatra&artist2=Aretha Franklin
Frank Sinatra and Aretha Franklin could have collaborated between 1954 and 1995%
```

---
layout: full
---

# The idea: create a web app as quickly as possible

```ruby
require 'sinatra'

get '/' do
  'Hello world!'
end
```

<!--
This is how a hello world looks in ruby. There's nothing more, you can just run this and it works.
-->

---
layout: full
---

# The idea: create a web app as quickly as possible

<<< ../projects/scalatra/src/main/scala/example/helloworld.scala#example scala {*}

<!--
And this is how it's ported to Scala.
-->

---
layout: full
---

# The artist collaboration implementation

````md magic-move
```scala
case class Artist(name: String, startYear: Int, endYear: Int)
```
```scala
case class Artist(name: String, startYear: Int, endYear: Int)

class CollaborationServlet extends ScalatraServlet {
  get("/collaboration") {
    ""
  }
}
```
```scala
case class Artist(name: String, startYear: Int, endYear: Int)

class CollaborationServlet extends ScalatraServlet {
  get("/collaboration") {
    val artist1 = findArtist(params.getOrElse("artist1", halt(400, "Missing parameter: artist1")))
    val artist2 = findArtist(params.getOrElse("artist2", halt(400, "Missing parameter: artist2")))
    ""
  }

  // this simulates a side-effectful call: example of an API or a DB call
  def findArtist(name: String): Artist = {
    val artists: List[Artist] = {
      val json =
        Source
          .fromResource("artists.json")
          .mkString
      parse(json).extract[List[Artist]]
    }

    artists
      .find(_.name == name)
      .getOrElse(halt(404, s"Artist $name not found"))
  }
}
```
```scala
case class Artist(name: String, startYear: Int, endYear: Int)

class CollaborationServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats =
    DefaultFormats.withBigDecimal

  get("/collaboration") {
    val artist1 = findArtist(params.getOrElse("artist1", halt(400, "Missing parameter: artist1")))
    val artist2 = findArtist(params.getOrElse("artist2", halt(400, "Missing parameter: artist2")))

    checkCollaboration(artist1, artist2)
  }

  // this simulates a side-effectful call: example of an API or a DB call
  def findArtist(name: String): Artist = { ... }
}
```
````

<!--
-->

---
layout: full
---

# Scalatra's philosophy (in theory)

#### Start small, build upwards
Start with a small core, and have lots of easy integrations for common tasks.

#### Freedom
Allow the user freedom to choose whatever structure and libraries make the most sense for the application being built.

#### Solid, but not stolid
Use solid base components. For instance, servlets aren’t cool, but they are extremely stable and have a huge community behind them. At the same time, work to advance the state of web application development by using new techniques and approaches.

#### Love HTTP
Embrace HTTP and its stateless nature. People get into trouble when they fool themselves into thinking things which aren’t true - fancy server-side tricks to give an illusion of statefulness aren’t for us.

---
layout: full
---

# It's pragmatic but...

Pragmatism isn't always the best option! This simple, quick approach comes with a heavy baggage.

<--
servlets... really?
-->

---
layout: full
---

# Every integration (JSON, DB, ...) is coupled with Scalatra

```scala {*|3}
trait Coffee extends ScalatraBase with FutureSupport {

  def db: Database

  get("/coffees") {
    // run the action and map the result to something more readable
    db.run(Tables.findCoffeesWithSuppliers.result) map { xs =>
      contentType = "text/plain"
      xs map { case (s1, s2) => f"  $s1 supplied by $s2" } mkString "\n"
    }
  }
}
```

<!--
Additionally, when we needed JSON, we needed to extend our servlet with the `JsonSupport` trait
-->

---
layout: full
---

# You don't know what's side-effectful

```scala
class CollaborationServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats =
    DefaultFormats.withBigDecimal

  get("/collaboration") {
    val artist1 = findArtist(params.getOrElse("artist1", halt(400, "Missing parameter: artist1")))
    val artist2 = findArtist(params.getOrElse("artist2", halt(400, "Missing parameter: artist2")))

    checkCollaboration(artist1, artist2)
  }

  def findArtist(name: String): Artist = {
    val artists: List[Artist] = {
      val json =
        Source
          .fromResource("artists.json")
          .mkString
      parse(json).extract[List[Artist]]
    }
    artists
      .find(_.name == name)
      .getOrElse(halt(404, s"Artist $name not found"))
  }
}
```

<!--
Additionally, when we needed JSON, we needed to extend our servlet with the `JsonSupport` trait
-->

---
layout: full
---

# And this all affects testability

```scala
class ScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[CollaborationServlet], "/*")

  test("GET /collaboration") {
    get("/collaboration", params = Map("artist1" -> "ArtistA", "artist2" -> "ArtistB")) {
      status should equal(200)
      body should equal(
        "ArtistA and ArtistB could have collaborated between 1985 and 1992"
      )
    }
  }
}
```

<!--
You need to have a server running to test most of your stuff. It takes a lot of discipline to be able to decouple your logic into small pure functions...
Also decoupling the web parts, the stateful/DB parts, the JSON parsing parts, are all hard because of that.
-->

---
layout: full
---

# Convenience vs safety vs pragmatism

// jakiś obrazek typu przeciąganie liny


---
layout: full
---

# Spray -> Akka HTTP

// TODO

---
layout: full
---

# We did concurrency and HTTP

But a similar story can be told about:
- databases
- JSON libraries
- testing frameworks
- frontend (Scala.js)
- dependency management
- HTTP clients
- API integrations
- ...

<!--
testing: akka-testkit test problems, testing concurrent stuff is hard

- HTTP
- Scala loves Python (Cask)
- Scala loves Ruby on Rails (Scalatra) - microframework
- Scala loves actors (Spray/Akka HTTP)
- Scala loves Spring (Play) Future and sometimes IO
- Scala loves FP (http4s) IO
- Scala loves types (tapir) final tagless done right (it's good for libraries, and it's good when it doesn't leak)
-->
