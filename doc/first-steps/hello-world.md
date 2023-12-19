---
description: A closer look at the Hello World Map Script
---

# Hello World

## The Test

The Test serves as a host, as a starting point. You cannot execute a process or map script directly. You need a Test, that creates and provides the execution environment for your script to test.

A single Test Class can contain one or mare test functions.&#x20;

This is what you run: **`@Test void test01()`** (see below)\
and the test then calls the referenced `ScriptFilename`.

```groovy
@TypeChecked
class HelloWorldTest {
    
    // Specify the script's filename that you want to test.
    static final String ScriptFilename = "msgHelloWorld.groovy"
    
    @Test
    void test01() {
        //
        // A Map script requires an ExecutionContext and
        // a Map with an entry for each input parameter.
        // The output is another Map containing all output parameters.
        //
        def inputParameters = [ a: 5,  b: 7 ]

        final MapScript script = new MapScript(ScriptFilename)
        def outputParameters = script.run( 
          ExecutionUtilContexts.empty(), 
          inputParameters)

        // Print to console windows and validate results
        println( "Test Total = " + outputParameters.total)
        assert  outputParameters.total == inputParameters.a + inputParameters.b
    }
}
```

## The Script

The script is placed in the _MyScripts_ folder, which contains all your Boomi scripts as they are copied and pasted into Boomi. Let's check the _HelloWorld_ Map Script.

```groovy
import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "msgHelloWorld"

_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

// This is the script's logic - not much - but anyways...
total = a + b
    
// Log the result and the end of the execution to process reporting
_logger.info("Total: " + total)
_logger.info('<<< End Script')

```

If you look at the Test above, you will see that it defines the `inputParameters [a=5, b=7]`. Then the script builds the total and it does some logging. That's it.

## The Debug Window

Run / Debug the Test!

<div align="left">

<figure><img src="../.gitbook/assets/image (27).png" alt="" width="563"><figcaption></figcaption></figure>

</div>

You have executed the Hello World Test! First thing to look at is the **Debug Window.**

{% hint style="info" %}
Forget about those WARNINGs. You cannot get rid of them. They appear because Groovy v2.4.13 is outdated, from IntelliJ's point of view.&#x20;
{% endhint %}

You can see all logs and outputs in the debug window. There is also the **Threads & Variables** Windows that you will use if you work with breakpoints to see your variables and data.

<figure><img src="../.gitbook/assets/image (25).png" alt=""><figcaption></figcaption></figure>

## How to use in Boomi

To use the tested script in Boomi, create a _Map Script Component_ (**Groovy v2.4**), copy and paste the Script's code - and enjoy!

<figure><img src="../.gitbook/assets/image (26).png" alt=""><figcaption></figcaption></figure>

## Breakpoint

Set a breakpoint in your script and **Debug** the Test again. The execution stops on the calculation line. Watch you variables:

<figure><img src="../.gitbook/assets/image (28).png" alt=""><figcaption></figcaption></figure>

Step over the current line - execute the calculation.

<figure><img src="../.gitbook/assets/image (29).png" alt=""><figcaption></figcaption></figure>

and check your variables

<figure><img src="../.gitbook/assets/image (30).png" alt=""><figcaption></figcaption></figure>
