---
theme: the-unnamed
title: Flavors of Scala
info: |
  ## Flavors of Scala

  [Michał Pawlik](https://michal.pawlik.dev)
  [Michał Płachta](https://michalplachta.com)
drawings:
  persist: true
transition: none
mdc: true
background: /spices.jpg
---

# Flavors of Scala

<!--
sd
-->

---

<!-- Just a draft, doesn't need to be here, can be moved, split into parts -->

# Scala has quite a story now


<!-- alternative theme approach ```mermaid { 'theme': 'base', 'themeVariables': { 'primaryColor': '#FFFFFF', 'lineColor': '#FFFFFF', 'cScale0':  '#000033', 'cScaleLabel0':  '#ffffff', 'cScale1':  '#000044', 'cScaleLabel1':  '#ffffff', 'cScale2':  '#000055', 'cScaleLabel2':  '#ffffff', 'cScale3':  '#000066', 'cScaleLabel3':  '#ffffff', 'cScale4':  '#000077', 'cScaleLabel4':  '#ffffff', 'cScale5':  '#000088', 'cScaleLabel5':  '#ffffff', 'cScale6':  '#000099', 'cScaleLabel6':  '#ffffff', 'cScale7':  '#0000aa', 'cScaleLabel7':  '#ffffff', 'cScale8':  '#0000bb', 'cScaleLabel8':  '#ffffff', 'cScale9':  '#0000cc', 'cScaleLabel9':  '#ffffff', 'cScale10': '#0000bb', 'cScaleLabel10': '#ffffff', 'cScale11': '#000066', 'cScaleLabel11': '#ffffff'} } -->

```mermaid { 'theme': 'base', 'themeVariables': { 'color': '#FFFFFF', 'fontSize': '36px'}, timeline: { disableMulticolor: true} }

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
layout: center
---

<div class="absolute top-5">

# Hello world 👋

</div>


<img class="absolute top-25 left-10 w-90" alt="" src="./michal_plachta.png" />

<img class="absolute top-25 right-10 w-90" alt="" src="./michal_pawlik.jpg" />


<v-click>

<img class="absolute top-50 left-80 w-90 bg-white rotate-5" alt="" src="./SiriusXM.png" />

</v-click>


<div class="absolute bottom-12 left-10 text-4xl font-bold">
MICHAŁ PŁACHTA
</div>

<div class="absolute bottom-12 right-10 text-4xl font-bold">
MICHAŁ PAWLIK
</div>

<!--
zaczynamy w 2004, przeszliśmy sporą drogę, wiele eksperymentów, komplikacji po drodze, ale jak na to spojrzymy z perspektywy czasu to ma to sens

kolejność jest chronologiczno-przypadkowa

take-aways:
- what kind of problems each approach has
- what trade-offs have we faced over the last years
- inspirations from other languages can both help and distract
-->

---
layout: center
---

# It's 2004

---
layout: center
background: kid-laptop.jpg
---

# We're young and ambitious


---
layout: center
---
 
# Let's do some Java

<!-- 
- How Java looked like in 2004 (1.4)
- Pizza features
- Java embraced generics, but nothing more
- Enter Scala 
-->


---

# Let's do some Java

State of Java in 2004, version 1.4

<v-clicks> 

- No generics yet (they came in Java 5)
- No annotations (introduced in Java 5)
- No enums (Java 5)
- No lambdas or functional programming capabilities (came much later in Java 8)
- No streams API (Java 8)
- No var keyword for type inference (Java 10)

</v-clicks>

<v-click> 
So there's plenty of room for Scala improvements
</v-click> 


---

# Java++

In 2004 Scala was a great innovation, let's see why

---

# Java++

Working with collections in Java 1.4 demo

- predefine a list 
- only keep the even numbers
- square each number

---

# Code example: From Java to Scala

First you create a list

<<< ../snippets/EvenSquares.java#list java {*}

---

# Code example: From Java to Scala

Then filter and square the values

<<< ../snippets/EvenSquares.java#map java {2-3|4,5,10|7|8|*}{lines: true}

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

<!--
-->

---

# Java++

Similarly for domain modeling. Let's consider a simple **immutable** `Person` class.

<!--
We want to model an immutable type.
-->

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

<v-click>

<img class="absolute bottom-20 right-20 w-80" alt="" src="./the-office-wow.webp" />

</v-click>

---
src: ./concurrency.md
---

---
src: ./http.md
---


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

Try it at home!

Watch [this talk](https://www.youtube.com/live/gYS3UkmFoHQ?t=719&cbrd=1) for more detailed introduction

---
layout: center
---

# TODO: Conclusions


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


<!--
Transition: Many things that do the same thing
Many projects experimented with some of these features and ended up with a mess
So let’s write a generic code and choose the “runtime” (monad) later!
Or Scala Future, or Actors (Hardcore Hybrid 😀 (Akka Actors + Monad Transformers)
-->

---

# What’s happening now

We have always challenged the status quo and it won't change...

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

# What is Simple Scala?

TODO: code example

<!--
all the things we learned and know now: let's use simple but powerful tools

Was Scalatra simple? Was MTL simple?

Easy vs simple

Simple Scala doesn't mean primitive Scala. Simple may mean "simple to read", "simple to change", "easy to maintain"
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

