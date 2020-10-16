 
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

Squashing is the practice of *reassembling multiple commits into a single one*
* Allows to forget "experimental" commits
* Allows to merge temporary changes into a single one 
* *Simplifies* history
* *Alters* history
* Can be performed via `merge` or manually

---

# Squashing manually

{{< gravizo >}}
  digraph G {
    rankdir=LR;
    rankdir=LR;
    C1 -> C2 -> C3 -> C4 -> C5 -> C6 [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    C6 -> HEAD [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}
`git reset --soft HEAD~4`
{{< gravizo >}}
  digraph G {
    graph[splines=ortho]
    rankdir=LR;
    C1
    C1 -> C2  [dir=back]
    {rank=same; C2, HEAD}
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    C3 [style=dashed];
    C4 [style=dashed];
    C5 [style=dashed];
    C6 [style=dashed];
    C2 -> C3 -> C4 -> C5 -> C6 [style=dashed, dir=back];
    rankdir=LR;
    C2 -> HEAD [dir=back, weight=0, penwidth=4, color=orange];
  }
{{< /gravizo >}}
`git commit`
{{< gravizo >}}
  digraph G {
    rankdir=LR;
    C1 -> C2 -> "C3'" [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    "C3'" [style=filled, fillcolor=red]
    "C3'" -> HEAD [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}

---

# Squashing with merge

{{< gravizo >}}
  digraph G {
    rankdir=LR;
    C1 -> C2 -> C3 -> C4 -> C5 -> C6 [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    master [style="filled,solid", shape=box, fillcolor=orange];
    {rank=same; master, HEAD}
    C6 -> HEAD [dir=back, penwidth=4, color=orange];
    C6 -> master [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}
`git branch target`
{{< gravizo >}}
  digraph G {
    rankdir=LR;
    C1 -> C2 -> C3 -> C4 -> C5 -> C6 [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    master [style="filled,solid", shape=box, fillcolor=orange];
    target [style="filled,solid", shape=box, fillcolor=orange];
    {rank=same; master, target, HEAD}
    C6 -> HEAD [dir=back, penwidth=4, color=orange];
    C6 -> master [dir=back, penwidth=4, color=orange];
    C6 -> target [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}
`git reset --hard HEAD~4`
{{< gravizo >}}
  digraph G {
    rankdir=LR;
    master [style="filled,solid", shape=box, fillcolor=orange];
    C1 -> C2 -> C3 -> C4 -> C5 -> C6 [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    target [style="filled,solid", shape=box, fillcolor=orange];
    {rank=same; C2, master}
    {rank=same; C3, HEAD}
    C2 -> HEAD [dir=back, penwidth=4, color=orange];
    C2 -> master [dir=back, penwidth=4, color=orange];
    C6 -> target [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}

---

# Squashing with merge

{{< gravizo >}}
  digraph G {
    rankdir=LR;
    master [style="filled,solid", shape=box, fillcolor=orange];
    C1 -> C2 -> C3 -> C4 -> C5 -> C6 [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    target [style="filled,solid", shape=box, fillcolor=orange];
    {rank=same; C2, master}
    {rank=same; C3, HEAD}
    C2 -> HEAD [dir=back, penwidth=4, color=orange];
    C2 -> master [dir=back, penwidth=4, color=orange];
    C6 -> target [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}
`git merge --squash target`
{{< gravizo >}}
  digraph G {
    compound=true;
    rankdir=LR;
    master [style="filled,solid", shape=box, fillcolor=orange];
    C1 -> C2 [dir=back]
    C2 -> C3 [weight=2, dir=back]
    C2 -> "C3'" [weight=1]
    "C3'" [style=filled, fillcolor=red]
    subgraph cluster_0 {
        C3 -> C4 -> C5 -> C6 [dir=back];
        color=red
        style=dashed
    }
    "C3'" -> C4 [lhead=cluster_0, style="dashed", dir=back, color=red]
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    target [style="filled,solid", shape=box, fillcolor=orange];
    "C3'" -> HEAD [dir=back, penwidth=4, color=orange];
    "C3'" -> master [dir=back, penwidth=4, color=orange];
    C6 -> target [dir=back, penwidth=4, color=orange];
    {rank=same; C2, master}
    {rank=same; "C3'", HEAD}
  }
{{< /gravizo >}}
`git branch -d target`
{{< gravizo >}}
  digraph G {
    rankdir=LR;
    C1 -> C2 -> "C3'" [dir=back];
    HEAD [style="filled,solid", shape=box, fillcolor=orange];
    "C3'" [style=filled, fillcolor=red]
    "C3'" -> HEAD [dir=back, penwidth=4, color=orange];
    master [style="filled,solid", shape=box, fillcolor=orange];
    "C3'" -> master [dir=back, penwidth=4, color=orange];
  }
{{< /gravizo >}}

---

# Squash, merge, or rebase?

Squashing results in *further alteration* than rebase

**Merge** when you want to *retain history*, keeping track of what happened

**Rebase** only when you are the only one with the commits, to favor *linearity*

**Squash** when you are the only one, and some of the commits are somewhat "tests", points in time you do not want to get back to anyway

---

# Cherry picking

---

# Git Submodules
