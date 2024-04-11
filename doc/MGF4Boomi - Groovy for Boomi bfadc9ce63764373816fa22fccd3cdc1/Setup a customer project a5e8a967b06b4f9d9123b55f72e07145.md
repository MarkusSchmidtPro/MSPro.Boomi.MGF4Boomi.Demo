---
layout:
  title:
    visible: true
  description:
    visible: false
  tableOfContents:
    visible: true
  outline:
    visible: true
  pagination:
    visible: true
---

# Create a new Project

{% hint style="danger" %}
<mark style="color:red;">**DO NOT create your project on a Google Drive Synced folder.**</mark>

<mark style="color:blue;">G-Drive folders are not appropriate for development purposes because G-Drive keeps locks on files and folders which will lead to unpredictable results.</mark>
{% endhint %}

{% hint style="info" %}
To get started, you simply **create a new project** as described below.

If this is your first time setup you must read the [Pre-Requisites 7deb11c4cf894c33b76456ab85cad596.md](<Pre-Requisites 7deb11c4cf894c33b76456ab85cad596.md> "mention") before, \
for example, to install your IDE.
{% endhint %}

Creating a new project is as simple as **creating a folder and running a PowerShell script**.

* Use any folder for your _scripts_ project,\
  for example:  `Boomi.Spaces\MSPro\`
* [Download the **`create-project.ps1`** script from GitHub](https://github.com/MarkusSchmidtPro/Boomi.Groovy.ReferenceProject/blob/main/bin/create-project.ps1) and&#x20;
  * save it into that folder.

<figure><img src="../.gitbook/assets/Untitled.png" alt=""><figcaption><p>GitHub</p></figcaption></figure>

*   **Right**-click the script file and _Run with PowerShell_

    <div align="left">

    <figure><img src="../.gitbook/assets/Untitled 1.png" alt=""><figcaption></figcaption></figure>

    </div>
*   After a couple of seconds \
    ![](broken-reference)\
    you should have got a `Scripts` directory, ready for use.\


    <div align="left">

    <figure><img src="broken-reference" alt="" width="460"><figcaption><p>Script directory</p></figcaption></figure>

    </div>
* Start [IntellJ Community Edition](<Pre-Requisites 7deb11c4cf894c33b76456ab85cad596.md>) and open your project

<div align="left">

<figure><img src="broken-reference" alt="" width="399"><figcaption></figcaption></figure>

</div>

<details>

<summary>UnauthorizedAccess</summary>

```jsx
PS C:\vStudio\BoomiProjects\ABC> **Get-ExecutionPolicy -List**

        Scope ExecutionPolicy
        ----- ---------------
MachinePolicy       Undefined
   UserPolicy       Undefined
      Process       Undefined
  CurrentUser       Undefined
 LocalMachine       AllSigned

**> Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope CurrentUser**
```

</details>



[First time setup with IntelliJ](../MGF4Boomi%20-%20Groovy%20for%20Boomi%20bfadc9ce63764373816fa22fccd3cdc1/Getting%20Started%20019408ce4279434d934d162b6ed03d4e/Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/First%20time%20setup%20with%20IntelliJ%208996f46e6cbe4fe9aac05d0d0a53dac2.md)
