# ====================================================================================================
# Usage: .\mgf4boomi.ps1 create <ProjectName>
#
# This script does these things
#   0. Create .\Scripts folder
#   1. Download and unzip a Reference Project (project template) from GitHub.
#   2. Update the local MGF4Boomi sources with the latest source from GitHub
#   3. Download and store the groovy-2.4.13 libraries from the internet.
#   4. Create a <ProjectName>.code.workspace, in case you want to use Git with VS-Code
# ====================================================================================================

function Get-SampleProject {

    Param( [string] $projectName)

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

function Get-Groovy {
    
    $zipFileName = "apache-groovy-binary-2.4.13"
    $url = "https://archive.apache.org/dist/groovy/2.4.13/distribution/$zipFileName.zip"

    Push-Location

    if( !(Test-Path .\lib) ) { New-Item -ItemType Directory -Path .\lib }
    Set-Location -Path .\lib
    
    Write-Host "Downloading Groovy from $url"

    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest $url -OutFile .\$zipFileName.zip
    $ProgressPreference = 'Continue'

    Expand-Archive .\$zipFileName.zip .\
    Remove-Item .\$zipFileName.zip
    Pop-Location
}


# ******** MAIN ******************

if ( $args.Count -ne 2 ) { throw "Usage: .\mgf4boomi.ps1 create projectname" }

switch( $args[0])
{
    "create" { 
        Get-SampleProject $args[1]

        Update-Framework
        # Framework is now sub-project of Reference project 
        if( !(Test-Path .\lib\groovy-2.4.13 )) { Get-Groovy }
    }

    default { 
        throw "Unknown parameter: $args[0]. Allowed: create"
    }
}

