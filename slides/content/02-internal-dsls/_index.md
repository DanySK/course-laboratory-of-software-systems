 
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

# Kotlin 202 -- DSLs

## a.k.a. Type safe builders

Domain-specific languages require, as first step, to have a **domain model**.
<br/>
Once the domain entities are available,
they will be elegantly instanced via DSL.
<br/>
The business logic will then be bound to the domain model inextricably.

The domain is modelled with `interface`s,
the DSL is built around them and their implementations by leveraging the mechanisms we have seen:
* trailing lambda convention
* functions with receiver
* member extension functions

---

# Kotlin 202 -- DSLs

## Example

```kotlin
fun main() {
    println(
        html {
            head { title { +"A link to the unibo webpage" } }
            body {
                p {
                    a(href = "http://www.unibo.it") {
                        +"Unibo Website"
                    }
                }
            }
        }.render()
    )
}
```

---

# Kotlin 202 -- DSLs

## Example

```html
<html>
	<head>
		<title>
			A link to the unibo webpage
		</title>
	</head>
	<body>
		<p>
			<a href="http://www.unibo.it">
				Unibo Website
			</a>
		</p>
	</body>
</html>
```

---

# Extra content

A lot of language details have been left out of this guide, non complete list:
* arrays
* enum classes
* spread operator
* `noinline` and `crossinline`
* coroutines
* interoperatibility with Java
* `inline class`es
