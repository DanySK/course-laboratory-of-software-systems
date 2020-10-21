+++

title = "DDD @ DIR"
description = ""
outputs = ["Reveal"]
aliases = [
    "/dir-ddd/"
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
#### Dipartimento di Informatica - Scienza e Ingegneria (DISI)
#### Campus di Cesena
&nbsp;
# Domain-Driven Design
## A Gentle Introduction
&nbsp;
#### Dott. Ing. [Angelo Croatti](mailto:a.croatti@unibo.it), PhD
&nbsp;
#### DIR - Distretto dell'Informatica Romagnolo
#### 8 Ottobre 2020

---

# Writing Software is easy...
## ... at least if it's greenfield software!

(*Scott Millett*)

---
## True Stories

> #### #1
> *When you need to modify code written by other developers*  
> *(or code you wrote 6/12 months ago)*  
> *we have two scenarios:*  
>
> *in the best case, it's **a bit boring**...*  
> *...but in the worst case, it's **a nightmare**!*

---

## True Stories

> #### #2
> *The software works... but you aren't sure exactly how!*

> #### #3
> *The software contains all the right frameworks and patterns,*  
> *and has been created using an agile approach...*  
> *...but introducing new features into the codebase is harder than it should be!*

> #### #4
> *Often, business/domain experts aren’t helpful*  
> *because the code bears no resemblance to the language they use.*

---

{{< image src="assets/frustrated.jpg" >}}

---

## Domain-Driven Design "Philosophy"

> *Domain-Driven Design (DDD) is a process that aligns your code with the reality of your problem domain*

* DDD facilitates the evolution of software
    * Adding new features becomes as easy as it was in the good old days of greenfield development

* DDD enforces *designers*/*developers* and *domain experts* working together to understand domain concepts, policies, and logic
    * Although it understand the need for software patterns, principles, methodologies, and frameworks

* Greater knowledge of the problem domain and synergy with the business
    * The produced software is easier to adapt for future enhancement

---

## Domain-Driven Design: A first Definition

<div class='left' style='float:left;width:30%'>

{{< image src="https://m.media-amazon.com/images/I/51OWGtzQLLL.jpg" >}}

</div>

<div class='right' style='float:right;width:70%'>

> Domain-Driven Design (DDD) is both *a way of thinking* and *a set of priorities*, aimed at accelerating software projects that have to deal with complicated domains*

&nbsp;
&nbsp;
&nbsp;
#### It offers to designers/developers  
#### the knowledge and skills they need
#### to tackle large or complex business systems
#### effectively

</div>

---

## Fundamental Concepts of DDD
### (in a nutshell)

1. Distill a large problem domain into **smaller sub-domains**

1. Identify the core sub-domains to reveal what is important
    * The core domains are those of greater value to the business which requires more focus and time

1. Collaborate with experts to discover an analysis model that will provide solutions to solve problems (**knowledge crunching**)

1. Use the same **ubiquitous language** to bind the analysis model to the code model

1. Enclose the model within a boundary to protect its integrity (**bounded context**)

1. Keep a **context map** to understand the relationships of all models in play

---

## The Domain-Driven Design Conceptual Space

{{< image src="assets/ddd_conceptual_space.png" >}}

---

## Software Complexity

* **The ability to recognize complexity is a crucial design skill**
    * It allows you to identify problems before you invest a lot of effort in them
    * It allows you to make good choices among alternatives

&nbsp;
&nbsp;
{{< image src="assets/ddd_software_complexity.png" width="70">}}

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

## Software Complexity Challenges

1. **Code Created without a Common Language**
    * A lack of focus on a *shared language and knowledge* of the problem domain results in a software that works but does not reveal the intent of the business
    * Code without a *binding to an analysis model* that the business understands will degrade over time

1. **A Lack of Focus on the Problem Domain**
    * Software projects fail when you don’t understand the business domain you are working within well enough
        * The more you invest in understanding your business domain, the better equipped you will be when you are trying to model it in software

1. **Coding is the easy part of development!**
    * Creating and keeping a useful software model of the domain that can fulfill business use-cases is the difficult part

---

*The DDD Golden Rule*

# Focus on the domain
# above anything else

> **Domain experts must work with the development team**  
> (to focus on the areas of the domain that are useful to be able)  
> **to produce valuable software!**

---

## Knowledge Crunching

{{< image src="assets/knowledge_crunching.png" width="50">}}

> **Distilling relevant information from the problem domain**  
> **to build a useful model that can fulfill the needs of business use cases**

---

## Shared Understanding through
## a Shared Ubiquitous Language (UL)

* UL must be made explicit and be used when describing the domain model and problem domain
    * The language should also be used in the code implementation of the model

* UL enables both the business and development teams to have *meaningful communication about the software*
    * It contains terminology from the business as well as new concepts and terms discovered when modeling the use case of the problem domain

* The shared understanding obtained with a well defined UL *prevents the need to constantly translate from a technical model to a business model*

---

# Domain knowledge is key,
# even more so than technical know‐how

*If you can’t speak to your business users in simple terms about complex concepts in the problem domain,*  
*you are not ready to start developing software within it*

---

## Effective Knowledge Crunching 

> **Creating a useful model is a collaborative experience:**  
> **however, business users can also find it tiring and unproductive!**

1. *Focus on the most interesting conversations*
1. *Start from the Use Cases and Ask Powerful Questions*
1. *Sketching*

---

{{< image src="assets/whirlpool.png" width="100">}}

---

## Focusing on the Core of the Domain Problem

* *Not all parts of a problem are equal!*
    * Some parts of the application are more important than others
    * Some parts need more attention and investment than others to make the application a success
    
* During knowledge crunching with domain experts, it’s important to reduce the noise of what’s unimportant to enable you to focus on what is important
    * *What is core changes over time!*

> **Domain-Driven Design is all about reducing complexity!**

* The Domain Model is at the center of Domain‐Driven Design
    * When it is expressed as a code implementation, it is bound to the analysis model through the use of the shared language
    * The model contains only what is relevant to solve problems in the context of the system
---

## Domain Model Evolution (without DDD)

{{< image src="assets/domain_model_evolution_wrong.png" >}}

---

## Domain Model Evolution (following DDD)

{{< image src="assets/domain_model_evolution_good.png" >}}

---

## How to create effective domain models?

1. **Don’t let the truth get in the way of a good model**
    * A domain model is not a model of real-life; it is a system of abstractions on reality, an interpretation

1. **Model only what is relevant**
    * You don’t have to include everything into the model

1. **Domain models are temporarily useful**
    * A domain model needs to be constantly refined to continually be useful

1. **Be explicit with terminology and limit your abstractions**

1. **Implement the model in code early and often**

---

*The DDD Basic Approach*

# Use Bounded Contexts
# to ensure Domain Integrity 

> **Bounded Context**
> * Defines the applicability of the model
> * Ensures that domain concepts outside a model's context do not distract from the problem it was designed to solve
> * Makes explicit what the model is responsible for and what it is not

---

 ## The Anatomy of a Bounded Context

{{< image src="assets/bounded-context.png" width="50">}}

> *A Bounded Context owns the vertical slide of functionalities from the presentation layer, through the domain logic layer, on to the persistence, and even to the data storage*

---

## Towards a Clean Architecture

DDD does not enforce the use of a particular architectural style  
... as long as the chosen one *must support the isolation of the domain logic*!

> ### **Dependency Inversion**
> {{< image src="assets/dependency-inversion.png" width="50">}}

---

## Popular Misconceptions of DDD

1. *Tactical Patterns are key to DDD! No, they aren't!*
    * DDD is less about software design patterns and more about problem-solving through collaboration.
    * DDD is not code-centric: its purpose is not to make elegant code. **Software is merely an artifact of DDD**.

1. *DDD is a Framework! No, it isn't!*
    * DDD is architecturally agnostic
    * Architectural styles can vary: they should apply at the bounded context level

1. *DDD is a Silver Bullet! No, it isn't!*
    * DDD can take a lot of effort: it requires an iterative development methodology, an engaged business, etc.
    * Not all software systems fit with this approach

---

## Why Domain-Driven Solutions
## often fail to deliver?

* Is not because of a lack of programming ability or technical expertise
    * ... but rather because of a lack of understanding, communication, and business knowledge. 

* If developers and customers cannot effectively communicate, aren’t aligned on the same goals
    * ... you ultimately cannot produce meaningful outcomes.

---

## (Avoid to)
# Looking for Tactical Pattern perfection

* Teams concerned only with writing code focus on the tactical patterns of DDD

* DDD is about discovering *what* you need to write, *why* you need to write it, and *how much* effort you should use

---

## (Avoid to)
# Overvaluing sample applications

* *"Can I see a sample application?"*
    * There are probably many good solutions that show the result of a product developed under a DDD process
    * but much of the benefit of DDD is not revealed when you only examine the code artifacts!

* DDD is performed on whiteboards, over coffee, with business experts.
    * A sample application does not reveal the result of Knowledge Crunching between domain experts and the development team.

---

## A team focusing too much on the tactical patterns
# is missing the point of DDD

> **The true value of DDD lies in the creation of a shared language, specific for a context that enables the design of an effective solution**

* Problems are solved not only in code but through *collaboration*, *communication*, and *exploration* with domain experts
    * Developers should not be judged on how quickly they can churn out code...
    * they must be judged on how they solve problems with or without code.

---

{{< image src="assets/what-else.jpg">}}

# ... plenty of concepts, patterns, guidelines, architectures, techniques