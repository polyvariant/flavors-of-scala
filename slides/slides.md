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
themeConfig:
  code-font-size: 1.2em
---

# Flavors of Scala

<!--

-->

---

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

<!--
This presentation will take 20 years... of Scala development. And we'll try to squeeze it as much as we can. This slide shows how much has happened over these years.

We'll try to go roughly chronogically and at the end we won't tell you what's the best approach. This talk is a history lesson and how different ideas were explored in the Scala community.
-->

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
which we've been part of for over 10 years. Hello!

[click] We both work in SiriusXM.
-->

---
layout: center
---

# It's 2004

---
layout: center
background: /kid-laptop.jpg
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

# Code example: From Java to Scala

First you create a list

<<< ../snippets/EvenSquares.java#list java {*}

<!--
In 2004 Scala was a great innovation, let's see why

Working with collections in Java 1.4 demo

- predefine a list 
- only keep the even numbers
- square each number

-->

---

# Code example: From Java to Scala

Then filter and square the values

<<< ../snippets/EvenSquares.java#map java {2-3|4,5,10|7|8|*}{lines: true}

<!--
- only keep the even numbers
- square each number

-->

---

# Code example: From Java to Scala

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
background: /musicians.jpg
---

# Java - immutable `Artist` class

<!--
We want to model an immutable type.
-->

---

# Code example: From Java to Scala


<div class="long-code">

<<< ../snippets/Artist.java java {*}{maxHeight:'400px',lines:true}

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

<<< ../projects/http4s/src/main/scala/example/model.scala#artist scala

<v-click>

<img class="absolute bottom-20 right-20 w-80" alt="" src="./the-office-wow.webp" />
<span class="absolute bottom-50 left-20 text-4xl font-bold bg-white text-black rotate-5">CONVENIENCE</span>

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
- It works great for library design (tapir!)
- See Noel's talk from this year's Scalar ([link](https://www.youtube.com/watch?v=nyMwp7--rY4))

</v-clicks>

---
layout: center
---

# Monadic effects are not the only option


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

---
layout: center
---

We're done here

<v-click>

# But the Scala community is not

</v-click>

---

# Scala community is not done

<v-clicks>

- Keep exploring
- Get inspired by other languages
- Have fun coding

</v-clicks>

---
layout: center
---

<div class="absolute top-5">

# Thank you!

</div>


<img class="absolute top-25 left-30 w-70" alt="" src="./michal_plachta.png" />

<img class="absolute top-25 right-30 w-70" alt="" src="./michal_pawlik.jpg" />


<div class="absolute bottom-30 left-26 text-4xl font-bold">
MICHAŁ PŁACHTA <br/>
</div>

<div class="absolute bottom-10 left-30 text-2xl">

<logos-bluesky/> bluesky [@mplachta.com](https://bsky.app/profile/mplachta.com) <br/>
<grommet-icons-blog/> blog [michalplachta.com](https://michalplachta.com)

</div>

<div class="absolute bottom-30 right-30 text-4xl font-bold">
MICHAŁ PAWLIK
</div>

<div class="absolute bottom-10 right-20 text-2xl">

<logos-bluesky/> bluesky [@michal.pawlik.dev](https://bsky.app/profile/michal.pawlik.dev) <br/>
<grommet-icons-blog/> blog [blog.michal.pawlik.dev](https://blog.michal.pawlik.dev)

</div>
