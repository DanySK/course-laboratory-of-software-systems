 
+++

title = "DDD - Elements"
description = ""
outputs = ["Reveal"]
aliases = [
    "/ddd-elements/"
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
## 03 - Elements
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

# Knowledge Crunching

---

## Knowledge Crunching

<!--Complex problem domains will contain a wealth of information, some of which will not be applicable to solving the problem at hand and will only act to distract from the real focus of your modelling efforts.-->

* Knowledge crunching is the art of **distilling relevant information from the problem domain to build a useful model that can fulfill the needs of business use cases**.

    * The key to bridging any knowledge gaps for the technical team when designing a solution for a problem domain.

* In order for a team to produce a  useful model they need to have a deep insight of the problem domain to ensure important concepts are not overlooked or misunderstood.
    * This can only be done through working in collaboration with domain experts (brainstorming together). 
    <!--* Without this there is a danger that a technical solution will be produced that is void of any real domain insight and something that cannot be understood by the business or by other developers during software maintenance or subsequent enhancements.-->

* The main goal: **discover and agree on a shared understanding of the problem domain to produce a model that can fulfill business use cases**.

---

## Knowledge Crunching

{{< image src="knowledge_crunching.png" >}}

<!--The process of knowledge crunching, as shown in Figure 2-1, starts with the behaviors of a system. The team go through the scenarios of the application with the business stakeholders and experts. This process is the catalyst to conversation, deep insight, and a shared understanding of the domain for all participants. It is therefore vital that stakeholders and subject matter experts are actively involved and engaged.-->

---

## Shared Understanding through a Shared Language

* An output of knowledge crunching and an artifact of the shared understanding is a common **Ubiquitous Language** (UL).
    * This language must be made explicit and be used when describing the domain model and problem domain.
    * The language should also be used in the code implementation of the model (with the same terms and concepts used as class names, properties, and method names). 
    * It is the language that enables both the business and development teams to have meaningful communication about the software.

<!--* UL is used to bind the code representation of the model to the conceptual model communicated in language and diagrams.-->

* The UL will contain terminology from the business as well as new concepts and terms discovered when modeling the use case of the problem domain.

* The shared understanding obtained with a well defined UL prevents the need to constantly translate from a technical model to a business model.

---

## The Importance of Domain Knowledge

* **Domain knowledge is key, even more so than technical know‐how.**

* Teams working in a business with complex processes and logic need to immerse themselves in the problem domain, assimilating all the relevant domain knowledge.

* If you can’t speak to your business users in simple terms about complex concepts in the problem domain, you are not ready to start developing software within it.

---

## The Role of Business Analysts

* A business analyst can help stakeholders flesh out their
initial ideas and capture inputs and outputs of the product.

* If you have odd whiz‐kid developers and are nervous about putting them in front of domain experts, you can also use business analysts as facilitators to help communication.

* What you don’t want to do is remove the direct communication between the development team and the people who understand that part of the business the most.

> *Who can act as a Business Analyst?*

---

## Knowledge crunching is an ongoing process

* Model‐driven design and the evolution of a domain model
is an ongoing process.

* Many models must be rejected in order to ensure you have a useful modelfor the current use cases of a system. 
    * **Collaboration among development team, business
stakeholders, and subject matter experts should not be constrained to the start of a project**.

* Knowledge crunching should be an ongoing concern with the business engaged throughout the _lifetime_ of the application build.
    * Note: when the system is in use the model may also need to evolve due to technical reasons such as performance or a better understanding of the systems usage.

* **A good model must be "malleable"!**

---

## Domain Experts vs. Stakeholders

{{< image src="domain_experts_stakeholders.png" width="70">}}

* A problem space gives you a set of requirements, inputs, and expected outputs: this is usually provided from your stakeholders.

* A solution space contains a model that can meet the needs of the requirements: this is where domain experts can help.

<!--As shown in Figure 2-2, if your stakeholder is not a domain expert then his role will differ greatly from that of a domain expert. A stakeholder will tell you what they want the system to do; they will focus on the inputs and outputs. A domain expert on the other hand will work with you in order to produce a useful model that can satisfy the needs of a stakeholder and the behaviors of the application.-->

---

## Effective Knowledge Crunching (1/3)

Creating a useful model is a collaborative experience: however, business users can also find it tiring and unproductive

1. *Focus on the most interesting conversations*
    * Start with the areas of the problem domain that keep the business up at night
    * E.g.: Which parts of the current system are hard to use? Which manual processes stop them from doing value‐adding work? What changes would increase revenue or improve operational efficiencies and save money from the bottom line?
    * The most interesting conversations will reveal where you should spend most of your effort on creating a shared understanding and a shared language.

---

## Effective Knowledge Crunching (2/3)

2. *Start from the Use Cases*
    * A use case lists the steps required to achieve a goal, including the interactions between users and systems.
    * Be careful to listen for domain terminology, because this forms the start of your shared language for describing and communicating the problem domain.
    * It’s also useful to read back the use case to the domain expert in your own understanding, so they can validate that you do understand the use case as they do. 
    * Don’t try to jump to a solution too quickly before you truly understand and appreciate the problem.

2. *Ask Powerful Questions*
    * The questions you ask during knowledge‐crunching sessions will go a long way toward your understanding of the importance of the product you are building and the intent behind it.

---

## Effective Knowledge Crunching (3/3)

4. *Sketching*
    * People often learn quicker by seeing visual representations of the concepts they are discussing.
    * Sketching simple diagrams is a common visualization technique DDD practitioners use to enhance knowledge-crunching sessions and maximize their time with domain experts.
    * Keeping your diagrams at a consistent level of detail will prevent you from showing too much detail or too little detail, meaning everyone can understand what you are trying to convey. It’s often better to create multiple diagrams each at a different level of detail.

---

## UML "Use Case" Diagram

* The use case diagram in UML captures the behavior of a system as it appears to an outside user. 
    * It partitions the system functionality into transactions meaningful to actors (users of a system).
    * The pieces of interactive functionality are called use cases.
* A use case describes an interaction with actors as a sequence of messages between the system and one or more actors.

* The term actor includes humans, as well as other computer systems and processes.

---

## Use Case Diagram: An Example (1/2)

{{< image src="uml_use_cases_ex01.png" >}}

---

## Actors

* An actor is an idealization of an external person, process, or thing interacting with a system.

* An actor characterizes the interactions that outside users may have with the system. 
    * At run time, one physical user may be bound to multiple actors within the system.
    * Different users may be bound to the same actor and therefore represent multiple instances of the same actor definition.
    * Each actor participates in one or more use cases.

* The internal implementation of an actor is not relevant in the use case
    * Actors may be defined in generalization hierarchies, in which an abstract actor description is shared and augmented by one or more specific actor descriptions.

---

## Use Case

* A use case is a coherent unit of externally visible functionality provided by a system unit and expressed by sequences of messages exchanged by the system unit and one or more actors of the system unit.

* The purpose of a use case is to define a
piece of coherent behavior without revealing the internal structure of the system.

* In the model, the execution of each use case is independent of the others, although an implementation of the use cases may create implicit dependencies among them.

* Each use case represents an orthogonal piece of functionality whose execution can be mixed with the execution of other use
cases.

---

## Use Case Diagram: An Example (2/2)

{{< image src="uml_use_cases_ex02.png" >}}

---

## Use Cases Relationships

* A use case can participate in several relationships, in addition to the association with actors

{{< image src="uml_use_cases_relationships.png" >}}

---

## UML "Activity" Diagram

* An activity diagram is a graph of nodes and flows that shows the flow of control (and data) through the steps of a computation
    * Execution steps can both sequential and concurrent

* An activity definition contains activity nodes: each of them represents the performance of a step in a workflow
    * Each node waits for the completion of its computation, then the execution proceeds to nodes found on the output flow

* An activity diagram my contain branches and forking

* Partitions can be used to organize activities in a model according to responsibility

* An activity diagram

--- 

## Activity Diagram: An Example (1/2)

{{< image src="xxx.png" >}}

# TBC

---

## Class Responsibility Collaboration (CRC) Cards


---

5. *Defer the Naming of Concepts in Your Model*

