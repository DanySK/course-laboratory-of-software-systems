 
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
fun foo(a: Int = 0, b: String = "foo"): Int = TODO() // TODO() is a builtin function throwing a `NotImplementedError`
foo(1, "bar") // OK, positional
foo(a = 1, b = "bar") // OK, named
foo(1, b = "bar") // OK, hybrid
foo(a = 1, "bar") // error: no value passed for parameter 'b'
foo() // OK, both defaults
foo(1) // OK, same as foo(1, "foo")
foo("bar") // error: type mismatch: inferred type is String but Int was expected
foo(b = "bar") // OK, same as foo(0, "bar")
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
## Nullable types

Every Kotlin type exists in two forms: normal, and nullable (likely inspired by Ceylon).
<br/>
Nullable types are suffixed by a `?` and require special handling
<br/>
`null` can't be assigned to non nullable types!
* Nullables are the Kotlin way to deal with `Option` types

```kotlin
var foo = "bar" // Okay, type is String
var baz: String? = foo // Okay, normal types can be assigned to nullables
foo = baz // error: type mismatch: inferred type is String? but String was expected
foo = null // error: null can not be a value of a non-null type String
```

---

# Kotlin 101
## Accessing nullable types

Nullable types memebers can't be accessed by `.`.
```kotlin
var baz: String? = "foo"
baz.length // error: only safe (?.) or non-null asserted (!!.) calls are allowed...
// on a nullable receiver of type String?
```

### Safe call operator `?.`
Performs runtime access to a member of a nullable object if it's not `null`, otherwise returns `null`
* Somewhat similar to Scala's `Option`'s `map` (but no monad involved)
```kotlin
var baz: String? = "foo"
baz?.length // returns 3, return type is "Int?", in fact...
val bar: Int = baz?.length // ...error: type mismatch: inferred type is Int? but Int was expected
baz = null
baz?.length // returns null, return type is still "Int?"
```

---

# Kotlin 101

### Non-null assertion `!!`
Also known as: *I want my code to break badly at runtime*
<br/>
* Invalidates the whole point of having nullable types by asserting that the nullable object is not `null` at runtime
* It should be **never** used
    * In fact its ugly syntax is so *ugly by purpose*

```kotlin
var baz: String? = "foo"
baz!!{{<comment_frag " // Returns 'foo', type String (non nullable)" >}}
baz!!.length{{<comment_frag " // returns 3, return type is Int" >}}
baz = null
baz!!{{<comment_frag " // throws a KotlinNullPointerException, like the good ol'times!" >}}
```

---

# Kotlin 101

### Elvis operator `?:`
Yeah it's actually named after Elvis Presley due to his haircut 😉
<br/>
* Returns the left operand if it's not `null`, otherwise the right one

```kotlin
var baz: String? = "foo"
baz ?: "bar" // Returns "foo", type String
baz?.length ?: 0 // returns 3, return type is Int
baz = null
baz ?: "bar" // Returns "bar", type String
baz?.length ?: 0 // returns 0, return type is Int
```

---

# Kotlin 101

## Platform types
Kotlin targets the JVM, JavaScript, and native code
<br/>
*None of them has nullable types!*

Nullability is unknown for types coming from the target platform, how to deal with them?
1. {{< frag c="Always consider them nullable (safe, but very unpleasant)" >}}
1. {{< frag c="Always consider them non nullable (code is lightweight and nice, but unsafe)" >}}

---

# Kotlin 101

## Platform types
Kotlin considers all foreign values whose nullability is unknown as *platform types*
* Their type is suffixed by `!` (e.g., `java.util.Date!`)
* At first use, their type is *implicitly disambiguated* (either nullable or non-nullable)
    * Namely, platform types can be used as non-nullable...
* Runtime nullability checks are put in place by the compiler (*fail fast!*)
    * ...but their actual nullablity is checked at use-site
* Platform types *can't be created* in Kotlin! They only come from interaction with "platform code"
* If the target platform offers some way to assert nullability, Kotlin tries to use it
    * e.g., if a Java method/parameter is annotated with `@NotNull` (or similar common alternatives) it will be interpreted as a non-nullable type

---

# Kotlin 101

## Type hierarchy

* In Java
    * top type: `Object`
    * bottom type: no bottom type
* In Scala
    * top type: `Any`
    * bottom type: `Nothing`
* In Kotlin:
    * top type: `{{<frag c="Any">}}`
    * bottom type:

---

# Kotlin 101

## Type hierarchy

* In Java
    * top type: `Object`
    * bottom type: no bottom type
* In Scala
    * top type: `Any`
    * bottom type: `Nothing`
* In Kotlin:
    * top type: ~~`Any`~~ `{{<frag c="Any?">}}`
    * bottom type: `{{<frag c="Nothing">}}`

---

# Kotlin 101

## `Boolean`s

Exactly as Java/Scala, but with nullability:
* `Boolean`: `true`/`false`
* `Boolean?`: `true`/`false`/`null`
*  `&&`, `!!`, and `!` operators work for *non-nullable* `Boolean`s.

Likewise Scala, boxing under the JVM is dealt with by the compiler
<br/>
`Boolean?` are always boxed (to be able to account for `null`)

---

# Kotlin 101

## Numeric types

Same as Scala, +nullability, +*unsigned experimental types*:
* `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`
    * And nullable equivalents, always boxed under the JVM
* `UByte`, `UShort`, `UInt`, `ULong`

---

# Kotlin 101

## Issues of implicit numeric types conversion

Implicit type conversion to "bigger" types is source of nasty errors when automatic boxing is involved.
<br/>
Consider the following Scala code:
```scala
Double.NaN == Double.NaN // false, OK, as every sane language
Double.NaN equals Double.NaN // true! Boxing + Singleton make equality inconsistent!
```
Another example:
```scala
val a: Int = 1
val b: Long = a
a == b // true
a equals b // false
```
This causes a chain of issues, as `==` and `equals` do a different job, as do `##` and `hashCode`: `Map`s can become very surprising!


---
# Kotlin 101

## Numeric type conversions in Kotlin

Kotlin numeric types are converted manually to prevent these issues:
```kotlin
val i: Int = 1
val l: Long = 1
val l: Long = i{{<comment_frag " // error: type mismatch: inferred type is Int but Long was expected" >}}
val l: Long = i.toLong(){{<comment_frag " // OK" >}}
i + l{{<comment_frag " // OK, operators are overloaded" >}}
l + i{{<comment_frag " // OK, operators are overloaded" >}}
```

---

# Kotlin 101

## Numeric literals

```kotlin
1234567 // Literal Int
1_234_567 // Literal Int, underscored syntax (preferable)
123L // Literal Long
1.0 // Literal Double
123e4 // Literal Double in scientific notation
1d // Nope :)
1f // Literal Float
1u // Literal UInt
0123 // error: unsupported [literal prefixes and suffixes] (no octal)
0xCAFE // Hex literal Int
0xCAFEBABE // Hex literal Long (automatic, as it does not fit an Int)
0x0000000 // Hex literal Int, even it'd fit a Byte
0b1111111_11111111_11111111_11111111 // Binary Int (Integer.MAX_INT)
0b11111111_11111111_11111111_11111111 // Binary Long
0b11111111_11111111_11111111_11111111u // Binary UInt!
0xFFFF_FFFF_FFFFu // ULong
```

---

# TODOS

## 101
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