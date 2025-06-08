
# Meanwhile...

### We figured there are Monads beyond `Future` and `IO`

* stdlib brings some like `List`, `Option`, `Either`
* Cats bring us some more `Validated`, `NonEmptyList`, `Kleisli`, `State`



<!--
Let's give them a quick look, starting with State Monad...
-->


---

# State Monad

<div class="absolute top-20 w-200">

Put simply, it's a way to chain mutations of a value.


<v-click>

<<< ../snippets/StateMonadCats.scala#example scala {1-7|8-13|14-15|16-19|*}{lines:true}

</v-click>

</div>

<!--
Here's how state monad is defined.

?? WHAT'S S and A here?

Two type parameters, S and A, S is the type of state, A is the result of the computation...
-->

<!--
Note that this is sequential, while Futgu
-->

---

# State Monad

Looks fun but we async and parallelism are not included

<!--
Futures were async!
-->

---

# Monad transformers

<div class="absolute top-25 w-200">

<<< ../snippets/StateTCatsEffect.scala#example scala {2-9|3|10-23|*}{lines:true}

</div>

---

# Monad transformers are leaky

<div class="absolute top-25 w-200">

There's a lot of lifting 🏋️

```scala {1}{lines:true}
val nextStation: StateT[IO, List[String], String] = StateT { stationsList =>
  IO.sleep(1.second) *> IO.pure {
    stationsList match {
      case Nil          => (Nil, "No more stations")
      case head :: tail => (tail, s"Current station: $head")
    }
  }
}
```

<v-click>

It can go as ridiculous as

```scala
type AppStack[A] = EitherT[StateT[Future, AppState, *], AppError, A]
```

</v-click>

</div>

<v-click>

<div class="absolute bottom-20 w-200">

## Perhaps we should stick to `Ref`? 🤔

</div>

</v-click>

<!--
It needs to be everywhere!

problems:
- one monad per app (giga effect)
- so everything needs to be lifted everywhere

MTL = Monad Transformers Library tried to overcome some of these issues by providing a lot of helper tools, in Scala as well.
-->
