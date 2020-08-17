 
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

## Kotlin (for Scala developers)

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Why Kotlin

JetBrains-made modern programming language 
* Focused on "practical use" (whatever that means)

Gaining momentum since Google adopted is as *official Android language* (along with Java and C++)

Clearly inspired by a mixture of Java, C#, Scala, and Groovy

**In this course** -- we'll need it for Gradle and *internal* domain specific languages

---

# Philosophy: Kotlin vs. Scala

**Sca**_la_ is a **scalable** _language_
* Few core constructs that enable a huge variety of programming patterns
* Born in academia
* State of the art type checker with advanced features
    * Higher Kinded Types
    * Type lambdas
    * Enough to do type programming...

**Kotlin** is somewhat *a better java*
* Born in industry, for the industry
* Many more "core" constructs and keywords than Scala
* Focused on getting productive quickly and reducing programming errors
* Focus on multi-target (can compile towards JVM, JavaScript, and native)
    * Scala can as well by the way
    * Kotlin puts more care into bidirectional compatibility

---

# Kotlin 101
## Defining functions, constant, variables

Similar to Scala. The keyword `def` is replaced by `fun`
```kotlin
val x = 10 // constant
var y = 20 // variable, can be reassigned
fun foo() = 20 // function definition, single expression
fun bar(): Int { // same as above with multiple expression
    return 20 // requires a return in this form...
}
fun baz() { } // Unless it returns Unit

```

---

# Kotlin 101
## Function parameters and return types

Much like Scala:

* All parameters are named, but can be invoked positionally as well
* Parameters can have defaults
* Types are annotatedd after the parameter name
* Invocation can be positional or by name, with the rule that once a named parameter is used, subsequent parameters must be named as well

```kotlin
fun foo(a: Int, b: String): Int = TODO() // TODO() is a builtin
// MANCANO I DEFAULT!!!!
function throwing a `NotImplementedError`
foo(1, "bar") // OK, positional
foo(a = 1, b = "bar") // OK, named
foo(1, b = "bar") // OK, hybrid
foo(a = 1, "bar") // error: no value passed for parameter 'b'
```

---

# Kotlin 101
## Top level functions

Kotlin supports top level functions

```kotlin
fun foo() {
    ...
}
```

By contrast, Scala requires to put them in an `object`

```scala
object Foo {
    def foo() {
        ...
    }
}
```

When targeting the JVM, Kotlin simply generates a `FileNameKt` class behind the scenes where the function is stored.
The behaviour can be controlled via annotations.


---

# Kotlin 101
## Program entry point

Naming a function `main` makes it a valid entry point:

```kotlin
fun main() = println("Hello World") // Valid entry point
```
```kotlin
fun main(arguments: Array<String>) = println("Hello World") // Valid entry point
```
```kotlin
fun main(arguments: Array<String>) {
    println("Hello World") // Return type is Unit, no need to return
} 
```

---

# Kotlin 101
## Program entry point


---

# TODOS

## 101
type hierarchy (top and bottom types)
nullable types
boolean types
numeric types
unsigned integers
string templates
multiline strings
packages and imports
compile time constants
varargs
infix
local functions
inline functions

## flow control
if
for
while
break and continue
when vs. pattern matching
labeled jumping (incl. qualified return)

## oop
classes and members (methods and properties)
object creation (no new)
access to members (invoke vs named access)
inheritance and open
inheritance and override
abstract
inheritance and disambiguation by `super<Class>`
interfaces and subtyping
implementation of members in interfaces (no backing field)
constructors
init blocks
secondary constructors
nullability and `lateinit`
getters
setters
accessors in interfaces
backing fields, `field`
objects
companion objects
visibility
controlling visibility of properties (`private set`)
equality / hashing / toString

## generics
syntax for functions
syntax for classes
type bounds
`where`
variance
use site variance and type projection
declaration site variance
star-projection
reified types
invocation of reified types

## basic collections?
list / mutablelist
set / mutableset
map / mutablemap
`in`

## extended OOP
data classes
copy / equals / toString
destructuring declarations
Pair / Triple
sealed classes
sealing and when
nested classes
inner classes
enum classes
anonymous classes via object expression
type aliasing
delegation and `by`
contract implementation by delegation (favor composition over inheritance)
delegated properties and variables
delegated properties https://kotlinlang.org/docs/reference/delegated-properties.html
lazy
observable
delegation via map (mutability via MutableMap)
implementing a custom delegate for properties

## functional kotlin
function types
function types and nullability
lambda expressions
trailing lambdas
implicit single parameters
closures
destructuring in lambdas
anonymous functions (rarely used)
invoke convention
inline functions

## extensions
extension functions vs implicits
extension properties (no backing field)
nullable receivers
companion extensions
generic extensions
extensions resolution
extension members (scope control)
visibility of private members

## extensions + functional
function types with receiver

## standard library
let / run / apply / with
arrays
primitive arrays
ranges

## performance
tail recursion
inline functions
noinline
crossinline
inline classes -- mentioned and skipped, ref to https://kotlinlang.org/docs/reference/inline-classes.html

## java interop
@JvmOverloads
@JvmDefault

## DSLs in Kotlin (type-safe builders)