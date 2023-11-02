# Setup a customer project

Navigate to your `[%ProjectRoot%` folder](Pre-Requisites%207deb11c4cf894c33b76456ab85cad596.md).

I recommend you create a new folder for customer, for example, `G:\BoomiProjects> **md DemoCustomer**`

- [Download the `create-project.ps1` script from GitHub](https://github.com/MarkusSchmidtPro/Boomi.Groovy.ReferenceProject/blob/8022e34655b0c4dd4a641d6f9ec4558e8b60d8a8/bin/create-project.ps1)
- Save it into your customer project folder

> PS C:\vStudio\BoomiProjects\ABC> .\create-project.ps1
.\create-project.ps1 : File C:\vStudio\BoomiProjects\ABC\create-project.ps1 cannot be loaded. The file
C:\vStudio\BoomiProjects\ABC\create-project.ps1 is not digitally signed. You cannot run this script on the
current system. For more information about running scripts and setting execution policy, see
about_Execution_Policies at https:/go.microsoft.com/fwlink/?LinkID=135170.
At line:1 char:1
> 

Default

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

- .\create-project.ps1
- `+ CategoryInfo : SecurityError: (:) [], PSSecurityException + FullyQualifiedErrorId : UnauthorizedAccess`
- Run the script

![Untitled](Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/Untitled.png)

![Untitled](Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/Untitled%201.png)

Et voilà, the project is ready → [Script Folder](Local%20disk%20folder%20structure%20d010906aac0344bab591f7bebd243856.md) 

You can now **open** the `DemoCustomer\**Scripts`** folder with **IntelliJ**

![Untitled](Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/Untitled%202.png)

[First time setup with IntelliJ](Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/First%20time%20setup%20with%20IntelliJ%208996f46e6cbe4fe9aac05d0d0a53dac2.md)