---
cover: ../../.gitbook/assets/positivity-message-cartoon-illustrations-concept.jpg
coverY: 0
layout:
  cover:
    visible: true
    size: full
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

Creating a new project is as simple as **creating a folder and running a PowerShell script**.

* Use any folder for your _scripts_ project,\
  for example:  `Boomi.Spaces\MSPro\`

{% hint style="danger" %}
<mark style="color:red;">**DO NOT create your project on a Google Drive synced folder.**</mark>

G-Drive folders are not appropriate for development purposes because G-Drive keeps locks on files and folders which will lead to unpredictable results.
{% endhint %}

* [Download the **`create-project.ps1`** script from GitHub](https://github.com/MarkusSchmidtPro/Boomi.Groovy.ReferenceProject/blob/main/bin/create-project.ps1) and \
  ![](../../.gitbook/assets/githubDownloadFile.png)
  * save it into you project folder.
* **Right**-**click** the script file and select _**Run with PowerShell**_

<figure><img src="../../.gitbook/assets/image.png" alt=""><figcaption></figcaption></figure>

* After a couple of seconds \
  ![](../../.gitbook/assets/psWorking.png)\
  you should have got a `Scripts` directory, ready for use.

<figure><img src="../../.gitbook/assets/image (1).png" alt=""><figcaption><p>Empty project directory with .idea folder</p></figcaption></figure>

* Start [IntellJ Community Edition](pre-requisites-7deb11c4cf894c33b76456ab85cad596/) and open your project

<figure><img src="../../.gitbook/assets/image (5).png" alt=""><figcaption><p>Open the Script directory (with .idea) in IntelliJ</p></figcaption></figure>

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



[First time setup with IntelliJ](../../MGF4Boomi%20-%20Groovy%20for%20Boomi%20bfadc9ce63764373816fa22fccd3cdc1/Getting%20Started%20019408ce4279434d934d162b6ed03d4e/Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/First%20time%20setup%20with%20IntelliJ%208996f46e6cbe4fe9aac05d0d0a53dac2.md)
