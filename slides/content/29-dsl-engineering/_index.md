
+++

title = "[LSS2021] DSL Engineering"
description = ""
outputs = ["Reveal"]
aliases = [
    "/dsl-engineering/"
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
# Domain-Specific Languages Engineering
&nbsp;
#### [A. Croatti](mailto:a.croatti@unibo.it)
&nbsp;
#### {{< course_name >}}
#### {{< academic_year >}}

---

# Domain-Specific Languages

* Domain-Specific Languages (DSL) are programming languages or specification languages that target a specific problem domain
    * They are not meant to provide features for solving all kinds of problems
    
* If a problem's domain is covered by a particular DSL, you will be able to solve that problem easier and faster by using that DSL instead of a general-purpose language (e.g., Java)

* A program or specification written in a DSL can then be interpreted or compiled into a general-purpose language
    * in other cases, the specification can represent simply data that will be processed by other systems

---

## From General Purpose Languages to DSL

* General Purpose Programming Languages (GPLs) are a means for programmers to instruct computers
    * All of them are Turing complete
        * they can be used to implement anything that is computable with a Turing machine
        * anything expressible with one Turing complete programming language can also be expressed with any other Turing complete programming language
    * All general Purpose Programming Languages are interchangeable

* Even within the field of general-purpose programming, there are different languages, each providing different features tailored to the specific tasks

* A Domain-Specific Language is simply *a language that is optimized for a given class of problems*, called *domain*
    * It is based on abstractions that are closely aligned with the domain for which the language is built

---

## GPL vs. DSL

||**GPL**|**DSL**|
|---|---|---
|**Domain**|large and complex|smaller and well-defined|
|**Language size**|large|small|
|**Turing completeness**|always|often not|
|**User-defined abstractions**|sophisticated|limited|
|**Designed by**|guru or committee|engineers and domain experts|
|**User community**|large, anonymous and widespread|small, accessible and local|
|**Evolution**|slow, often standardized|fast-paced|
|**Deprecation/incompatible changes**|almost impossible|feasible|


---

## DSL Engineering

* Engineering a DSL is not just about syntax
    * it also has to be "brought to life" – DSL programs have to be executed somehow
    
* It is important to understand the separation of domain contents into *DSL*, *execution engine* and *platform*
    1. Some concerns are different for each program in the domain; the DSL provides tailored abstractions to express this variability concisely
    1. Some concerns are the same for each program in the domain; these typically end up in the platform
    1. Some concerns can be derived by fixed rules from the program written in the DSL; these concerns are handled by the execution engine


* There are two main approaches to building execution engines
    1. **Translation** (aka generation or compilation) -- translates a DSL program into a language for which an execution engine on a given target platform already exists
    1. **Interpretation** -- building a new execution engine (on top of your desired target platforms) which loads the program and executes it directly

---

## Benefits of using DSL (1/3)

1. **Productivity**
    * Once you’ve got a language and its execution engine for a particular domain, dealing with such a domain concepts becomes much more efficient

1. **Quality**
    * Using DSLs can increase the quality of the created product: fewer bugs, better architectural conformance, increased maintainability.
    * This is the result of the removal of (unnecessary) degrees of freedom for programmers, the avoidance of duplication of code (if the DSL is engineered in the right way)

1. **Validation and Verification**
    * DSL programs are more semantically rich than GPL programs

1. **Data Longevity**
    * If done right, models are independent of specific implementation techniques
    * They are expressed at a level of abstraction that is meaningful to the domain

---

## Benefits of using DSL (2/3)

5. **A Thinking and Communication Tool**
    * If you have a way of expressing domain concerns in a language that is closely aligned with the domain, your thinking becomes clearer, because the code you write is not cluttered with implementation details
    * Using DSLs allows you to separate essential from accidental complexity, moving the latter to the execution engine
    * The act of building the language can help you improve your understanding of the domain for which you build the DSL
        * A DSL is a formalization of the Ubiquitous Language

5. **Domain Experts Involvement**
    * Domain experts can easily read, and often write program code, since it is not cluttered with implementation details irrelevant to them
    * You can generate visualizations, reports or even interactive simulators that are suitable for use by domain experts

---

## Benefits of using DSL (3/3)

7. **No Overhead**
    * If you are generating source code from your DSL program (as opposed to interpreting it) you can use domain-specific abstractions without paying any runtime overhead, because the generator, just like a compiler, can remove the abstractions and generate efficient code

7. **Platform Independent/Isolation**
    * Using DSLs and an execution engine makes the application logic expressed in the DSL code independent of the target platform
    * It is absolutely feasible to change the execution engine and the target platform
    * Portability is enhanced, as is maintainability, because DSLs support separation of concerns

---

## DSL Examples
### [#1] DOT - A DSL to define Graphs

* DOT is a language that can describe graphs, either directed or non directed
    * The most known implementation [Graphviz](https://graphviz.org/)

* The language permits also to define the shape of the nodes, their colors and many other characteristicss.

```Dot
digraph graphname {   
    yellow -> orange -> red;  
    orange -> green; 
}
```

---

## DSL Examples
### [#2] Plant UML - A DSL to draw UML diagrams

* [PlantUML](https://plantuml.com/) Can be used to define UML diagrams of different kinds
    * With a similar syntax different kinds of diagrams can be defined like class diagrams or use case diagrams

```PlantUML
@startuml 
actor MyUser    
actor CustomerCare  
database database   
MyUser -> CustomerCare : Ask a refund    
CustomerCare -> database : Verify the data   
CustomerCare -> MyUser : Issue a refund  
@enduml
```

* Using a textual DSL to define UML diagrams have several advantages. In particular, it is easier to version, and it can be modified by everyone without special tools

---

## DSL Examples
### [#3] Gherkin – A DSL to define functional tests

* Gherkin is a DSL for defining functional tests: it has a very flexible syntax that makes it look almost like free tex

* Developers, domain experts and stakeholders can sit around a table and define some scenarios: these scenarios will be then executable as tests, to verify whether the application meet the expectations

```Gherkin
Scenario: Verify withdraw at the ATM works correctly  
Given John has 500$ on his account  
When John ask to withdraw 200$  
And John inserts the correct PIN    
Then 200$ are dispensed by the ATM  
And John has 300$ on his account
```

* A developer defines specific commands like `{name} has {amount}$ on his account` and defines the code that execute this command in the GPL 
    * Once the developers have created these commands, specific to the application of interest, all users can use them while defining their functional tests

---

## DSL Examples
### [#4] HTML – web layout

* HTML is a DSL for defining (web) documents

```html
<html> 
    <head>    
        <title>My beautiful page</title>    
    </head>   
    <body>    
        <div id="main">   
            <h1>Really interesting title!</h1>  
            <p>And the content is even better!</p>  
        <div>   
    </body>   
</html> 
```

* Also CSS is a kind of DSL

---

## DSL Examples
### [#4] VHDL – hardware design

* VHDL is a DSL used to define circuits, providing higher level concepts that those engineers can used to define their systems

```vhdl
DFF : process(RST, CLK) is    
begin   
if RST = '1' then   
Q <= '0';    
elsif rising_edge(CLK) then 
Q <= D;  
end if; 
end process DFF;
```

---

<div class='left' style='float:left;width:50%'>

# Why Adopting Domain Specific Languages?

</div>

<div class='right' style='float:right;width:50%'>

1. To communicate with domain experts

1. To focus on the important concepts

1. To hide the implementation (or the technical details) exposing only the information that really matters

</div>

---

<div class='left' style='float:left;width:50%'>

# Focus and productivity

</div>

<div class='right' style='float:right;width:50%'>

* **DSL mantains value over time**
    * E.g. Consider the case of HTML, a  page written 20 years ago can still be opened using devices that no one was able to imagine 20 years ago...

* **DSL captures only the logic, the really valuable part of the program**

</div>

---

## Implementing a DSL

* Implementing a DSL means developing a program that is able to:
    1. read text written in that DSL
    1. parse it
    1. process it
    1. and then possibly interpret it or generate code in another language

* Depending on the aim of the DSL, this may require several phases, but most of these phases are typical of all implementations

---

## Implementing a DSL
### Parsing (1/2)

* The implementation of a language has to make sure that the program respects the *syntax of that language*

* We need to break the program into *tokens*
    * Each token is a single atomic element of the language: this can be a keyword, an identifier, a symbol name, a literal

* The process of converting a sequence of characters into a sequence of tokens is called *lexical analysis*
    * The program or procedure that performs such analysis is called a *lexical analyzer* (or *lexer*, *scanner*)

* Having the sequence of tokens from the input file is not enough
    * We must make sure that they form a valid statement in our language, that is, they *respect the syntactic structure* expected by the language
    * This phase is called *parsing* (or *syntactic analysis*)

---

## Implementing a DSL
### Parsing (2/2)

* There are tools to deal with parsing so that you do not have to implement a parser by hand
    * There are DSLs to specify the *grammar of the language*, and from this specification they automatically generate the code for the lexer and parser

* A *grammar* is *a set of rules that describe the form of the elements that are valid according to the language syntax*

---

## Implementing a DSL
### The Abstract Syntax Tree (1/2)

* Once the program is checked as correct from the syntactic point of view (parsed correctly) the implementation will have to do something with the elements of the program
    * First of all, the overall correctness of a program cannot always be determined during parsing

* One of the correctness checks that usually cannot be performed during parsing is type checking, that is, checking that the program is correct with respect to types.
    * Trying to embed type checking in a grammar specification could either make the specification more complex, or it could be simply impossible

* Type checking is part of the semantic analysis of a program
    * This often includes managing the symbol table, that is, for instance, handling the variables that are declared and that are visible only in specific parts of the program

---

## Implementing a DSL
### The Abstract Syntax Tree (2/2)

* During parsing, we should also build a representation of the parsed program and store it in memory so that we can perform the semantic analysis on the memory representation without needing to parse the same text over and over again

* A convenient representation in memory of a program is a tree structure called the **Abstract Syntax Tree** (or AST)
    * The AST represents the abstract syntactic structure of the program
    * In this tree, each node represents a construct of the program

* Once the AST is stored in memory, the DSL implementation will not need to parse the program anymore, and it can perform all the additional semantic checks on the AST, and if they all succeed, it can use the AST for the final stage of the implementation, which can be the interpretation of the program or code generation

---

## Implementing a DSL
### IDE integration

* Even once you have implemented your DSL, that is, the mechanisms to read, validate, and execute programs written in your DSL, your work cannot really be considered finished

* Nowadays, a DSL should be shipped with good IDE support
    *  all the IDE tooling that programmers are used to could really make the adoption of your DSL successful

* If your DSL is supported by all the powerful features in an IDE such as a syntax-aware editor, immediate feedback, incremental syntax checking, suggested corrections, auto-completion, and so on, then it will be easier to learn, use, and maintain

---

## DSL Main Ingredients (Recap)

|Element|Definition
|---|---
|**Concrete Syntax**| - Defines the notation with which users can express programs
|**Abstract Syntax**| - Is a data structure that can hold the semantically relevant information expressed by a program<br> - It is typically a tree or a graph<br> - It does not contain any details about the notation
|**Static semantics**| - The set of constraints and/or type system rules to which programs have to conform, in addition to being structurally correct (with regards to the concrete and abstract syntax)
|**Execution semantics**| - Refers to the meaning of a program once it is executed<br> - It is realized using the execution engine

---

# Eclipse Xtext

[https://www.eclipse.org/Xtext/index.html](https://www.eclipse.org/Xtext/index.html)

* A framework for development of programming languages and domain-specific languages

* Xtext allow to define a language using a powerful grammar language
    * It generates the full infrastructure, including parser, linker, typechecker, compiler, ...
    * Along with the editing support for Eclipse, or any editor that supports the Language Server Protocol

* Xtext offers an editor and a parser for the developed DSL
    * The parser produces  model of your code using the [Eclipse Modeling Framework](https://www.eclipse.org/modeling/emf/) (EMF)
    * The editors generated by Xtext can be deeply customized

* Sometimes, it is used in combination with [Eclipse Xtend](https://www.eclipse.org/xtend/)
    * A flexible and expressive dialect of Java

---

# JetBrains MPS
## Meta Programming system

[https://www.jetbrains.com/mps/](https://www.jetbrains.com/mps/)

* MPS brings the flexibility of DSL into the world of programming languages

* MPS uses a generative approach
    * You can define generators for a language to transform user code into compilable code written in a more conventional GPL

* MPS is particularly good at, but is not limited to, generating Java code
    * You can also generate C, XML, FHTML, PDF, LaTeX, JavaScript, and more

---

## Books

<div class='left' style='float:left;width:50%'>

{{< image src="http://voelter.de/images/books/dsleng.png" width="28">}}

*DSL Engineering*, by M. Voelter, DSLBook  
[link](http://dslbook.org/)

</div>

<div class='right' style='float:right;width:50%'>

{{< image src="https://images-na.ssl-images-amazon.com/images/I/51CGoLIK4tL.jpg" >}}

*Implementing Domain-Specific Languages with Xtext and Xtend*, by L. Bettini, PACKT  
[link](https://www.packtpub.com/product/implementing-domain-specific-languages-with-xtext-and-xtend-second-edition/9781786464965)

</div>
