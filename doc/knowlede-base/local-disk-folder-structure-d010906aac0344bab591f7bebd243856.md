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

# Local disk folder structure

## Global folder structure on your machine

<table><thead><tr><th width="218">Component</th><th>Path</th></tr></thead><tbody><tr><td>Groovy SDK</td><td><code>%UserHome%.groovy\sdk\groovy-2.4.13\</code></td></tr><tr><td>Local ATOM (64-bit)</td><td><code>c:\Program Files\Boomi AtomSphere\LocalAtom\</code></td></tr><tr><td>Boomi Projects Root</td><td>Whereever you want, recommended: <code>Boomi.Spaces\</code></td></tr></tbody></table>

## Customer project folder Structure

<table><thead><tr><th width="191">Synonym</th><th width="301">Folder</th><th>Description</th></tr></thead><tbody><tr><td>%<mark style="color:blue;">ProjectRoot</mark>%</td><td><code>Boomi.Spaces</code></td><td>That is the root where you manage all Boomi related files.</td></tr><tr><td>%<mark style="color:green;">ProjectFolder</mark>%</td><td>%<mark style="color:blue;">ProjectRoot</mark>%\CustomerName<br><code>Boomi.Spaces\MSPro</code></td><td>This is where you store all customer (project) related files.</td></tr><tr><td>%ScriptFolder%</td><td>%<mark style="color:green;">ProjectFolder</mark>%\Scripts<br><code>Boomi.Spaces\MSPro\Scripts</code></td><td>The root for your customer related script.</td></tr></tbody></table>

### Script Folder

| Directory    | Purpose                                                                          |
| ------------ | -------------------------------------------------------------------------------- |
| .idea        | IntelliJ Projectfiles                                                            |
| bin          | Tools                                                                            |
| lib          | MGF4Boomi Framework                                                              |
| src          | Groovy Source Files                                                              |
| src\MyScript | Your Groovy Map- and Process-Scripts that you want to use with Boomi AtomSphere. |
| src\Test     | The Groovy Test files used to run and test your custome scripts.                 |
