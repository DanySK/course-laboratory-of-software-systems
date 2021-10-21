 
+++

title = "Continuous Integration"
description = "Make things work, keep them working, move fast"
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

# {{< course_name >}}

## Continuous Integration

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Continuous Integration

The practice of integrating code with a main development line **continuously**
<br>
_Verifying_ that the build remains intact
* Requires *build automation* to be in place
* Requires *testing* to be in place
* Pivot point of the *DevOps* practices
* Historically introduced by the extreme programming (XP) community
* Now widespread in the larger DevOps community

---

# The Integration Hell

* Traditional software development takes several months for *“integrating”* a couple of years of development
* The longer there is no integrated project, the higher the **risk**

{{< image src="integration-traditional.png" max-w="40" max-h="50" >}}
{{< image src="integration-continuous.png" max-w="40" max-h="50" >}}

---

## Microreleases and protoduction

* High frequency integration may lead to high frequency releases
    * Possibly, *one per commit*
    * Of course, *versioning* must be appropriate...

Traditionally, **protoduction** is jargon for a *prototype that ends up in production*

{{< image src="protoduction.jpg" max-w="25" max-h="40" >}}

* Traditionally used with a *negative* meaning
    * It implied software
        * *unfinished*,
        * *unpolished*,
        * *badly designed*
    * Very common, unfortunately
* This si different in a continuously integrated environment
    * *Incrementality* is fostered
    * Partial features are *up to date* with the mainline

---

## Intensive operations should be elsewhere
* The build process should be *rich* and *fast*
* Operations requiring a long time should be *automated*
    * And run somewhere else than devs' PCs

<p>
{{< image src="compiling.png" max-h="50" >}}
</p>

---

## Continuous integration software

Software that promotes CI practices should:
* Provide *clean environments* for compilation/testing
* Provide a *wide range* of environments
    * Matching the relevant specifications of the actual targets
* High degree of *configurability*
* Possibly, *declarative configuration*
* A *notification system* to alert about failures or issues
* Support for *authentication* and deployment to external services

**Plenty** of integrators on the market

Circle CI, Travis CI, Werker, done.io, Codefresh, Codeship, Bitbucket Pipelines, GitHub Actions, GitLab CI/CD Pipelines, JetBrains TeamCity...

We will use GitHub actions: GitHub integration, free for FOSS, multi-os OSs supported

(Travis CI was better designed, but it has been essentially killed for FOSS)

---

# Core concepts

Structure operations into groups of *tasks* that execute either in sequence or in parallel

(not so different than Gradle tasks...)

The hierarchy an naming of each *task* changes with the specific product.

In **Travis CI**:
* *builds* contain *stages* that run sequentially
* *stages* contain *jobs* that run in parallel on separate VMs
* *jobs* contain *phases* that run sequentially
* *phases* are commands or sequences of commands

In GitHub Actions:
* *workflows* contain *jobs* that run in parallel on separate VMs, but can express dependencies
* *jobs* contain *steps* that run sequentially on the same VM
* *steps* can be either commands or sequences of commands, or application of *actions*
* *actions* can be scripts or sequences of *actions*

**No standard architecture!**

---

## Pipeline design

In essence, designing a CI system is designing a software construction, verification, and delivery *pipeline*
with the abstractions provided by the selected provider.

1. **Think** of all the operations required starting from one or more *blank* VMs
    * OS configuration
    * Software installation
    * Project checkout
    * Compilation
    * Testing
    * Secrets configuration
    * Delivery
    * ...
2. **Organize** them in a dependency graph
3. **Model** the graph with the provided CI tooling

Configuration can grow complex, and is usually stored in a YAML file
<br>
(but there are exceptions, JetBrains TeamCity uses a Kotlin DSL).

---

## GitHub Actions: Configuration

Workflows are configured in YAML files located in the default branch of the repository in the `.github/workflows` folder.

One configuration file $\Rightarrow$ one workflow

For security reasons, workflows may need to be manually activated in the *Actions* tab of the GitHub web interface.

---

## GitHub Actions: Runners

Executors of GitHub actions are called *runners*: virtual machines
(hosted by GitHub)
with the GitHub Actions runner application installed.

**Note**: the GitHub Actions application is open source and can be installed locally,
creating "*self-hosted runners*". Self-hosted and GitHub-hosted runners can work together.

Upon their creation, runners have a default environment, which depends on their *operating system*
* Documentation available at [https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners#preinstalled-software](https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners#preinstalled-software)

---

## Convention over configuration

Several CI systems inherit the "*convention over configuration* principle.

For instance, by default (with an empty configuration file) Travis CI builds a Ruby project using `rake`.

GitHub actions **does not** adhere to the principle:
if left unconfigured, the runner does nothing
(it does not even clone the repository locally).

Probable reason: Actions is an *all-round* repository automation system for GitHub,
not just a "plain" CI/CD pipeline

$\Rightarrow$ It can react to many different events, not just changes to the git repository history

---

## GHA: basic workflow structure

Minimal, simplified workflow structure:

```yaml
# Mandatory workflow name
name: Workflow Name
on: # Events that trigger the workflow
jobs: # Jobs composing the workflow, each one will run on a different runner
    Job-Name: # Every job must be named
        # The type of runner executing the job, usually the OS
        runs-on: runner-name
        steps: # A list of commands, or "actions"
            - # first step
            - # second step
    Another-Job: # This one runs in parallel with Job-Name
        runs-on: '...'
        steps: [ ... ]
```

---

### DRY with YAML

We discussed that automation / integration pipelines **are** part of the software
* They are subject to the same (or even higher) quality standards
* All the good engineering principles apply!

YAML is often used by CI integrators as preferred configuration language as it enables some form of DRY:
1. Anchors (`&` / `*`)
2. Merge keys (`<<:`)

```yaml
hey: &ref
  look: at
  me: [ "I'm", 'dancing' ]
merged:
  foo: *ref
  <<: *ref
  look: to
```

Same as:

```yaml
hey: { look: at, me: [ "I'm", 'dancing' ] }
merged: { foo: { look: at, me: [ "I'm", 'dancing' ] }, look: to, me: [ "I'm", 'dancing' ] }
```

---

## GitHub Actions' actions

GHA's YAML parser *does not support standard YAML anchors and merge keys*

(it is a well-known limit with a bug open since ages)

GHA achieves reuse via:
* "**actions**": *reusable parameterizable **steps***
    * *JavaScript* (working on any OS)
    * *Docker container*-based (linux only)
    * *Composite* (assemblage of other actions)
* "**reusable workflows**": *reusable and parameterizable **jobs***

Many actions are provided by GitHub directly,
and many are developed by the community.

---

## Workflow minimal example

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-example.yml" to=20 >}}

---

## Workflow minimal example

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-example.yml" from=22 to=39 >}}

---

## Workflow minimal example

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-example.yml" from=40 to=60 >}}

---

## Workflow minimal example

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-example.yml" from=61 >}}

---

## GHA expressions

GitHub Actions allows *expressions* to be included in the workflow file
* Syntax: `${{ <expression> }}`
* Special rule: `if:` conditionals are automatically evaluated as expressions, so `${{ }}` is unnecessary
    * `if: <expression>` works just fine

The language is rather limited, and documented at
* [https://docs.github.com/en/actions/learn-github-actions/expressions](https://docs.github.com/en/actions/learn-github-actions/expressions)

* The language performs a *loose equality*
    * Equal types are compared
    * Different types are *coerced to integers* when compared

* When a string is required, any type is *coerced to string*
    * String comparison ignores case

---

### GHA Expressions Types

| Type | Literal | Number coercion | String coercion
|---|---|---|---|
| Null | `null` | `0` | `''` |
| Boolean | `true` or `false` | `true`: `1`, `false`: `0` | `'true'` or `'false'` |
| String | `'...'` (mandatorily single quoted) | Javascript's `parseInt`, with the exception that `''` is `0` | none |
| JSON Array | unavailable | `NaN` | error |
| JSON Object | unavailable | `NaN` | error |

Arrays and objects exist and can be manipulated, but cannot be created

---

### GHA Expressions Operators

* Grouping with `( )`
* Array access by index with `[ ]`
* Object deference with `.`
* Logic operators: not `!`, and `&&`, or `||`
* Comparison operators: `==`, `!=`, `<`, `<=`, `>`, `>=`

---

### GHA Expressions Functions

Functions *cannot be defined*. Some are *built-in*, their expressivity is *limited*. They are documented at

[https://docs.github.com/en/actions/learn-github-actions/expressions#functions](https://docs.github.com/en/actions/learn-github-actions/expressions#functions)

#### Job status check functions

* `success()`: `true` if none of the previous steps failed
    * By default, every step has an implicit `if: success()` conditional
* `always()`: always `true`, causes the step evaluation even if previous failed, but supports combinations
    * `always() && <expression returning false>` evaluates the expression and does not run the step
* `cancelled()`: `true` if the workflow execution has been canceled
* `failure()`: `true` if a previous step of any previous job has failed

---

## The GHA context

The expression can refer to some objects provided by the context. They are documented at

[https://docs.github.com/en/actions/learn-github-actions/contexts](https://docs.github.com/en/actions/learn-github-actions/contexts)

Some of the most useful are the following

* `github`: information on the workflow context
    * `.event_name`: the event that triggered the workflow
    * `.repository`: repository name
    * `.ref`: branch or tag that triggered the workflow
        * e.g., `refs/heads/<branch>` `refs/tags/<tag>`
* `env`: access to the environment variables
* `steps`: access to previous step information
    * `.<step id>.outputs.<output name>`: information exchange between steps
* `runner`:
    * `.os`: the operating system
* `secrets`: access to secret variables (in a moment...)
* `matrix`: access to the build matrix variables (in a moment...)

---

## Checking out the repository

By default, GitHub actions' *runners do **not** check out the repository*
* Actions may not need to access the code
    * e.g., Actions automating issues, projects

It is a *common* and *non-trivial* operation (the checked out version must be the version originating the workflow), thus GitHub provides an action:

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-example.yml" from=46 to=47 >}}

Since actions typically do not need the entire history of the project, by default the action checks out *only the commit that originated the workflow* (`--depth=1` when cloning)
* *Shallow cloning* has better *performance*
* $\Rightarrow$ It may break operations that rely on the entire history!
    * e.g., the git-sensitive semantic versioning system

Also, *__tags__ don't get checked out*

---

## Checking out the whole history

{{< github repo="action-checkout" path="action.yml" from=6 >}}

* Check out the repo with the maximum *depth*
* Recursively check out all *submodules*
* Checkout all *tags*

---

## Writing outputs

Communication with the runner happens via *[workflow commands](docs.github.com/en/actions/learn-github-actions/workflow-commands-for-github-actions)*
<br>
The simplest way to send commands is to print on standard output a message in the form:
<br>
`::workflow-command parameter1={data},parameter2={data}::{command value}`

In particular, actions can set outputs by printing:
<br>
`::set-output name={name}::{value}`

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/use-step-outputs.yml" from=6 >}}

---

## Build matrix

Most software products are meant to be *portable*
* Across operating systems
* Across different frameworks and languages
* Across runtime configuration

A good continuous integration pipeline should test *all the supported combinations**
* or a sample, if the performance is otherwise unbearable

The solution is the adoption of a **build matrix**
* Build variables and their allowed values are specified
* The CI integrator generates the *cartesian product* of the variable values, and launches a build for each!
* Note: there is no built-in feature to exclude some combination
    * It must be done manually using `if` conditionals

---

## Build matrix in GHA

{{< github repo="Tutorial-GitHub-Actions-Minimal" path=".github/workflows/workflow-matrix.yml" from=19 >}}

---

## Private data and continuous integration

We would like the CI to be able to
* Sign our artifacts
* Delivery/Deploy our artifacts on remote targets

Both operations **require private information to be shared**

Of course, private data *can't be shared*
* Attackers may steal the identity
* Attackers may compromise deployments
* In case of open projects, attackers may exploit *pull requests*!
    * Fork your project (which has e.g. a secret environment variable)
    * Print the value of the secret (e.g. with `printenv`)

How to *share a secret* with the build environment?

---

## Secrets

Secrets can be stored in GitHub at the repository or organization level.

GitHub Actions can access these secrets from the context:
* Using the `secrets.<secret name>` context object
* Access is allowed only for workflows generated by local events
    * Namely, no secrets for pull requests

Secrets can be added from the web interface (for mice lovers), or via the GitHub API.

```ruby
#!/usr/bin/env ruby
require 'rubygems'
require 'bundler/setup'
require 'octokit'
require 'rbnacl'
repo_slug, name, value = ARGV
client = Octokit::Client.new(:access_token => 'access_token_from_github')
pubkey = client.get_public_key(repo_slug)
key = Base64.decode64(pubkey.key)
sodium_box = RbNaCl::Boxes::Sealed.from_public_key(key)
encrypted_value = Base64.strict_encode64(sodium_box.encrypt(value))
payload = { 'key_id' => pubkey.key_id, 'encrypted_value' => encrypted_value }
client.create_or_update_secret(repo_slug, name, payload)
```

---

## In-memory signatures with Gradle

Signing in CI is easier if the key can be *stored in memory*
<br>
the alternative is to *install a private key in the runner*

```kotlin
if (System.getenv("CI") == true.toString()) {
    signing {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}
```
* The `CI` environment variable is automatically set to `"true"` on most CI environments
    * Including GitHub Actions
* The `signingKey` and `signingPassword` properties must get *passed* to Gradle
    * One way is to pass them on the command line:
        * `./gradlew -PsignigngKey=... -PsigningPassword=... <tasks>`
    * Alternatively, they can be stored into environment variables
        * Gradle auto-imports properties named `ORG_GRADLE_PROJECT_<variableName>`

```yaml
env:
    ORG_GRADLE_PROJECT_signingKey: ${{ secrets.SIGNING_KEY }}
    ORG_GRADLE_PROJECT_signingPassword: ${{ secrets.SIGNING_PASSWORD }}
```

---

## DRY with GitHub Actions

Imperative behaviour in GitHub Actions is encapsulated into *actions*

**Actions** are executed as a single logical step, with **inputs** and **outputs**

Their metadata is written in a `actions.yml` file on the repository root

GitHub actions stored on GitHub are usable without further deployment steps
* By using `owner/repo@<tree-ish>` as reference

---

## GitHub Actions' metadata

```yaml
name: 'A string with the action name'
description: 'A long description explaining what the action does'
inputs:
  input-name:  # id of input
    description: 'Input description'
    required: true # whether it should be mandatorily specified
    default: 'default value' # Default value, if not specified by the caller
outputs:
  # Outputs will be set by the action when running
  output-name: # id of output
    description: 'Description of the output'
runs: # Content depends on the action type
```

---

## Composite actions

Composite actions allow the *execution of multiple steps* that can be *scripts* or *other actions*.

```yaml
runs:
    using: composite
    steps: [ <list of steps> ]
```

The action is contained in its metadata descriptor `action.yml`root, e.g.:

{{< github repo="action-checkout" path="action.yml" >}}

From: [https://github.com/DanySK/action-checkout](https://github.com/DanySK/action-checkout)

It can be used with:

{{< github path=".github/workflows/build-and-deploy.yml" from=22 to=23 >}}

---

## Composite actions: limitations

* No support for secrets, they must be passed *as parameters*

```yaml
name: 'Composite action with a secret'
description: 'For teaching purposes'
inputs:
  token:  # github token
    description: 'Github token for deployment. Skips deployment otherwise.'
    required: true
runs:
  using: "composite"
  steps:
    - run: '[[ -n "${{ inputs.token }}" ]] || false'
      name: Fail if the toke is unset
```

* No conditional steps (ouch...)
    * They can be somewhat emulated inside the script

---

## Docker container actions

#### Wait, what is a container?

* We might need to deviate for a moment: >> [**click here!**](../09-containerization) <<

#### How to

* Configure the `Dockerfile` of the container you want to use
* Prepare the main script and declare it as `ENTRYPOINT`
* Define inputs and outputs in `action.yml`
* In the `runs` section select `docker` and the arguments order

```yaml
runs:
  using: 'docker'
  image: 'Dockerfile' # Alternatively, the name of an existing image
  args:
    - ${{ inputs.some-input-name }}

```

---

## Limitations of Docker container actions

No multi-OS

---

## JavaScript actions

---

## Reusable workflows

---

# Stale builds

1. Stuff *works*
2. *Nobody touches it* for months
3. Untouched stuff is now *borked*!

Ever happenend?

* Connected to the issue of **build reproducibility**
    * The higher the build *reproducibility*, the higher its *robustness*
* The default runner configuration may change
* Some tools may become unavailable
* Some dependencies may get unavailable

**The sooner the issue is known, the better**

$\Rightarrow$ *Automatically run the build every some time* even if nobody touches the project
* How often? Depends on the project...
* **Warning**: GitHub Actions disables `cron` CI jobs if there is no action on the repository, which makes the mechanism less useful

---

## Automated software upgrades

---

## Additional checks and reportings

There exist a number of recommended services that provide additional QA and reports.

Non exhaustive list:
* [Codecov.io](https://codecov.io/)
    * Code coverage
    * Supports Jacoco XML reports
    * Nice data reporting system
* [Sonarcloud](https://sonarcloud.io/)
    * Multiple measures, covering reliability, security, maintainability, duplication, complexity...
* [Codacy](https://www.codacy.com/)
    * Automated software QA for several languages
* [Code Factor](https://www.codefactor.io/)
    * Automated software QA

---

## High quality FLOSS checklist

The [Linux Foundation](https://www.linuxfoundation.org/) [Core Infrastructure Initiative](https://www.coreinfrastructure.org/) created a checklist for high quality FLOSS.

**[CII Best Practices Badge Program https://bestpractices.coreinfrastructure.org/en](https://bestpractices.coreinfrastructure.org/en)**


* *Self-certification*: no need for bureaucracy
* Provides a nice *TODO list* for a high quality product
* Releases a *badge* that can be added e.g. to the project homepage
