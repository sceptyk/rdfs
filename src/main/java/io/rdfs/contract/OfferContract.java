package io.rdfs.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class OfferContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516105b03803806105b08339810180604052602081101561003357600080fd5b81019080805164010000000081111561004b57600080fd5b8201602081018481111561005e57600080fd5b815164010000000081118282018710171561007857600080fd5b505060008054600160a060020a0319163317905580519093506100a492506001915060208401906100ab565b5050610146565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ec57805160ff1916838001178555610119565b82800160010185558215610119579182015b828111156101195782518255916020019190600101906100fe565b50610125929150610129565b5090565b61014391905b80821115610125576000815560010161012f565b90565b61045b806101556000396000f3fe608060405234801561001057600080fd5b5060043610610052577c010000000000000000000000000000000000000000000000000000000060003504632852b71c811461005757806341c0e1b51461010a575b600080fd5b61005f610114565b60405180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156100ce5781810151838201526020016100b6565b50505050905090810190601f1680156100fb5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b610112610266565b005b60018054604080516020600284861615610100026000190190941693909304601f8101849004840282018401909252818152606093600093919290918301828280156101a15780601f10610176576101008083540402835291602001916101a1565b820191906000526020600020905b81548152906001019060200180831161018457829003601f168201915b50506000549395505073ffffffffffffffffffffffffffffffffffffffff90921691503390506101cf61028b565b73ffffffffffffffffffffffffffffffffffffffff928316815291166020820152604080519182900301906000f08015801561020f573d6000803e3d6000fd5b506040805133815273ffffffffffffffffffffffffffffffffffffffff8316602082015281519293507fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c929081900390910190a133ff5b60005473ffffffffffffffffffffffffffffffffffffffff163314156102895733ff5b565b6040516101948061029c8339019056fe608060405234801561001057600080fd5b506040516040806101948339810180604052604081101561003057600080fd5b50805160209091015160008054600160a060020a0319908116331790915560028054600160a060020a0394851690831617905560018054939092169216919091179055610112806100826000396000f3fe6080604052348015600f57600080fd5b50600436106058577c01000000000000000000000000000000000000000000000000000000006000350463338cdca18114605d5780633ccfd60b14606557806341c0e1b514606b575b600080fd5b60636071565b005b606360bf565b606360c2565b6002546040805173ffffffffffffffffffffffffffffffffffffffff9092168252517f2749295aa7ffdbd4d16719dc03d592cd081eebd9bb790ceedce201a40675fc039181900360200190a1565b33ff5b60005473ffffffffffffffffffffffffffffffffffffffff1633141560e45733ff5b56fea165627a7a72305820f1e14691d80cf8b312dbecb7fad3570be1d73f2c44b3dfcb3a75778158e084080029a165627a7a7230582044a019ff789117212bb744df69731223a1364f6484cb759fac7a27baaba8b6b70029";

    public static final String FUNC_ACCEPT = "accept";

    public static final String FUNC_KILL = "kill";

    public static final Event OWNERCHANGED_EVENT = new Event("OwnerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected OfferContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OfferContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OfferContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OfferContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> accept() {
        final Function function = new Function(
                FUNC_ACCEPT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OwnerChangedEventResponse> getOwnerChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERCHANGED_EVENT, transactionReceipt);
        ArrayList<OwnerChangedEventResponse> responses = new ArrayList<OwnerChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.file = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerChangedEventResponse> ownerChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnerChangedEventResponse>() {
            @Override
            public OwnerChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERCHANGED_EVENT, log);
                OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
                typedResponse.log = log;
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.file = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerChangedEventResponse> ownerChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERCHANGED_EVENT));
        return ownerChangedEventFlowable(filter);
    }

    @Deprecated
    public static OfferContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OfferContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OfferContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OfferContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OfferContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OfferContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OfferContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OfferContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OfferContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, byte[] _fileChunk) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_fileChunk)));
        return deployRemoteCall(OfferContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<OfferContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, byte[] _fileChunk) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_fileChunk)));
        return deployRemoteCall(OfferContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OfferContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, byte[] _fileChunk) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_fileChunk)));
        return deployRemoteCall(OfferContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OfferContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, byte[] _fileChunk) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_fileChunk)));
        return deployRemoteCall(OfferContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class OwnerChangedEventResponse {
        public Log log;

        public String newOwner;

        public String file;
    }
}
