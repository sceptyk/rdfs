pragma solidity >=0.4.22 <0.6.0;

contract KillableContract {
    address owner;

    constructor() public {
        owner = msg.sender;
    }

    function kill() public {
        if (msg.sender == owner)
            selfdestruct(msg.sender);

    }
}

contract OfferContract is KillableContract {
    event OfferAccepted(
        address newOwner,
        address file,
        bytes fileChunk
    );

    bytes fileChunk;

    constructor(bytes memory _fileChunk) public {
        fileChunk = _fileChunk;
    }

    function accept() public {
        address fileContract = address(new FileChunkContract(owner, msg.sender));
        emit OfferAccepted(msg.sender, fileContract, fileChunk);

        selfdestruct(msg.sender);
    }
}

contract FileChunkContract {
    event DownloadRequest(address owner);
    event DownloadResponse(bytes fileChunk);
    event CancelSharing();

    address payable provider;
    address owner;

    constructor(address _owner, address payable _provider) public{
        owner = _owner;
        provider = _provider;
    }

    function request() public {
        emit DownloadRequest(owner);
    }

    function share(bytes memory _fileChunk) public {
        emit DownloadResponse(_fileChunk);
    }

    function cancel() public {
        emit CancelSharing();
        selfdestruct(provider);
    }
}