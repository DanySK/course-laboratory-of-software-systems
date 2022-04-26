 
+++

title = "DevOps in a nutshell"
description = "Make things work, keep them working, move fast"
outputs = ["Reveal"]
layout = "bundle"

[reveal_hugo]
transition = "slide"
transition_speed = "fast"
custom_theme = "custom-theme.scss"
custom_theme_compile = true

[reveal_hugo.custom_theme_options]
targetPath = "css/custom-theme.css"
enableSourceMap = true

+++

# Development and programming paradigms

## Continuous Integration

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

{{% slide content="devops.intro" %}}

---

# Worflow organization

* Design a workflow that **fits the team**
  * Delicate balance between *chaotic organization* and *excessive bureaucracy*
  * The larger the team, the more control is usually needed more control is usually needed
  * Sparse teams and localized teams have different requisites
* Project progress is marked also by progress **version control system** 
* Select a *branching model* model that matches the organization
  * possibly, a *forking model* for certain kinds of organization
* **Standardize** the development environment
  * Which newlines on which kind of files?
  * What should be in tracking?

---

# Version Control Systems

A system responsible for managing changes to the project files

* Records the history
* Enables roll-backs
* Promotes collaborative workflows
* **Distributed** vs. *Centralized*
  * **Every developer has a whole copy of the entire history**
  * *There exist a central point of synchronization*
* Also called Source Content Management (SCM)

---

## Centralized Version Control Systems

{{< image src="2021-04-14-centralized-vcs.svg" max-h="55">}}

---

## Decentralized VCS

{{< image src="2021-04-14-decentralized-vcs.svg" max-h="55">}}

---

## Real-world DVCS

{{< image src="2021-04-14-dvcs-sink.svg" max-h="55">}}

---

## Reference DVCS: Git

De-facto reference distributed version control system

* *Distributed*
* *Differential tracking*
* Born in *2005* to replace BitKeeper as SCM for the Linux kernel
  * Performance was a major concern
  * Written in C
* Developed by Linus Torvalds
  * Now maintained by Hamano and others
* *Unix-oriented*
  * Tracks Unix file permissions
* Very *fast*
  * At conception, 10 times faster than Mercurial¹, 100 times faster than Bazaar


¹ Less difference now, Facebook vastly improved Mercurial

---

## Funny historical introduction

{{< youtube id="4XpnKHJAok8" autoplay="true" title="Linus Torvalds introduces Git at Google">}}

---

## Distributed version control with git: a recap

You should already be acquainted with the tool, this is an express guide/recap

Sub-commands and files are in `monospace`, concepts in *italic*

---

## Distributed version control with git: a recap

### *Repository*

* Collection of metadata containing the history of the project
* Reified in the `.git` folder
* The location of the `.git` folder marks the root of the repository

---

## Distributed version control with git: a recap

### *Working tree*
* The directory associated with the root of your repository and its contents
* Contains the files tracked by git
* Not all files in the working tree are tracked

---

## Distributed version control with git: a recap

### `git init`
* Initializes a new repository from the current directory

---

## Distributed version control with git: a recap

### *Stage*

The **changes** that will be saved next

{{< image src="staging.png" >}}


---

## Distributed version control with git: a recap

### `git add <files>`
* Moves the current **changes** in `<files>` into the *stage*

### `git reset <files>`
* Removes the current **changes** to `<files>` from the *stage*

---

## Distributed version control with git: a recap

### `.gitignore`
* A file listing the pathspecs that git should **ignore** even if added
* Adding is still possible via `--force`

### `.gitattributes`
* Defines attributes for path names
* Can enforce the correct line ending
* Can provide ways to diff binary file (by conversion to text, needs configuration)
* Example: 
```.gitattributes
* text=auto eol=lf
*.[cC][mM][dD] text eol=crlf
*.[bB][aA][tT] text eol=crlf
*.[pP][sS]1 text eol=crlf
```
---

## Distributed version control with git: a recap

### `git commit`
* Create a new *changeset* with the contents of the stage
* Requires a **message**
* Using appropriate messages is **extremely important**

### *HEAD*
* Pointer to the current commit

---

## Distributed version control with git: a recap

### `git tag -a`
* Associates a **symbolic name** to a commit
* Often used for **versioning**
* Advanced uses in **{{< course_name >}}**

---

## Distributed version control with git: a recap

### *Branch*
* A **named** development line
<br>
{{< image src="branches.svg" >}}

### `master`
* Default branch name
  * legacy of BitKeeper
  * Modern versions of git let user select
  * Some prefer `main`

---

## Distributed version control with git: a recap

### `git checkout`
* Moves *HEAD* across commits
* Used to switch *branches*
* Can be used to create new *branches* (with `-b`)

### *detached HEAD*
* Special mode in which commits are not saved
* The system goes in *detached HEAD* when *HEAD* is not the last commit on a *branch*

---

## Distributed version control with git: a recap

### `git branch`
* Controls creation, visualization, and deletion (`-d`) of branches

### `git merge`
* **Unifies** a target branch with the current branch
* Creates a *merge commit*
* The merging algorithm is configurable
* **Conflicts** must be solved manually

### *fast-forward*
* A special merge mode applicable when a branch is *behind* another
* The target branch is *updated without a commit*
* Active by default, can be disabled (`--no-ff`)

---

## Distributed version control with git: a recap

### *Remote*
* (possibly remote) locations hosting copies of branches of this repository exist

### `git remote`
* Configures the *remotes*

### *upstream*
* The default *remote* for network operations.

---

## Distributed version control with git: a recap

### `git clone`
* Copies a repository from a possibly remote location.
* Alternative to `init`
* Automatically sets the local branch *upstream* to the cloned location.

---

## Distributed version control with git: a recap

### `git fetch <remote>`
* **Updates** the state of `<remote>`
* If remote is omitted, updates the state of the branch *upstream*'s *remote*

### `git pull <remote> <branch>`
* **Shortcut** for `git fetch && git merge FETCH_HEAD`

### `git push <remote> <branch>`
* Sends local changes *remote* *branch*
* Requires branches to **share a root**
* If remote and branch are omitted, updates are sent to the *upstream*

---

## Advanced git

Discussed in **{{< course_name >}}**

* Lightweight vs. annotated tagging
* Stashing
* Rebasing
* Rebased pulling
* Squashing
* Cherry picking
* Submodules
* Hooks

---

## Best practices

* The **CLI** is your *truth*
  * Beware of the GUIs
* Prepare an *ignore list* early
  * And *maintain it*
  * And maybe prepare it manually and don't copy/paste it
* When you have untracked files, *decide whether you want to track them or ignore them*
* Be very careful with *what* you track
* Prepare an *attribute file*

---

## GitHub

* Hosting for git repositories
* Free for open source
* Some limitations for closed source
* Academic accounts
* De-facto standard for open source projects
* One static website per-project, per-user, and per-organization
  * first-class support for Jellyll (a Ruby framework for static website generation)

---

# DVCS: Workflows

> with great power comes great responsibility

and also

> power is nothing without control

Elements to consider:
* How *large* is the team?
* How *complex* is the project?
* Do team members work *together* (in spacetime)?
* Do team members *trust* each other?

---

## Trunk-based development(-like)

Single branch, shared truth repository, frequent merges
<br>
{{< image src="2021-04-14-dvcs-sink.svg" max-h="55">}}

* *Small* teams, *low-complexity* projects, *colocated* teams, *high* trust
* Typical of small company projects

---

## Git flow (classic)

Multiple branches, shared truth repository
<br>
{{< image src="2021-04-14-dvcs-flow-sink.svg" max-h="55">}}

* *Large* teams, *high-complexity* projects, *preferably colocated* teams, *high* trust
* Typical of large company projects

---

## Git flow structure

{{< image src="2021-04-13-gitflow.svg" max-h="55">}}

---

## Forks versus branches

* In Git, separate *development lines* are separate *branches*
* However, everyone has a *copy* of the *same repository*
* Git *hosting services* can *identify copies* of the same project belonging to different users

These copies are called **forks**

* Branches on one fork can be requested to be merged on another fork
  * With **merge request** (also called **pull request**, depending on the host)
* Pull requests enable easier code review
  * Necessary when the developer *does not trust* the contributor
  * But very useful anyway
* Working with pull requests is **not part of git** and *requires host support*
  * GitHub, GitLab, and Bitbucket all support pull requests

---

## Single branch, multiple forks

* Single branch, multiple independent repository copies

<p>
{{< image src="2021-04-14-dvcs-fork.svg" max-h="50">}}
</p>

* *Unknown* team size, *low-complexity* projects, *sparse* teams, *low* trust
* Typical of small open-source projects

---

## Git flow over multiple forks

* Single branch, multiple independent repository copies

<p>
{{< image src="2021-04-14-dvcs-flow-fork.svg" max-h="50">}}
</p>

* *Unknown* team size, *high-complexity* projects, *sparse* teams, *low* trust
* Typical of complex open-source projects

---

# Documenting projects using GitHub

Documentation of a project is **part of the project**

* **Documentation** must stay in the same repository of the project
* However, it should be *accessible to non-developers*

Meet GitHub Pages

* GitHub provides an *automated* way to publish *webpages from **Markdown*** text
* Markdown is a *human readable markup language*, [easy to learn](https://learnxinyminutes.com/docs/markdown/)
  * These slides are written in Markdown
  * (generation is a bit richer, but just to make the point)
* Supports *Jekyll* (a Ruby framework for static website generation) out of the box
  * We will not discuss it today
  * But maybe in LSS...

---

## Setting up a GitHub Pages website

Two possibilities:
1. Select a branch to host the pages
    * Create an orphan branch with `git checkout --orphan <branchname>`
    * Write your website, one Markdown per page
    * Push the new branch
1. Use a `docs/` folder in a root of some branch
    * Could be `master` or any other branch

---

## Setting up a GitHub Pages website

Once done, enable GitHub pages on the repository settings:

{{< image src="gh-pages.png" >}}

---

## GitHub Pages URLs

* Repository web-pages are available at `https://<username>.github.io/<reponame>`
* User web-pages are available at `https://<username>.github.io/`
  * They are generated from a repository named `<username>.github.io`
* Organization web-pages are available at `https://<organization>.github.io/`
  * They are generated from a repository named `<organization>.github.io`

---

{{% slide content="build-automation.intro" %}}

---

# Dependency management

* Any software **depends** on other software
    * The *runtime environment* (think of the Java Virtual Machine)
    * The *core libraries* (e.g., `java.*`, or `scala.*`, or `kotlin.*`)
    * Possibly *third-party libraries* (e.g., Google Guava, Apache Commons...)
    * Possibly *external resources* (e.g., images, sounds, translation files...)
* Normally, this *software depends on other software*
    * That *depends on other software*
        * That *depends on other software*
            * That *depends on other software*, and so on...
* A normal applications has a **tree** of dependencies

---

## Simple example: rate a movie

Example **requirements**:
* *Visit* IMDb.org and rottentomatoes (Movie databases)
* *Search* for a title of a movie or series (e.g., Breaking Bad)
* *Print* all available information (director, year, ratings, ...)

---

{{< mentimeter "66b4eb67105e9c1fa3916b9344d6b8ad/a62cf5c48ed8" >}}

---

## Actual result

{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="16" to="20" %}}
{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="24" to="24" %}}
{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="32" to="46" %}}
{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="53" to="53" %}}
{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="66" %}}

---

## The trick: using a few libraries

* **Jooq Jooλ**
    * Unchecked lambda functions
* A **library for OMDB**
    * Queries OMDB given a valid API key, hiding HTTP, communication, and parsing
* **SLF4J + Logback**
    * Logging for Java

---

## Actual dependency tree

```
+--- com.omertron:API-OMDB:1.5
|    +--- commons-codec:commons-codec:1.10
|    +--- org.apache.commons:commons-lang3:3.4
|    +--- com.fasterxml.jackson.core:jackson-core:2.8.7
|    +--- com.fasterxml.jackson.core:jackson-annotations:2.8.7
|    +--- com.fasterxml.jackson.core:jackson-databind:2.8.7
|    |    +--- com.fasterxml.jackson.core:jackson-annotations:2.8.0 -> 2.8.7
|    |    \--- com.fasterxml.jackson.core:jackson-core:2.8.7
|    +--- org.slf4j:slf4j-api:1.7.24 -> 1.7.36
|    \--- org.yamj:api-common:2.1
|         +--- org.apache.httpcomponents:httpclient:4.5.3
|         |    +--- org.apache.httpcomponents:httpcore:4.4.6
|         |    +--- commons-logging:commons-logging:1.2
|         |    \--- commons-codec:commons-codec:1.9 -> 1.10
|         \--- org.slf4j:slf4j-api:1.7.24 -> 1.7.36
+--- org.jooq:jool:0.9.14
+--- org.slf4j:slf4j-api:1.7.36
\--- ch.qos.logback:logback-classic:1.2.11
     +--- ch.qos.logback:logback-core:1.2.11
     \--- org.slf4j:slf4j-api:1.7.32 -> 1.7.36
```

* few *direct* dependencies
* many *transitive* dependencies

In large projects, *transitive* dependencies dominate

---

## Towards a **dependency hell**

* It's common for non-toy projects to get past 50 dependencies
* *Searching*, *downloading* and *verifying compatibility* by hand is unbearable
* **Version conflicts** arise soon
  * one of your direct dependencies uses library A at version 1
  * another uses library A at version 2
  * $\Rightarrow$  *transitive dependency conflict* on A
* **Upgrading** by hand requires, *time*, *effort* and *tons of testing*

---

## Dealing with dependencies

**Source import**

Duplication, more library code than business code, updates almost impossible, inconsistencies, unmaintainable

**Binary import**

Hard to update, [toxic for the VCS](https://bitbucket.org/danysk/exploded-repository-example)

**Desiderata**

* *Declarative* specification of libraries and versions
* *Automatic retrieval*
* Automatic *resolution of transitive dependencies*
* Dependency **scopes**
  * You may need *compile-only*, *test-only*, and *runtime-only* dependencies
* Customizable software *sources*

---

# Gradle

A paradigmatic example of a hybrid automator:
* Written mostly in Java
* with an outer Groovy DSL
* ...and, more recently, a Kotlin DSL

### Our approach to Gradle

* We are going to learn *a bit of how to use* Gradle for *simple JVM projects*
    * Mostly by example
* We are **not** going to *learn Gradle*
    * Again, this is done in LSS for those interested

---

## Gradle: main concepts

* **Project** -- A collection of files composing the software
    * The *root* project can contain *subprojects*
* **Build file** -- A special file, with the build information
    * situated in the root directory of a project
    * instructs Gradle on the organization of the project
* **Dependency** -- A resource required by some operation.
    * May have dependencies itself
    * Dependencies of dependencies are called *transitive* dependencies
* **Configuration** -- A group of dependencies with *three roles*:
    1. *Declare* dependencies
    2. *Resolve* dependency declarations to actual artifacts/resources
    2. *Present* the dependencies to consumers in a suitable format
* **Task** -- An atomic operation on the project, which can:
  * have input and output files
  * depend on other tasks (can be executed only if those are completed)

---

## Gradle: under the hood

* The Gradle build script is *a valid Kotlin script* (using the Gradle API)
* Anything that has not a valid *Kotlin syntax* is not a valid Gradle build script
* *Kotlin* and *Groovy* picked as they allow *easy DSL creation*
* The *feeling* is to just have *to configure* an existing software
    * *Declarative*, much like Maven plugins
* When needed, it is easy to configure *custom behaviour*
    * fiddle with internals
    * write in functional or imperative fashion

---

## Gradle: minimal java build

* By default, Gradle inherits the maven convention for source organization:

```ruby
+-- src # All sources
    +-- main # One folder per "source set", all code in a source set shares dependencies
    |   +-- java # One folder per language
    |   +-- kotlin
    |   +-- resources # Resources go here
    |   \-- scala
    \-- test # Test sources are separated, different dependencies        
        +-- java # Same structure as main
        +-- kotlin
        +-- resources # Resources go here
        \-- scala
```

---

## Gradle: minimal java build

`src/main/java/HelloWorld.java`

{{% code path="PPS-ci-examples/00-minimal/src/main/java/HelloWorld.java" %}}

`build.gradle.kts`

{{% code path="PPS-ci-examples/00-minimal/build.gradle.kts" %}}

Yes, it's a one-liner

---

## Gradle: minimal example execution

* The `gradle` command accepts the names of *tasks*  to execute as parameters
* The `java` plugin introduces several tasks:
  * `compile<source set><language name>` (e.g., `compileTestJava`): compiles a specific source set
  * `compile<language name>`: compiles all source sets of a language
  * `build`: runs all compilation tasks
  * `tasks`: displays available tasks
    * usually used as `tasks --all` to show also *uncategorized* tasks

---

## Dependency management in Gradle

`repositories`
* Where to retrieve software from
    * suggested ones are `mavenCentral()` and `google()`
    * ~~`jcenter()`~~ was common, but it has been sunsetted (May 2021)

---

## Dependency management in Gradle

`dependencies`
* Separated by **scope**
    * Scopes define *when* a dependency should be available
        * `api` (libraries only): abstractions are exposed publicly and clients are expected to use them
        * `implementation`: used internally, clients should not care
        * `testImplementation`: used to compile and run tests
        * (`test`)`compileOnly`: only available when compiling (typically used for annotations)
        * (`test`)`runtimeOnly`: only required at runtime (typically used when components are loaded reflectively)
    * Scopes map to **configurations**
* Specified as `<group>:<artifact>:<version>`
    * `+` is a special marker for "latest"
    * Maven-style ranges supported (e.g., (1.2, 1.4])
* Transitive dependencies are resolved automatically (if available in some repository)

---

## The build system as dependency


* *no guarantee*  that automation written with some tool at version `X`, will work at version `Y`!
* $\Rightarrow$ **The build system is itself a dependency**

* A global dependency on the build tool is **hard to capture**
* Often, it becomes a *prerequisite expressed in natural language*
    * e.g., "you need Maven 3.6.1 to build this software"
* *Critical* issues when different pieces of the same system depend on different build tool versions

---

## The Gradle wrapper

Gradle proposes a (partial) solution with the so-called *Gradle wrapper*

* *A minimal program* that simply downloads the version of gradle written in a configuration file
* *Generable* with the built-in task `wrapper`
    * `gradle wrapper --gradle-version=<VERSION>`
* Prepares scripts for bash and cmd to run Gradle at the specified version
    * `gradlew`
    * `gradlew.bat`

The Gradle wrapper is *__the__ correct way* to use gradle, and we'll be using it from now on.


---

## Gradle: our toy example

`src/main/java/it/unibo/sampleapp/RateAMovie.java`

{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="16" to="45" %}}
...
{{% code path="PPS-ci-examples/01-dependencies/src/main/java/it/unibo/sampleapp/RateAMovie.java" from="67" %}}

---

## Gradle: our toy example

`build.gradle.kts`

{{% code path="PPS-ci-examples/01-dependencies/build.gradle.kts" %}}

`settings.gradle.kts`

{{% code path="PPS-ci-examples/01-dependencies/settings.gradle.kts" %}}

---

## Gradle: toy example execution

* The `application` plugin introduces a `run` task:
  * Depends on `build`
  * runs the specified main class passing the `runtimeClasspath` to the `-cp` option of `java`
  * `./gradlew` run

**Note**: exectution requires a valid TheTVDB API Key in a plain text file `src/main/resources/APIKey`

---

## Gradle: multi-language projects

`src/main/groovy/HelloGroovy.groovy`

{{% code path="PPS-ci-examples/02-multilang/src/main/groovy/HelloGroovy.groovy" %}}

`src/main/java/HelloWorld.java`

{{% code path="PPS-ci-examples/02-multilang/src/main/java/HelloWorld.java" %}}

---

## Gradle: multi-language projects

`src/main/kotlin/HelloKt.kt`

{{% code path="PPS-ci-examples/02-multilang/src/main/kotlin/HelloKt.kt" %}}

`src/main/scala/HelloScala.scala`

{{% code path="PPS-ci-examples/02-multilang/src/main/scala/HelloScala.scala" %}}

---

## Gradle: multi-language projects

`build.gradle.kts`

{{% code path="PPS-ci-examples/02-multilang/build.gradle.kts" %}}

---

# Quality Assurance

*"It works"* **is _not_ good enough**

(besides, the very notion of "it works" is rather debatable)

* Software quality should be *continuously assessed*
* The assessment should *automatic* whenever possible
* **QA should be integrated in the build system!**
  * It is fine to *fail the build* if quality criteria are not met

---

## Quality Assurance: levels

* **Static**: the project is analyzed for defects without executing it or its components
  * *Style* and *coherence*
  * *Flawed programming* patterns
    * Known bugs, suboptimal programming, security flaws, ...
  * Violations of the *DRY* principle
* **Dynamic**: the project (or some parts) are executed
  * *Testing*
    * Multifaceted issue
    * $\Rightarrow$ Plenty of detail in the upcoming lectures

---

## Quality Assurance: style and coherence

Automated checkers are also called *linters*, often provide an auto-formatting tool

**Idiomatic** and **standardized** code:
* reduces *complexity*
* improves *understandandability*
* prevents *style-changing commits* with *unintelligible diffs*
* lowers the *maintenance* burden and related *costs*
* simplifies *code reviews*

In *Java*:
[Checkstyle](https://checkstyle.sourceforge.io/),
[PMD](https://pmd.github.io/)

In *Kotlin*:
[IDEA Inspection](https://github.com/JetBrains/inspection-plugin),
[Ktlint](https://ktlint.github.io/)

In *Scala*:
[Scalafmt](https://scalameta.org/scalafmt/),
[Scalastyle](http://www.scalastyle.org/rules-1.0.0.html)

---

## Quality Assurance: flawed programming patterns

Identification and reporting of *patterns* known to be *problematic*

* Early-interception of *potential bugs*
* Enforce *good programming principles*
* Improves *performance*
* Reduces *complexity*
* Reduces *maintenance cost*

In *Java*:
[PMD](https://pmd.github.io/),
[SpotBugs](https://spotbugs.github.io/)

In *Kotlin*:
[Detekt](https://detekt.github.io/detekt/)
[IDEA Inspection](https://github.com/JetBrains/inspection-plugin)

In *Scala*:
[Scalafix](htthttps://scalacenter.github.io/scalafix/),
[Wartremover](http://www.wartremover.org/)

---

## Quality Assurance: violations of the DRY principle

Code *replicated* rather than *reused*

* improves *understandandability*
* Reduces *maintenance cost*
* simplifies *code reviews*

General advice: **never copy/paste** your code
* If you need to copy something, you probably need to *refactor* something

Multi-language tool: [Copy/Paste Detector (CPD)](https://pmd.github.io/latest/pmd_userdocs_cpd.html) (part of PMD)

---

## Quality Assurance: testing and coverage

**Automated** software verification
* Unit level
* Integration testing
* End-to-end testing

Extension of testing can be evaluated via **coverage**.
* Coverage tells you *how much code is **untested**, not how much is tested*

Several frameworks, recommended ones:

* Testing for *all JVM languages*: [Junit/Jupiter (JUnit 5)](https://junit.org/junit5/docs/current/user-guide/)
* Testing for *Kotlin*: [Kotest](https://kotest.io/)
* Testing for *Scala*: [Scalatest](https://www.scalatest.org/)
* Coverage for all JVM languages: [JaCoCo](https://www.eclemma.org/jacoco/)
* Coverage for Scala: [Scoverage](http://scoverage.org/)

---

## Quality Assurance: JUnit + Gradle

`src/main/scala/it/unibo/test/Test.scala`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="04-junit/src/main/scala/it/unibo/test/Test.scala">}}

---

## Quality Assurance: JUnit + Gradle

`src/test/scala/MyTest.scala`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="04-junit/src/test/scala/MyTest.scala">}}

---

## Quality Assurance: JUnit + Gradle

`build.gradle.kts`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="04-junit/build.gradle.kts">}}

---

## Quality Assurance: Scalatest + Scoverage

Let's switch *testing framework* and enable *coverage*

`src/main/scala/it/unibo/test/Test.scala`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="03-scalatest/src/main/scala/it/unibo/test/Test.scala">}}

---

## Quality Assurance: Scalatest + Scoverage

`src/test/scala/Test.scala`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="03-scalatest/src/test/scala/Test.scala">}}

---

## Quality Assurance: Scalatest + Scoverage

`build.gradle.kts`

{{< github owner="APICe-at-DISI" repo="PPS-ci-examples" path="03-scalatest/build.gradle.kts">}}

---

## Gradle: QA execution

* The `java` plugin (applied by the `scala` plugin under the hood) also introduces:
  * `test`: a task that runs all tests
  * `check`: a task that runs the whole quality assurance suite

---

## Additional checks and reportings

There exist a number of recommended services that provide additional QA and reports.

Non exhaustive list:
* [Codecov.io](https://codecov.io/)
    * Code coverage
    * Supports Jacoco XML reports
    * Nice data reporting system
* [Sonarcloud](https://sonarcloud.io/)
    * Multiple measures, covering reliability, security, maintainability, duplication, complexity...
* [Codacy](https://www.codacy.com/)
    * Automated software QA for several languages
* [Code Factor](https://www.codefactor.io/)
    * Automated software QA

---

{{< slide content="ci.intro" >}}

---

{{< slide content="ci.concepts" >}}

---

## CI in GitHub Actions

* Very *open-source friendly*
* Integrated in GitHub
* *Less mature* than other integrators
  * My personal feeling is that it was built *bottom-up*...
* Some questionable limitations
* A lot of parallelism (20 concurrent jobs, 5 for MacOS X)

---

## CI in GitHub Actions

**step**
* A (*possibly named*) *command* or *sequence of commands*
* Can be executed on the virtual machine or inside a container
* Defines *success* or *failure*
  * Usually, the return value of the last command

**action**
* A *reusable step*
  * DRY in CI $\Rightarrow$ {{< course_name >}}

---

## CI in GitHub Actions

**job**
* A *sequence of steps* that run in parallel
* Executed on a *virtual machine*
* A job is *successful* if all its steps are successful
* Multiple jobs can be spawned at once via *matrix expansion*
  * e.g., spawn one job for each OS and version of the JVM
  * CI Matrix expansion $\Rightarrow$ {{< course_name >}}

**workflow**
* A *named sequence of steps*
* There can be multiple workflows per repository
  * DRY/Reusability in {{< course_name >}}
* Workflows are *isolated*

---

## GitHub Actions: Configuration

Workflows are configured in the `.github/workflows/` folder of the repository
* One **YAML** file per *workflow*
* GHA YAML is (sic) *non-standard*
  * DRY is limited: *__no__ anchors*, *__no__ merge keys*
* In the following, I assume you know YAML
  * You can [learn it in minutes](https://learnxinyminutes.com/docs/yaml/)
  * Just remember that anchors and merge keys are not supported

---

## GitHub Actions: Triggering event

Workflows *react* to [events](https://docs.github.com/en/actions/reference/events-that-trigger-workflows)
* Events that trigger a workflow are specified under the `on` key

```yaml
on:
  push: # Trigger the workflow on push
    branches:
      - main # but only for the main branch
  pull_request: # Also trigger for pull requests
    branches:
      - main # but only for the main branch
  page_build: # Also trigger on page_build
  release: # Also trigger on release...
    types:
      - created # ...created
  schedule:
    - cron:  '*/30 5,17 * * *' # Cron syntax (see crontab.guru)
```

---

## GitHub Actions: Environment preparation

GitHub actions job run on a *fresh virtual machine*
* The *operating system* can be selected (Windows, Ubuntu, MacOS X)
  * Multi OS builds $\Rightarrow$ {{< course_name >}}
* The repository needs to be *cloned manually*
* Any operation should be *configured manually*

Rather than *convention over configuration*, GHA use **actions** as a form of configuration reuse
* Actions can be found on the [GH marketplace](https://github.com/marketplace?type=actions)
* Developing new actions $\Rightarrow$ {{< course_name >}}

---

## GitHub Actions: a simple gradle build

```yaml
name: Clone Repository
on:
  push: # On every push on any branch
jobs:
  Build: # Job name
    runs-on: ubuntu-latest # Operating system selection
    env: # Environment can be controlled at the job or step level
      TERM: dumb # Sets the environment variable TERM to "dumb"
    steps:
      - name: Checkout # custom name Checkout the repository
        uses: actions/checkout@v2 # Action implemented in this repository, tag "2"
      - uses: joschi/setup-jdk@v2.3.0 # Action implemented in repository "joschi/setup-jdk" tag "2.3.0"
        with: # Actions parameters (action name omitted)
          java-version: 15
      - name: Build
        run: ./gradlew check
```

---

# Automated delivery

Once the *reference environment* in CI completed the software construction and testing,
it should **sign** and then **deliver** the result.
* Possibly, even **deploy** the result, putting it at work

### Some delivery targets

* [GitHub releases](https://docs.github.com/en/github/administering-a-repository/about-releases)
  * Retract, language agnostic, good for releases
* [Sonatype OSSRH (a.k.a. *Maven Central*)](https://search.maven.org/)
  * De-facto standard for JVM products, no retract policy
* [GitHub packages](https://github.com/features/packages)
  * No retract, authentication required also in read mode, immature
* ~~JFrog Bintray~~ [✝ 2021-05-01](https://jfrog.com/blog/into-the-sunset-bintray-jcenter-gocenter-and-chartcenter/)
  * ~~Maven Central + other stuff~~

In [this repository](https://github.com/AlchemistSimulator/Alchemist/tree/3535e1b42097c86c6d5d3662fff554558381e5fc),
delivery is enabled towards all the aforementioned destinations

---

# No-retract

Your error will remain in the repositories *forever* and you will *never* be able to
fix it, you will need to *create a new release* with a *different version number*.

**Anectodal apologia of no-retract policies**

In March 2016, Azer Koçlu unpublished more than 250 of his modules
from NPM, which is a popular package manager used by Javascript
projects to install dependencies, because he was asked to rename the
module `Kik`, whose name is the same of an instant messaging app that
wanted to publish a homonym module.
Unfortunately, one of those dependencies was `left-pad`, a *11-line-long*
Javascript module, used by *thousands* of projects. This brought
breackages throughout many Javascript products (most notably Node and
Babel). NPM had to un-unpublish `left-pad`.

---

## Picking version numbers

> Without compliance to some sort of formal specification, version
numbers are essentially useless for dependency management. By
giving a name and clear definition to the above ideas, it becomes
easy to communicate your intentions to the users of your software.

### [Semantic versioning](http://semver.org/)

* Formally described, RFC-style
* Three levels plus optional: `MAJOR.MINOR.PATCH[-OTHER][+BUILD]`
  * `MAJOR` — Any incompatible API change
    * Major `0` is for initial development: anything may change at any time.
  * `MINOR` — Add functionality in a backwards-compatible manner
  * `PATCH` — Backwards-compatible bug fixes
  * `OTHER` — Optionally decorates the version with additional information.
  * `BUILD` — Optionally decorates the version with build information.
* First release: `0.1.0`, `1.0.0` formalizes the API
