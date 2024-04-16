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

# \_First Steps

I assume you have [everything prepared](../../), you have started IntellJ and you are ready to run your first srcipt, right? Let's first look a bit around, to see what we see in IntelliJ:

<figure><img src="broken-reference" alt=""><figcaption><p>IntelliJ Community Edition</p></figcaption></figure>

There are three projects

{% tabs %}
{% tab title="MyScript" %}
The _MyScript_ project contains all your **Process**- and **Map Scripts** that you are going to develop, debug and test here. Once you are happy with the result, you can _copy & paste_ the code from the script file to Boomi Integration where you can use it there without any modification.

```
src\
+-- MapScript\
+-- ProcessScript
```
{% endtab %}

{% tab title="Test" %}
The _Test_ folder contains tests and test data. A Test has typicall two purposes:

a) ensure that your script(s) work according to the specifications

b) serve as a host to run Process- or Map-Scripts in well defined context.

In general, you won't run a Process or Map-Script directly, you run a Test that executes your Boomi Process/Map script.

```
src\
+-- MapScript\
+-- ProcessScript

TestData\
+-- XML\
+--- JSON
```
{% endtab %}

{% tab title="MGF4Boomi" %}
_MGF4Boomi_ is the Framework that is needed to emulate the ATOM Runtime on your local machine. The Framework provides the neccesary context to use (Dynamic) Document and Process Properties, as well as the Mapping input and output variables etc. etc.&#x20;

There is no need for you to worry about all these details \
\-> collapse the project and forget about it!
{% endtab %}
{% endtabs %}

### Run your first "Hello World" Map Script

* Open (double-click) <mark style="color:blue;">**`Test`**</mark>`\src\mapScript\`**`HelloWorld`**<mark style="color:blue;">**`_Test`**</mark>**`.groovy`**\
  You remember: the Test is the host from where you run scripts.
* Click on the green arrow in the Test and run the script\
  ![](broken-reference)
* Open the Map-Script: **`MyScript/src/`**<mark style="color:green;">**`mapScript`**</mark>`/msgHelloWorld.groovy`

{% hint style="info" %}
`msg` is my abbreviation for **M**essage **S**cript **G**roovy,  \
where `psg` is **P**rocess **S**cript **G**roovy.&#x20;
{% endhint %}

* Check the script code and see the output window

<div align="left">

<figure><img src="broken-reference" alt="" width="563"><figcaption><p>IntelliJ - Run Results</p></figcaption></figure>

</div>

*   Once you are happy with the results, copy & paste the `msgHelloWorld.groovy` scipt file code into AtomSphere and use it.

    <figure><img src="broken-reference" alt=""><figcaption><p>Script Code copied &#x26; pasted</p></figcaption></figure>
* The Scipt in Action

<div align="center">

<figure><img src="broken-reference" alt="" width="563"><figcaption><p>The Script in a MAP with fix input parameters: a=8, b=6</p></figcaption></figure>

</div>

* Check the logs after execution

<figure><img src="broken-reference" alt="" width="563"><figcaption></figcaption></figure>
