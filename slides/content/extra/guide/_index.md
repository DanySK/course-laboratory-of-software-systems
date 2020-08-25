 
+++

title = "Guide for writing markdown slides"
description = "A Hugo theme for creating Reveal.js presentations"
outputs = ["Reveal"]
aliases = [
    "/guide/"
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

# {{< course_name >}}
## Short guide to writing slides in Markdown

---

# Headers

# H1
## H2
### H3
#### H4

---

# Text

normal text

`inline code`

*italic*

**bold**

**_emphasized_**

*__emphasized alternative__*

~~strikethrough~~

[link](http://www.google.com)

---

# Lists and enums

1. First ordered list item
1. Another item
    * Unordered sub-list.
    * with two items
        * another sublist
            1. With a sub-enum
            1. yay!
1. Actual numbers don't matter, just that it's a number
  1. Ordered sub-list
1. And another item.

---

# Inline images

![Alternative text](hires.png)

---

## Fallback to shortcodes for resizing

Autoresize specifying

* `max-w` (percent of parent element width) and/or `max-h` (percent of viewport height) as max sizes , and
* `width` and/or `height` as *exact* sizes (as percent of viewport size)

{{< image src="hires.png" max-h="40">}}

---

## Low res, plain markdown

![](memelr.jpeg)

---

## Hi res, plain markdown

![](hires.png)

---

## Low res, default

{{< image src="memelr.jpeg" >}}

---

## Hi res, default

{{< image src="hires.png" >}}

---

## Low res, enlarged horizontally

{{< image src="memelr.jpeg" width="100">}}

---

## Low res, enlarged vertically

{{< image src="memelr.jpeg" height="100">}}

---

## Hi res, reduced horizontally

{{< image src="hires.png" width="50">}}

---

## Hi res, reduced vertically

{{< image src="hires.png" height="50">}}

---

## Hi res, reducing maximum expansion horizontally

{{< image src="hires.png" max-w="50">}}

---

## Hi res, reducing maximum expansion vertically

{{< image src="hires.png" max-h="50">}}

---

{{< slide background-image="hires.png" >}}

# Large images as background
## (May affect printing)

---

{{< slide background-image="hires.png" state="blur-animation-light"  transition="fade-in fade-out" >}}

# Also available with blur and custom transitions
## (May affect printing)

---

# $$\LaTeX{}$$


Inline equations like $E=mc^2$

$$\frac{n!}{k!(n-k)!} = \binom{n}{k}$$  

---

# Code snippets


```kotlin
val x = pippo
```

```go
package main
 
import "fmt"
 
func main() {
    fmt.Println("Hello world!")
}
```

---

# Tables

Colons can be used to align columns.

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

There must be at least 3 dashes separating each header cell.
The outer pipes (|) are optional, and you don't need to make the 
raw Markdown line up prettily. You can also use inline Markdown.

---

# Quotes

> Multiple
> lines
> of
> a
> single
> quote
> get
> joined

> Very long one liners of Markdown text automatically get broken into a multiline quotation, which is then rendered in the slides.

---

# Fragments

* {{< frag c="pluto" >}}
* {{< frag c="pluto" >}}
* {{< frag c="pluto" >}}
