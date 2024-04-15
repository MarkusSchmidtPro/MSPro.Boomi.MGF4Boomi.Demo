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

# UnauthorizedAccess error

```
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
