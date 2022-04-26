+++

title = "Development and Operations"
description = "Introduction to DevOps"
outputs = ["Reveal"]

[reveal_hugo]
transition = "slide"
transition_speed = "fast"
custom_theme = "custom-theme.scss"
custom_theme_compile = true

[reveal_hugo.custom_theme_options]
targetPath = "css/custom-theme.css"
enableSourceMap = true

+++

# Laboratory of Software Systems

## Development and operations

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

---

{{% slide content="devops.intro" %}}

---

# DevOps practices

## *Workflow organization*

#### Lightweight processes and shared responsibility 

* Participants should have clear roles
* Lifecycle phases should be mapped into the workflow

<p>

### **Tools**

* Version control systems
  * Reproducible project history
  * Enable several workflows
* Workflow management platforms
  * Tracking of progress, issues, proposals
  * Activities are linked to code

---

## Gitflow


{{< image src="assets/gitflow.png" h="70" >}}

---

## Forks and pull requests

{{< image src="assets/fork-and-pr.svg" height=50 >}}

---

# DevOps practices
## *Self-testing code*

#### **Code includes tests**

* Several testing levels
    * Unit, acceptance, integration, etc.
* Testing is **entirely automated**
* Solving an issue implies the creation of a *regression test*
* Tests are executed *for every change*

<p>

### **Tools**
* Build automators
  * Compile, test, and run QA on software

---

# DevOps practices
## *Code quality control*

#### **Code is coherent and understandable**

* Consistent code style across the board
    * Improved readability
    * Much cleaner diffs
    * Improved bisection / regression tracking
* Extremely **clean code** *reduces* the need for documentation
    * Newbies can be productive more quickly
* A large part of code QA can be automated!

<p>

### **Tools**
* Build automators
* Static analyzers
  * Verify metrics and rules without execution

---

# DevOps practices
## *Continuous integration*

#### **Code must not diverge**

* Working copies are *kept in sync* with the mainline
    * How its done depends on the workflow organization
* Build and test *automatically* on a fresh machine
* Build and test *on all the target platforms*
* Promptly intercept (feedback) failures and issues
* Deploy successful artifacts

<p>

### **Tools**

* Continuous Integration platforms
  * Run the automation pipeline on reference environments
  * Promotes infrastructure as code
    * (at least for the build/test/check phase)

---

# Continuous integration

#### **Risk reduction**

{{< image src="assets/integration-traditional.png" max-w="25" max-h="50" >}}
$\Rightarrow$
{{< image src="assets/integration-continuous.png" max-w="25" max-h="50" >}}

---

# DevOps practices
## *Continuous Delivery*

#### **Every working build should produce a potential release**

* The CI pipeline should produce final artifact
* Artifacts should be available for quick deployment

<p>

### **Tools**

* Build automators
* Continuous Integration platforms

---

# DevOps practices
## *Continuous Deployment*

#### **Actual deployment should be automatic**

* There should be a strategy for delivered code to enter production
  * Reversibly (e.g. [blue *green* deployment](https://martinfowler.com/bliki/BlueGreenDeployment.html))
  * Gradually (e.g. [canary release](https://martinfowler.com/bliki/CanaryRelease.html))
* *Infrastructure* should be *defined by software*
* Deliverability *before* new features

<p>

### **Tools**

* Continuous Integration platforms
* Configuration management software
  * Enables *Continuous configuration automation*
  * Defines infrastructure via software with zero human interaction

---

# DevOps *at work*

### An internal project where techniques are applied

* ["Truth" Repository](https://github.com/AlchemistSimulator/Alchemist/)
* [Branches](https://github.com/AlchemistSimulator/Alchemist/branches)
* [Forks](https://github.com/AlchemistSimulator/Alchemist/network/members)
* [Example pull request](https://github.com/AlchemistSimulator/Alchemist/pull/469)
* [Example automatic update](https://github.com/AlchemistSimulator/Alchemist/pull/479)
* [CI/CD pipeline](https://travis-ci.org/github/AlchemistSimulator/Alchemist)
* [(Build) Infrastructure as code](https://github.com/AlchemistSimulator/Alchemist/blob/develop/.travis.yml)

[**These slides are in CI/CD as well!**](https://github.com/DanySK/Course-Laboratory-of-Software-Systems)
* [This is the latest build](https://travis-ci.com/github/DanySK/Course-Laboratory-of-Software-Systems)

---

# DevOps for DIR

## *Version control*

* **Basics** of distributed version control
  * including *SVN ["reeducation"](http://archive.is/LYwng)*
  * Repository creation and *local* maintenance
  * Basics of *collaboration* (branching, merging)
  * *Multi-repo* (forks, review, pull requests)
* Agile **workflows** depending on specific needs
* **Advanced** distributed version control
  * History alteration (Rebasing / Squashing)
  * Bisection
  * Cherry picking
  * DVCS sub-modularization
    * (used in these slides)

---

# DevOps for DIR

## *Automation*

* Software lifecycle organization and build automation
  * From *source* to *tested and deployable* in one command 
  * [Gradle](https://gradle.org/) is the house speciality
    * Basics
    * Build organization
    * Customization and advanced
    * Custom plugin development
* Advanced automation
  * **Requires working CI** *and* **comprehensive testing** or it's going to burn
  * [Automated software updates](https://github.com/DanySK/upgradle/pull/122)
  * [Automated build infrastructure updates](https://github.com/AlchemistSimulator/Alchemist/pull/464)

---

# DevOps for DIR

## *Continuous Integration / Deployment / Delivery* 

* Adding **CI** to existing projects
  * Needs a *committment to* the *DevOps* philosophy
* *Multiplatform* testing
  * OS type and version
  * Platform (JDK, CLR, Python interpreter) version
  * Multi-stage
* Continuous *deployment*
  * "One click away from production"
* Continuous *delivery*
  * From code to production automagically
