 
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
fun foo() { // same as above with multiple expression
    return 20 // requires return in this form
}
```

---

# Kotlin 101
## Function parameters, return types

Much like Scala:

```kotlin
fun foo(): Int = 10 // types are annotated with a column and the type name
fun foo() = 10 // return type annotation can be omitted (if the compiler can infer it)
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