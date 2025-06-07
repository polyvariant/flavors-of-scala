---

# Meanwhile...

### The community is flirting with various approaches to state management

<v-clicks>

- Actors
- State Monad
- Monad transformers
- Offloading state management entirely to the database ("stateless")

</v-clicks>


<!--
Let's give them a quick look, starting with State Monad...
-->

---

# State Monad

Put simply, it's a way to chain mutations of a value.


---

# State Monad

Put simply, it's a way to chain mutations of a value.

<<< ../snippets/StateMonad.scala#stateMonadClass scala {1-3|5-9|*}{lines:true}

<!--
Here's how state monad is defined.

?? WHAT'S S and A here?

Two type parameters, S and A, S is the type of state, A is the result of the computation...
-->

---

# State Monad

You can manipulate the state using helpers

<<< ../snippets/StateMonad.scala#stateMonadObject scala {*}{lines:true}

---

# State Monad

<<< ../snippets/StateMonad.scala#stateMonadMain scala {*}{lines:true}

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

<<< ../snippets/StateTExample.scala#example scala {1|10-16|3-9|*}{lines:true}

</div>

---

# Monad transformers are leaky

<div class="absolute top-25 w-200">

There's a lot of lifting 🏋️

```scala {6}{lines:true}
def doLiftedAsyncComputation() = {
  val work = Future {
    Thread.sleep(10) // simulate doing the work
    21
  }
  StateT.liftF[Future, Int, Int](work)
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

## Was simple `Future` that bad? 🤔

</div>

</v-click>

<!--
It needs to be everywhere!

problems:
- one monad per app (giga effect)
- so everything needs to be lifted everywhere

MTL = Monad Transformers Library tried to overcome some of these issues by providing a lot of helper tools, in Scala as well.
-->
