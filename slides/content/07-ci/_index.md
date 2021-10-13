 
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

### DRY CI

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

GHA achieves reuse via "**actions**":
* Reusable operations

---

## Travis CI: Environment selection

By default, travis tries to build a *Ruby* project
<br>
Travis itself is written in Ruby

Selection of a different system configuration can be operated with the `language` keyword.
<br>
Over [30 languages](https://docs.travis-ci.com/user/language-specific/) are natively supported.

Some of the most interesting for us are:
* `java`
* `scala`
* `minimal` (bare environment with just bash and Ruby)

Every environment has some *pre-defined behaviour*,
for instance the `language: java` environment automatically searches for a `pom.xml` and in case runs Maven,
if not found searches for a `gradlew` file and runs `./gradlew assemble` in the `install` phase and `./gradlew check` in the `script` phase.
* Default phase behavior can be disabled with `<phase_name>: true`

---

## Travis CI: clone depth control

By default, Travis CI *does not clone the entire history* of a repository.
<br>
It performs instead a *shallow clone*, by running `git clone --depth 50 <repo-url>`

This *interferes with DVCS-based versioning*, which may well need to look behind of many commits.

This behaviour can be controlled with the `git` key:

```yaml
git:
  depth: false # Accepts false or any number representing the commit count, defaults to 50
```

The same key allows for controlling further behaviour, such as disabling `autocrlf`:

```yaml
git:
  depth: false # Accepts false or any number representing the commit count, defaults to 50
  autocrlf: input # Prevents git from trying to be smart with line endings
```

---

## Travis CI: Testing on different OSs

If our software supports *multiple operating systems*, it is a good practice to verify that changes do not *break compatibility*

A few integrators allow to perform a multi-os build. In Travis it is possible via the `os` key, supporting:
* `linux` -- A Ubuntu Linux installation
* `macos` -- A MacOS X installation
* `windows` -- A Windows Server installation

Specifying *multiple operating systems* causes the execution of *multiple jobs* in the same build

*Note*: Some languages or system configurations may be unavailable for some environments!
* e.g., there is no `java` environment available on `windows`

---

## Custom environment

If a particular environment of need is unavailable, one solution is
<br>
**build it yourself**

* start from `language: minimal`
* if the software you need is installable via **apt** or **homebrew**, use the `addons` keyword:
```yaml
addons:
  apt:
    packages: foo
  homebrew:
    packages: bar
```
* install packages under windows using **[chocolatey](https://chocolatey.org/)** (pre installed on Travis Windows instances)
* if you need to perform a manual installation, you can write it in bash

---

## Improved resilience on Travis

* operations that may fail due to network issues or long time can be worked around using Travis built in functions:
    * `travis_retry COMMAND` tries the same `COMMAND` three times, giving up only if it fails for three times
    * `travis_wait N COMMAND` allows `COMMAND` to run without any output for longer than the default 10 minutes.
        * The hard limit on 50 minutes per job however remains

---

## A Java custom environment

It could be useful to test on specific versions of the JDK, for instance.
<br>
A multi platform installer exists: [Jabba](https://github.com/shyiko/jabba)

Also, a Travis-CI dedicated tool has been written to ease installation via Travis: [Gravis-CI](Gravis-CI)
* Automatically installs Jabba
* Uses Jabba to install the version of the JDK specified in the `JDK` environment variable

```yaml
env:
  global:
    - GRAVIS_REPO="https://github.com/DanySK/Gravis-CI.git" # Convenience variable for shortening commands
    - GRAVIS="$HOME/gravis" # Convenience variable for shortening commands
    - JDK="adopt@1.11.0-8" # Partial versions supported, adopt@1.11 would pull the latest adopt 1.11

before_install:
  - travis_retry git clone --depth 1 $GRAVIS_REPO $GRAVIS # Check out the script set
  - source $GRAVIS/install-jdk # Install the JDK you configured in the $JDK environment variable
```

---

## Build Matrix

Similarly to the operating systems, other environment features may need to get tested, e.g. the JDK versions

Travis automatically combines some variables into a *[build matrix](https://docs.travis-ci.com/user/build-matrix/)*
* Generates one job for each combination of such variable
* Expands `os`, custom language keys (such as `rvm` or `python`), and `env.matrix` keys
* `jobs.include` can be used to add further jobs to the matrix
* `jobs.exclude` can be used to filter jobs out of the matrix

```yaml
...
env:
  global:
    ...
  matrix:
    - JDK="adopt@"
    - JDK="adopt@1.11"
    - JDK="adopt-openj9@"
    - JDK="adopt-openj9@1.11"
jobs:
  include: # Add a job with default OS and the specified matrix environment
    env:
      - JDK="adopt@1.8" 
  exclude: # Excludes builds with OSX and OpenJ9
    - { os: osx, env: [ JDK="adopt-openj9@" ] } # JSON-like syntax, YAML is a superset of JSON
    - { os: osx, env: [ JDK="adopt-openj9@1.11" ] }
```

---

## Build Stages and workspaces

Spanning several jobs upfront may make the build very slow
* A single mistake will be detected only after all jobs failed
* In case of limited resources, it can take a long time
* Most errors will affect *all* builds

**Build stages** allow to group one or more jobs that are to be run in parallel
* By default, all jobs run the `test` stage
* Jobs from a stage can communicate with jobs on another stage via **[workspaces](https://docs.travis-ci.com/user/using-workspaces/)**

Consider for instance this structure:
1. Run a build on the *reference* environment
2. If successul, then *test on all the supported configurations*
3. If everything is successful, then *deploy*

this can be mapped into a *three-stages* configuration

---

## Travis YAML validation

* Builds can get *complex*
* The corrisponding YAML file can get *rich*
* Syntax *errors are possible and likely*

* Use *[anchors](https://yaml.org/spec/1.2/spec.html#id2765878)* and *[merge keys](https://yaml.org/type/merge.html)* to foster reuse
* *Validate* YAML before pushing
    * Travis CI provides a Ruby tool for this!

<br>
<br>

#### Using `travis` from the local terminal
* Installation: `gem install travis`
    * Make sure you have the Gem install folder in your `PATH`
* Run `travis lint` to verify if your YAML file complies

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

## Sharing a secret

Travis supports the insertion of secret **variables** in two ways:
* From the *web interface* (easier, quicker)
    * More options $\Rightarrow$ Settings $\Rightarrow$ Environment variables
* From the *command line* (more compleicated)
    * `travis encrypt <String to encrypt>  --pro`
        * e.g., `travis encrypt 'PASSWORD="foobar"'`
    * produces a `secure: <code>` entry that can be pasted in `.travis.yml`
        * e.g.:
```yaml
env:
  global:
    secure: <code>
```

Travis also supports sharing a **single secret file**
* It gets encrypted using `travis encrypt-file <file> --pro`
* The output command must be included in `.travis.yml`
    * e.g. in the `before_install` phase of any job requiring it
* The resulting encrypted file must be tracked
    * Be careful not to track the original one!

---

## Shared secrets: best practices

* Leverage the single shared file for sharing an **ASCII-armored** PGP key
    * Allows for *signing* artifacts
    * *Signing keys are large* and cannot fit a variable
    * Obtain it with `gpg --armor --export-secret-keys > secrets.asc`
    * Encrypt with `travis encrypt-file secrets.asc --pro`
    * Remove it (`rm secrets.asc`)
    * Track the encrypted file `git add secrets.asc.enc`
    * In the `install` or `before_install` phase:
        * Add the `openssl` command printed by travis in the `before_install` phase
        * Add `export ORG_GRADLE_PROJECT_signingKey=$(cat secrets.asc)`
* In case you need *multiple files*:
    * create an archive, encrypt it, decrypt on the build server, and unpack there
* Store *passwords* into *encrypted variables*
* Secrets are *only available in builds launched from the original repository*
    * Otherwise there'd be no protection from *pull request attacks*
    * *Make sure pull requests can build without secrets*!
    * e.g. by *excluding phases or jobs* that use secrets

---

## Signing using in-memory keys in Gradle

```kotlin
if (System.getenv("CI") == true.toString()) {
    signing {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}
```
* The `CI` environment variable is automatically set to true on Travis CI
    * See [https://docs.travis-ci.com/user/environment-variables/#default-environment-variables](https://docs.travis-ci.com/user/environment-variables/#default-environment-variables)
* The `signingKey` and `signingPassword` properties must get *passed* to Gradle
    * The best way is probably by exporting them as environment variables
    * Gradle auto-imports properties named `ORG_GRADLE_PROJECT_<variableName>`
    * This mechanism can be exploited to inject properties using environment variables:
        * `export ORG_GRADLE_PROJECT_signingKey=$(cat secrets.asc)`
        * A secret variable named `ORG_GRADLE_PROJECT_signingPassword` from the web UI

---

## Conditional jobs, stages, and deployments

One way to prevent pull requests to run deployment phases that would fail
<br>
(due to unavailability of secrets)
<br>
is to use **conditional jobs, stages, and deployments**

To do so, the keyword `if` can be used:
* On the whole build, to skip it entirely
* On a `stages` entry, to skip a single stage
* On a `jobs.include` entry, to exclude that job

The [Travis condition syntax is documented at https://docs.travis-ci.com/user/conditions-v1](https://docs.travis-ci.com/user/conditions-v1)

---

## Travis CI's `deploy`

The `deploy` phase is special in Travis:
* It does not allow to run custom commands
* It expects entries with:
    * A deploy provider
    * A configuration

Full reference: [https://docs.travis-ci.com/user/deployment-v2](https://docs.travis-ci.com/user/deployment-v2)

Example:
```yaml
deploy:
  provider: releases # Deploys on GitHub Releases
  file: build/libs/*.jar # Files to deploy
  edge: true # opt in to the new deploy API
  on: # filter
    all_branches: true
    # tags: true # deploy only tags
```

---

# Stale builds

1. Stuff *works*
2. *Nobody touches it* for months
3. Untouched stuff is now *borked*!

Ever happenend?

* Connected to the issue of build reproducibility
* The default configuration of Travis CI may change
    * By the way, explicitly using `dist` for Linux builds may help
* Some tools may become unavailable
* Some dependencies may get unavailable

**The sooner the issue is known, the better**

$\Rightarrow$ *Automatically run the build every some time* even if nobody touches the project
* How often? Depends on the project...

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
