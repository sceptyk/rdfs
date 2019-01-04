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
    event OwnerChanged(
        address newOwner,
        address file
    );

    bytes fileChunk;

    constructor(bytes memory _fileChunk) public {
        fileChunk = _fileChunk;
    }

    function accept() public returns(bytes memory) {
        address file = address(new FileContract(owner, msg.sender));
        emit OwnerChanged(msg.sender, file);
        selfdestruct(msg.sender);
        return fileChunk;
    }
}

contract FileContract is KillableContract{
    event NewRequest(address owner);

    address provider;
    address owner;

    constructor(address _owner, address _provider) public{
        owner = _owner;
        provider = _provider;
    }

    function request() public{
        emit NewRequest(owner);
    }

    function withdraw() public{
        selfdestruct(msg.sender);
    }
}

contract ResponseContract{
    bytes fileChunk;

    constructor(bytes memory _fileChunk) public{
        fileChunk = _fileChunk;
    }

    function retrieve() public returns(bytes memory){
        selfdestruct(msg.sender);
        return fileChunk;
    }
}