---
cover: .gitbook/assets/positivity-message-cartoon-illustrations-concept.jpg
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
    visible: false
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
  ![](.gitbook/assets/githubDownloadFile.png)
  * save it into you project folder.
* **Right**-**click** the script file and select _**Run with PowerShell**_

<div align="left">

<figure><img src=".gitbook/assets/image (5).png" alt="" width="563"><figcaption></figcaption></figure>

</div>

* After a couple of seconds \
  ![](.gitbook/assets/psWorking.png)\
  you should have got a `Scripts` directory, ready for use.

<div align="left">

<figure><img src=".gitbook/assets/image (1) (1).png" alt="" width="563"><figcaption><p>Empty project directory with .idea folder</p></figcaption></figure>

</div>

[using-groovy-for-boomi](using-groovy-for-boomi/ "mention")
