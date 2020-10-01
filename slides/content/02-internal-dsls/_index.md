 
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

# Kotlin 203 -- DSLs

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

# Kotlin 203 -- DSLs

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

# Kotlin 203 -- DSLs

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

<div style='position: relative; padding-bottom: 56.25%; padding-top: 35px; height: 0; overflow: hidden;'><iframe sandbox='allow-scripts allow-same-origin' allowfullscreen='true' allowtransparency='true' frameborder='0' height='315' src='https://www.mentimeter.com/embed/9e8329b62b8c7bd8daf911dd652d4ee4' style='position: absolute; top: 0; left: 0; width: 100%; height: 100%;' width='420'></iframe></div>

---

# Kotlin 203 -- DSLs

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

---

# Kotlin 203 -- DSLs

## Detailing the domain

* All `Element`s can be rendered to plain text, possibly with some indentation
```kotlin
interface Element {
    fun render(indent: String = ""): String
}
```

* `TextElement`s are composed of simple `text`, possibly rendered with intentation
```kotlin
interface TextElement : RepeatableElement {
    val text: String
    override fun render(indent: String) = "$indent$text\n"
}
```
* Tags have a `name`, they can have `children Element`s, and key/value `attributes`
```kotlin
interface Tag : Element {
    val name: String
    val children: List<Element>
    val attributes: Map<String, String>
}
```

---

# Kotlin 203 -- DSLs

## Domain implementation

* Let's pick a `String` as indentation
* The abstract domain uses a map to define attributes,
but working with `Pair`s is not very clean:
we convey a better meaning to what we are doing by relying on a `typealias Attribute`
* `TextElement` is trivial to implement, it's just text

```kotlin
const val INDENT = "\t" // Compile time constant
typealias Attribute = Pair<String, String> // Just to avoid writing Pair<String, String>
data class Text(override val text: String) : TextElement
```

---

# Kotlin 203 -- DSLs

## Domain implementation: `Tag`

`Tag` is a bit more complex, let's try to factor the common part of all `Tag`s into an `AbtractTag`
* `name` is easy
* `Attribute`s get registered at creation time. We use a `vararg` to make the construction nicer
* We decide for a mutable implementation of `children`
* Subclasses are allowed to `register` sub elements
    * We can check if an element is `Repeatable` or not

```kotlin
abstract class AbstractTag(override val name: String, vararg attributes: Attribute) : Tag {
    final override var children: List<Element> = emptyList() // Override val with var
        private set(value) { field = value } // Write access only from this class
    final override val attributes: Map<String, String> = attributes.associate { it }
    fun registerElement(element: Element) {
        if (element is RepeatableElement || members.none { it::class == element::class }) {
            members = members + element
        } else {
            throw IllegalStateException("cannot repeat tag ${element::class.simpleName} multiple times:\n$element")
        }
    }
}
```

---

# Kotlin 203 -- DSLs

## Domain implementation: `Tag`'s rendering
* We can now write how an HTML `Tag` is rendered

```kotlin
abstract class AbstractTag(override val name: String, vararg attributes: Attribute) : Tag {
        ...
        final override fun render(indent: String) = // Rendering by multiline string!
            """
            |$indent<$name${renderAttributes()}>
            |${renderChildren(indent + INDENT)}
            |$indent</$name>
            """
            .trimMargin() // Trims what's left of a |
            .replace("""\R+""".toRegex(), "\n") // In case there are no children, no empty lines
    private fun renderChildren(indent: String): String =
        children.map { it.render(indent) }.joinToString(separator = "\n")
    private fun renderAttributes(): String = attributes.takeIf { it.isNotEmpty() }
        ?.map { (attribute, value) -> "$attribute=\"$value\"" } // Safe fluent calls
        ?.joinToString(separator = " ", prefix = " ")
        ?: "" // Elvis operator
}
```

---

# Kotlin 203 -- DSLs

## DSL: entry point

We will now:
1. Define the language entities
2. Find out a way to nest them nicely

**Note:** In this example we mix the language and model implementation.
<br/>
It'd be cleaner to define a complete API (favoring immutability),
and then wrap it in a DSL (where mutability is mandatory)

```kotlin
class HTML(vararg attributes: Attribute = arrayOf()) : AbstractTag("html", *attributes)
fun html(vararg attributes: Attribute, init: HTML.() -> Unit): HTML = HTML(*attributes).apply(init)
```
* A top level function to access the DSL
* Mandatory parameter first (but with a `vararg` they are implicitly optional)
* Configuration function in form of function with receiver
* A `class` representing our entry point

---

# Kotlin 203 -- DSLs

## DSL: entry point

We can now write:
```kotlin
html { }.render()
html("lang" to "en") { }.render()
```
producing:
```html
<html>
</html>
<html lang="en">
</html>
```

---

# Kotlin 203 -- DSLs

## DSL: adding elements

* The base system is always the same!
```kotlin
class Head : AbstractTag("head") {
    fun title(configuration: Title.() -> Unit = { }) = registerElement(Title().apply(configuration))
}
abstract class TagWithText(name: String, vararg attributes: Attribute) : AbstractTag(name, *attributes) {
    // Scoping via member extensions! 
    operator fun String.unaryMinus() = registerElement(Text(this)) // Syntax for writing plain text
}
class Title : TagWithText("title")
class Body(vararg attributes: Attribute) : TagWithText("body", *attributes)
const val newline = "<br/>"
```
```kotlin
html("lang" to "en") {
    head { title { -"An experiment" } }
    body {
        -"My contents"
        -newline
        -"And some more contents"
    }
}
```

---

# Kotlin 203 -- DSLs

## DSL: reusing elements

* Common DSL elements can be factored and reused via inheritance

```kotlin
abstract class BodyTag(name: String, vararg attributes: Attribute) : TagWithText(name, *attributes) {
    // <a> and <p> can be nested everywhere in the body
    fun p(vararg attributes: Attribute, configuration: P.() -> Unit) =
        registerElement(P(*attributes).apply(configuration))
    fun a(href: String? = null, vararg attributes: Attribute, configuration: Anchor.() -> Unit) =
        registerElement(Anchor(href, *attributes).apply(configuration))
}
class Body(vararg attributes: Attribute) : BodyTag("body", *attributes) // Changed hierarchy
class P(vararg attributes: Attribute) : BodyTag("p", *attributes), RepeatableElement // Repeatable
class Anchor(
    href: String? = null,
    vararg attributes: Attribute
) : BodyTag("a", *(if (href == null) emptyArray() else arrayOf("href" to href)) + attributes),
    RepeatableElement // Repeatable
```

---

# Kotlin 203 -- DSLs

## Preventing scope leaking

Right now, this is valid:
```kotlin
html { head { head { title { title { } } } } }
```
But it produces invalid HTML!
<br/>
The reason is that Kotlin's method resolution automatically searches in "outer" *implicit receivers*.
<br/>
The code is equivalent to:
```kotlin
html { this.head { this@html.head { this.title { this@head.title { } } } } }
```

Inside a DSL, we usually (but not always) do not want the *implicit receiver* to "escape" its scope
<br/>
Kotlin provides an annotation used to create scope-blocking annotations:
```kotlin
@DslMarker // I'm defining an annotation that will prevent scope leaking
annotation class HtmlTagMarker // I'm calling it HtmlTagMarker
@HtmlTagMarker // All entities whose this should not get called automatically get annotated
class HTML(vararg attributes: Attribute = arrayOf()) : AbstractTag("html", *attributes) { ... }
@HtmlTagMarker
class Head : AbstractTag("head") { ... }
@HtmlTagMarker // Annotating the common superclass suffices
abstract class TagWithText(name: String, vararg attributes: Attribute) : AbstractTag(name, *attributes) { ... }

```
---

# Kotlin 202 -- DSLs

## Preventing scope leaking

With the DslMarker:
```kotlin
html {
    head {
// 'fun head(configuration: Head.() -> Unit): Unit' can't be called in this context by implicit receiver.
        head { // Compilation Error
            title {
// 'fun title(configuration: Title.() -> Unit = ...): Unit' can't be called in this context by implicit receiver.
                title { } // Compilation Error
            }
        }
    }
}
```
You can still do this manually of course:
```kotlin
html { head { this@html.head { title { this@head.title { } } } } } // compiles
```
But you must be *explicitly willing* to access an outer receiver

---

# Kotlin 203 -- DSLs

## Summing up

1. First and foremost **model the domain**
    * A domain model is a *prerequisite* to write a DSL
    * Focus on a *clean programming API*
2. Provide an implementation for your domain elements
3. Compose the API elements into DSL blocks
    * Functions with receiver as configuration blocks
    * Member extensions to control scope
    * Reuse by inheritance
    * Receiver "leakage" via annotations
