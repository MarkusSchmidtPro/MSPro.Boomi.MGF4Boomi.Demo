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

# IntelliJ Setup

With IntelliJ IDEA we have to do a first time setup to tell IntelliJ IDEA about the [Local disk folder structure](https://www.notion.so/Local-disk-folder-structure-d010906aac0344bab591f7bebd243856?pvs=21) .

*   Run IntelliJ IDEA and open your first project

    <figure><img src="../.gitbook/assets/Untitled.png" alt=""><figcaption></figcaption></figure>

IntelliJ IDEA will complain about not yet knowing the global libraries and SDKs.

<figure><img src="../.gitbook/assets/Untitled 1.png" alt=""><figcaption></figcaption></figure>

Do not click on these yellow bars, yet. We will setup manually!!!

### Three things to do

<details>

<summary>Configure the ATOMSphere JDK</summary>

*   Open the Module settings or press F4 \
    **Platform Settings** → **SDKs** → **+** → **Add JDK …**\


    <figure><img src="../.gitbook/assets/Untitled 2.png" alt="" width="491"><figcaption></figcaption></figure>

<!---->

*   Actually, we do not need the JDK, the JRE from the ATOM is right for us.\


    <figure><img src="../.gitbook/assets/Untitled 3.png" alt=""><figcaption></figcaption></figure>

</details>

<details>

<summary>Configure Groovy SDK 2.4.13</summary>

![](<../.gitbook/assets/Untitled 4.png>)

Use **Library → Create … → Choose Groovy SDK** location

![](<../.gitbook/assets/Untitled 5 (2).png>)

Close the dialog → **OK** and stop! A project library can be used only in the current project. However, we want to use the Groovy SDK in all future projects. That's why we want to **configure it as a global library**.

![](<../.gitbook/assets/Untitled 6.png>)

![](<../.gitbook/assets/Untitled 7.png>)

</details>

<details>

<summary>Configure the ATOMSphere libraries</summary>

In your Project Dialog (F4) add a New Global Library: `Java C:\Program Files\Boomi AtomSphere\LocalAtom\lib`

<img src="../.gitbook/assets/Untitled 8.png" alt="" data-size="original">

Add it to all Modules

![](<../.gitbook/assets/Untitled 9.png>)

And, finally, give it a more meaningful name

![](<../.gitbook/assets/Untitled 10.png>)

</details>

You are ready to go with IntelliJ and you can start debugging the _HelloWorldTest_ script:

<figure><img src="../.gitbook/assets/Untitled 11.png" alt=""><figcaption></figcaption></figure>
