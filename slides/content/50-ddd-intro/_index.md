 
+++

title = "DDD - Intro"
description = ""
outputs = ["Reveal"]
aliases = [
    "/ddd-intro/"
]

[reveal_hugo]
transition = "slide"
transition_speed = "fast"
custom_theme = "custom-theme.scss"
custom_theme_compile = true

[reveal_hugo.custom_theme_options]
targetPath = "css/custom-theme.css"
enableSourceMap = true

+++

#### Alma Mater Studiorum - Università di Bologna
#### Computer Science and Engineering Department (DISI)
&nbsp;
# Domain-Driven Design
## 01 - Introduction
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

# Writing Software is easy...
## ... at least if it's greenfield software!

(*Scott Millett*)

---

## A true Story

* When it comes to modifying code written by other developers (or code you wrote 6/12 months ago) it can be a bit of a bore at best and a nightmare at worst.

* The software works... but you aren’t sure exactly how!

* It contains all the right frameworks and patterns, and has been created using an agile approach... but introducing new features into the codebase is harder than it should be.

* Even business experts aren’t helpful because the code bears no resemblance to the language they use.

* Working on such systems becomes a chore, leaving developers frustrated and devoid of any coding pleasure.

---

## Domain-Driven Design "Philosophy"

> *Domain-Driven Design (DDD) is a process that aligns your code with the reality of your problem domain.*

* As your product evolves, adding new features becomes as easy as it was in the good old days of greenfield development.

* Although DDD understands the need for software patterns, principles, methodologies, and frameworks, it values developers and domain experts working together to understand domain concepts, policies, and logic equally.

* With greater knowledge of the problem domain and a synergy with the business, developers are more likely to build software that is more readable and easier to adapt for future enhancement.

* **Following the DDD philosophy will give developers the knowledge and skills they need to tackle
large or complex business systems effectively.**

---

## The Art of the Possible

{{< plain_image src="art_of_possible.png" h="70" >}}

---

## Domain-Driven Design: A first Definition

<div class='left' style='float:left;width:30%'>

[{{< plain_image src="https://m.media-amazon.com/images/I/51OWGtzQLLL.jpg" h="50" >}}](https://www.oreilly.com/library/view/domain-driven-design-tackling/0321125215/)

</div>

<div class='right' style='float:right;width:70%'>

> *Domain-Driven Design (DDD) is both a way of thinking and a set of priorities, aimed at accelerating software projects that have to deal with complicated domains.*
>
> (Eric Evans, Domain-Driven Design, 2003)

</div>

---

## Foundamental Concepts of DDD (in a nutshell)

1. Distill a large problem domain into **smaller sub-domains**.

1. Identify the core sub-domains to reveal what is important.
    * The core domains are those of greater value to the business which requires more focus, effort, and time.

1. Collaborate with experts to discover an analysis model that will provide solutions to solve problems or reveal opportunities particularly in the core domain.

1. Use the same **ubiquitous language** to bind the analysis model to the code model.
    * Use **tactical patterns** to separate technical code from domain code to prevent accidental complexity.

1. Split the model into smaller models
    * Enclose the model within a **boundary** to protect the
models integrity.

1. Keep a **context map** to understand the relationships, social and technical, of all models in play.

---

## DDD "Conceptual Space"

{{< plain_image src="ddd_conceptual_space.png" h="80" >}}

---

## Software Complexity

* **The ability to recognize complexity is a crucial design skill**
    * It allows you to identify problems before you invest a lot of effort in them
    * It allows you to make good choices among alternatives

* Complexity (C) is anything related to the structure of a software system that makes it hard to understand and modify the system

$$C = \sum_{p}^{}c_pt_p$$ 

* Decomposing a software system into subparts, for each part $p$ its own real complexity is given by the complexity of the part ($c_p$) weighted by the fraction of time spent by developers on it ($t_p$).

* In terms of _costs/benefits_: in a complex system it takes a lot of work to implements even small improvements, while in a simple system larger improvements can be implemented with less effort. 

---

## Symptoms of Software Complexity

1. **Change Amplification**
    * A seemingly simple change requires code modifications in many different places

1. **Cognitive Load**
    * How much a developer need to know in order to complete a task?
    * A higher cognitive load means that developers have to spend more time learning the required information (they can miss something important)

1. **Unknown unknowns**
    * Which pieces of code must be introduced/modified to complete a task?
    * What information a developer must have to carry out the task successfully?

---

## Causes of Software Complexity

1. **Dependencies**
    * A dependency exists when a given piece of code can not be understood and modified in isolation.
    * In general, dependencies are part of the software and can't be completely eliminated. The goal is to reduce as much as possible them. 

1. **Obscurity**
    * Obscurity occurs when important information is not obvious.
    * it is a design issue: a simplified design reduce obscurity.

---

## Complex Problem Domains

{{< plain_image src="ddd_software_complexity.png" h="100" >}}

---

## Strategic Design

* The incremental nature of complexity makes it hard to control
    * In order to slow its growth, you must adopt **"zero-tolerance" philosophy**


* If you want a good design, you must take a more **strategic approach** where you invest time to produce clean designs (and fix problems)
    1. Working code isn't enough!
    1. The primary goal must be to produce a great design.
    1. Great design doesn't come for free!
    1. Great design eventually pays for itself (and sooner you might think).

---

## Software Complexity Challenges (1/3)
### Code Created without a Common Language

* A lack of focus on a **shared language and knowledge** of the problem domain results in a codebase that works but does not reveal the intent of the business.

* This makes codebases difficult to read and maintain because translations between the analysis model and the code model can be costly and error-prone.

* Code without a **binding to an analysis model** that the business understands will degrade over time.

* Teams that do not utilize the rich vocabulary of the problem domain in code will decrease their chances of discovering new domain concepts when collaborating with business experts.

---

## Software Complexity Challenges (2/3)
### A Lack of Organization

{{< plain_image src="ddd_lack_of_organization.png" h="45" >}}

* The initial incarnation of a system is fast to produce and often a well‐rounded success...

    * ... but because there is little focus based on the design of an application around a model of the problem domain, subsequent enhancements are troublesome.

* The codebase lacks the required synergy with the business behavior to make change manageable.

---

## Software Complexity Challenges (3/3)
### A Lack of Focus on the Problem Domain

* Software projects fail when you don’t understand the business domain you are working within well enough. 


* Coding is the easy part of development. 

* Outside of non‐functional requirements, **creating and keeping a useful software model of the domain that can fulfill business use-cases is the difficult part**. 

* The more you invest in understanding your business domain, the better equipped you will be when you are trying to model it in software to solve its inherent business problems.

---

## The Problem Domain

* A problem domain refers to **the subject area for which you are building software**.

* DDD stresses the need to **focus on the domain above anything else** when working on creating software for large‐scale and complex business systems.

* **Domain experts must work with the development team** to focus on the areas of the domain that are useful to be able to produce valuable software.

* For example:
    * _when writing software for the healthcare it is not important to learn to become a doctor. What is important to understand is the terminology of the health industry, how different departments view patients and care, what information doctors gather, and what they do with it_.

---

