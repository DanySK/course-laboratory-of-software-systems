 
+++

title = "Picking version numbers"
description = "Assignign meaning to revisions"
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

## Selecting a version number

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Software versioning

The process of assigning a **unique identifier** to a **unique state** of some software
* Used to *distinguish* different software states
* Used to *refer* to different states of the same software
* The identifier is normally a sequence of alfanumeric characters spaced by dots, slashes, and dashes
* Assigning IDs in a *predictable* way could help gathering *information* on the software itself

---

# Versioning levels

Versioning can happen at different levels, for instance:

* *Version system automatic versioning*
    * Fine grained
    * Automatic
    * Non progressive
    * Non linear (i.e., versions can't be sorted)
* *Subproject / feature version*
    * Often leveraging codenaming
    * Non linear
    * e.g., Java JSRs, Kotlin KEEPs, etc.
* *Software release*
    * Usually manual
    * Usually linear

---

# Versioning scopes

* **Internal**
    * Identifies a point in development
    * Changes do not impact the "outer world"
* **External**
    * A publicly visible release of the software
    * Changes are disruptive

Often for *open source* projects, internal and external versioning *coincide*

---

# Versioning appraoaches
## Code naming

The version is represented by a (usually pronounceable) *word*, *short phrase* or *acronym*
* With few exceptions, *does not provide any direct information on the project* (often by purpose)
* Very frequently used internally to refer to new features
* Often used to "protect" the corporation from information leaking
* Often *changed to purposely create confusion*
* Used to separate pre-release versions from final releases
    * e.g., Project Longhorn only became Windows Vista at release time
* Often associated for *commercial reasons* to other version numbers
    * e.g. Ubuntu 18.04 *Bionic Beaver* or MacOS X 10.13.5 *Sierra*
* Reasons for codenames are often *political and commercial* rather than technical

---

# Versioning appraoaches
## Date based versioning

The version is represented by **a string representing the release date**
* Dates *don’t always match development rate*
    * A project may change more in a week than in a year
* Useful for projects that are *fast-paced* (multiple releases per week)
* Useful as a *companion* for other versioning schemes
* Useful at the *commercial level* for clearly indicating the novelty (and the age) of a project
    * e.g. Windows 98, Office 2003

---

# Versioning appraoaches
## Unary numbering

The version is represented by a **string whose length grows at each version**
* Only useful for project that reached *maturity*
* Extremely *unlikely* in today’s software world
* May lead to *version length explosion*

---

# Versioning appraoaches
## Degree of retro compatibility

The version is represented one or more sequences, separately incremented, that
<br>
**reflect incrementally widespread changes in the product**

* Often used *in conjunction with other techniques*
* Often used *badly* (see the Linux kernel)
* *Formal methodologies* for applying it exist
* Sometimes instead of indicating API-level changes, the version may indicate user-level perceivable changes
    * Very much depends on who are the clients/customers

---

# Versioning in the real world
## Microsoft Windows versioning

Combination of all the techniques:
* *Dates* (Windows 9x, 2001)
* *Codenames* (NT, Vista, XP, Millenium Edition)
* Pre-release codenames (Longhorn)
* Dates for internal builds
* Incremental versions on *multiple levels*
    * e.g., Windows 95 is also MS-DOS 7.0 and Windows 4.00
* *Separation* between “commercial” versions and “actual” “versions”
    * e.g., Windows 7 is actually Windows 6.1, and Windows 10 is actually Windows 6.4
    * One future Windows may actually become Windows 7.0, clashing with the “commercial” version of an older product

**Proliferation of methodologies and inconsistencies lead to issues**
* Ever wondered why there is no Windows 9?

---

# Versioning in the real world
## Canonical Ubuntu versioning

Association of a *date in format* `YY.MM` and a *two word codename* in form of `Adjective AnimalName`. Both the words of the codename begin with the
same letter.
* Version number *does not track changes*
    * The development is arguably linear
    * But actually new versions may bring in substantial novelties, e.g. entirely new desktop environments
* "LTS" can be optionally appended to identify *“Long Term Support”* versions
* Two versions of Ubuntu can be compared by date but also by the first letter of their codename
    * “Zesty Zapus” is newer than “Utopic Unicorn”
    * Unfortunately, since there are a limited number of letters, “Bionic Beaver” is newer than “Xenial Xerus” and “Zesty Zapus”

---

# Versioning in the real world
## Wine versioning

Formerly a *pure date*, in ISO format without hyphens, e.g. `20040505`.

The project *switched to a classic versioning* in form of `major.minor`
* The change may give some headaches to dependency managers, since `20040505` is bigger than `3.9` and other subsequent versions.
* A `0.Date` format for initial development releases would have been advisable with hindsight

---

# Versioning in the real world
## $\TeX$ versioning

*Purely unary numbering* converging to $\pi{}$

* Current version (released in January 2004) is `3.14159265`
* Every time a new version is produced, a number from $\pi{}$ is added to the version string
* Sustainable just because $\TeX$ is now extremely stable, and development is almost frozen

> At the time of my death, it is my intention that the then-current versions of $\TeX$ [...] be forever left unchanged,
except that the final version numbers to be reported in the “banner” lines of the programs should become:
`TeX, Version $\pi $` [...].
From that moment on, all “bugs” will be permanent “features”.
**<div style="text-align: right"> Donald E. Knuth</span>**

---

# Versioning in the real world
## Python Enhancement Proposal 440 ([PEP440](https://www.python.org/dev/peps/pep-0440/))

It is *the* way Python software should be versioned
* Flexible but complicated
* Order of release segments is mandated

Format: `[N!]N(.N)*[{a|b|rc}N][.postN][.devN]`
* Epoch segment: `N!`
* *Mandatory* Release segment: `N(.N)*`
* Pre-release segment: `{a|b|rc}`
* Post-release segment: `.postN`
* Development release segment: `.devN`

---

# Versioning in the real world
## [Semantic Versioning (SemVer)](https://semver.org/)

Encodes version numbers and their change to *convey meaning about the underlying code* and what has been modified from one version to the next.
* Written in RFC-style
* No-retract
* Versioned using Semantic versioning
* Format `X.Y.Z[-P][+B]` 
    * `X` $\Rightarrow$ Major
    * `Y` $\Rightarrow$ Minor
    * `Z` $\Rightarrow$ Patch
    * `P` $\Rightarrow$ Pre-release
    * `B` $\Rightarrow$ Build metadata

---

# Versioning in the real world
## [Semantic Versioning (SemVer)](https://semver.org/)

* Software using Semantic Versioning **MUST** *declare a public API*.
This API could be declared in the code itself or exist strictly in documentation.
However it is done, it *should be precise and comprehensive*.
* Once a versioned package has been released, the contents of that version **MUST NOT** *be modified*.
Any modifications **MUST** *be released as a new version*.
* A normal version number MUST take the form `X.Y.Z` where `X`, `Y`, and `Z` are *non-negative integers*, and **MUST NOT** *contain leading zeroes*.
* `X` is the major version, `Y` is the minor version, and `Z` is the patch version.
Each element **MUST** *increase numerically*.
* Patch version `Z` **MUST** be *incremented if only backwards compatible bug fixes are introduced*.
A bug fix is defined as an internal change that fixes incorrect behavior.

---

# Versioning in the real world
## [Semantic Versioning (SemVer)](https://semver.org/)

* Minor version `Y` **MUST** be incremented if *new, backwards compatible functionality is introduced* to the public API.
    * It **MUST** be incremented if *any public API functionality is marked as deprecated*.
    * It **MAY** be incremented if *substantial new functionality* or improvements are introduced within the private code.
    * It **MAY** include patch level changes.
    * Patch version **MUST** *be reset to 0* when minor version is incremented.
* Major version `X` **MUST** be incremented if any *backwards incompatible changes* are introduced to the *public API*.
    * It `MAY` *include minor and patch level changes*.
    * Patch and minor version **MUST** *be reset to 0* when major version is incremented.
* Major version zero (`0.y.z`) is for *initial development*.
*Anything may change at any time*.
The public API should not be considered stable.

---

# Versioning in the real world
## [Semantic Versioning (SemVer)](https://semver.org/)

* Version `1.0.0` *defines the public API*.
    * The way in which the version number is incremented after this release is dependent on this public API and how it changes.
* A *pre-release* version **MAY** be denoted by *appending a hyphen and a series of dot separated identifiers* immediately following the patch version.
    * Identifiers **MUST** comprise *only ASCII alphanumerics* and *hyphen* `[0-9A-Za-z-]`.
    * Identifiers **MUST NOT** *be empty*.
    * Numeric identifiers **MUST NOT** *include leading zeroes*.
    * A *pre-release version* indicates that *the version is unstable* and might not satisfy the intended compatibility requirements as denoted by its associated normal version.
* *Build metadata* **MAY** be denoted by *appending a plus sign* and a series of dot separated identifiers immediately following the patch or pre-release version.
    * Identifiers **MUST** comprise *only ASCII alphanumerics and hyphen* `[0-9A-Za-z-]`.

---

## The importance of a versioning methodology

**Think** *before* choosing a versioning schema, and then **be consistent**

* *Semantic versioning is warmly recommended*
    * Can be *integrated with the DVCS*!
    * Dates can be added (e.g. in the pre-release or build-metadata sections)
* *Codenames* can be used informally
    * For internal subprojects
    * As part of product promotion or for commercial activities 
* *Dates* may make sense for projects with fast and steady development
    * Possibly as part of a Semantic Versioned project
    * Dates are useful as part of a *better versioned* system
