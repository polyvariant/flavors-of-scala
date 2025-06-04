# Bright Future

## Now that we have immutable data types and pure functions, concurrency should be much easier right?

---

# Bright Future

<<< ../snippets/Futures.scala#examples scala {1|3-6|8-10|12-15|*}{lines:true}

<!-- 

Let's not mention the problem of referential transparency yet, we'll come back to it in CE/ZIO section

At this point it's enough to say that immutability, functions as first class citizens and concurrency primitives 
played very well together and it was tempting to use them for large scare concurrent systems

This snippet could be better, treat it as a placeholder


# How Scala helped

- 2004-2012 Java++, how Scala helped there
- Immutability (built-in collections)
- Case classes
- Pure functions
- `Option` (handling nulls) (vs Optional Java 1.7)
- `Future` (it’s a “lots of hand waving” Monad)

może powinniśmy zamienić ten slajd na jakiś wrapup + timeline
-->


<!-- ---  -->

<!-- # Code example: testing Futures -->

<!-- TODO: testing using type parameters, ID monad from Scalaz/early Cats, perhaps difficulty of handling state? -->

<!-- 

Może ten przykład z testowaniem pokazać jako uzasadnienie pomysłu z CE/ZIO zamiast przed samą akką?

-->

<!--
Transition: FUTURE -> concurrency -> Akka Actors
We have immutability, pure functions, concurrency should be simple now, right? Right? (pic)
Problems designing and programming
Hanging Futures
One-off jobs 
What about the state?
-->

<!--
We are using the std lib Future. Type that represents a value that may be available in the future (like fetching from a DB).

flatMapping ("computation chaining") allows us to work on a value that is not yet available which was a revolutionary idea in mainstream languagues back then. 

every modern language has a type like this but it wasn't the case in 2010.
-->

---

# Futures are nice, but I need to manage state!

What if I need to concurrently modify the state?

<v-clicks>

<<< ../snippets/FutureStateProblem.scala#examples scala {1|9|3-7|*}{lines:true}

</v-clicks>

<!--
TODO: can we simplify?

ask the audience what is the final counter result here

in Scala (since it's a JVM language) we also have a posibility to use the synchronized keyword to guard access to critical section (monitor-based synchronization).
-->

---

# How do we fix that?


<v-clicks>

There's one famous solution out there

</v-clicks>

---
background: /actors_collage.jpg
layout: center
---

# Actors!

## (Scala loves Erlang)

--- 
layout: center
---

# ~~Who's~~ What's an Actor anyway?

---

# Inbox and state management

<img class="absolute top-30 right-10 w-170" alt="" src="/actors.svg" />

<v-clicks>

* Actor has a message box and internal state
* It's logic is triggered by sending message
* Actor can occupy **one** or **zero** threads
* `Actor system` provides the runtime
* `Actor system` can be a cluster

</v-clicks>

---

# They can cooperate!

<!-- images from https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/How-the-Actor-Model-works-by-example -->

<img class="absolute top-20 right-30 w-170" alt="" src="https://itknowledgeexchange.techtarget.com/coffee-talk/files/2023/12/asynchronous_communication_modes_in_the_actor_model-f.png" />


<!-- https://itknowledgeexchange.techtarget.com/coffee-talk/files/2023/12/understanding_the_actor_model-f.png more advanced example -->


---

# Actors in Akka/Pekko

<!-- Ten przykład chyba musimy uprościć do "klient" -> "pizzeria" -->

<div class="absolute top-25 w-200">

```mermaid

sequenceDiagram
    participant ClientActor as Client
    participant WaiterActor as Waiter
    participant KitchenActor as Kitchen

    ClientActor->>WaiterActor: Order("Pasta")
    WaiterActor->>KitchenActor: Cook("Pasta")
    Note right of KitchenActor: Simulate cooking time
    KitchenActor-->>WaiterActor: Ready("Pasta")
    WaiterActor-->>ClientActor: Serve("Pasta")

```

</div>

---

# Actors in the Kitchen

<div class="absolute top-25 w-200">

<<< ../snippets/ActorsKitchen.scala scala {*}{maxHeight:'420px',lines:true}

</div>

<!--
IDE experience
-->

---

# Problems with Akka-based design

<small>(that Scala devs encountered)</small>

<v-clicks>

- Navigation, discoverability
- One big actor or many small ones
- Lifecycle
- More generally: testing…

</v-clicks>

<!--
- coming back from IDE and summarizing

we'll come back to testing problems with actor systems later on
-->

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

--- 
background: /world-if.webp
layout: center
---

# World if `Future` was a monad

(Scala loves Haskell)
<!-- # What was so bad about the `Future` anyway? -->

<!-- <img class="absolute top-30 right-10 w-170" alt="" src="/world-if.webp" /> -->


<!--
# Side note: Twitter Stack / Linkerd ???
- Twitter Future early adoption
- Problem: built-in Scala Future was more popular
- Long-standing problem in Scala: FP vs Java++
  - At least two groups of programmers pulling Scala in their own preferred direction 
    - Future vars 
    - Later: pure FP vs casual FP (https://softwaremill.com/what-is-functional-programming/)
-->


---

# IO - the ultimate separation of concerns

Think `Future` but lazy

<<< ../snippets/FutureVsIO.scala#example scala {1-5|7-11|13-14|*}{lines:true}

---

# How is it better than MTL?

<<< ../snippets/IORefExample.scala#example scala {5-11|1-3|2|13-17|*}{lines:true}

<v-click>

<div class="absolute bottom-15 w-200">

### Everything is wrapped with `IO`

</div>

</v-click>

<!--
no lifting! just a composition of values and a single type

Speaker B: but one problem that we had with MTL is still there: one monad for the whole application (everyone needs to adhere to it, even external libs!) 

(it can be tackled with some discipline but it rarely happens in big dynamically developed projects)
-->

---

# Monadic Effects

We have a bunch of them!

- Cats / Monix
- Scalaz
- ZIO
- Cats Effect IO


---

# But I like my other IO monad better!

So `cats.effect.IO` is just one implementation, we should abstract it!

<!--
For example: there were experiments with an IO monad that has an explicit error type (IO used here has a fixed JVM Throwable error type which wasn't good enough for some programmers).

Everyone was writing their own IO monad.
-->

---
layout: center
background: /galaxy-brain.jpg
---

# Final tagless - the final abstraction


---

# Final tagless

<div class="absolute top-22 w-200">

<<< ../snippets/TaglessRefExample.scala#tagless scala {1-4,6-9|11-23|*}{maxHeight:'420px',lines:true}

</div>


---

# Final tagless

<<< ../snippets/TaglessRefExample.scala#ce-implementation scala {*}{lines:true}

<v-click>

<<< ../snippets/TaglessRefExample.scala#other-implementation scala {1|2|6-10|*}{lines:true}

</v-click>

---

# What the `F[_]`

You telling me I now need `F[_]: Async: Monad: Clock: UUIDGen` to run hello world?

<div v-click="1">
  <img class="absolute top-50 left-100 w-100" alt="" src="./godfather.png" />
</div>

---

# Is Tagless Final the dead end?

<v-clicks>

- Not necessarily!
- It works great for library design
- See Noel's talk from this year's Scalar ([link](https://www.youtube.com/watch?v=nyMwp7--rY4))

</v-clicks>

---

# Can we do better than Monadic effects?

<v-clicks>

- Some argue a single beefy monad is easier to reason about
- The problem with any `F[_]` is that it mixes definition, sequencing and handling in one type.
- We're not done yet!

</v-clicks>

---

# Algebraic effects

Abstract operations with handlers!

---

# Meet Kyo

<div class="absolute top-25 w-200">

<<< ../snippets/KyoBasicExample.scala#example-app scala {5-12|4|14|16|*}{lines:true}

</div>

<v-click>

<div class="absolute bottom-10 w-200">

No `IO[A]` in sight!

</div>

</v-click>



---

# Kyo Ref example

<<< ../snippets/KyoRefExample.scala scala {*}{lines:true}

<!---


TODO: chyba warto tu pokazać kawałek Kyo które robi algebraic effects, podlinkować talk wprowadzający https://www.youtube.com/live/gYS3UkmFoHQ?t=719&cbrd=1

-->
