---
description: Test and Scripts
---

# General

There are **Tests** and there are Map- and Process **Scripts**.

## The Test

The Test serves as a host, as a starting point. You cannot execute a process or map script directly. You need a Test, that creates and provides the execution environment for your script to test.

{% hint style="info" %}
A single Test class can contain one or mare `@Test` functions. This makes sense if you want to Unit Test your scripts. \
I do normally start with one or two tests functions (incl. edge tests), to debug and test what I am developing.\
Over the time, when my script evolves and gets new functionality, I add new Tests. At the end of the day, you always want to run _all_ tests successfully. The old ones, because you want to ensure the script's behaviours has not changed, and the new ones, to ensure the new functionality works properly.
{% endhint %}

This is what you run: **`@Test void test01()`**and the test then calls the referenced _MapScript_.

```groovy
class HelloWorld_Test 
{
    // Specify the Boomi Script that your want to test in this class.
    final MapScript _testScript = new MapScript("msgHelloWorld.groovy")

    @Test
    void test01() {

        HashMap scriptContext = [
                a: 5,
                b: 7
        ]
        _testScript.run(scriptContext)

        // -----------------------------------------------------
        // Perform your tests here to check whether the script 
        // execution met your expectation.
        assert scriptContext.total != null, "Script did not set 'total' as output parameter!"
        assert scriptContext.total == scriptContext.a + scriptContext.b, "Calculation result does not meet expectations!"

        // Print to console windows and validate results
        println("Test Total = " + scriptContext.total)
    }
}
```

## The Script

The script is placed in the _MyScripts_ project, which contains all your Boomi scripts as they are copied and pasted into Boomi. Let's check the _HelloWorld_ Map Script.

```groovy
import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "msgHelloWorld"

final _logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

// This is the script's logic - not much - but anyways...
total = a + b
    
// Log the result and the end of the execution to process reporting
_logger.info("Total: " + total)
_logger.info('<<< End Script')

```

If you look at the Test above, you will see that the Test defines the `inputParameters [a=5, b=7]`. Then the script builds the total and it does some logging. That's it.
