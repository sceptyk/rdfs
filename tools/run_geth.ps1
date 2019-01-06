param(
    [Parameter(Mandatory=$true)][string]$dir
)

Invoke-Expression -Command "geth -rpcapi personal,db,eth,net,web3 --rpc --rinkeby --syncmode 'light' --datadir $($dir)"