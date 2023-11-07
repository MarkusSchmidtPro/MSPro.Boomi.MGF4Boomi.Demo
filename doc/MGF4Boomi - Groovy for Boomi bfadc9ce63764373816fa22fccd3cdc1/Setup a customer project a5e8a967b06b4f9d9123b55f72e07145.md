# Setup a customer project

I recommend having a dedicated directory on your hard-drive for all your Boomi documents. Our example folder is called `DemoCustomer`. (I decided to use customer names for Boomi folders to be able to support more than one customer.)

*   [Download the `create-project.ps1` script from GitHub](https://github.com/MarkusSchmidtPro/Boomi.Groovy.ReferenceProject/blob/8022e34655b0c4dd4a641d6f9ec4558e8b60d8a8/bin/create-project.ps1)

    <figure><img src="../.gitbook/assets/image (11).png" alt=""><figcaption><p>GitHub</p></figcaption></figure>
*   Save it into your customer project folder: `DemoCustomer`

    <figure><img src="../.gitbook/assets/image (12).png" alt=""><figcaption><p>Save file to project folder</p></figcaption></figure>
*   **Right**-click the file and _Run with PowerShell_

    <figure><img src="../.gitbook/assets/image (13).png" alt=""><figcaption></figcaption></figure>
*   Finally you shoudl have got a `Scripts` directory, ready for use.\


    <figure><img src="../.gitbook/assets/image.png" alt=""><figcaption><p>Script directory</p></figcaption></figure>

<details>

<summary>UnauthorizedAccess</summary>

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

</details>



[First time setup with IntelliJ](../MGF4Boomi%20-%20Groovy%20for%20Boomi%20bfadc9ce63764373816fa22fccd3cdc1/Getting%20Started%20019408ce4279434d934d162b6ed03d4e/Setup%20a%20customer%20project%20a5e8a967b06b4f9d9123b55f72e07145/First%20time%20setup%20with%20IntelliJ%208996f46e6cbe4fe9aac05d0d0a53dac2.md)
