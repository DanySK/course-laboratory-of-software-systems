 
+++

title = "Introduction to Laboratory of Software Systems"
description = "Description of the course"
outputs = ["Reveal"]

[reveal_hugo]
transition = "slide"
transition_speed = "fast"
custom_theme = "custom-theme.scss"
custom_theme_compile = true

[reveal_hugo.custom_theme_options]
targetPath = "css/custom-theme.css"
enableSourceMap = true

+++

# {{< course_name >}}

## Internal Domain-Specific Languages
## In Kotlin

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# DSLs

Languages that capture a *specific domain*
* Usually not meant for general purpose computation
    * Even if some could support it
* Provide language level support for expressing *domain entities*
    * their *structure*
    * their *behavior*
    * their *interactions*

Being able to express a DSL requires a **formalization of the domain**
<br/>
which in turn requires a **deep understanding of the domain**

---

# DSLs in Kotlin

Languages with a flexible syntax are good candidates to host DSLs:
* Scala (via `implicit`s, infixing, currying, mixed brackets)
* Groovy (trailing lambdas, extension methods, invoke convention)
* Ruby (powerful metaprogramming, dynamicity)
* of course, Kotlin (whose syntax is a mix of Groovy and Scala)

Key features for building DSLs:
* Invoke convention
* Trailing lambda convention
* Operator definition and overloading
* `infix` calls
* Extension functions
* Lambda and function types with receiver
* Extension members

---

# Kotlin 202 -- DSLs

## a.k.a. Type safe builders

Domain-specific languages require, as first step, to have a **domain model**.
<br/>
Once the domain entities are available,
they will be elegantly instanced via DSL.
<br/>
The business logic will then be bound to the domain model inextricably.

The domain is better modelled with `interface`s,
<br/>
whose implementations are manipulated by an infrastructure exposing the DSL

---

# Kotlin 202 -- DSLs

## A simple HTML DSL

Desired syntax:

```kotlin
html {
    head {
        title { -"A link to the unibo webpage" }
    }
    body {
        p("class" to "myCustomCssClass") {
            a(href = "http://www.unibo.it") { -"Unibo Website" }
        }
    }
}.render()
```
---

# Kotlin 202 -- DSLs

## A simple HTML DSL

Result:

```html
<html>
	<head>
		<title>
			A link to the unibo webpage
		</title>
	</head>
	<body>
		<p class="myCustomCssClass">
			<a href="http://www.unibo.it">
				Unibo Website
			</a>
		</p>
	</body>
</html>
```

---

# Kotlin 202 -- DSLs

## The HTML abstract domain

* An HTML document is made of text `Element`s
* These `Element`s can be either `Tag`s or plain `Text`
* Some `Element`s can be `Repeatable`
* `Text` elements are always `Repeatable`
* Some `Tag`s can be `Repeatable`

```kotlin
interface Element
interface RepeatableElement : Element
interface Tag : Element
interface RepeatableTag : Tag, RepeatableElement
interface TextElement : RepeatableElement
```

