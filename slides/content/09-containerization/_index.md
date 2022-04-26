 
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

{{< image src="memes/works-on-my-machine.jpeg" width="23">}}
{{< image src="memes/docker-born.jpeg" width="20.8">}}

---

## Lightweight virtual machines?

{{< image src="memes/lightweight-container.jpg" >}}

---

## More similar to well-confined processes

{{< image src="memes/chroot.jpeg" height="60" >}}

---

# Docker

Docker is a containerization platform

*Standard de-facto* in industry

**Base concepts**
- *Image*
  * a *read-only template* with instructions for creating a Docker container
  * images can get built upon other images
  * images are made of a stack of *layers*
- *Container*
  * a *runnable instance* of an image
  * namely, a "writable layer" atop an image
- *Service*
  * A software component in charge of running one or multiple containers

---

# Docker architecture

- *Registry*: repository of images
- *Daemon*: service pulling images from registries and instancing containers
- *Client*: interface towards the daemon

![](architecture.svg)

---

# Running docker containers

1. Install docker
2. Add your user to the `docker` group
3. Enable the docker service (on most Linux distributions `systemctl start docker`)
4. Pull an image: `docker pull adoptopenjdk`
5. Run a container! `docker run adoptopenjdk`

Every container provides a *default command*, running without options runs such default in a *non-interactive* terminal.

Running in interactive mode can be achieved with the `-i` option

Running a custom command can be achieved with writing the command after the image name
* e.g., `docker run -i adoptopenjdk bash`
* parameters for the custom command can follow
* use the `t` option to run in a *pseudo-tty*
* use the `--rm` to remove the container after use

---

# Interaction with the outside world

A docker container runs *in isolation*.

Environment variables, network ports, and file system folders are **not** shared.

Sharing must be explicit and requires options to be specified

* Passing environment variables: `-e <name>=<value>`
* Mounting volumes: `-v <host>:<guest>:<options>`
  * `<host>` is the path on the host system
  * `<guest>` is the location where it will be mounted on the guest
  * `<options>` can be optionally specified as mount options (e.g., `rw`, `ro`)
* Publishing ports: `-p <host>:<guest>`
  * `<host>` is the port on the host system
  * `<guest>` is the corresponding port on the container

---

# Managing images

Every image has a unique **ID**, and may have an associated **tag**

The subcommand `images` lists the pulled images and their associated information

The subcommand `image` allows for running maintenance tasks, e.g.
* `docker image ls` -- same as `docker images`
* `docker image prune` -- removes unused images
* `docker image rm` -- removes images by name
* `docker image tag` -- associates a tag to an image

---

# Creating docker images

Docker images are written in a *Dockerfile*

Every command inside a Dockerfile generates a new *layer*

The final stack of layers creates the final *image*

The `docker build` command interprets the Dockerfile commands to produce a sequence of layers

Changes to a layer do not invalidate previous layers

---

# Dockerfile syntax

```dockerfile
# Pulls an image from docker hub with this name. Alternatively, "scratch" can be used for an empty container
FROM manjarolinux/base 
# Runs a command
RUN pacman -Sy --noconfirm gnupg archlinux-keyring manjaro-keyring
# Copies a file from the local folder into the image
COPY makepkg.conf /etc/makepkg.conf
# Adds a new environment variable
ENV GEM_HOME=/rubygems/bin
# Configures the default command to execute
CMD bash
```

---

# Naming images

Image naming is done via *tags*

The easiest way to do so is assigning tags at *build time* with the `-t` options of `docker build`

The option can be repeated multiple times to make multiple tags

```bash
docker build -t "myImage:latest" -t "myImage:0.1.0"
```

`latest` is usually used to identify the most recent version of some image

---

# Publishing docker images

Images get published in *registries* 

The most famous, free for publicly available images, is *Docker Hub*

By default, Docker uses Docker Hub as registry (both for `pull` and `push` operations)

Docker Hub requires registration and CLI login:
* `docker login docker.io`

Once done, publication is performed via `push`:
* `docker push <image name>`

---

# Building docker images in CI

Of course, as any other software, *custom docker images should get built in CI*

Several integrators use containers as build environments: it is possible to *build a container using a container*

More in general, there is *no inherent limit to nesting containers*
