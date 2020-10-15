 
+++

title = "Advanced Versioning Techniques"
description = "Submodules, rebasing, squashing, cherry-picking"
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

## Advanced Versioning Techniques

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Advanced merging

* In classic merging, diverging branches are reunited through a **merge commit**

![](basic-rebase-1.png)

---

# Advanced merging

```bash
git checkout master
git merge experiment
```

![](basic-rebase-2.png)

---

# Advanced merging: rebase

* Merging is not the only way to reunite diverging branches
* We may want, for instance, to simulate that `C4` was developed after `C3`
    * For instance, because it was on a separate part of the codebase
* Merging *forces* to record the creation and reunion of a development line, but in some cases it may be undesirable
    * Project history *hard to understand* because of too many merges
    * The separation was actually a successful small experiment

**Rebasing** provides a way to alter the project history by *changing the parent*  of (re-base) existing commits

---

# Advanced merging: rebase

![](basic-rebase-1.png)

```bash
git checkout experiment
git rebase master
```

![](basic-rebase-3.png)




