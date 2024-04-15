---
description: One time preparation of your development computer
cover: ../../../.gitbook/assets/target_1990x480.jpg
coverY: 0
layout:
  cover:
    visible: true
    size: hero
  title:
    visible: true
  description:
    visible: true
  tableOfContents:
    visible: true
  outline:
    visible: true
  pagination:
    visible: true
---

# Pre-Requisites

There are a few things you need, before you can start Groovy Scripting on your local machine. With other words: your development environment needs to be setup once.

<details>

<summary>Local ATOM</summary>

First of all, you need a local Boomi ATOM on your machine. There is no need to start the ATOM, but for local debugging Groovy will need to resolve:

* the ATOM’s binaries (Boomi’s Java libraries) and
* the bundled Java Run-Time (JRE).

[install-a-local-atom.md](install-a-local-atom.md "mention")

</details>

<details>

<summary>IntelliJ Community Edition - Groovy IDE</summary>

_Markus' Boomi for Groomi uses_ **JetBrains IntelliJ IDEA.** I recommend the Ultimate Version for professional use, but the [Community Edition](https://www.jetbrains.com/idea/download/other.html) should also be good enough.\
\-> [Download and install IntelliJ](https://www.jetbrains.com/idea/download/?section=windows#section=windows)

</details>

<details>

<summary>Groovy 2.4.13 SDK</summary>

Boomi Integration uses **Groovy v2.4.13** to run Groovy scripts, and because you will want to test and debug all your scripts under the same run-time conditions, we need the right Groovy SDK for it (Groovy v1.5 is not supported!).

Normally, the project configuration script takes care that you will have the right version installed. There is **no need to install Groovy manually.** [more ...](./#groovy-2.4.13-sdk)

</details>
