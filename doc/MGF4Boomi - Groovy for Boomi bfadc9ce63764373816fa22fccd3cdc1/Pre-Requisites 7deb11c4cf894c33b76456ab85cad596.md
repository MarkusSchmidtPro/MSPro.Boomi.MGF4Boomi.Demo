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

[Download and install an ATOM](https://help.boomi.com/bundle/integration/page/t-atm-Downloading\_the\_local\_Atom\_installer.html) on your local machine.

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled.png" alt="Untitled" data-size="original">

Do not forget to copy you installation token:

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled 1.png" alt="Untitled" data-size="original">

It is very recommended you install the ATOM in this path: `c:\Program Files\Boomi AtomSphere\LocalAtom\`

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled 2.png" alt="Untitled" data-size="original">

</details>

<details>

<summary>Groovy 2.4.13 SDK</summary>

Boomi Integration uses **Groovy 2.4.13** to run Groovy scripts (v1.5 is not supported here). And because we want to test and debug all our scripts under the same run-time conditions we need the right Groovy SDK for it.

Chose between manual installation or a PowerShell script.

#### Manual download and installation

* [Download the Groovy SDK](https://archive.apache.org/dist/groovy/2.4.13/distribution/) **apache-groovy-sdk-2.4.13.zip**&#x20;
* Unzip to `%UserProfile%\.groovy\sdk\groovy-2.4.13`.

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled 5.png" alt="Untitled" data-size="original">

#### Use PowerShell script to install the right Groovy SDK

[Download `get-groovy.ps1` from GitHub](https://github.com/MarkusSchmidtPro/Boomi.Groovy.ReferenceProject/blob/8022e34655b0c4dd4a641d6f9ec4558e8b60d8a8/bin/get-groovy.ps1)

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled 3.png" alt="Untitled" data-size="original">

and run in a PowerShell console:

<img src="Getting Started 019408ce4279434d934d162b6ed03d4e/Pre-Requisites 7deb11c4cf894c33b76456ab85cad596/Untitled 4.png" alt="Untitled" data-size="original">

</details>

<details>

<summary>Groovy IDE</summary>

The last component you need is a **Groovy IDE**.

I recommend [download and install **JetBrains IntelliJ IDEA**](https://www.jetbrains.com/idea/download/#section=windows) (enable the new UI).

The provided project templates have been built for _IntelliJ IDEA_. If you chose another IDE you will have to adjust your project setup according to it.

Optional but recommended: [Visual Studio Code](https://code.visualstudio.com/) (VS-Code).

</details>
