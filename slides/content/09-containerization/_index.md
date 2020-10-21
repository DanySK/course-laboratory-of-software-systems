 
+++

title="Containerization"
description="Containerization basics and CI"
outputs=["Reveal"]

[reveal_hugo]
transition="slide"
transition_speed="fast"
custom_theme="custom-theme.scss"
custom_theme_compile=true

[reveal_hugo.custom_theme_options]
targetPath="css/custom-theme.css"
enableSourceMap=true

+++

# {{< course_name >}}

## Containerization: Basics and CI

### [Danilo Pianini](mailto:danilo.pianini@unibo.it)

{{< today >}}

---

# Containers

{{< gravizo >}}
digraph structs {
    rankdir=LR
    graph [fontname="helvetica", layout=neato]
    edge [fontname="helvetica"]
    node [fontname="helvetica", shape=record, style="rounded, filled", fontcolor=white, fixedsize=true, width=3];
    "Bare metal" [shape=plaintext, style="", fontcolor=black fontsize=20, pos="0,0!", layout=neato]
    Physical [fillcolor=black, pos="0,0.5!"]
    "Operating System" [fillcolor=gray, pos="0,1.1!"]
    Runtime [fillcolor=green, pos="0,1.7!"]
    App1 [fillcolor=red, width=0.95, pos="-1.025,2.3!"]
    App2 [fillcolor=red, width=0.95, pos="0,2.3!"]
    App3 [fillcolor=red, width=0.95, pos="1.025,2.3!"]

    "Virtual Machine" [shape=plaintext, style="", fontcolor=black fontsize=20, pos="3.5,0!"]
    VM_Phy [label=Physical, fillcolor=black, pos="3.5,0.5!"]
    "Host Operating System" [fillcolor=gray, pos="3.5,1.1!"]
    Hypervisor [fillcolor=orange, pos="3.5,1.7!", fontcolor=black]
    GuestOS1 [fillcolor=gray, width=0.95, pos="2.475,2.3!"]
    GuestOS2 [fillcolor=gray, width=0.95, pos="3.5,2.3!"]
    GuestOS3 [fillcolor=gray, width=0.95, pos="4.525,2.3!"]
    Runtime1 [fillcolor=green, width=0.95, pos="2.475,2.9!"]
    Runtime2 [fillcolor=green, width=0.95, pos="3.5,2.9!"]
    Runtime3 [fillcolor=green, width=0.95, pos="4.525,2.9!"]
    VM_App1 [label=App1, fillcolor=red, width=0.95, pos="2.475,3.5!"]
    VM_App2 [label=App2, fillcolor=red, width=0.95, pos="3.5,3.5!"]
    VM_App3 [label=App3, fillcolor=red, width=0.95, pos="4.525,3.5!"]

    "Container" [shape=plaintext, style="", fontcolor=black fontsize=20, pos="7,0!"]
    C_Phy [label=Physical, fillcolor=black, pos="7,0.5!"]
    C_K [label="Operating System", fillcolor=gray, pos="7,1.1!"]
    "Container Service" [fillcolor=blue, pos="7,1.7!"]
    C_Rt1 [label=Runtime1, fillcolor=green, width=0.95, pos="5.975,2.3!"]
    C_Rt2 [label=Runtime2, fillcolor=green, width=0.95, pos="7,2.3!"]
    C_Rt3 [label=Runtime3, fillcolor=green, width=0.95, pos="8.025,2.3!"]
    C_App1 [label=App1, fillcolor=red, width=0.95, pos="5.975,2.9!"]
    C_App2 [label=App2, fillcolor=red, width=0.95, pos="7,2.9!"]
    C_App3 [label=App3, fillcolor=red, width=0.95, pos="8.025,2.9!"]
}
{{< /gravizo >}}

Runtime **isolation** without operating system **replication**

---

# Why containers?

{{< image src="memes/works-on-my-machine.jpeg" width="36.5">}}
{{< image src="memes/docker-born.jpeg" width="33">}}

---

## Lightweight virtual machines?

{{< image src="memes/lightweight-container.jpg" >}}

---

## Lightweight virtual machines?

{{< image src="memes/chroot.jpeg" >}}


