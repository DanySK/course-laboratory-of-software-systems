 
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

## Software licensing

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# What is a license

**A legal instrument used to regulate access, use, and redistribution of software**

* You generally want to retain some guarantees on the software, e.g.:
    * be recognized as the *original creator*;
    * decide whether or not someone else can *redistribute* it
    * decide under which circumstances the software can be *used*
    * *get paid* if others use it
* Law can change greatly among countries
    * If you are not a lawyer, or you can’t pay a lawyer, **don’t come up with your own license**
    * A custom license is likely to be in *conflict* with the law of some countries
    * Better choosing something *proven to work*

---

# Copyright vs. Copyleft

### Copyright

**Legal right** that *grants the creator* of an original work *exclusive* rights for its *use* and *(re)distribution*

### Copyleft

**Practice** (*not a legal right!*) in which *the creators surrenders some, but not all, rights* under copyright law.
* *Strong*: all derived works inherit the copyleft license
* *Weak*: some derived work may not inherit it
* *Full*: all the parts of the work are distributed under the terms of the copyleft license
* *Partial*: only some parts are covered by the copyleft license.

---

# Ownership vs. Licensing

### Ownership

**Possession** of a copy of software.

The possession *implies right to use*, even if such use implies a violation of the license (e.g. for making changes to the software, or making incidental copies).

### Licensing

The software is not sold, but merely “licensed”, namely **permitted to be used**, *under the conditions of a End-user license agreement* (EULA).

---

# Proprietary vs. Free

{{<image src="rights.svg">}}

### Proprietary

The software publisher grants the right to use *a certain number of copies under the conditions of an EULA*, but **does not transfer ownership** of the copies to the customer.
Usage of the software may be subjected to acceptance of the EULA.

### Free

The software publisher grants *extensive rights to modify and redistribute* the software, often prohibiting rolling back such rights (strong copyleft).