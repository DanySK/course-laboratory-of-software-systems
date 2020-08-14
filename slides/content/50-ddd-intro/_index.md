 
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

* With greater knowledge of the problem domain and synergy with the business, developers are more likely to build software that is more readable and easier to adapt for future enhancement.

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

## Fundamental Concepts of DDD (in a nutshell)

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

* The initial incarnation of a system is fast to produce and often a well‐rounded success...

    * ... but because there is little focus based on the design of an application around a model of the problem domain, subsequent enhancements are troublesome.

* The codebase lacks the required synergy with the business behavior to make change manageable.

{{< plain_image src="ddd_lack_of_organization.png" h="45" >}}

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
    * _when writing software for healthcare it is not important to learn to become a doctor. What is important to understand is the terminology of the health industry, how different departments view patients and care, what information doctors gather, and what they do with it_.

---

## Strategic Patterns of the DDD

* Domain-Driven Design deals with both the challenge of
    1. understanding a problem domain, and
    1. creating a maintainable solution that is useful to solve problems within it. 

* It achieves this by utilizing a number of strategic patterns.

--- 

## Strategic Patterns of the DDD (1/5)
### Distilling the Problem Domain to Reveal What Is Important

* Not all of a large software product needs be perfectly designed.

* Development teams and domain experts use analysis patterns and knowledge crunching to distill large problem domains into more manageable subdomains.
    * This distillation reveals the core sub-domain

* DDD emphasizes the need to focus effort and talent on the core
subdomain(s).
    * This is the area that holds the most value and is key to the success of the application.

* Discovering the core domain helps teams understand why they’re producing the software and what it means for the software to be successful for the business. 
    * As the business evolves, so in turn must the software; it needs to be adaptable.
    * Investment in code quality for the key areas of an application will help it change with the business. 

---

## Strategic Patterns of the DDD (2/5)
### Creating a Model to Solve Domain Problems

* A software model is built for each subdomain to handle domain problems and to align the software with the business boundaries.
    * Each model should be an abstraction satisfying requirements and use cases...
    * ... while still retaining the rules and logic of the business domain.
    
* The development team should focus as much energy and effort on the model and domain logic as it does on the pure technical aspects of the application.

* To avoid accidental technical complexity the model is kept isolated from code.

---

## Strategic Patterns of the DDD (3/5)
### Using a Shared Language to Enable Modeling Collaboration

* Models are built through the collaboration of domain experts and the development team.

* Communication is achieved using an ever‐evolving shared language known as the **ubiquitous language (UL)** to efficiently and effectively connect a software model to a conceptual analysis model.

* The software model is bound to the analysis model by using the same terms of the UL for its structure and class design.

* Insights, concepts, and terms that are discovered at a coding level are replicated in the UL and therefore the analytical model. Likewise when the business reveals hidden concepts at the analysis model level this insight is fed back into the code model
    * This is the key that enables the domain experts and development teams to evolve the model in collaboration.

---

## Strategic Patterns of the DDD (4/5)
### Isolate Models from Ambiguity and Corruption

* Models sit within a **bounded context**, which defines the applicability of the model and ensures that its integrity is retained.
    * Larger models can be split into smaller models and defined within separate bounded contexts
    * ... when ambiguity in terminology exists
    * ... when multiple teams are a working in order to further reduce complexity.

* Bounded contexts are used to form a protective boundary around models
    * allowing the different models of the overall solution to evolve within well‐defined business contexts.

* Models are isolated from infrastructure code to avoid the accidental complexity of merging technical and business concepts.

* Bounded contexts also prevent the integrity of
models being corrupt by isolating them from third-party code.

---

## Strategic Patterns of the DDD (5/5)
### Understanding the Relationships between Contexts

* In Domain-Driven Design it is important understanding how separate models and contexts work together to solve domain problems that span across subdomains.

* **Context maps help you to understand the bigger picture**;
    * to understand what models exist
    * what they are responsible for, and 
    * where their applicability boundaries are. 

* These maps reveal how different models interact and what data they exchange to fulfill business processes.
    * The relationships between the connections and (more importantly) the grey area of processes is often not captured or well understood by the business.

---

## Problem Space vs. Solution Space

* All of the strategic patterns of the DDD help to manage the complexity of a problem (the **problem space**) or they manage complexity in the solution (the **solution space**).

* The problem space distils the problem domain into more manageable subdomains. DDD’s impact in the problem space is to reveal what is important and where to focus effort.

* The solution space of DDD covers patterns that can shape the architecture of your applications and make it easier to manage.

---

## The Problem Space

{{< plain_image src="ddd_problem_space.png" h="90" >}}

---

## The Solution Space

{{< plain_image src="ddd_solution_space.png" h="90" >}}

---

## Popular Misconceptions of DDD

1. *Tactical Patterns are key to DDD! No, they aren't!*
    * DDD is less about software design patterns and more about problem solving through collaboration.
    
    * DDD is not code centric: its purpose is not to make elegant code. **Software is merely an artifact of DDD**.

1. *DDD is a Framework! No, it isn't!*
    * DDD is architecturally agnostic in that there is no single architectural style you must follow to implement it.
    * Architectural styles can vary because they should apply at the bounded context level and not the application level.

1. *DDD is a Silver Bullet! No, it isn't!*
    * DDD can take a lot of effort, it requires an iterative development methodology, an engaged business, and smart developers.
    * Not all software systems fit with this approach. Trivial domains don’t warrant the level of sophistication as they have little or no domain logic. 

---

## Why Domain-Driven Solutions often fail to deliver?

* The reason that solutions fail to deliver is not because of a lack of programming ability or technical expertise, but rather because of a lack of understanding, communication, and business knowledge. 

* If developers and customers cannot effectively communicate, aren’t aligned on the same overarching goals then even with the most accomplished programmers in the world, you ultimately cannot produce meaningful outcomes.

* Some common mistakes:
    1. *Looking for Tactical Pattern perfection*
    1. *Overvaluing sample applications*
    1. *Missing the real value of DDD*

---

## (Avoid to) Looking for Tactical Pattern perfection

* Teams concerned only with writing code focus on the tactical patterns of DDD.
    * Building block patterns are a guide, not a bible!

* DDD is about discovering what you need to write, why you need to write it, and how much effort you should use. 

* Understanding the what and the why of problem-solving is a more important process to get correct than how you are going to implement it in code.

---

## (Avoid to) Overvaluing sample applications

* One of the most often-asked questions on software development forums is "Can I see a sample application?"
    * There are probably many good solutions that show the result of a product developed under a DDD process, but much of the benefit of DDD is not revealed when you only examine the code artifacts. 

* DDD is performed on whiteboards, over coffee, and in the corridors with business experts.
    * A sample application does not reveal the many conversations and collaborations between domain experts and the development team.

* The code artifact is the product of months and months of hard work, but it only represents the last iteration.

---

## Missing the real value of DDD

* A team focusing too much on the tactical patterns is missing the point of DDD.

* The true value of DDD lies in the creation of a shared language, specific for a context that enables developers and
domain experts to collaborate on solutions effectively.
    * The removal of ambiguity in conversations and effortless communication is the goal. 
    * These foundations must be in place before any coding takes place to give teams the best chance of solving problems.

* Problems are solved not only in code but through collaboration, communication, and exploration with domain experts.
    * Developers should not be judged on how quickly they can churn out code; they must be judged on how they solve problems with or without code.

---

## Summing up

* DDD is as a development philosophy: it promotes a new domain‐centric way of thinking and design software systems.

* It is the learning process, not the end goal, which is the greatest strength of DDD. 

* Any team can write a software product to meet the needs of a set of use cases, but teams that put time and effort into the problem domain they are working on can consistently evolve the product to meet new business use cases.

* DDD is not a strict methodology in itself but must be used with some form of iterative software project methodology to build and evolve a useful model.
