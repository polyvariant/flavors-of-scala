
# Meanwhile...

### We figured there are Monads beyond `Future` and `IO`

* stdlib brings some like `List`, `Option`, `Either`
* Cats bring us some more `Validated`, `NonEmptyList`, `Kleisli`, `State`



<!--
Let's give them a quick look, starting with State Monad...
-->

---

# Either

Let's handle errors the right way!

````md magic-move
```scala
def findArtist(name: String)(implicit ec: ExecutionContext): Future[Artist] = 
  Future {
    val artists: List[Artist] = {
      val jsonText = Source.fromResource("artists.json").mkString
      jsonParse[List[Artist]](jsonText)
    }
    artists.find(_.name == name).head
  }
```
```scala
def findArtistIO(name: String): IO[Artist] = 
  IO {
    val artists: List[Artist] = {
      val jsonText = Source.fromResource("artists.json").mkString
      jsonParse[List[Artist]](jsonText)
    }
    artists.find(_.name == name).head
  }
```
```scala
def findArtistIOEither(name: String): IO[Either[ArtistNotFound, Artist]] =
  IO {
    val artists: List[Artist] = {
      val jsonText = Source.fromResource("artists.json").mkString
      jsonParse[List[Artist]](jsonText)
    }
    artists.find(_.name == name).toRight(ArtistNotFound(name))
  }
```
````

<v-click>
Not the most convenient

```scala
val result = for {
  maybeArtist1 <- findArtistIOEither(artistIn1)
  maybeArtist2 <- findArtistIOEither(artistIn2)
} yield for {
  artist1 <- maybeArtist1
  artist2 <- maybeArtist2
} yield checkCollaboration(artist1, artist2)
```


</v-click>


---

# EitherT


````md magic-move
```scala
def findArtistIOEither(name: String): IO[Either[ArtistNotFound, Artist]] =
  IO {
    val artists: List[Artist] = {
      val jsonText = Source.fromResource("artists.json").mkString
      jsonParse[List[Artist]](jsonText)
    }
    artists.find(_.name == name).toRight(ArtistNotFound(name))
  }
```
```scala
def findArtistMTL(name: String): EitherT[IO, ArtistNotFound, Artist] =
  EitherT { 
    IO {
      val artists: List[Artist] = {
        val jsonText = Source.fromResource("artists.json").mkString
        jsonParse[List[Artist]](jsonText)
      }
      artists.find(_.name == name).toRight(ArtistNotFound(name))
    }
  }
```
````

<v-click>

Convenient client side API again!

```scala
val result = for {
  artist1 <- findArtistMTL(a1)
  artist2 <- findArtistMTL(a2)
} yield checkCollaboration(artist1, artist2)
```
</v-click>


---

# Monad transformers are leaky

<div class="absolute top-25 w-200">

But there's a lot of lifting 🏋️

```scala {1}{lines:true}
def findArtistMTL(name: String): EitherT[IO, ArtistNotFound, Artist] = EitherT { IO {
    val artists: List[Artist] = {
      val jsonText = Source.fromResource("artists.json").mkString
      jsonParse[List[Artist]](jsonText)
    }
    artists.find(_.name == name).toRight(ArtistNotFound(name))
  }
}
```

<v-click>

It can go as ridiculous as

```scala
type AppStack[A] = EitherT[StateT[IO, AppState, *], AppError, A]
```

</v-click>

</div>

<v-click at="1">

<img class="absolute bottom-10 right-10 w-90 bg-white rotate-2" alt="" src="./out-of-hand.jpg" />

</v-click>

<!--
It needs to be everywhere!

problems:
- one monad per app (giga effect)
- so everything needs to be lifted everywhere

MTL = Monad Transformers Library tried to overcome some of these issues by providing a lot of helper tools, in Scala as well.
-->
