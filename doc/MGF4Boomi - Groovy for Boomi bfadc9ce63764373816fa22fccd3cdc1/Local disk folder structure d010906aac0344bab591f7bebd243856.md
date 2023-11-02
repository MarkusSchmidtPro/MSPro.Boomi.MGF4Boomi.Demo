# Local disk folder structure

## Global folder structure on your machine

| Component           | Path                                              |
| ------------------- | ------------------------------------------------- |
| Groovy SDK          | %UserHome%.groovy\sdk\groovy-2.4.13\\             |
| Local ATOM (64-bit) | c:\Program Files\Boomi AtomSphere\LocalAtom\\     |
| Boomi Projects Root | Whereever you want, recommended: G:\BoomiProjects |

## Customer project folder Structure

| Synonym         | Folder                     | Description                                                   |
| --------------- | -------------------------- | ------------------------------------------------------------- |
| %ProjectRoot%   |                            | That is the root where you manage all Boomi related files.    |
| %ProjectFolder% | %ProjectRoot%\CustomerName | This is where you store all customer (project) related files. |
| %ScriptFolder%  | %ProjectFolder%\Scripts    | The root for your customer related script.                    |

### Script Folder

![Untitled](<../MGF4Boomi - Groovy for Boomi bfadc9ce63764373816fa22fccd3cdc1/Getting Started 019408ce4279434d934d162b6ed03d4e/Local disk folder structure d010906aac0344bab591f7bebd243856/Untitled.png>)

| Directory    | Purpose                                                                          |
| ------------ | -------------------------------------------------------------------------------- |
| .idea        | IntelliJ Projectfiles                                                            |
| bin          | Tools                                                                            |
| lib          | MGF4Boomi Framework                                                              |
| src          | Groovy Source Files                                                              |
| src\MyScript | Your Groovy Map- and Process-Scripts that you want to use with Boomi AtomSphere. |
| src\Test     | The Groovy Test files used to run and test your custome scripts.                 |
