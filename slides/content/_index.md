 
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

# Contacts

#### Prioritize the forum [`https://virtuale.unibo.it/course/view.php?id=30734`](https://virtuale.unibo.it/course/view.php?id=30734):
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

On [the course webpage](https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2021/412677)

---

# Organization

Lectures in lab with immediate hands-on.
<br>
If you are following remotely, make sure you can experiment using your local system.

## Anti-coronavirus measures

* **Reduced room capacity** (will be relaxed maybe if things go well)
* **Mandatory _"certificazione verde"_ a.k.a. _green pass_**
* **Mandatory face masks** (sorry, no way around)
* **Immuni** app warmly recommended to all those with a compatible device

## Timetable

* **Wednesday 13:00--16:00** --- Lab 3.1 --- Usually with Pianini
* **Friday 14:00--17:00** --- Lab 3.1 --- Usually with Croatti

Changes will be published on the forum

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

# Exam

### **Discussion** of a **group project**
* Must feature:
  * *Domain-driven* design
  * Clear development process and *DevOps* practices
  * Full-scale *automation*
    * Including *continuous integration* and delivery
* Can be a **joint effort** with other courses
  * Discussed on a case-by-case basis

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

### Feeling lazy?
We prepared a container with all the course's software:
  * [https://hub.docker.com/repository/docker/danysk/linux-didattica](https://hub.docker.com/repository/docker/danysk/linux-didattica)

Follow the instructions at [https://github.com/DanySK/docker-linux-didattica](https://github.com/DanySK/docker-linux-didattica)

### Feeling Windows-y?

The container can be converted into a WSL2 Linux distribution.

(Instructions available in the same repository as above)

---

# Software in lab

The PCs are equipped with the WSL2 image

* There should be a link on the Desktop
* Double-clicking it should pop up a zsh shell
  * Wait for the first terminal to show before starting others

---

# Overview

* [The DevOps philosophy](01-devops-intro)
* [Kotlin for Scala developers](02-kotlin)
  * [Internal DSLs construction](03-internal-dsls)
* [Build automation](04-build-automation)
* Sharing
  * [Software versioning](05-version-selection)
  * [Software licensing](06-licenses)
* [Continuous integration/delivery/deployment](07-ci)
* [Advanced version control](08-advanced-git)
* [Containerization](09-containerization)


---

# Extra

There is some additional material not involving the course, that gets shipped together with it though.

Typically, because it's somewhat related.

* [Extra content](extra)

