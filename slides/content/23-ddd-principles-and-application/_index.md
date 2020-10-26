 
+++

title = "[LSS2021] DDD > Principles, Philosophy"
description = ""
outputs = ["Reveal"]
aliases = [
    "/ddd-principles-and-application/"
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
## 03 - Principles, Application and Philosophy
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

# Domain-Driven Design

## *is*

a philosophy born out of a need to realign  
the focus of development teams writing software for complex domains

## *is not*

a framework or a set of predefined templates that you can apply to a project  
(although there is value in design patterns and frameworks)

---

### Designers and developers Teams  
### should embrace the problem domain they are working within  
### and look outside of their technical tunnel vision!

The most fundamental point to being a grat software specialist  
is to understand the problem domain you work within  
and value this as much as you value your technical expertise

---

*The whole DDD idea in 6 words*
# Merge the People,
# Split the Software!

(by Alberto Brandolini - [eventstorming.com](https://www.eventstorming.com/))

---

# Common Problems
## When Starting with Domain-Driven Design

---

<div class='left' style='float:left;width:60%'>

{{< image src="assets/evans_model.png" >}}

</div>

<div class='right' style='float:right;width:40%'>

# #1
## Overemphasizing the importance of tactical patterns

* Using the same architecture for all Bounded Contexts

* Striving for Tactical Pattern perfection

* Mistaking the Building Blocks for the value of DDD

</div>

---

<div class='left' style='float:left;width:40%'>

# #2
## Focusing on Code rather than the Principles of DDD

</div>

<div class='right' style='float:right;width:60%'>

{{< image src="assets/tech_details.png" >}}

</div>

---

<div class='left' style='float:left;width:60%'>

{{< image src="assets/ul.png" >}}

</div>

<div class='right' style='float:right;width:40%'>

# #3
## Missing the Real value of DDD: Collaboration, Communication and Context

* Underestimating the importance of Context

* Causing Ambiguity and Misinterpretations by failing to create a UL

* Designing technical-focused solutions due to a lack of collaboration

</div>

---

<div class='left' style='float:left;width:40%'>

# #4
## Keep the code uncoupled to the model

</div>

<div class='right' style='float:right;width:60%'>

{{< image src="assets/model_code_coupling.png" >}}

</div>

---

<div class='left' style='float:left;width:60%'>

{{< image src="assets/model.png" >}}

</div>

<div class='right' style='float:right;width:40%'>

# #5
## Spending too much time on what's not important

* Making Simple Problems Complex

* Applying DDD Principles to a trivial domain with little
business expectation

* Using the Domain Model Pattern for every Bounded Context

* Ask yourself: is it worth this extra complexity?

</div>

---

# #6
## Underestimating the cost of applying DDD

* Trying to succeed without a motivated and focused team

* Attempting collaboration when a Domain Expert is not behind the project

* Learning in a noniterative development methodology

* Applying DDD to every problem

* Sacrificing pragmatism for needless purity

* Always striving for "beautiful code"

---

# Applying the
# Domain-Driven Design Approach

---

# Domain-Driven Design "Preconditions"

1. A skilled, motivated, and passionate team that is eager to learn

1. A nontrivial problem domain that is important to your business

1. Domain experts who are aligned to the vision of the project

1. An iterative development methodology

---

# Educating your team

> *When communicating the philosophy of DDD to your team, focus not on the pattern language but, instead, on the alignment with the business, the importance of strategic contexts, and the focus on the language used to describe a model.*

# Speaking to your business

> *Your business stakeholders don’t want to hear about the next new development philosophy or methodology. Talk of the need for the development team to be more aligned to the vision and intent of the business. A stakeholder will see the value in the development team spending time with business experts and aligning themselves to the expectations of the business.*

---

*An effective way to engage stakeholders*
# Behavior-Driven Development (BDD)

* A methodology focused on capturing the behavior of a system and then driving design from the outside in
    * based on the Test-Driven Development (TDD)

* BDD uses concrete domain scenarios during conversations with domain experts and stakeholders to describe the behaviour of a system
    * follows the GWT approach (Given, When, Then) to structure conversations

---

# BDD - An Example (1/3)

> feature: *Free Delivery for Large Orders* (e-commerce website)

> ### USER STORY
> In order to increase the average order total, which is €50, as the marketing manager, I would like to offer free delivery if customers spend €60.

---

# BDD - An Example (2/3)

<div class='left' style='float:left;width:50%'>

### BDD Scenario #1
*Scenario*: Customer satisfies the spend threshold for free delivery
 
*Given*: Threshold for free delivery is set at €60  
*And*: I am a customer who has a basket totaling €50  
*When*: I add an item to my basket costing €11  
*Then*: I should be offered free delivery

</div>

<div class='right' style='float:right;width:50%'>

### BDD Scenario #2
*Scenario*: Customer does not satisfy the spend threshold for free delivery but triggers message to up sale

*Given*: Threshold for free delivery is set at €60  
*And*: I am a customer who has a basket totaling €50  
*When*: I add an item to my basket costing €9  
*Then*: I should be told that if I increase my basket goods total by €1, I will be offered free delivery

</div>

---

# BDD - An Example (3/3)

> ### (Another) USER STORY
> In order to target different countries that have different spending habits, as the marketing manager, I would like to set the qualifying order total threshold for each delivery country.

---

# DDD "Step-by-Step"

1. Engaging with an Expert
1. Select a Behavior and Model Around a Concrete Scenario
1. Collaborate with the Domain Expert on the Most Interesting Parts
1. **Evolve UL to Remove Ambiguity**
1. Throw Away Your First Model, and Your Second
1. Implement the Model in Code
1. Keep the Solution Simple and Your Code Boring
1. Integrate the Model Early and Often
1. **Nontechnical Refactoring**
1. Decompose Your Solution Space

---

# "Learn to Unlearn"

> *Don’t get attached to ideas; trial and error is required to reveal concepts in the domain that will help you solve business problems.*
>
> *Code within the testing namespace alongside the tests until you are happy with the design; you will be a lot happier to spike solutions and throw away a useless model that you haven’t committed to the application namespace.*
>
> (by S. Millett)

---

## Tackling Ambiguity - An Example (1/2)

> *In an e-commerce site, introduce the rule that prevents overseas customers from adding more than 50 of any one item to their basket*

```java
public class Basket {
    private BasketItems items;

    public void add(Product product) {
        if (basketContainsAnItemFor(product)) {
            var item_quantity = getItemFor(product).quantity().add(new Quantity(1));
            if (item_quantity.containsMoreThan(new Quantity(50))) {
                throw new Exception("You can only purchase 50 of a single product.");
            } else {
                getItemFor(product).increaseItemQuantityBy(new Quantity(1));
            }
        } else {
            items.Add(BasketItemFactory.createItemFor(product));
        }    
    }
}
```

---

## Tackling Ambiguity - An Example (2/2)

Refactoring

```java
public class Basket {
    private BasketItems items;

    public void add(Product product) {
        if (basketContainsAnItemFor(product)) {
            var item_quantity = getItemFor(product).quantity().add(new Quantity(1));
            if (OverSeasSellingPolicy.isSatisfiedBy(item_quantity)) {
                getItemFor(product).increaseItemQuantityBy(new Quantity(1));
            } else {
                throw new OverSeasSellingPolicyException(String.format(
                    "You can only purchase %d of a single product.", OverSeasSellingPolicy.THRESHOLD));
            }
        } else {
            items.Add(BasketItemFactory.createItemFor(product));
        }    
    }
}
```

```java
public class OverSeasSellingPolicy {
    public static final Quantity THRESHOLD = new Quantity(50);

    public static boolean isSatisfiedBy(Quantity item_quantity, Country country) {
        return !item_quantity.containsMoreThan(THRESHOLD);
    }
}
```

---

## Relations between
# Domain-Driven Design
## and
# Agile Software Development

---

# *"Domain-Driven Design belongs on the shelf of every **thoughtful** software developer"*
### by Kent Beck
(one of the 17 original signatories of the Agile Manifesto)

--- 

A key problem within many Agile projects is that
## the deep thinking about design can be ignored to produce more features

* Software designers coerced into adding features or functionalities due to time and budged pressures
    * (sometimes) the important aspect of the overall system design is left behind within security and interoperability aspects

* Often, in the Agile Development Process, the code and related databases become the focus of attention
    * business logic, business processes, domain requirements are ignored in the rush to produce a rapid prototype to impress management

---

## Common design issues in Agile

* Processing logic is implemented in the UI
* Data Persistence is mixed in with Processing logic
* Databases are overloaded with slow, locking queries
* Stateful architectures are preferred in place of stateless ones
* (Micro)Services are coupled together
* Code is produced but not tested properly and is fragile
* ...

---

# DDD can help alleviate some of Agile’s design weaknesses

* DDD encourages an iterative process of collaboration to explore a model and develop a shared language between development teams and domain experts

* Business Logic or "Domain Logic" is embedded into the process at its inception, where processes, workflows, functionality and entity relationships are determined

* DDD approach is not top-down, abstract, nor does it ignore iterative rapid prototypes
    * It force the separation of logical layers and ensures that the Business Logic, not the UI, is the key aspect of development

---

*A promising approach*
## Large-Scale Agile and Domain-Driven Design

<div class='left' style='float:left;width:50%'>

{{< image src="assets/paper-front.png" >}}

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="assets/agile-ddd.png" >}}

([https://link.springer.com/chapter/10.1007/978-3-319-91602-6_16](https://link.springer.com/chapter/10.1007/978-3-319-91602-6_16))

</div>
