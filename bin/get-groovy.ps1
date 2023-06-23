# ====================================================================================================
#   Download the groovy-2.4.13 SDK from the internet
#   install to $userhome\.groovy\sdk\v$groovyVersion
# ====================================================================================================


function Get-Groovy {
    Param( [string] $sdkPath, [string] $groovyVersion)

    if( Test-Path $groovySdkPath\groovy-$groovyVersion) { 
        throw "Groovy SDK $groovyVersion already installed! $groovySdkPath"
    }

    $zipFileName = "apache-groovy-binary-$groovyVersion"
    $url = "https://archive.apache.org/dist/groovy/$groovyVersion/distribution/$zipFileName.zip"

    Push-Location
    Set-Location -Path $sdkPath
    
    Write-Host "Downloading Groovy from $url"

    $ProgressPreference = 'SilentlyContinue'
    Invoke-WebRequest $url -OutFile .\$zipFileName.zip
    $ProgressPreference = 'Continue'

    Expand-Archive .\$zipFileName.zip .\
    Remove-Item .\$zipFileName.zip
    Pop-Location
}


# ******** MAIN ******************

$groovyVersion = "2.4.13"
$userhome = $env:homedrive + $env:homepath
$groovySdkPath =  "$userhome\.groovy\sdk"

New-Item -ItemType Directory -Path $groovySdkPath 
Write-Host "Installation Groovy SDK v$groovyVersion into: $groovySdkPath"
Get-Groovy $groovySdkPath $groovyVersion 


# ******** MAIN ******************
