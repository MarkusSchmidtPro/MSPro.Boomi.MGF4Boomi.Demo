---
description: One time preparation of your development computer
cover: ../.gitbook/assets/nasa_space_shuttle_columbia.jpg
coverY: 0
---

# Pre-Requisites

There are a few things you need in advance.

<details>

<summary>Local ATOM</summary>

First of all, you need a local ATOM on your machine. There is no need to start the ATOM, but for local debugging Groovy will need to resolve:

* the ATOM’s binaries (Boomi’s Java libraries) and
* the bundled Java Run-Time (JRE).

[install-a-local-atom.md](../readme/pre-requisites-7deb11c4cf894c33b76456ab85cad596/install-a-local-atom.md "mention")

</details>

<details>

<summary>IntelliJ Community Edition - Groovy IDE</summary>

MGF4Boomi has been developed and tested with [**JetBrains IntelliJ IDEA**](https://www.jetbrains.com/idea/download/#section=windows) - the **Community Edition** is good enough -> **download and install** ! All provided project templates have been built for _IntelliJ IDEA_. If you chose another IDE you will have to adjust your project setup according to it.

</details>

<details>

<summary>Groovy 2.4.13 SDK</summary>

Boomi Integration uses **Groovy v2.4.13** to run Groovy scripts, and because you will want to test and debug all your scripts under the same run-time conditions we need the right Groovy SDK for it (v1.5 is not supported).

Normally, the project configuration script takes care that you will have the right version installed. There is **no need to** [**install Groovy manually**](<Pre-Requisites 7deb11c4cf894c33b76456ab85cad596.md#groovy-2.4.13-sdk>).

</details>
