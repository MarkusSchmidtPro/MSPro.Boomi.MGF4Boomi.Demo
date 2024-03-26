# https://www.patridgedev.com/2018/09/17/cleaning-up-unused-images-in-your-markdown-content/
Get-ChildItem .\.gitbook\assets | Where { (Get-ChildItem .\*.md | Select-String $_.Name).Count -eq 0 } | ForEach { $_.FullName } | Remove-Item
