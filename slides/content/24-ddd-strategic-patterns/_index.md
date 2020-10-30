 
+++

title = "[LSS2021] DDD > Strategic Patterns"
description = ""
outputs = ["Reveal"]
aliases = [
    "/ddd-strategic-patterns/"
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
## 04 - Strategic Patterns
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

## Outline

1. Software Architectures for the DDD

1. Bounded Context Integration

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

<div class='left' style='float:left;width:50%'>

## A "revised" Layered Architecture

* At the heart of the architecture there is the domain layer

* Surrounding the domain layer there is an application layer
    * It abstracts the low-level details of the domain behind a coarse-grained API
    * The APIs represent the business use cases of the application

* The domain logic and application layers are isolated and protected from the "external" accidental complexities

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/layered_architecture.png" >}}

</div>

---

# Dependency Inversion

<div class='left' style='float:left;width:65%'>

{{< image src="assets/dependency-inversion.png" width="65">}}

</div>

<div class='right' style='float:right;width:35%'>

* The domain layer and application layers at the center of the architecture should not depend on any other layers

* The application layer is dependent only on the domain layer
    * it orchestrates the handling of the use cases by delegating to the domain layer

</div>

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

* **Contains all the technical details of the software system**
    * such as enabling the application to be consumed, whether by humans via a user interface or by applications via a set of web service or message endpoints
    * is responsible for the technical implementation of storing information on the state of domain objects

* Can provide capabilities for logging, security, notification, and integration with other bounded contexts and applications

---

<div class='left' style='float:left;width:50%'>

# Clean Architecture

1. Independent of Frameworks
1. Testable
1. Independent of the UI
1. Independent of the data persistence layer
1. Independent of any external agency

> *No "name" in an outer circle can be mentioned by an inner circle*

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/CleanArchitecture.jpg" width="40">}}

(from *Clean Architecture* by Robert C. Martin)

</div>

---

<div class='left' style='float:left;width:50%'>

# Microservices Architecture

* Nowadays, an extremely popular architecture style
    * ... heavily inspired by DDD Bounded Context concept!

* The goal is to have a high level of decoupling
    * Microservices favor duplication over reuse

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
    * *Choreography* - excellent domain isolation might generate overhead in communications

* Data Isolation
    * Avoid all kind of coupling, especially shared data schemas and databases (used as integration point)

---

## Sidecar Pattern with Service Plane

{{< image src="assets/sidecar_pattern.png" >}}

---

<div class='left' style='float:left;width:50%'>

*For good architectures...*
# Be Pragmatic, Yet Visionary

* *Pragmatic* - Dealing with things sensibly and realistically in a way that is based on practical rather than theoretical considerations

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

# The Issue

> *After identifying the bounded contexts in your system the next step is to decide how you will integrate them to carry out full business use cases*

* Software services need to have **relationships** with each other to provide advanced behaviors
    * relationships can reflect the domain and domain experts expectations
    * relationships may impact the scalability of the (distributed) system

* A good place where to start identifying relationships is by identifying your bounded contexts

---

<div class='left' style='float:left;width:50%'>

# Bounded Contexts
# must be Autonomous

</div>

<div class='right' style='float:right;width:50%'>

* As systems grow, dependencies become more significant in a negative way
    * You should strive to avoid most forms of coupling unless you have a very good reason...
    
* A coupling on code means that one team can break another team’s code
    * ... or cause bottlenecks that slow down the delivery of new features!
    
* A runtime coupling between subsystems means that one system cannot function without the other

</div>

---

## Bunded Contexts Integration at the code level
## (to be avoided)

* Multiple Bounded Contexts within the same project
    * Namespace/packages to enforce the separation
    * SRP vs. DRY: which one to prefer?

* Integration via a Shared Database
    * Distinct domain concepts mapped to the same database concept with multiple attributes

* Multiple teams working in a single codebase
    * Git Workflow is not a cure-all 

* ...
---

## "Physical" Boundaries

{{< image src="assets/physical_boundaries.png">}}

---

<div class='left' style='float:left;width:50%'>

## Integration with Legacy Systems

* *Bubble Context*
    * An ad-hoc ACL mediates the communication among the bubble context and the legacy system

* *Autonomous Bubble Context*
    * The ACL acts as a synchronization barrier, the interaction with the legacy system(s) is asynchronous

* *Exposing Legacy Systems as Services*
    * aka Open Host Service Pattern

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/legacy_integration.png" width="30">}}

[www.domainlanguage.com/ddd/surrounded-by-legacy-software](https://www.domainlanguage.com/ddd/surrounded-by-legacy-software)

</div>

---

# Integration Strategies
# for Distributed Bounded Contexts

<div class='left' style='float:left;width:50%'>

When bounded contexts are separate services that communicate with each other over the network we have a distributed system.
    
In these systems, choosing the wrong integration strategy might cause slow or unreliable systems that lead to negative business impacts!

</div>

<div class='right' style='float:right;width:50%'>

* **Bad Practices**
    * Database and/or Flat File Integration
    * Remote Procedure Call (RPC)

* **Good Practices**
    * Messaging
    * REST

</div>

---

## The (old) RPC Scenario

{{< image src="assets/distributed_rpc_scenario.png" >}}

---

## Event-Driven Reactive DDD

{{< image src="assets/distributed_reactive_scenario.png" >}}

---

## Service-Oriented Architectures & Reactive DDD

> ***Reactive programming** is a set of low‐level technical guidelines that lead to loosely-coupled software components*

> ***SOA** is a high‐level concept that facilitates loosely coupled business capabilities*

The combination of the two appears an optimal direction for the DDD approach!

---

## SOA & Reactive DDD

<div class='left' style='float:left;width:50%'>

* How to combine these benefits with DDD?
    * **View your bounded contexts as SOA services, so that you can map high‐level bounded contexts onto low‐level, event‐driven software components**

* Combining SOA and reactive programming provides a platform to:
    * align your infrastructure with business priorities
    * deal with scalability and reliability challenges
    * organize your teams by aligning them with bounded contexts to reduce communication overhead

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/soa_and_reactive.png" width="45">}}

</div>

---

## Domain-Driven Design & REST

* **Resources** - fit well with DDD because domain concepts can be expressed as resources, further spreading the UL
    * E.g. In a financial domain, transactions that transfer funds from one account to another
        * The UL has an entry for each type of transaction, such as *B2B Transaction* or *Personal Transaction*, that become resources accessible from proper URIs
            * `http://mydomain.com/B2bTransactions`
            * `http://mydomain.com/PersonalTransactions`

* **Hypermedia** - an opportunity for DDD to express the domain concepts more explicitly
    * E.g. In a car insurance policy, each step of the application process could be expressed as links in hypermedia to the next possible steps, according with the UL terminology
    * It can be used to model also workflows or domain processes

---

## Bounded Context Integration via REST

* *Be careful!* - A direct exposition of the domain model via RESTful API might be brittle!
    * **Each change in the domain model is directly reflected in the system interface**

* A viable solution
    * introduce a separate Bounded Context for the system's interface layer and use appropriate strategies to access the actual Core Domain from the system's interface model
    * so, the Core Domain is decoupled from the system's interface model
    * the interface model must be driven by the use cases

* A step further
    * CQRS - Command Query Responsibility Segregation