 
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
  * homepage: [`https://www.unibo.it/sitoweb/a.croatti/en`](https://www.unibo.it/sitoweb/a.croatti/en)

---

# Forum and contacts

#### Prioritize the forum [`http://bit.ly/lss-2020`](http://bit.ly/lss-2020):
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

Slides will be produced with a *rolling release* model.
<br>
(of course trying to get them ready at least the day before the lecture)

## Books 

No mandatory books, but there are both:
* Recommended readings
* Additional useful books

On [the course webpage](https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2020/412677)

---

# Organization

Lectures in lab with immediate hands-on.
<br>
If you are following remotely, make sure you can experiment using your local system.

## Anti-coronavirus measures

* **Mandatory face masks** (sorry, no way around)
* **Immuni** app warmly recommended to all those with a compatible device

## Timetable

* **Thursday 14:00--17:00** --- Lab 3.1 
* **Friday 14:00--17:00** --- Lab 3.1
<br>
Changes will be published on [the forum](http://bit.ly/lss-2020)

---

# Goals

1. Design systems top-down, from the **domain**
2. Zero-overhead from *domain definition* to *executable code*
3. *Agile* development practices, *DevOps* philosophy
4. *High automation* + *technical excellence*

---

# Prerequisites

* Knowledge of *Java* and *Scala*
* Minimal ability with `git`
  * initializing and managing the repository options
  * committing
  * branching and merging
  * fetching and pushing
* A *curious mindset*
  * Never stop when it works, stop when you know *why* it does

---

# Help me help you

<iframe src="https://giphy.com/embed/fdLR6LGwAiVNhGQNvf" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p></p>

* [Language survey at https://virtuale.unibo.it/mod/choice/view.php?id=363641](https://virtuale.unibo.it/mod/choice/view.php?id=363641)
  * We might switch to full English, but we don't want to scare people away
* [ATTLS Survey at https://virtuale.unibo.it/mod/survey/view.php?id=363472](https://virtuale.unibo.it/mod/survey/view.php?id=363472)
  * Helps the teachers understand the best way to present information

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

# Software

## Required
* A working internet connection
* A working JDK installation
  * Consider using [Jabba](https://github.com/shyiko/jabba)
* Kotlin (kotlinc REPL working)
* Gradle
* Docker

## Recommended
* IntelliJ Idea
* Visual Studio Code
* A decent Unix terminal
* Ruby Gem

---

# Course Container

Feeling lazy?
We prepared a container with all the course's software:
  * [https://hub.docker.com/repository/docker/danysk/linux-didattica](https://hub.docker.com/repository/docker/danysk/linux-didattica)

Follow the instructions at [https://github.com/DanySK/docker-linux-didattica](https://github.com/DanySK/docker-linux-didattica)

---

# Software in lab

The PCs are equipped with the container
1. Launch Docker
1. Launch the script on the Desktop
1. Wait for it to start
1. Close it, stop Docker
1. Restart Docker
1. Launch the X Server
1. Now use the container freely
1. Data is saved in your desktop in `DockerHome` (preserve the folder if you need)

Note: data is not persisted automatically, at end of the session you need to copy it in your home folder

---

# Overview

<div class='left' style='float:left;width:50%'>

**Module 1**

* [Kotlin for Scala developers](01-kotlin)
  * [Internal DSLs construction](02-internal-dsls)
* [The DevOps philosophy](03-devops-intro)
* [Build automation](04-build-automation)
* Sharing
  * [Software versioning](05-version-selection)
  * [Software licensing](06-licenses)
* [Continuous integration/delivery/deployment](07-ci)
* [Advanced version control](08-advanced-git)
* [Containerization](09-containerization)

</div>

<div class='right' style='float:right;width:50%'>

**Module 2**

* [Domain-Driven Design](21-ddd-intro)
  * [Elements](22-ddd-elements)
  * [Principles and Application](23-ddd-principles-and-application)
  * [Strategic Patterns](24-ddd-strategic-patterns)
  * [Tactical Patterns](25-ddd-tactical-patterns)
  * [CQRS Pattern](26-ddd-cqrs)
* [Enterprise Software Architectures](27-enterprise-software-architectures)
* [Elements of Metamodeling](28-metamodeling)
* [DSL Engineering](29-dsl-engineering)

</div>

---

# Extra

There is some additional material not involving the course, that gets shipped together with it though.

Typically, because it's somewhat related.

* [Extra content](extra)

