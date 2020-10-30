 
+++

title = "[LSS2021] DDD > Building Blocks"
description = ""
outputs = ["Reveal"]
aliases = [
    "/ddd-building-blocks/"
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
## 05 - Tactical Buildings Blocks
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

## Outline

1. Building Blocks Overview

1. Value Objects

1. ...

---

## Domain-Driven Design Tactical Patterns

* Introduced by E. Evans to enable the creation of effective domain models
    * are built around the OOP/FP techniques, best practices, ...
    * they represent a guideline for DDD designers/developers

* Their role is to help to manage complexity and ensure clarity behavior within the domain model
    * **each building block is designed to have a single responsibility**
    * they constitute the **metamodel** developers can use to map domain concepts into the code, according to the UL

---

## Building Blocks Categorization

<div class='left' style='float:left;width:40%'>

1. **Domain Modeling Patterns** -- represent the policies and logic within the problem domain (relationships among objects, model rules, ...) and helping to achieve the MDD
    * *Entities*
    * *Value Objects*
    * *Domain Services*
    * (*Modules*)

1. **Lifecycle Patterns** -- deal with the creation and persistence of the objects that represent the domain structure
    * *Aggregates*
    * *Factories*
    * *Repositories*



</div>

<div class='right' style='float:right;width:60%'>

{{< image src="assets/evans_tacticals_overview.png" width="55">}}

3. **Emerging Patterns** -- collateral patterns respect to Evans' original definition
    * *Domain Events*
    * *Event Sourcing*

</div>

---

{{< image src="assets/tacticals_overview.png" >}}

---

## DDD into the code - an Example

{{< image src="assets/js_example.png" width="100">}}

---

<div class='left' style='float:left;width:50%'>

# Value Objects

* Value objects represent **the elements or concepts of your domain that are known only by their characteristics**
    * they are used as descriptors for elements in your model
    * they do not require a unique identity
    * they are defined by their attributes

* Value objects are treated as **immutable**
    * once created, they can never alter their state

* Are often passed as parameters in messages between object
    * they are temporarily created for an operation and then discarded

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/value_objects.png" >}}

</div>

---

<div class='left' style='float:left;width:50%'>

# Entities

* An entity represents **a concept in the domain that is defined by its identity rather than its attributes**
    * its identity remains fixed throughout its lifecycle while its attributes may change

* Entities are **mutable** as the attributes can change
    * they can delegate work
    * are also called "reference objects"

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/entities.png" >}}

</div>

---

# Domain Services

* Domain services **encapsulate domain logic and concepts that are not naturally modeled as value objects or entities in your model**

* Domain services have no identity or state
    * their responsibility is to orchestrate business logic using entities and value objects

---

## Code Example

```javascript
// An example of a Value Object

class Price {
  amountInEuroCents: number
  
  inEuros(): number {
    // implementation
  }

  // other logic
}
```

```javascript
// An example of an Entity

class ShoppingCart {
  id: ShoppingCartId;

  items: Array<ShoppingCartItem>
    
  addItem(item: ShoppingCartItem) {
    // implementation
  }
}
```

```javascript
// For e-commerce, an example of a domain service would be the checkout step

function checkout(shoppingCart: ShoppingCart, ...) {
  // do service stuff
}
```

---

# Modules

* The Modules in the domain layer should emerge as a meaningful part of the model
    * ... telling the story of the domain on a larger scale

* There should be low coupling between Modules and high cohesion within them, both code-wise and concept-wise:
    * *low coupling*: there is a limit to how many things a person can think about at once
    * *high cohesion*: incoherent fragments of ideas are as hard to understand as an undifferentiated melt of ideas 

> **Best Practices**
>
> * Give the Modules names that become part of the Ubiquitous Language: modules and their names should reflect insight into the domain
> * When creating modules, favor conceptual clarity over technical convenience (if both are not achievable together)

---

# Aggregates

* Entities and value objects collaborate to form complex relationships that meet invariants within the domain model
    * When dealing with large interconnected associations of objects, it is often difficult to ensure consistency and concurrency when performing actions against domain objects

* The Aggregate pattern **ensures consistency and defines transactional concurrency boundaries for object graphs**
    * Large models are **split by invariants** and grouped into aggregates of entities and value objects that are treated as a conceptual whole
    * Relationships between aggregate roots should be implemented by keeping a reference to the ID of another aggregate root and not a reference to the object itself

---

# Aggregates

{{< image src="assets/aggregates.png" >}}

---

<div class='left' style='float:left;width:60%'>

# Aggregate Root

{{< image src="assets/aggregate_root.png" width="55">}}

</div>

<div class='right' style='float:right;width:40%'>

* An aggregate root acts as the entry point into the aggregate
    * No other outside entity or value object can hold a reference to an object within the aggregate

*  Objects outside the aggregate can only reference the aggregate root of another aggregate

* Any changes to objects in the aggregate need to come through the root

* The root encapsulates the data of the aggregate and only exposes behaviors to change it

</div>

---

# Factories

* A factory **ensures that all invariants are met before the domain object is created**
    * If the creation of an entity or a value object is complex, you should delegate the construction to a factory
    * If a domain object is simple and has no special rules for valid construction, favor a constructor method over a factory object
    
* Factories can be also used when re‐creating domain objects from persistent storage

{{< image src="assets/factories.png" >}}

---

# Repositories

<div class='left' style='float:left;width:50%'>

{{< image src="assets/repositories.png" width="45">}}

</div>

<div class='right' style='float:right;width:50%'>

* A repository is a pattern that **abstracts the underlying persistence store from the model allowing you to create a model without thinking about infrastructure concerns**

* Because an aggregate is an "atomic unit", is not possible to persist changes to it without persisting the entire aggregate
    * The repository is the mechanism that you should use to retrieve and persist aggregates
    
* A repository *is an infrastructure concern*
    * It is not always necessary to abstract away the underlying framework doing all the hard work

</div>

---

<div class='left' style='float:left;width:50%'>

# Domain Events

* A domain event is **something that has happened in the problem domain that the business cares about**

* Events can be used to record changes to a model, or as a form of communication across aggregates

* Often an operation on a single aggregate root can result in side effects that are outside the aggregate root boundary
    * In this case: other aggregates within the model can listen for events and act accordingly

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/domain_events.png" width="45">}}

> *Domain Events help to uncouple bounded context and to focus on the "when"*

</div>

---

# Event Sourcing

<div class='left' style='float:left;width:30%'>

* An alternative to traditional snapshot‐only persistence

* Event Sourcing **allows to store the series of events that lead up to the state**

</div>

<div class='right' style='float:right;width:70%'>

{{< image src="assets/event_sourcing.png" width="60">}}

</div>

---

## Summing up (1/2)

| Building Block | Salient Points           
| --- |---
| Entities | - Are defined by their identity <br> - Identity remains constant throughout its lifetime <br> - Are responsible for equality checks
| Value Objects | - Describe the properties and characteristics within the problem domain <br> - Have no identity <br> - Are immutable
| Domain Services | - Contain domain logic that can't naturally be placed in an entity or value object <br> - Have no internal state
| Modules | - Are used to decompose, organize, and increase the readability of the domain model <br> - Help to define clear boundaries between domain objects <br> - They operate on a higher level of abstraction than aggregates and entities

---

## Summing up (2/2)

| Building Block | Salient Points           
| --- |---
| Aggregates | - Decompose large object graphs into small clusters of domain objects to reduce implementation complexity <br> - Represent domain concepts, not just generic collections of domain objects <br> - Are based around domain invariants
|  Factories | - Separate use from construction <br> - Encapsulate complex entity and value object construction
| Repositories | - Expose the interface of an in-memory collection of aggregate roots <br> - Provide the retrieval and persistence needs of aggregate roots <br> - Decouple the domain layer from database strategies and infrastructure code
| Domain Events | - Makes domain events more explicit in code <br> - They are part of the ubiquitous language (UL)
| Event Sourcing | - Replaces traditional snapshot‐only storage with a full history of events that produce the current state

---

<div class='left' style='float:left;width:50%'>

</div>

<div class='right' style='float:right;width:50%'>

</div>


