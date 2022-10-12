 
+++

title = "Introduction to Laboratory of Software Systems"
description = "Description of the course"
outputs = ["Reveal"]

[reveal_hugo.custom_theme_options]
targetPath = "css/custom-theme.css"

+++

# Introduction to the course

{{% import path="reusable/header.md" %}}

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

#### Prioritize the forum [`https://virtuale.unibo.it/mod/forum/view.php?id=970526`](https://virtuale.unibo.it/mod/forum/view.php?id=970526):
  * All technical question
  * Any other non personal question

<p>

#### When using the email
  * Include *both* teachers, *always*

<p>

#### Office hours
* Danilo Pianini $\Rightarrow$ check [the teacher webpage](https://www.unibo.it/sitoweb/danilo.pianini/en)
* Angelo Croatti $\Rightarrow$ check [the teacher webpage](https://www.unibo.it/sitoweb/a.croatti)

---

# Resources

* These *slides* should contain everything you need
* code examples produced during the lecture will be available right after
* most code is already on GitHub

Slides will be produced with a *rolling release* model.

## Books 

No mandatory books, but there are both:
* Recommended readings
* Additional useful books

On [the course webpage](https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2022/412677)

---

# Organization

Lectures in lab with immediate hands-on.

## Timetable

* **Wednesday 13:00--16:00** --- Lab 3.3 --- Usually with Pianini
* **Friday 14:00--17:00** --- Lab 3.3 --- Usually with Croatti

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
  * We care about the *domain modeling* and the application of *DevOps techniques*
  * You can pick a project of another course, apply them there, and it is fine for LSS
* Can be a project created for LSS alone
  * If you are short on ideas, we can help :)

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
