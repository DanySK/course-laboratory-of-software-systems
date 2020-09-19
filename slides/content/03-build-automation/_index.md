 
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
* **Task** -- An atomic operation on the project, which can
  * have input and output files
  * depend on other tasks (can be executed only if those are completed)
* **Build file** -- A special file instructing Gradle on the actual organization of projects and available tasks

---

    * Dependency management and configurations
    * The build system as a dependency
    * Hierarchial organization
    * Isolation of imperativity
    * Declarativity via DSLs
    * Reuse via plug-ins
    * Testing plug-ins
    * Existing plugins