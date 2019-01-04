$cd = Split-Path $MyInvocation.MyCommand.Path
Push-Location $cd

New-Item -ItemType directory -Path temp -Force > $null
Remove-Item -Path temp\* > $null
Invoke-Expression -Command "solc ..\src\main\resources\solidity\FileShare.sol --bin --abi --optimize -o temp"

Get-ChildItem .\temp -Filter *.bin |
        Foreach-Object {
            Invoke-Expression -Command "web3j solidity generate -b temp\$($_.BaseName).bin -a temp\$($_.BaseName).abi -o ..\src\main\java -p io.rdfs.contract"
        }

Remove-Item -Path temp -Force > $null
Pop-Location

Write-Host "Wrappers created successfully"