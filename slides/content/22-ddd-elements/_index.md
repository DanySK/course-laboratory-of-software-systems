 
+++

title = "[LSS2021] DDD > Elements"
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
## 02 - Elements
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

## Outline

1. The role of Knowledge Crunching

1. Focusing on the Core Domain of a Software System

1. Model-Driven Design

1. Domain Model Implementation Patterns

1. Bounded Contexts

1. Software Architectures for the DDD

1. Bounded Context Integration

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

{{< image src="assets/knowledge_crunching.png" >}}

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

# Domain knowledge is key,
## even more so than technical know‐how

* If you can’t speak to your business users in simple terms about complex concepts in the problem domain...
    * ... you are not ready to start developing software within it!

* Teams working in a business with complex processes and logic need to immerse themselves in the problem domain
    * assimilating all the relevant domain knowledge

---

## The Role of
# Business Analysts

> A business analyst can help stakeholders flesh out their initial ideas  
and capture inputs and outputs of the product

* Business analysts as facilitators to help communication
    * e.g. if you have odd whiz‐kid developers and are nervous about putting them in front of domain experts...

* What you don’t want to do is remove the direct communication between the development team and the people who understand business requirments and processes

> *Who can act as a Business Analyst?*

---

## Knowledge crunching is an ongoing process

* Model‐driven design and the evolution of a domain model is an ongoing process.

* Many models must be rejected in order to ensure you have a useful model for the current use cases of a system. 
    * **Collaboration among the development team, business stakeholders, and subject matter experts should not be constrained to the start of a project**.

* Knowledge crunching should be an ongoing concern with the business engaged throughout the _lifetime_ of the application build.
    * Note: when the system is in use the model may also need to evolve due to technical reasons such as performance or a better understanding of the systems usage.

* **A good model must be "malleable"!**

---

## Domain Experts vs. Stakeholders (1/2)

{{< image src="assets/domain_experts_stakeholders.png" >}}

---

## Domain Experts vs. Stakeholders (2/2)

* The *problem space* gives you a set of requirements, inputs, and expected outputs
    * this is usually provided from your stakeholders
    * they tell what they want the system to do 

* The *solution space* contains a model that can meet the needs of the requirements
    * this is where domain experts can help
    * the goal is to produce a useful model that can satisfy the needs of a stakeholder and the behaviors of the application

---

## Effective Knowledge Crunching (1/3)

> **Creating a useful model is a collaborative experience: however, business users can also find it tiring and unproductive!**

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
        * Useful diagrams: UML Use Case, UML Activity, CRC Cards

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

{{< image src="assets/uml_use_cases_ex01.png" >}}

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

{{< image src="assets/uml_use_cases_ex02.png" >}}

---

## Use Cases Relationships

* A use case can participate in several relationships, in addition to the association with actors

{{< image src="assets/uml_use_cases_relationships.png" >}}

---

## UML "Activity" Diagram

* An activity diagram is a graph of nodes and flows that shows the flow of control (and data) through the steps of a computation
    * Execution steps can both sequential and concurrent

* An activity definition contains activity nodes: each of them represents the performance of a step in a workflow
    * Each node waits for the completion of its computation, then the execution proceeds to nodes found on the output flow

* An activity diagram my contain branches and forking

* Partitions can be used to organize activities in a model according to responsibility

* The leaves of an activity diagram are actions

--- 

## Activity Diagram: An Example (1/2)

{{< image src="assets/activity_diagram_example01.png" >}}

---

## Activity Diagram: An Example (2/2)

{{< image src="assets/activity_diagram_example02.png" >}}

---

## Class Responsibility Collaboration (CRC) Cards

* Because DDD is built around the core idea of a shared language, it is important to use knowledge-gathering techniques that focus on creating a concise and powerful language

* CRC (Class Responsibility Collaboration) cards are divided into three areas and contain the following information:
    1. A class **name**, which represents a concept in the domain
    1. The **responsibilities** of the class
    1. Classes that are **associated** and are required to fulfill its purpose

* CRC cards focus the team and the business experts on thinking about the language of the concepts in the problem domain.

* Martin Fowler has described CRC cards as a viable alternative to UML sequence diagram to design the dynamics of object interaction and collaboration

---

## CRC Cards: An Example

{{< image src="crc_ex.png" width="100">}}

---

## Rapid Prototyping

* Favour rapid prototyping during knowledge‐crunching sessions!
    * Business users like nothing more than screen mock‐ups, because they reveal so much more about the intent they have behind a product
    * Users understand UI; they can interact with it and act out work flows clearly

* Another form of rapid prototyping is to capture requirements in code
    * Starting to code will help focus analysis sessions
    * Starting to implement abstract ideas from knowledge crunching will enable you to validate and prove your model
    * It also helps to avoid only abstract thinking, which can lead to analysis paralysis

* Coding quickly helps create your powerful questions and helps find missing use cases
    * NOTE (1): Coding at this level is part of the analysis process!
    * NOTE (2): Only create a code model of what is relevant and within the specific context to solve a given problem; you can’t effectively model the entire domain.

---

## Impact Mapping

* Impact mapping allows to go beyond traditional requirements documents, working on what impacts the business is trying to make

* Once you understand the impact the business is trying to make, you can play a more effective role in helping them to achieve it    
    * Significantly for DDD, you will be able to ask better questions during knowledge-crunching sessions since you know what the business wants to achieve

* Impact mapping is a very informal technique
    * You simply create mind‐map-like diagrams that accentuate key business information

---

## Impact Mapping: An Example

{{< image src="assets/impact_mapping.png" >}}

---

## Whirlpool Process of Model Exploration

* A method of modeling and knowledge crunching that can complement other agile methodologies and be called upon at any time of need throughout the lifetime of application development
    * Proposed by Eric Evans

* It is used not as a modeling methodology but rather for **when problems are encountered during the creation of a model**. 

--- 

{{< image src="assets/whirlpool.png" width="100">}}

---

## Whirlpool's Activities (1/2)

1. **Scenario Exploring**
    * A domain expert describes a scenario (with concrete examples) that the team is worried about or having difficulty with in the problem domain
    * Then, the group then maps the scenario, like event storming in a visual manner in an open space

1. **Modeling**
    * At the same time of running through a scenario, the team starts to examine the current model and assesses its usefulness for solving the scenario expressed by the domain expert

1. **Challenging the Model**
   * Once the team has amended the model or created a new model they then challenge it with further scenarios from the domain expert to prove its usefulness

---

## Whirlpool's Activities (2/2)

3. **Harvesting and Documenting**
    * Significant scenarios that help demonstrate the model should be captured in documentation
    * Business scenarios will change less often than the model so it is useful to have a collection of important ones as a reference

3. **Code Probing** 
    * When insight into the problem domain is unlocked and a design breakthrough occurs the technical team should prove it in code to ensure that it can be implemented

---

# Focusing on the Core Domain

---

## Focusing on the Core Domain

* **Not all parts of a problem are equal!**
    * Some parts of the application are more important than others
    * Some parts need more attention and investment than others to make the application a success
    
* During knowledge crunching with domain experts, it’s important to reduce the noise of what’s unimportant to enable you to focus on what is important

* **Domain-Driven Design is all about reducing complexity!**
    * A single monolithic model would increase the complexity
    * You should break the problem domain down so that you are able to create smaller models in the solution space
---

## Domain and Subdomains

* Large problem domains can be partitioned into *subdomains* to manage complexity and to separate the important parts from the rest of the system

* Understanding the subdomains of your system enables you to break down the problem space

* Subdomains are abstract concepts
    * don’t get subdomains confused with the organizational structure of a company
    
* **By distilling the problem domain you reduce complexity by dividing and conquering the problem**
    * Smaller models can be created and understood within the context of a subdomain
    * This removes the need for a single large model to represent the entire problem domain

---

## The Core Domain

* To know where to invest the most effort and quality, it’s crucial to understand where the core domains are, because these are key to making software successful

* This knowledge is distilled from knowledge‐crunching sessions working in collaboration with domain experts to understand the most important aspect of the product under design and development

* **What is core changes over time!**

* One of the fundamental shifts in mentality required for writing software for complex core domains is to focus on the product rather than view it as a standalone project
---

## An Example: Auction Web Site (1/4)

<div class='left' style='float:left;width:50%'>

* We will consider the domain model of an online auction site

* There are many different components that make up the large overall system

* Some parts will be found in any online system, but some will be unique to the domain and specific business

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/online_auction_ex01.png" width="40">}}

</div>

---

## An Example: Auction Web Site (2/4)

<div class='left' style='float:left;width:50%'>

* Membership: the area of the systems that deals
with the registrations, preferences, and details of members.

* Seller: all the processes and behaviors that deal with the seller
activities

* Auction: managing the timing of auctions and dealing with bid activity

* Listings: the catalogs of items that are available on the auction site

* Dispute resolution: the area that deals with disputes between members and sellers

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/online_auction_ex02.png" width="40">}}

</div>

---

## An Example: Auction Web Site (3/4)

<div class='left' style='float:left;width:50%'>

* Core domains of the system are the *seller* and the
*auction*.
    * The seller domain contains the ratings for a seller and the domain logic for determining seller fees
    * The auction core domain is the mechanism for running an auction and handling bids

* The *membership* and *listing* domains support the core domains by providing bidders the opportunity to create accounts and find items for sale.

* The *dispute resolution* domain is generic in that it can be served using a commercial off‐the‐shelf package

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/online_auction_ex03.png" width="40">}}

</div>

---

## An Example: Auction Web Site (4/4)

<div class='left' style='float:left;width:50%'>

{{< image src="assets/online_auction_ex04.png" width="45" >}}

</div>

<div class='right' style='float:right;width:50%'>

* The online auction site is divided into two physical applications

* The dispute domain has been fulfilled by an off‐the‐shelf
package while the core and supporting domains will be designed using a custom web application

</div>

---

## Anticorruption Layer

{{< image src="assets/anticorruption_layer.png" width="60" >}}

* Wraps the communication with legacy or third‐party code to protect the integrity of a bounded context
* Manages the transformation of one context’s view to another, retaining the integrity of new code

---

## Hints

1. *Focus on clean boundaries over perfect models!*

1. *The Core Domain doesn’t always have to be perfect the first time!*

1. *Build Subdomains for replacement rather than reuse!*

---

# Model-Driven Design

---

## From the Problem Space
## to the Solution Space

* It is important to implement in code the analysis model that was produced during knowledge-crunching sessions

* DDD requires to produce a single model that serves as an analysis model for business people to understand and which is implemented using the same terminology and concepts in code

* This process is known as **Model‐Driven Design** (MDD)

---

## Model-Driven Design

* Model‐Driven Design is the process of binding an analysis model to a code implementation model
    * ensuring that both stay in sync and are useful during evolution
    
* Model‐Driven Design differs from Domain-Driven Design
    * MDD it is focused on implementation and any constraints that may require changes to an initial model
    * DDD focuses on language, collaboration, and domain knowledge
    
* The two complement each other
    * The MDD approach enables domain knowledge and the shared language to be incorporated into a software model that mirrors the language and mental models of the business experts

---

## The Domain Model

{{< image src="assets/domain_model_role.png" width="60">}}

* The Domain Model is at the center of Domain‐Driven Design
    * When it is expressed as a code implementation, it is bound to the analysis model through the use of the shared language
    * The model contains only what is relevant to solve problems in the context of the system

---

## Domain Model Evolution (without DDD)

{{< image src="assets/domain_model_evolution_wrong.png" width="80">}}

---

## Domain Model Evolution (following DDD)

{{< image src="assets/domain_model_evolution_good.png" width="75">}}

---

## About the Importance of the Ubiquitous Language

> *Ubiquitous Language (UL) represents the Key of an effective DDD-MDD approach*

* UL enables teams to organize both the mental and the code model with ease
    * It achieves an unambiguous meaning because of the shared understanding that it brings to the teams
    
* UL also provides clarity and consistency in meaning
    * The language is ultimately expressed in code, but speech, sketch, and documentation are also important for creating the language
    * The language is constantly explored, verified, and refined with new insights and greater knowledge

---

## An Example (1/5)

* A business user is describing the process of customers at an e‐commerce site requesting a replacement for an order that wasn’t delivered

> When a customer doesn’t receive her goods, she can request a new order for free.
> She logs into her account and clicks on the I Have Not Received My Items button.
> If she has already been flagged as having received a free order, she can’t get another one without speaking to customer service.
> Otherwise, we will send her a free order and update the database to show that this customer has already claimed for a lost item. We will then contact the courier to see if we can claim back the cost of the lost order.

---

## An Example (2/5)

> When a customer doesn’t receive her goods, she can request a new order for free.
> She logs into her account and clicks on the I Have Not Received My Items button.
> If she has already been flagged as having received a free order, she can’t get another one without speaking to customer service.
> Otherwise, we will send her a free order and update the database to show that this customer has already claimed for a lost item. We will then contact the courier to see if we can claim back the cost of the lost order.

* *Is the use-case description focused on the business process?*

---

## An Example (3/5)

> When a customer doesn’t receive her goods, she can request a new order for free.
> **She logs into her account and clicks on the I Have Not Received My Items button.**
> If she has already been flagged as having received a free order, she can’t get another one without speaking to customer service.
> Otherwise, we will send her a free order and update the database to show that this customer has already claimed for a lost item. We will then contact the courier to see if we can claim back the cost of the lost order.

* *Is this sentence relevant for the domain model?*
* *Does it gives any value or insight?*

---

## An Example (4/5)

> When a customer doesn’t receive her goods, she can request a new order for free.
> She logs into her account and clicks on the I Have Not Received My Items button.
> **If she has already been flagged as having received a free order, she can’t get another one without speaking to customer service.**
> Otherwise, we will send her a free order and update the database to show that this customer has already claimed for a lost item. We will then contact the courier to see if we can claim back the cost of the lost order.

* *Some experts may have experience with databases and may go as far as suggesting
data schemas!*
    * *This gives the team no deep understanding of the domain*

---

## An Example (5/5)
### Rewriting the Use Case Description

> If you have not received an order, you can submit an undelivered order notification. 
> If this is your first claim, a replacement order is created.
> If you have made a claim before, your claim case is opened and assigned to a customer service representative, who will investigate the claim.
> In all cases, a lost mail compensation case is opened and sent to the courier with details of the consignment that was undelivered.

* *This new description reveals many important domain concepts missing before*
* *The customer is no longer the most important concept!*

---

## Best Practices for Shaping the UL

1. Ensure the **linguistic consistency**
    * If you are using a term in code that the domain expert doesn’t say, you need to check it with her
        * It could be that you have found a concept that was required, so it needs to be added to the UL and understood by the domain expert
        * Alternatively, maybe you misunderstood something that the domain expert said; therefore, you should rectify the code with the correct term

1. Create a **glossary of domain terms** with the domain expert to avoid confusion
    * Ensure that you use one word for a specific concept
    * Don’t use terms that have a specific meaning in software development (such as design pattern names...)

1. Use the language to drive the design of your code
    * The **UL should be visible everywhere**, from namespaces to classes, and from properties to method names

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

# Domain Model Patterns

---

## The Domain Layer

<div class='left' style='float:left;width:50%'>

* The **Domain Layer** is the area of the code that contains the domain model
    * Isolation among *domain model complexity* and *application technical complexity*

* Several Patterns available to implement a domain model in code

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/domain_layer.png" >}}

</div>

---

## Domain Model Pattern

<div class='left' style='float:left;width:50%'>

{{< image src="assets/domain_model_pattern.png" >}}
</div>

<div class='right' style='float:right;width:50%'>

* **A good fit for complex domains with rich business logic**
    * An Object-Oriented Model with Data and Behaviour
    * The Domain Model is separated from the infrastructure

* Generally designed according to the DDD Building Blocks

* Do not confuse the domain model with the data persistence model
    * The first incorporates also business processes, associations, rules, ...

</div>

---

## Domain Model Pattern - An Example

{{< image src="assets/domain_model_example1.png" >}}

---

## Domain Model Pattern - Details

* It is a pure object‐oriented approach that involves creating an abstract model of the real business domain and is useful when dealing with complex logic and workflow

* It is persistence ignorant and relies on mapper classes and other abstraction patterns to persist and retrieve business entities

* **Avoid trying to apply the domain model pattern for everything**
    * It is a good candidate when you have to model complex logic or part of the problem domain that requires clarity because it’s important, or will change often due to continued investment
---

## Transaction Script Pattern

<div class='left' style='float:left;width:50%'>

{{< image src="assets/transaction_script_pattern.png" >}}
</div>

<div class='right' style='float:right;width:50%'>

* The transaction script pattern follows a procedural style of development rather than an object‐oriented approach

    * A single procedure is created for each of your business transactions, and it is grouped in some kind of static manager or service class
    * Each procedure contains all the business logic that is required to complete the business transaction from the workflow, business rules, and validation checks to persistence in the database.

</div>

---

## Transaction Script Pattern - An Example

{{< image src="assets/transaction_script_example1.png" >}}

---

## Transaction Script Pattern - Details

* It is simple to understand and can be fast to get new team members up to speed without prior knowledge of the pattern

    * Useful for the parts of your domain that have little or no logic
    * If logic becomes complex, the transaction script pattern can quickly become hard to manage because, by its nature, duplication can occur quickly

* The problems with the transaction script pattern are revealed when an application grows and the business logic complexities increase

---

## Table Module Pattern

* The table module pattern maps the object model to the database model
    * A single object represents a table or view or a document in the database
    * The object is responsible for all persistence needs along with business logic behavior

* At a first glance, it might not be a good fit for DDD
    * it is good for simpler parts of the domain that are isolated by a bounded context and that are simply forms over data
---

## Anemic Domain Module Pattern

* It is very similar to the domain model pattern
    * There are domain objects that represent the business domain...
    * ... but without any behaviors!

* It is a good candidate for parts of your domain model that have little logic
    * The anemic domain model can incorporate the UL and be a good first step when trying to create a rich domain model
    * It is a good choice when associated to a functional programming approach

---

# Bounded Contexts

---

*The DDD Basic Approach*

# Use Bounded Contexts
# to ensure Domain Integrity 

> **Bounded Context**
> * Defines the applicability of the model
> * Ensures that domain concepts outside a model's context do not distract from the problem it was designed to solve
> * Makes explicit what the model is responsible for and what it is not

---

## The Challenges of a Single Model

* At the core of Domain‐Driven Design there is the need to create explicit, evolvable models in code that align with the shared conceptual models
    * As new domain insights are gained, they can be incorporated into the model efficiently
    
* If a single model is used for an entire system, concepts from one area of the model can be confused with similar-sounding concepts from another
area of the system (and even become coupled to them)

* DDD advocates that you break up a large complex system into multiple code models

---

## (1) A model can grow in complexity

{{< image src="assets/bounded_challenges_1.png" >}}

---

## (2) Multiple Teams working on a Single Model

{{< image src="assets/bounded_challenges_2.png" >}}

---

## (3) Ambiguity in the Language of the Model

{{< image src="assets/bounded_challenges_3.png" >}}

---

## (4) The Applicability of a Domain Concept...

{{< image src="assets/bounded_challenges_4.png" >}}

---

## (4) ... and the violation of the SRP

{{< image src="assets/bounded_challenges_4b.png" >}}

---

## Divide and Conquer with Bounded Context

{{< image src="assets/bounded_contexts_transition.png" >}}

---

## Subdomains vs. Bounded Contexts

{{< image src="assets/subdomains_and_bounded_contexts.png" >}}

---

## The Anatomy of a Bounded Context

<div class='left' style='float:left;width:60%'>

{{< image src="assets/bounded-context.png" width="55">}}

</div>

<div class='right' style='float:right;width:40%'>

* A Bounded Context owns the *vertical slide* of functionalities
    * from the presentation layer
    * through the domain logic layer
    * on to the persistence
    * and even to the data storage

* *Autonomy* is a key feature of each bounded context

* Relations among bounded contexts must be described using context maps

</div>

---

# Software Architectures
# for the Domain-Driven Design

---

## Towards a
# Clean Architecture

DDD does not enforce the use of a particular architectural style  
... as long as the chosen one *must support the isolation of the domain logic*!

* Several architectural styles can be used
    * e.g. ubiquitous layered architecture, hexagonal architecture, ...

* The Microservice Architectural Style is the closest one to the DDD philosophy
---

## A "revised" Layered Architecture

<div class='left' style='float:left;width:50%'>

{{< image src="assets/layered_architecture.png" >}}

</div>

<div class='right' style='float:right;width:50%'>

* At the heart of the architecture there is the domain layer

* Surrounding the domain layer there is an application layer
    * It abstracts the low-level details of the domain behind a coarse-grained API
    * The APIs represent the business use cases of the application

* The domain logic and application layers are isolated and protected from the "external" accidental complexities
</div>

---

## Dependency Inversion

{{< image src="assets/dependency-inversion.png" width="50">}}

* The domain layer and application layers at the center of the architecture should not depend on any other layers
* The application layer is dependent only on the domain layer
    * it orchestrates the handling of the use cases by delegating to the domain layer

---

## The Application Service Layer

* **Represents the use cases and behavior of the application**
    * use cases are implemented as application services that contain application logic to coordinate the fulfillment of a use case by delegating to the domain and infrastructural layers

* **Operates at a higher level of abstraction than the domain objects**
    * exposing a coarse-grained set of services while hiding the details of the domain layer ("*what the system does, but not how it does it"*)
    * is a facade for the domain model

* **Enables the support of different clients without compromising the domain layer's integrity**
    * clients must adapt to the application layer APIs, transforming the output in a suitable format

---

## The Infrastructural Layer

* Contains all the technical details of the software system
    * such as enabling the application to be consumed, whether by humans via a user interface or by applications via a set of web service or message endpoints
    * is responsible for the technical implementation of storing information on the state of domain objects

* Can provide capabilities for logging, security, notification, and integration with other bounded contexts and applications

---

<div class='left' style='float:left;width:40%'>

# Clean Architecture

1. Independent of Frameworks
1. Testable
1. Independent of the UI
1. Independent of the data persistence layer
1. Independent of any external agency

> *No "name" in an outer circle can be mentioned by an inner circle*

</div>

<div class='right' style='float:right;width:60%'>

{{< image src="assets/CleanArchitecture.jpg" >}}

(from *Clean Architecture* by Robert C. Martin)

</div>

---

<div class='left' style='float:left;width:50%'>

# Microservices Architecture

* Nowadays, an extremely popular architecture style
    * ... heavily inspired by DDD Bounded Context concept!

* The goal is to have a high level of decoupling
    * Microservices favour duplication over reuse

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/microservices_topology.png"  width="40">}}

(from *Fundamentals of Software Architecture*  
by R. Richards and N. Ford)

</div>

---

## Microservices and Bounded Contexts

* Each (micro)service models a domain/workflow
    * Each service represents a bounded context...
    * ... or more than one!

* Service Boundaries delineated according:
    * *Purpose* - each microservice should be extremely functionally cohesive
    * *Transactions* - each microservice must reduce issues related to transaction collaboration among domain entities
    * *Choreography* - excellent domain isolation might generate a overhead in communications

* Data Isolation
    * Avoid all kind of coupling, especially shared data schemas and databases (used as integration point)

---

## Sidecar Pattern with Service Plane

{{< image src="assets/sidecar_pattern.png" >}}

---

<div class='left' style='float:left;width:50%'>

*For a good architecture...*
# Be Pragmatic, Yet Visionary

* *Pragmatic* - Dealing with things sensibly and realistically in a way that is based on a practical rather than a theoretical considerations

* *Visionary* - Thinking about or planning the future with imagination or wisdom

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/pragmatic_visionary.png" width="30">}}

(from *Fundamentals of Software Architecture*  
by R. Richards and N. Ford)

</div>

---

# Bounded Context Integration

---

