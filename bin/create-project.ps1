# ====================================================================================================
# Usage: .\create-project <projectname>
#
# This script does ..
#   1. Download and unzip a Reference Project (project template) from GitHub.
#   2. Update the local MGF4Boomi sources with the latest source from GitHub
#   3. Create a <ProjectName>.code.workspace, in case you want to use Git with VS-Code
# ====================================================================================================

function Get-SampleProject {

    $projectName = Split-Path -Path $pwd -Leaf

    Push-Location
    # Create project folder
    # if( !(Test-Path .\$projectName) ) { New-Item -ItemType Directory -Path .\$projectName }
    # Set-Location -Path .\$projectName
    

    #
    # GitHub Repository, from where to donwload the Sample Project
    #
    $GitHubProject = "Boomi.Groovy.ReferenceProject"
    $Branch = "main"
    $url = "https://github.com/MarkusSchmidtPro/$GitHubProject/archive/refs/heads/$Branch.zip"

    $scriptFolder = "Scripts"
    Write-Output "Target script folder: $scriptFolder"
    if( Test-Path .\$scriptFolder ) { throw "Target folder $scriptFolder exists!"}

    #if( Test-Path ".\$GitHubProject-$Branch" ) { throw "Folder exists: $GitHubProject-$Branch"}

    Write-Host "Downloading from GitHub $GitHubProject"
    Invoke-WebRequest $url -OutFile .\$projectName.zip

    Write-Host      "Unzipping $projectName.zip to .\$GitHubProject-$Branch"
    Expand-Archive  .\$projectName.zip .\
    Remove-Item     .\$projectName.zip

    Write-Host      "Renaming target folder to $scriptFolder"
    Rename-Item     -Path ".\$GitHubProject-$Branch" -NewName ".\$scriptFolder"

    Rename-Item     -Path ".\$scriptFolder\$GitHubProject.code-workspace" -NewName "$projectName.code-workspace"

    Set-Location $scriptFolder
}

function Update-Framework {

    $GitHubProject = "MGF4Boomi"
    $Branch = "main"
    $url = "https://github.com/MarkusSchmidtPro/$GitHubProject/archive/refs/heads/$Branch.zip"

    $projectName = $GitHubProject

    Push-Location
    Set-Location -Path .\lib
    Write-Host "Downloading MGF4Boomi from $url"
    Invoke-WebRequest $url -OutFile .\$projectName.zip
    Remove-Item .\$projectName              # Create because it is s sub-project of Reference project
    Expand-Archive .\$projectName.zip .\    # to MGF4Boomi-main
    Rename-Item .\$GitHubProject-$Branch .\$projectName
    Remove-Item .\$projectName.zip
    Pop-Location
}


# ******** MAIN ******************

Get-SampleProject 
Update-Framework

# ******** MAIN ******************
