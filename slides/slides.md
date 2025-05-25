---
theme: the-unnamed
title: Flavors of Scala
info: |
  ## Flavors of Scala

  [Michał Pawlik](https://michalplachta.com)
  [Michał Płachta](https://michalplachta.com)
drawings:
  persist: true
transition: none
mdc: true
background: /spices.jpg
---


# Flavors of Scala


---

<!-- Just a draft, doesn't need to be here, can be moved, split into parts -->

# Timeline 


<!-- alternative theme approach ```mermaid { 'theme': 'base', 'themeVariables': { 'primaryColor': '#FFFFFF', 'lineColor': '#FFFFFF', 'cScale0':  '#000033', 'cScaleLabel0':  '#ffffff', 'cScale1':  '#000044', 'cScaleLabel1':  '#ffffff', 'cScale2':  '#000055', 'cScaleLabel2':  '#ffffff', 'cScale3':  '#000066', 'cScaleLabel3':  '#ffffff', 'cScale4':  '#000077', 'cScaleLabel4':  '#ffffff', 'cScale5':  '#000088', 'cScaleLabel5':  '#ffffff', 'cScale6':  '#000099', 'cScaleLabel6':  '#ffffff', 'cScale7':  '#0000aa', 'cScaleLabel7':  '#ffffff', 'cScale8':  '#0000bb', 'cScaleLabel8':  '#ffffff', 'cScale9':  '#0000cc', 'cScaleLabel9':  '#ffffff', 'cScale10': '#0000bb', 'cScaleLabel10': '#ffffff', 'cScale11': '#000066', 'cScaleLabel11': '#ffffff'} } -->

```mermaid { 'theme': 'base', 'themeVariables': { 'color': '#FFFFFF'}, timeline: { disableMulticolor: true} }

timeline
    2004 : Scala is published - the first official release of Scala programming language
         : Java++
    2008 : Scalaz – the first major functional programming library in Scala
    2010 : Akka – actor-based concurrency framework for distributed systems
         : Twitter Futures – Twitter's custom implementation of Futures
    2011 : Finagle – Twitter's RPC framework 
         : Twitter Util – utility libraries supporting Finagle and Twitter infrastructure
    2011 : Play Framework – web framework inspired by Ruby on Rails
    2012 : Apache Spark – distributed data processing engine (Big Data)
    2014 : Apache Flink – stream processing engine with event-at-a-time model
    2015 : http4s – purely functional web framework (initially built on Scalaz)
    2016 : Cats – modern functional programming library from Typelevel
         : Monix – functional & async library (Task, Observable, etc.)
    2017 : Cats Effect – effect system and runtime for functional Scala
    2018 : ZIO – functional effect system with typed errors and environment
         : Mill – alternative build tool for Scala (by Li Haoyi)
         : Ammonite – enhanced Scala REPL and scripting tool
    2020 : Skunk – purely functional PostgreSQL library built on Cats Effect
    2023 : Kyo – algebraic effect system with native support, STM, schedulers
         : Ox – Safe direct-style concurrency and resiliency for Scala on the JVM
         : Gears - experimental cross-platform asynchronous programming library
    2024 : Yaes – minimal, modular, and purely functional effect system
```


---
 
# Java++

<!-- 
- How Java looked like in 2004 (1.4)
- Pizza features
- Java embraced generics, but nothing more
- Enter Scala 
-->


---

# Java++

State of Java in 2004, version 1.4
<v-clicks> 

* No generics yet (they came in Java 5)
* No annotations (introduced in Java 5)
* No enums (Java 5)
* No lambdas or functional programming capabilities (came much later in Java 8)
* No streams API (Java 8)
* No var keyword for type inference (Java 10)

</v-clicks> 

<v-click>So there's plenty of room for Scala improvements</v-click>



---

# Java++

In 2004 Scala was a great innovation, let's see why

---

# Java++

In Java 1.4, when you wanted to produce a list of even squares

---

# Code example: From Java to Scala

First you create a list

<<< ../snippets/EvenSquares.java#list java {*}

---

# Code example: From Java to Scala

Then filter and square the values

<<< ../snippets/EvenSquares.java#map java {*}

---

# Code example: From Java to Scala

Print at last

<<< ../snippets/EvenSquares.java#foreach java {*}

---

# Code example: From Java to Scala

In the meantime with Scala

<v-click>

<<< ../snippets/JavaPlusPlus.scala#map scala {*}

</v-click>

---

# Java++

Similarly for domain modeling. Let's consider a simple immutable `Person` class.

---

# Code example: From Java to Scala

<!-- 
TODO would be nice to show it in steps using https://sli.dev/features/shiki-magic-move
unfortunately `<<< ./person.md ` doesn't work as expected. We could inline `person.md` but still we need to do something about the scrolling
 -->

<div class="long-code">

<<< ../snippets/Person.java java {*}{maxHeight:'400px',lines:true}

</div>

<style>

.long-code > div.slidev-code-wrapper { 
  overflow: hidden;
}
/* animate the code to scroll to the bottom and back to the top, to show how long the definition is */
.long-code code {
  display: flex;
  flex-direction: column;
  animation: scroll-text 20s linear infinite; /* Apply the scroll animation */
  animation-delay: 3s; /* start the animation after 3 seconds */
}

@keyframes scroll-text {
  0% {
    transform: translateY(0); /* Start at the top */
  }
  50% {
    transform: translateY(-70%); /* Scroll up (change -70% based on the content height) */
  }
  100% {
    transform: translateY(0); /* Scroll back to the starting point */
  }
}
</style>

---

# Code example: From Java to Scala

The same thing in Scala

<<< ../snippets/Person.scala#Person scala

---

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

# Code example: akka-testkit 

TODO: how to test that a message wasn’t sent

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

---
layout: full
---

# Now we have everything

<v-clicks>

- We have immutability
- pure functions
- concurrency is simple now
- right?
- Right?

</v-clicks>

<div v-click="4">
  <img class="absolute top-30 left-100 w-100" alt="" src="./godfather.png" />
</div>

<!---
(Bonus: and Dependency injection for free)


TODO: chyba warto tu pokazać kawałek Kyo które robi algebraic effects, podlinkować talk wprowadzający https://www.youtube.com/live/gYS3UkmFoHQ?t=719&cbrd=1

-->

---
layout: full
---

# Many things can do the same thing

<small>and developers will make sure to use them all</small>


<div class="absolute top-30 left-40 flex flex-col items-center p-7 rounded-2xl bg-black shadow-xl w-100">
  <div class="flex">
    <span class="text-2xl font-medium text-white">Let's write generic and add "runtime" later</span>
  </div>
  <div>
    <img class="size-full shadow-xl rounded-md" alt="" src="/explaining-stressed.gif" />
  </div>
</div>

<!--
Transition: Many things that do the same thing
Many projects experimented with some of these features and ended up with a mess
So let’s write a generic code and choose the “runtime” (monad) later!
Or Scala Future, or Actors (Hardcore Hybrid 😀 (Akka Actors + Monad Transformers)
-->

---

# What’s happening now
- Scala cli (you can use it as a scripting language)
- More approachable
- Better packaging
- Scala simplified a lot of features

That all means that Scala is the beginner friendly language that can be used to teach universal functional features that are available in many other languages:
- ADTs
- Pattern matching
- Immutability (inc. collections) 
- Pure functions
- Concurrency
- Streams, stream-based APIs

---

# What’s next ???
- The ecosystem has matured a lot but:
  - Some people use IO/final tagless and won’t go back
  - Some people explore Algebraic Effects/Kyo/yaes (https://github.com/rcardin/yaes) 
  - Some people are still not happy and try different options, starting from basics: Direct style: Ox/Gears
  - Some people use Scala as Java++ and don’t talk about it (there are many of them!)
  - Exploration inside the language is still happening: e.g. Capabilities
- Depending on when you used or saw Scala code, your feelings about the language can be different
- But right now this is a simple FP language that anybody can use!

---

# Simple Scala

TODO: code example

<!--
all the things we learned and know now: let's use simple but powerful tools
-->

--- 

# Side note: Dependency injection ???

- constructor based?
- Autowire magic?
- Guice?
- Another design problem that hasn’t been generally solved for everyone

<!--
- Potential transition? 
- Capabilities
“Non-functional” algebras for dependencies (pre-tagless final)
-->

