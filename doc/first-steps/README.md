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

# 1⃣ First Steps

I assume you have [everything prepared](../), you have started IntellJ and you are ready to run your first srcipt, right? Let's first look a bit around, what we see in IntelliJ:

<figure><img src="../.gitbook/assets/image (16).png" alt=""><figcaption><p>IntelliJ Community Edition</p></figcaption></figure>

There are three projects

{% tabs %}
{% tab title="MGF4Boomi" %}
_MGF4Boomi_ is the Framework that is needed to emulate the ATOM Runtine on your local machine. The Framework provides the neccesary context to use (Dynamic) Document and Process Properties, as well as the Mapping input and output variables etc. etc.&#x20;

There is no need for you to worry about all these details \
\-> collapse the project and forget about it!
{% endtab %}

{% tab title="MyScript" %}
The _MyScript_ project contains all your **Process**- and **Map Scripts** that you are going to develop, debug and test here. Once you are happy with the result, you can copy and paste the script to Boomi Integration to use it there without any modification.

```
src\
+-- MapScript\
+-- ProcessScript
```
{% endtab %}

{% tab title="Test" %}
The _Test_ folder contains test and test data. A test creates and provides a necessary context for a script to run. In general, you don't run a script directly, you run a test that executes your Boomi Process/Map script.

```
src\
+-- MapScript\
+-- ProcessScript

TestData\
+-- XML\
+--- JSON
```
{% endtab %}
{% endtabs %}

In case there is something to configure: [intellij-setup.md](../intellij-setup.md "mention")

<figure><img src="../.gitbook/assets/image (17).png" alt=""><figcaption></figcaption></figure>

### Hello World Map Script

* Open (double-click) `Test\src\MapScript\`**`msgHelloWorldTest.groovy`**
* click on the green arrow and run the script
* [hello-world.md](hello-world.md "mention")

{% hint style="info" %}
`msg` is my abbreviatoin for **M**essage **S**cript **G**roovy,  \
where `psg` is **P**rocess **S**cript **G**roovy.&#x20;

`...Test.groovy` means, this is a Test - not the script itself.&#x20;
{% endhint %}

<figure><img src="../.gitbook/assets/Untitled 11.png" alt=""><figcaption></figcaption></figure>
