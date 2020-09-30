 
+++

title = "Build Automation"
description = "The art of letting machines do the job for you, with Gradle as example"
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

Gradle offers some facilities to make it writing new tasks easier
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

## Gradle: principle of automation

Let's try something more involved: compiling some Java source located in `src`.
<br>
#### **PRINCIPLE**
> *If you know how to do it, then you can instruct a machine to do it*

Compiling a Java source is just matter of invoking the `javac` compiler:
* Passing the files to be compiled
* Passing an appropriate classpath where to look for dependencies
* Passing where to put generated files

*Once you learn how some product is built, and you know how to build it by hand*
<br>
**you have all the knowledge required to automate its construction**

---

## Gradle: compiling from scratch

Let's compile a simple `src/HelloWorld.java`:
```java
class HelloWorld {
    public static void main(String... args) {
        System.out.println("Hello, World!");
    }
}
```
Build logic:
1. Find the sources to be compiled
2. If any, find `javac`
3. Invoke `javac -d destination <files>`

```gradle
import org.gradle.internal.jvm.Jvm
tasks.register<Exec>("compileJava") {
    val sources = findSources() // 
    if (sources.isNotEmpty())  { // If the folder exists and there are files
        val javacExecutable = Jvm.current().javacExecutable.absolutePath // Use the current JVM's javac
        commandLine(
            "$javacExecutable",
            "-d", "$buildDir/bin", // destination folder: the output directory of Gradle, inside "bin"
            *sources
        )
    }
    // the task's doLast is inherited from Exec
}
```
---

## Gradle: compiling from scratch

Here is the `findSources()` function:
* Pure Kotlin
* Single expression
* Fluent safe call chaining

```kotlin
fun findSources(): Array<String> = projectDir // From the project
    .listFiles { it: File -> it.isDirectory && it.name == "src" } // Find a folder named 'src'
    ?.firstOrNull() // If it's not there we're done
    ?.walk() // If it's there, iterate all its content (returns a Sequence<File>)
    ?.filter { it.extension == "java" } // Pick all Java files
    ?.map { it.absolutePath } // Map them to their absolute path
    ?.toList() // Sequences can't get converted to arrays, we must go through lists
    ?.toTypedArray() // Convert to Array<String>
    ?: emptyArray() // Yeah if anything's missing there are no sources
```
Execution:
```bash
gradle compileJava

BUILD SUCCESSFUL in 693ms
```
Compiled files are in `build/bin`!

---

## Gradle: dependency management

Dependency management in Gradle depends from two fundamental concepts:
* **Dependency**, a resource of some kind, possibly having other (*transitive*) dependencies
* **Configuration**, a *resolvable* (mappable to actual resources) set of dependencies

Let's see a use case: compiling a Java source with a dependency
* In `javac` terms, we need to feed some jars to the `-cp` flag of the compiler
* In Gradle (automation) terms, we need:
    * a *configuration* representing the compile classpath
    * one *dependency* for each library we need to compile

---

## Gradle: dependency management

Conceptually, we want something like:
```kotlin
// Gradle way to create a configuration
val compileClasspath by configurations.creating // Delegation!
dependencies {
    forEachLibrary { // this function does not exist, unfortunate...
        compileClasspath(files(it))
    }
}
```
To be consumed by our improved compile task:
```kotlin
tasks.register<Exec>("compileJava") {
    // Resolve the classpath configuration (in general, files could be remote and need fetching)
    val classpathFiles = compileClasspath.resolve()
    val sources = findSources() // Find sources
    if (sources != null)  {
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":" // Deal with Windows conventions
        commandLine(
            "$javacExecutable", "-cp", classpathFiles.joinToString(separator = separator),
            "-d", "bin", *sources
        )
    }
}
```
We just need to write `forEachLibrary`, but that is just a Kotlin exercise...

---

## Micro exercise in Kotlin

...not particularly difficult to solve:
1. It's just something we need to do for each library
```kotlin
// In the context of a DependencyHandlerScope
fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) {
    findLibraries().forEach { // For each library (function to be written)
        todo(it) // this.todo(it) -> invoke todo on this passing the library
    }
}
```
2. `findLibraries()` is similar to `findSources()`, let's refactor:
```kotlin
fun findSources() = findFilesIn("src").withExtension("java") // OK now we need findFiles()
fun findLibraries() = findFilesIn("lib").withExtension("jar") // And we also need a way to invoke withExtension
```
3. Let's use an intermediate class representing a search on a folder:
```kotlin
fun findFilesIn(directory: String) = FinderInFolder(directory)
data class FinderInFolder(val directory: String) {
    fun withExtension(extension: String): Array<String> = TODO()
}
// Now it compiles! We just need to write the actual method, but that's easy
```
---

## Micro exercise in Kotlin

Complete solution:
```kotlin
data class FinderInFolder(val directory: String) {
    fun withExtension(extension: String): Array<String> = projectDir
        .listFiles { it: File -> it.isDirectory && it.name == directory }
        ?.firstOrNull()
        ?.walk()
        ?.filter { it.extension == extension }
        ?.map { it.absolutePath }
        ?.toList()
        ?.toTypedArray()
        ?: emptyArray()
}
fun findFilesIn(directory: String) = FinderInFolder(directory)
fun findSources() = findFilesIn("src").withExtension("java")
fun findLibraries() = findFilesIn("lib").withExtension("jar")
fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) {
    findLibraries().forEach { todo(it) }
}

```

---

## Gradle: task dependencies

Next step: we can compile, why not executing the program as well?

1. Let's define a `runtimeClasspath` configuration
    * "inherits" from `compileClasspath`
    * includes the output folder
    * In general we may need stuff at runtime that we don't need at compile time
        * E.g. stuff loaded via reflection
```kotlin
val runtimeClasspath by configurations.creating {
    extendsFrom(compileClasspath) // Built-in machinery to say that one configuration is another "plus stuff"
}
dependencies {
    ...
    runtimeClasspath(files("$buildDir/bin"))
}
```
2. Let's write the task
```kotlin
tasks.register<Exec>("runJava") {
    val classpathFiles = runtimeClasspath.resolve()
    val mainClass = "PrintException" // Horribly hardcoded, we must do something
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(javaExecutable, "-cp", classpathFiles.joinToString(separator = separator), mainClass)
}
```

---

## Gradle: task dependencies

Let's run it!

```bash
❯ gradle runJava

> Task :runJava FAILED
Error: Could not find or load main class PrintException
Caused by: java.lang.ClassNotFoundException: PrintException

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':runJava'.
> Process 'command '/usr/lib/jvm/java-11-openjdk/bin/java'' finished with non-zero exit value 1
```

$\Rightarrow$ The code was not compiled!
* We need `runJava` to run after `compileJava`
* One task depends on another!

---

## Gradle: task dependencies

```kotlin
// Let's get a reference to the task
val compileJava = tasks.register<Exec>("compileJava") {
    ...
}
tasks.register<Exec>("runJava") {
    ...
    dependsOn(compileJava) // runJava can run only if compileJava has been run
}

```

Run now:

```bash
TERM=dumb gradle runJava
> Task :compileJava

> Task :runJava
java.lang.IllegalStateException
        at PrintException.main(PrintException.java:5)

Just printed a stacktrace, I'm fine actually

BUILD SUCCESSFUL in 775ms
2 actionable tasks: 2 executed
```

---

## Build automation: dependencies everywhere

Dependencies permeate the world of build automation.
* At the *"task"* level
    * Compile dependencies
    * Runtime dependencies
* At the *"build"* level
    * Phases of the lifecycle (*configurations* in Gradle) depend on other phases
    * *Tasks* depend on other tasks

$\Rightarrow$ *at the **global** level as well!*

*no guarantee*  that automation written with some tool at version `X`, will work at version `Y`!

---

## The Gradle wrapper

* A global dependency on the build tool is **hard to capture**
* Often, it becomes a *prerequisite expressed in natural language*
    * e.g., "you need Maven 3.6.1 to build this software"
* *Critical* issues when different pieces of the same system depend on different build tool versions

Gradle proposes a (partial) solution with the so-called *Gradle wrapper*

* *A minimal program* that simply downloads the version of gradle written in a configuration file
* *Generable* with the built-in task `wrapper`
    * `gradle wrapper --gradle-version=<VERSION>`
* Prepares scripts for bash and cmd to run Gradle at the specified version
    * `gradlew`
    * `gradlew.bat`

The Gradle wrapper is *the correct way* to use gradle, and we'll be using it from now on.

---

## Cleaning up

A source of failures when building is *dirty status*.
<br>
For istance, in the previous example, before we introduced a dependency between tasks:

* clean execution fails
* execution after a *manual* execution of compile works
    * **false positive!**

We need a way to start clean.
<br>
This usually involves cleaning up the build directory - not so hard in our example

```kotlin
tasks.register("clean") { // A generic task is fine
    if (!buildDir.deleteRecursively()) {
        throw IllegalStateException("Cannot delete $buildDir")
    }
}
```

---

## Build hierarchies

Sometimes projects are *modular*
<br>
Where a module is a sub-project with a clear identity, possibly reusable elsewhere

Examples:
* A smartphone application with:
    * A common library
    * A software that uses such library for the actual app
* Bluetooth control software comprising:
    * Platform-specific drivers
    * A platform-agnostic bluetooth API and service
    * A CLI interface to the library
    * A Graphical interface

Modular software *simplifies maintenance* and *improves understandability*
<br>
Modules may **depend** on other modules
<br>
Some build tasks of some module may require build tasks *of other modules* to be complete before execution

---

## Hierarchial project

Let us split our project into two components:
* A base library
* A stand-alone application using the library

We need to reorganize the build logic to something similar to
```
hierarchial-project
|__:library
\__:app
```

Desiderata:
* We can compile any of the two projects from the root
* We can run the app from the root
* Calling a run of the app implies a compilation of the library
* We can clean both projects

---

## Authoring subprojects in Gradle

Gradle (as many other build automators)
offers built-in support for *hierarchial projects*.
<br>
Gradle is limited to *two levels*, other products such as Maven have no limitation

Subprojects are listed in a `settings.gradle.kts` file
<br>
Incidentally, it's the same place where the project name can be specified

Subprojects *must have their own* `build.gradle.kts`
<br>
They can also have their own `settings.gradle.kts`, e.g. for selecting a name different than their folder

---

## Authoring subprojects in Gradle

1. Create a settings.gradle.kts and declare your modules:

```gradle
rootProject.name = "project-with-hierarchy"

include(":library") // There must be a folder named "library"
include(":app") // There must be a folder named "app"
```

2. In the root project, configure the part common to **all** projects in a `allprojects` block
    * e.g., in our case, the `clean` task should be available for each project

```gradle
allprojects {
    tasks.register("clean") { // A generic task is fine
        if (!buildDir.deleteRecursively()) {
            throw IllegalStateException("Cannot delete $buildDir")
        }
    }
}
```

---

## Authoring subprojects in Gradle

3. Put the part shared by *solely the sub-projects* into a `subprojects` block
    * e.g., in our case, the `compileJava` task and the related utilities

```kotlin
subprojects {
    // This must be there, as projectDir must refer to the *current* project
    data class FinderInFolder(val directory: String) ...
    fun findFilesIn(directory: String) = FinderInFolder(directory)
    fun findSources() = findFilesIn("src").withExtension("java")
    fun findLibraries() = findFilesIn("lib").withExtension("jar")
    fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) ...
    val compileClasspath by configurations.creating
    val runtimeClasspath by configurations.creating { extendsFrom(compileClasspath) }
    dependencies { ... }
    tasks.register<Exec>("compileJava") { ... }
}
```

---

## Authoring subprojects in Gradle

4. In each subproject's `build.gradle.kts`, add further customization as necessary
    * e.g., in our case, the `runJava` task can live in the `:app` subroject
5. Connect configurations to each other using dependencies
    * in `app`'s `build.gradle.kts`, for instance:
```gradle
dependencies {
    compileClasspath(project(":library")) { // My compileClasspath configuration depends on project library
        targetConfiguration = "runtimeClasspath" // Specifically, from its runtime
    }
}
```
6. Declare inter-subproject task dependencies
    * Tasks may fail if ran out of order!
    * Compiling `app` requires `library` to be compiled!
    * inside `app`'s `build.gradle.kts`:
```gradle
tasks.compileJava { dependsOn(project(":library").tasks.compileJava) }
```

*Note*: `library`'s `build.gradle.kts` is actually empty at the end of the process

---

## Mixed imperativity and declarativity

At the moment, we have part of the project that's declarative, and part that's imperative:

* **Declarative**
    * configurations and their relationships
    * dependencies
    * task dependencies
    * project hierarchy definition
    * some parts of the task configuration
* **Imperative**
    * Operations on the file system
    * some of the actual task logics
    * resolution of configurations

The *declarative* part is the one *for which we had a built-in API for*!

---

## Unavoidability of imperativity
### (and its isolation)

The base mechanism at work here is *hiding imperativity under a clean, declarative API*.

Also *"purely declarative"* build systems, such as Maven, which are driven with markup files, *hide* their imperativity behind a curtain (in the case of Maven, plugins that are configured in the `pom.xml`, but implemented elsewhere).

*Usability*, *understandability*, and, ultimately, *maintability*, get increased when:
* *Imperativity* gets *hidden* under the hood
* Most (if not all) the operations can be *configured* rather than *written*
* Configuration can be *minimal for common tasks*
    * **Convention over configuration**, we'll get back to this
* Users can *resort to imperativity* in case of need

---

## Isolation of imperativity
### Task type definition

Let's begin our operation of isolation of imperativity by refactoring our hierarchy of operations.

* We have a number of "Java-related" tasks.
* All of them have a classpath
* One has an output directory
* One has a "main class"

{{< gravizo "We can use Kotlin to extend the base Gradle API and impleent our own stuff" >}}
@startuml
interface Exec
interface JavaTask extends Exec {
    classpath : Set<File>
    javaExecutable : File
}
interface JavaCompile extends JavaTask {
    outputDirectory : File
}
interface JavaExecute extends JavaTask {
    mainClass : String
}
@enduml
{{< /gravizo >}}

---

## Creating a new Task type in Gradle

Gradle supports the definition of new task types:
* New tasks *must implement* the `Task` interface
    * They *usually inherit* from `DefaultTask`
* They must be *extensible* (`open`)
    * At runtime, Gradle creates subclasses on the fly
* They must have *a parameterless constructor annotated* with `@Inject`
    * Costruction of tasks happens via dependency injection
* A public method can be marked as `@TaskAction`, and will get invoked to execute the task

```kotlin
open class Clean @Inject constructor() : DefaultTask() {
    @TaskAction
    fun clean() {
        if (!project.buildDir.deleteRecursively()) {
            throw IllegalStateException("Cannot delete ${project.buildDir}")
        }
    }
}
```

---

### Input, output, caching, and continuous build mode

In general, it is a good practice (that will become mandatory in future gradle releases)
to *annotate every public property* of a task with a marker annotation that determines whether it is an *input* or an *output*.
* `@Input`, `@InputFile`, `@InputFiles`, `@InputDirectory`, `@InputDirectories`
* `@OutputFile`, `@OutputFiles`, `@OutputDirectory`, `@OutputDirectories`

#### Why?

1. **Performance**
    * Gradle caches intermediate build results, using input and output markers to undersand whether or not some task is *up to date*
    * This allows for *much* faster builds while working on large projects
        * Time to build completion can decrease from tens on minutes to seconds!
2. **Continuous build**
    * When launched with the `-t` option, Gradle re-runs the requested tasks every time *something changes*
    * (In/Out)put markers are used to understand *what* to actually run again

---

## Isolation of imperativity
### Idea

In our main `build.gradle.kts`

```gradle
// Imperative part
abstract class JavaTask(javaExecutable: File = Jvm.current().javaExecutable) : Exec() { ... }sub
open class CompileJava @javax.inject.Inject constructor() : JavaTask(Jvm.current().javacExecutable) { ... }
open class RunJava @javax.inject.Inject constructor() : JavaTask() { ... }

// Declarative part
allprojects { tasks.register<Clean>("clean") }
subprojects {
    val compileClasspath by configurations.creating
    val runtimeClasspath by configurations.creating { extendsFrom(compileClasspath) }
    dependencies {
        findLibraries().forEach { compileClasspath(files(it)) }
        runtimeClasspath(files("$buildDir/bin"))
    }
    tasks.register<CompileJava>("compileJava")
}
```
In subprojects, only have the declarative part

Unfortunately, *subprojects have no access to the root's defined types*
* Fragile access only via reflection
* Enormous **code duplication**

---

## Isolation of imperativity
### Project-wise API extension (plugin)

Gradle provides the functionality we need (project-global type definitions) using a special `buildSrc` folder
* Requires a Gradle configuration file
    * What it actually does will be clearer in future
* Requires a peculiar directory structure
```bash
├── build.gradle.kts
├── buildSrc
│   ├── build.gradle.kts
│   └── src
│       └── main
│           └── kotlin
│               └── OurImperativeCode.kt
└── settings.gradle.kts
```
* Allows imperative code to get *isolated* and *shared* among subprojects!

---

## Isolation of imperativity
### Project-wise API extension (plugin)

`buildSrc/build.gradle.kts` (clearer in future)

```kotlin
plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}
```

`buildSrc/sr/main/kotlin/JavaOperations.kt` (excerpt, full code in the repo)

```kotlin
open class Clean @Inject constructor() : DefaultTask() { ... }
abstract class JavaTask(javaExecutable: File = Jvm.current().javaExecutable) : Exec() { ... }
open class CompileJava @javax.inject.Inject constructor() : JavaTask(Jvm.current().javacExecutable) {
    @OutputDirectory // Marks this property as an output
    var outputFolder: String = "${project.buildDir}/bin/"
    ...
}
open class RunJava @javax.inject.Inject constructor() : JavaTask() {
    @Input // Marks this property as an Input
    var mainClass: String = "Main"
    ...
}
```

---

## Isolation of imperativity
### Project-wise API extension (plugin)

Our Project's `build.gradle.kts` (full):

```kotlin
allprojects {
    tasks.register<Clean>("clean")
}
subprojects {
    val compileClasspath by configurations.creating
    val runtimeClasspath by configurations.creating { extendsFrom(compileClasspath) }
    dependencies {
        findLibraries().forEach { compileClasspath(files(it)) }
        runtimeClasspath(files("$buildDir/bin"))
    }
    tasks.register<CompileJava>("compileJava")
}

```

**Purely declarative, yay!**

---

## Isolation of imperativity
### Project-wise API extension (plugin)

Our `app` suproject's `build.gradle.kts` (full):

```gradle
dependencies {
    compileClasspath(project(":library")) { targetConfiguration = "runtimeClasspath" }
}
tasks.compileJava {
    dependsOn(project(":library").tasks.compileJava)
    fromConfiguration(configurations.compileClasspath.get())
}
tasks.register<RunJava>("runJava") {
    fromConfiguration(configurations.runtimeClasspath.get())
    mainClass = "PrintException"
}
```

**Purely declarative, yay!**

---

## Divide, conquer, encapsulate, adorn

General approach to a *new* build automation problem:

**Divide**
* Identify the *base steps*, they could become your task
    * Or any concept your build system exposes to model an atomic operation

**Conquer**
* Clearly express the *dependencies* among them
    * Build a *pipeline*
* Implement them
* Provide a clean API

**Encapsulate**
* Confine imperative logic, make it an *implementation detail*

**Adorn**
* Provide expressive, easy, immedate access to the API via *DSL*!

*Not very different than what's usually done in (good) software development*

---

* Declarativity via DSLs
concept of plugin
the kotlin plugin
* jvm variant
* with its configurations
our "plugin for java"
testing a plugin (kotest + Gradle api + classpath trick)
some existing plugins
* detekt
* ktlint
* jacoco
* refreshVersions
