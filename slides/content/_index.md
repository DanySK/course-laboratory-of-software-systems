 
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

## Introduction to the course

### [Danilo Pianini](mailto:danilo.pianini@unibo.it) -- [Angelo Croatti](a.croatti@unibo.it)

{{< today >}}

---

# Professors

### Danilo Pianini
  * email: [`danilo.pianini@unibo.it`](mailto:danilo.pianini@unibo.it)
  * homepage: [`https://www.unibo.it/sitoweb/danilo.pianini/en`](https://www.unibo.it/sitoweb/danilo.pianini/en)

### Angelo Croatti
  * email: [`a.croatti@unibo.it`](mailto:a.croatti@unibo.it)
  * homepage: [`https://www.unibo.it/sitoweb/angelo.croatti/en`](https://www.unibo.it/sitoweb/angelo.croatti/en)


---

# Forum and contacts

#### Prioritize the forum [`TBD`](TBD):
  * All technical question
  * Any other non personal question

<p>

#### When using the email
  * Include *both* tachers, *always*


<p>

#### Office hours
Teachers available both physically and via Microsoft Teams
  * Danilo Pianini $\Rightarrow$ check [the teacher webpage](https://www.unibo.it/sitoweb/danilo.pianini/en)
  * Angelo Croatti $\Rightarrow$ check [the teacher webpage](https://www.unibo.it/sitoweb/a.croatti)


---

# Resources

These *slides* should contain everything you need
<br>
code examples produced during the lecture will be available right after

## Books 

No mandatory books, but there are both:
* Recommended readings
* Additional useful books
On [the course webpage](https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2020/412677)

---

# Organization

Lectures in lab with immedate hands-on.
<br>
If you are following remotely, make sure you can experiment using your local system.

## Timetable

* **Thursday 14:00--17:00** --- Lab 3.1 
* **Friday 14:00--17:00** --- Lab 3.1


---

# Overview

* [Kotlin for Scala developers](01-kotlin)
  * Internal DSL construction
* Build automation
* Version control
* Continuous integration/deployment/delivery
* Containerization
* Distributed software systems engineering
* Domain driven design
  * Including external DSL construction
* Cyber-physical systems

---

# Software

## Required
* A working JDK installation
  * Consider using [Jabba](https://github.com/shyiko/jabba)

<p>

## Recommended
* IntelliJ Idea
* A decent Unix terminal

---

# Goals

1. Design systems top down, from the **domain**
2. Zero-overhead from *domain definition* to *executable code*
3. *Agile* development practices, *DevOps* phylosophy
4. *High automation* + *technical excellence*

---

# Exam

### **Discussion** of a **group project**
* Must feature:
  * Domain-driven design
  * Clear development process and agile practices
  * Automation
  * Continuous integration and delivery
* Can be a joint effort with other courses
  * Discussed case-by-case

---

# Prerequisites

* Knowledge of *Java* and *Scala*
* Minimal ability with *`git`*
  * initializing and managing the repository options
  * committing
  * branching and merging
  * fetching and pushing

---

# Extra

There is some additional material not involving the course, that gets shipped together with it though.

Typically, because it's somewhat related.

* [Extra content](extra)

