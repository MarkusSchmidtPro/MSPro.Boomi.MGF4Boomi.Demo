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

| Directory    | Purpose                                                                          |
| ------------ | -------------------------------------------------------------------------------- |
| .idea        | IntelliJ Projectfiles                                                            |
| bin          | Tools                                                                            |
| lib          | MGF4Boomi Framework                                                              |
| src          | Groovy Source Files                                                              |
| src\MyScript | Your Groovy Map- and Process-Scripts that you want to use with Boomi AtomSphere. |
| src\Test     | The Groovy Test files used to run and test your custome scripts.                 |

<div align="left">

<figure><img src=".gitbook/assets/Untitled (2) (2).png" alt="" width="186"><figcaption></figcaption></figure>

</div>
