Split-Path $MyInvocation.MyCommand.Path | Push-Location

New-Item -ItemType directory -Path temp -Force > $null
Remove-Item -Path ..\src\main\java\io\rdfs\contract\* -Force > $null
Remove-Item -Path temp\* -Force -Recurse > $null

Write-Host "Environment prepared successfully"

Get-ChildItem ..\src\main\resources\solidity -Filter *.sol |
        ForEach-Object {
            Invoke-Expression -Command "solc $($_.FullName) --bin --abi --optimize -o temp"
        }

Write-Host "Solidity files compiled successfully"

Get-ChildItem temp -Filter *.bin |
        Foreach-Object {
            Invoke-Expression -Command "web3j solidity generate -b temp\$($_.BaseName).bin -a temp\$($_.BaseName).abi -o ..\src\main\java -p io.rdfs.contract"
        }

Write-Host "Wrappers created successfully"

Remove-Item -Path temp -Force -Recurse > $null
Pop-Location

Write-Host "Cleanup performed successfully"