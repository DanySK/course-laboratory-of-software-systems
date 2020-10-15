 
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
---

# Advanced merging: rebase

```bash
git checkout experiment
git rebase master
```

![](basic-rebase-3.png)


```bash
git checkout master
git merge experiment # Fast forward!
```

![](basic-rebase-4.png)

---

# Advanced rebasing


![](interesting-rebase-1.png)

We want to leave `server` as is, but rebase `client` onto `master`

---

# Advanced rebasing

Option `--onto` can be used to transplant entire branches
* `git rebase --onto destination start end`
    * pick commits from `start` to `end`
    * reply them starting from `destination`

```bash
git rebase --onto master server client
```

![](interesting-rebase-2.png)

Reads: *pick all commits from `server` (excluded) to `client` (included), remove them and reply them starting from `master`*

---

# Rebase pull

`pull` is a non-atomic operation:
1. `git fetch`
2. `git merge FETCH_HEAD`

There is no reason why the operation of branch joining must be `merge`: it could well be `rebase`!

1. `git fetch`
2. `git rebase FETCH_HEAD`

*Both operations* are actually supported! The full commands would be:
* `git pull --merge` $\Rightarrow$ default behaviour (in old `git` versions)
* `git pull --rebase` $\Rightarrow$ reunite using rebase!

**Note:** new versions of git require configuration and do not start with merge as default!
* `git config --global pull.rebase [true/false]`

---

# Perils of rebasing

> **Do not rebase commits that exist outside your repository**

Rebasing *rewrites the project history* and as such generates *incompatible histories*
* Remote pushes may get *refused*!
* pushing with `--force` *rewrites history remotely* and **may delete other people commits**!
* `git pull --rebase` is safe, if the local commits where never pushed in any remote
    * It is actually a good practice to default to it
    * `git config --global pull.rebase true`

---

# Rebasing or merging?

Select depending on what you *conceptually* want:
* I want to **record of what actually happened**: then **merge**
    * History is preserved
    * Messy commits are there
* I want to **tell the story of how your project was made**: then **rebase**
    * History is *modified*
    * Commits are cleaner

They tell two stories:
* *rebase* is the book
* *merge* is the story of how the book was written

---

# Squashing

---

# Cherry picking

---

# Git Submodules
