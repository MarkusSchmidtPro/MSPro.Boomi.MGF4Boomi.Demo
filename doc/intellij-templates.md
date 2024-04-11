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
    visible: false
---

# IntelliJ Templates

IntelliJ supports custom file templates that you may use to simplify the creation of new Boomi and Test Scripts.

## Setup Templates

* Navigate to your _Scripts_ folder and create `.\Scripts\.idea\fileTemplates` if it does not exist.
* Download the templates from [here](https://github.com/MarkusSchmidtPro/MGF4Boomi/tree/main/src/templates) \
  or find them under `.\Scripts\lib\MGF4Boomi\src\templates`
  * and copy them to the `.\Scripts\.idea\fileTemplates` folder, \
    so that IntelliJ will recorgnize these files as templates.

{% hint style="info" %}
**You must restart IntelliJ to make it aware fo the new templates.**
{% endhint %}

## Use Templates

Right click on any script folder where you want to create a new test class and/or a new script.

<figure><img src=".gitbook/assets/image.png" alt="" width="563"><figcaption></figcaption></figure>

Fill out the neccessary information for your new test and/or script.&#x20;

<figure><img src=".gitbook/assets/image (2).png" alt="" width="516"><figcaption></figcaption></figure>

click OK and your blank script is there.

<figure><img src=".gitbook/assets/image (3).png" alt="" width="563"><figcaption></figcaption></figure>

## Manage Templates

> File → Settings ⇒ Editor → File and Code Templates

Select **Scheme : Project**

<figure><img src=".gitbook/assets/IntelliJTemplate.png" alt=""><figcaption></figcaption></figure>
