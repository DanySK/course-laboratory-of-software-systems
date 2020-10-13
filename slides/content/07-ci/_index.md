 
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
* Circle CI, Travis CI, Werker, done.io, Codefresh, Codeship, Bitbucket Pipelines...

We will use Travis CI
* GitHub integration
* Free for academy
* Multiple OSs supported

---

# Core concepts

**phase**
* A *named sequence of commands*
* A phase succeeds if all the commands succeed
* The next phase starts only if the previous was successful
* The names and order of phases are *pre-determined*

**job**
* Operation consisting in:
    * *spawning* of a fresh *virtual environment*
    * *configuration* of the virtual environment
    * *cloning* of the repository
    * *ordered execution of all phases* 

---

# Core concepts

**stage**
* A *group of jobs* that run in parallel
* A build finishes when all of its jobs are finished.
* A stage is successful if all of its stages are successful
* The next stages starts only if the previous was successful

**build**
* A *group of stages* that run in *sequence*
* A build finishes when all of its stages are finished.
* A build is successful if all of its stages are successful

---

## Job lifecycle


{{< gravizo "Travis CI Job lifecycle" >}}
@startuml

(*) -right-> "before_install"
"before_install" -right-> "install"
"install" -right-> "before_script"
"before_script" -right-> "script"
"script" -right-> "before_cache"
if "TRAVIS_TEST_RESULT" then
  --> [true] "after_success"
  --> "before_deploy"
else
  --> [false] "after_failure"
  --> "before_deploy"
"before_deploy" -left-> "deploy"
"deploy" -left-> "after_deploy"
"after_deploy" -left-> "after_script"
"after_script" -left-> (*)

@enduml
{{< /gravizo >}}

* If `before_install`, `install` or `before_script` returns a non-zero exit code, the build is errored and stops immediately.
* If `script` returns a non-zero exit code, the build is failed, but *continues to run* before being marked as failed.
* The exit code of `after_success`, `after_failure`, `after_script`, `after_deploy` and subsequent stages *do not affect the build result*.
    * However, if one of these stages times out, the build is marked as failed.

---

## Travis CI: Configuration

Travis CI is configured in a single `travis.yml` file located in the repository root

At registration time, it integrates with GitHub

New pushes on repositories which contain `.travis.yml` get automatically built

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
  autocrlf: input # Prevents git to try to be smart with line endings
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

```


---

* **submodule repo**
* environment variables
* build matrix
* conditional jobs
* stages and workspaces
* private variables
* file encryption
* cronjobs
* caches
* conditional builds
* conditional deployments
* Codecov.io
* Sonarcloud
* CI practices
* ASCII armored in memory signign
* Repo-level automation
