
# Bright Future

Now that we have immutable data types and pure functions, concurrency should be much easier right?

---

# Bright Future

<<< ../snippets/Futures.scala#examples scala

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
# Side-note: Spark ???
- Google map-reduce (referencing streams)
- Pure functions
- Hard to test
- But… Scala got popular because of it
- FP programmers took advantage of Spark to promote itself


# Akka Actors (2012)
- Shared mutable state
- Everyone dreams big 
- Scala got even more popular because of it
- FP programmers took advantage of Spark to promote itself
- Code examples: free monad-based Akka actors  -->

---

# Futures are nice, but I need to manage state!

What if I need to concurrently modify the state?

<v-clicks>

<<< ../snippets/FutureStateProblem.scala#examples scala {1|9|3-7|*}{lines:true}

</v-clicks>

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

(Scala loves Erlang)

--- 
layout: center
---

# ~~Who's~~ What's an Actor anyway?

---

# Inbox and state management

<img class="absolute top-30 right-10 w-170" alt="" src="/actors.svg" />

<v-clicks>

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


# Actors in Akka

TODO: example implementation, show the DSL so that we can refer to code navigation issues (no callstack)


---


# Problems with Akka-based design

<small>(that Scala devs encountered)</small>

<v-clicks>

- Navigation, discoverability
- One big actor or many small ones
- Lifetime
- More generally: testing…

</v-clicks>

<!---
Transition: Problems with Akka-based design
Problems:
- Navigation, discoverability
- One big actor or many small ones
- Lifetime
- More generally: testing…
-->

---

# Meanwhile...

The community romansuje with different approach to state management: actors, State Monad (transformers too) or just leaving it to databases ("stateless")

---

# Perhaps Future wasn't that bad?


---

# What would we like to have instead?

- IO!

---

# Side note: Twitter Stack / Linkerd ???
- Twitter Future early adoption
- Problem: built-in Scala Future was more popular
- Long-standing problem in Scala: FP vs Java++
  - At least two groups of programmers pulling Scala in their own preferred direction 
    - Future vars 
    - Later: pure FP vs casual FP (https://softwaremill.com/what-is-functional-programming/)

--- 

# Functional techniques

- Scala loves Haskell
- IO Monad
- Scalaz
- ZIO
- Cats / Monix
- Cats IO


<div>
But we still have: Future, Twitter Future, some Actors
</div>

---

# Final tagless

Type all the things / hardcore FP
Code example: ???

TODO: Add Kyo slide

<!---


TODO: chyba warto tu pokazać kawałek Kyo które robi algebraic effects, podlinkować talk wprowadzający https://www.youtube.com/live/gYS3UkmFoHQ?t=719&cbrd=1

-->
