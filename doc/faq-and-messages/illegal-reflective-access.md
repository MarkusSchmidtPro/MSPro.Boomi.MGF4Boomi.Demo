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

# Illegal Reflective Access

Typically, when you run a test you will get these WARNINGS:

```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.codehaus.groovy.reflection.CachedClass (file:/C:/Users/marku/.groovy/sdk/groovy-2.4.13/lib/groovy-2.4.13.jar) to method java.lang.Object.finalize()
WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.reflection.CachedClass
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
```

The reason is the outdated Groovy Script Version 2.4.13 and I haven't been able to get rid of it yet. Simply ignore all WARNING on focus on your INFO messages:

<figure><img src="../.gitbook/assets/image (6).png" alt=""><figcaption><p>Typical ouput</p></figcaption></figure>
