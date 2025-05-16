---
theme: unicorn
layout: full
title: Flavors of Scala
info: |
  ## Flavors of Scala

  [Michał Pawlik](https://michalplachta.com)
  [Michał Płachta](https://michalplachta.com)
drawings:
  persist: true
transition: none
mdc: true
---

# Flavors of Scala

TODO: timeline

<!--
Hello everyone!
-->


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
<<< ../snippets/Person.java java {*}{maxHeight:'400px',lines:true}

---

# Code example: From Java to Scala

<<< ../snippets/Person.scala#Person scala

---

# How Scala helped

- 2004-2012 Java++, how Scala helped there
- Immutability (built-in collections)
- Case classes
- Pure functions
- `Option` (handling nulls) (vs Optional Java 1.7)
- `Future` (it’s a “lots of hand waving” Monad)

---

# Code example: testing Futures

TODO: testing using type parameters, ID monad from Scalaz/early Cats

<!--
Transition: FUTURE -> concurrency -> Akka Actors
We have immutability, pure functions, concurrency should be simple now, right? Right? (pic)
Problems designing and programming
Hanging Futures
One-off jobs 
What about the state?
-->

---

# Side-note: Spark ???
- Google map-reduce (referencing streams)
- Pure functions
- Hard to test
- But… Scala got popular because of it
- FP programmers took advantage of Spark to promote itse;f

---

# Akka Actors (2012)
- Shared mutable state
- Everyone dreams big 
- Scala got even more popular because of it
- FP programmers took advantage of Spark to promote itself
- Code examples: free monad-based Akka actors 

---

# Problems with Akka-based design

<small>(that Scala devs encountered)</small>

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

