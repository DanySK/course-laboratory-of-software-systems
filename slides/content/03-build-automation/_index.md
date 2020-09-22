 
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

## Build Automation

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Build automation

The process of automating the creation of *software artifacts*
<br/>
going from *source* code to *tested* deployable artifact

May include, depending on the system specifics:
* Automated *source code manipulation* and generation
* Source code *quality assurance*
* *Dependency management*
* *Compilation*, linking
* *Binary manipulation*
* *Test execution*
* Test *quality assurance* (e.g., coverage)
* API *documentation*
* *Packaging*
* *Delivery*


---

# Overview

* Build automation: basics, styles
* Gradle as paradigmatic build automator
    * Core concepts and basics
    * Dependency management and configurations
    * The build system as a dependency
    * Hierarchial organization
    * Isolation of imperativity
    * Declarativity via DSLs
    * Reuse via plug-ins
    * Testing plug-ins
    * Existing plugins

---

## Build automation: basics and styles

It's just a software that automates the building of some software
<br/>
hence, all concerns that hold for sofware creation hold for build systems creation.

**Imperative style**: write a script that tells the system what to do to get
from code to artifacts
* *Examples*: make, cmake, Apache Ant
* Verbose, repetitive
* Configuration (declarative) and actionable (imperative) logics mixed together
* Highly configurable

**Declartive style**: adhere to some convention, specify additional configuration,
and let the tool decide what to do actually
* *Examples*: Apache Maven
* Separation between *what* to do and *how* to do it
* Configuration limited by the provided options

---

## Hybrid automators

Create a *declarative infrastructure* upon an *imperative basis*, and
*allow easy access to the underlying machinery*

**DSL**s are helpful in this context: they can "hide" imperativity without ruling it out

Still, many challenges remain open:
* How to reuse the build logic?
    * within a project, and among projects
* How to structure multiple logical and interdependent parts?

---

# Gradle

A paradigmatic example of a hybrid automator:
* Written mostly in Java
* with an outer Groovy DSL
* ...and, more recently, a Kotlin DSL

### Our approach to Gradle

* We are **not** going to learn "how to use Gradle"
* We are going to *learn Gradle*

---

## Gradle: main concepts

* **Project** -- A collection of files comprising the software
    * A project can contain another project:
    * the container project is the **root project**
    * the contained projects are **subproject**s
* **Build file** -- A special file, situated in the root directory of a project,
instructing Gradle on the actual organization of the project projects
* **Dependency** -- A resource required by some operation.
    * May have dependencies itself
    * Dependencies of dependencies are called *transitive* dependencies
* **Configuration** -- A group of dependencies with *three roles*:
    1. *Declare* dependencies
    2. *Resolve* dependency declarations to actual artifacts/resources
    2. *Present* the dependencies to consumer in a suitable format
* **Task** -- An atomic operation on the project, which can
  * have input and output files
  * depend on other tasks (can be executed only if those are completed)

---

## Gradle from scratch: empty project

Let's start as empty as possible, just point your terminal to an empty folder and:
```bash
gradle tasks
```

Stuff happens: if nothing is specified,
<br>
*Gradle considers the folder where it is invoked as a project*
<br>
*The project name matches the folder name*

Let's understand what:
<br>

```bash
Welcome to Gradle <version>!

Here are the highlights of this release:
 - Blah blah blah

Starting a Gradle Daemon (subsequent builds will be faster)
```

Up to there, it's just performance stuff:
Gradle uses a background service to speed up cacheable operations

---

## Gradle from scratch: empty project

```bash
> Task :tasks

------------------------------------------------------------
Tasks runnable from root project
------------------------------------------------------------

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.
```

Some tasks exist already!
They are built-in.
Let's ignore them for now.


---

## Gradle from scratch: empty project

```bash
Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project '00-empty'.
components - Displays the components produced by root project '00-empty'. [incubating]
dependencies - Displays all dependencies declared in root project '00-empty'.
dependencyInsight - Displays the insight into a specific dependency in root project '00-empty'.
dependentComponents - Displays the dependent components of components in root project '00-empty'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project '00-empty'. [incubating]
outgoingVariants - Displays the outgoing variants of root project '00-empty'.
projects - Displays the sub-projects of root project '00-empty'.
properties - Displays the properties of root project '00-empty'.
tasks - Displays the tasks runnable from root project '00-empty'.
```

Informational tasks. Among them, the `tasks` task we just invoked

---

## Gradle: configuration vs execution

It is time to create our first *task*
<br>
Create a `build.gradle.kts` file as follows:

```gradle
tasks.register("brokenTask") { // creates a new task
    println("this is executed at CONFIGURATION time!")
}
```

Now launch gradle with `gradle brokenTask`:
```bash
gradle broken
this is executed at CONFIGURATION time!

BUILD SUCCESSFUL in 378ms
```
Looks ok, but it's **utterly broken**

---

## Gradle: configuration vs execution

Try launching `gradle tasks`
* We do not expect our task to run, we are launching something else

```bash
❯ gradle tasks

> Task :tasks

------------------------------------------------------------
Tasks runnable from root project
------------------------------------------------------------

this is executed at CONFIGURATION time!
Build Setup tasks
```

**Ouch!**

**Reason**: the build script executes when Gradle is invoked, and *configures* tasks and dependencies.
<br>
Only later, when a task is invoked, it is *actually executed*
*

---

## Gradle: configuration vs execution

Let's write a *correct* task
```gradle
tasks.register("helloWorld") {
    doLast { // This method takes as argument a Task.() -> Unit
        println("Hello, World!")
    }
}
```

Execution with `gradle helloWorld`
```bash
gradle helloWorld

> Task :helloWorld
Hello, World!
```

---

## Gradle: configuration vs execution

### Why two separate phases?

Delaying the actual execution allows for a more *fine grained configuration*
<br>
This will be especially useful when *modifying existing behavior*

```gradle
tasks.register("helloWorld") {
    doLast { println("Hello, World!") }
}

tasks.getByName("helloWorld") { // let's find an existing task 
    doFirst { // Similar to doLast, but adds operations in head
        println("Configured later, executed first.")
    }  
}
```

```bash
gradle helloWorld

> Task :helloWorld
Configured later, executed first.
Hello, World!
```

---

## Gradle: task types

Gradle offers some facilities to make it easier writing new tasks
<br>
An example is the [`org.gradle.api.Exec`](https://docs.gradle.org/current/javadoc/org/gradle/api/tasks/Exec.html) task type, representing a command to be executed on the underlying command line

At task registration time, it is possible to specify the task type.
<br>
Any `open class` implementing [`org.gradle.api.Task`](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html) can be instanced

```gradle
import org.gradle.internal.jvm.Jvm // Jvm is part of the Gradle API
tasks.register<Exec>("printJavaVersion") { // Do you Recognize this? inline function with reified type!
    // Configuration action is of type T.() -> Unit, in this case Exec.T() -> Unit
    val javaExecutable = Jvm.current().javaExecutable.absolutePath 
    commandLine( // this is a method of class org.gradle.api.Exec
        javaExecutable, "-version"
    )
    // There is no need of doLast / doFirst, actions are already configured
    // Still, we may want to do something before or after the task has been executed
    doLast { println("$javaExecutable invocation complete") }
    doFirst { println("Ready to invoke $javaExecutable") }
}
```

```
> Task :printJavaVersion
Ready to invoke /usr/lib/jvm/java-11-openjdk/bin/java
openjdk version "11.0.8" 2020-07-14
OpenJDK Runtime Environment (build 11.0.8+10)
OpenJDK 64-Bit Server VM (build 11.0.8+10, mixed mode)
/usr/lib/jvm/java-11-openjdk/bin/java invocation complete
```

---



    * Dependency management and configurations
    * The build system as a dependency
    * Task dependencies
    * Hierarchial organization
    * Isolation of imperativity
    * Declarativity via DSLs
    * Reuse via plug-ins
    * Testing plug-ins
    * Existing plugins