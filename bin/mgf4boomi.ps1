
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
    Expand-Archive .\$projectName.zip .\
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

if ( $args.Count -ne 2 ) { throw "Projectname is missing!" }

switch( $args[0])
{
    "init" { 
        Get-SampleProject $args[1]

        Update-Framework
        if( !(Test-Path .\lib\groovy-2.4.13 )) { Get-Groovy }
    }

    default { 
        throw "Unknown parameter: $args[0]"
    }
}

