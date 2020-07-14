 
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

# Short guide to writing slides in Markdown

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

![Alternative text](https://images.unsplash.com/photo-1530878955558-a6c31b9c97db?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9)

---

{{< slide background-image="https://images.unsplash.com/photo-1530878955558-a6c31b9c97db?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9" >}}

# Large images as background
## (warning: hard to print)

---

{{< slide background-image="https://images.unsplash.com/photo-1530878955558-a6c31b9c97db?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9" state="blur-animation-light"  transition="fade-in fade-out" >}}

# Also available with blur and custom transitions
## (Printing is destroyed, avoid on printable decks)

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
