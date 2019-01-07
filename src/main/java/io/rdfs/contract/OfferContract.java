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
import org.web3j.abi.datatypes.DynamicBytes;
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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161061f38038061061f8339810180604052602081101561003357600080fd5b81019080805164010000000081111561004b57600080fd5b8201602081018481111561005e57600080fd5b815164010000000081118282018710171561007857600080fd5b505060008054600160a060020a0319163317905580519093506100a492506001915060208401906100ab565b5050610146565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100ec57805160ff1916838001178555610119565b82800160010185558215610119579182015b828111156101195782518255916020019190600101906100fe565b50610125929150610129565b5090565b61014391905b80821115610125576000815560010161012f565b90565b6104ca806101556000396000f3fe608060405234801561001057600080fd5b5060043610610052577c010000000000000000000000000000000000000000000000000000000060003504632852b71c811461005757806341c0e1b514610061575b600080fd5b61005f610069565b005b61005f6101a7565b6000805473ffffffffffffffffffffffffffffffffffffffff163361008c6101cc565b73ffffffffffffffffffffffffffffffffffffffff928316815291166020820152604080519182900301906000f0801580156100cc573d6000803e3d6000fd5b50604080513380825273ffffffffffffffffffffffffffffffffffffffff841660208301526060928201838152600180546002600019610100838516150201909116049484018590529495507f8ee53500d193476d1bc5530f760daa80599d9507fcef12f5740357509c152928949193869391906080830190849080156101945780601f1061016957610100808354040283529160200191610194565b820191906000526020600020905b81548152906001019060200180831161017757829003601f168201915b505094505050505060405180910390a133ff5b60005473ffffffffffffffffffffffffffffffffffffffff163314156101ca5733ff5b565b6040516102c2806101dd8339019056fe608060405234801561001057600080fd5b506040516040806102c28339810180604052604081101561003057600080fd5b50805160209091015160018054600160a060020a03938416600160a060020a0319918216179091556000805493909216921691909117905561024b806100776000396000f3fe608060405234801561001057600080fd5b506004361061005d577c01000000000000000000000000000000000000000000000000000000006000350463338cdca181146100625780636a0cc2051461006c578063ea8a1af014610112575b600080fd5b61006a61011a565b005b61006a6004803603602081101561008257600080fd5b81019060208101813564010000000081111561009d57600080fd5b8201836020820111156100af57600080fd5b803590602001918460018302840111640100000000831117156100d157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610168945050505050565b61006a610204565b6001546040805173ffffffffffffffffffffffffffffffffffffffff9092168252517f3e5cd52c5531cb354cd5c5449dae533761ee232111b076f55c012dfbfa2a35619181900360200190a1565b7f57f4f88d1588277c615f44dd27fa72d37eb94d2ac7a329ca214069b6e3fbfc67816040518080602001828103825283818151815260200191508051906020019080838360005b838110156101c75781810151838201526020016101af565b50505050905090810190601f1680156101f45780820380516001836020036101000a031916815260200191505b509250505060405180910390a150565b60005473ffffffffffffffffffffffffffffffffffffffff16fffea165627a7a72305820dc12ca6cacae589d083e4f7a01fa9d1d4488fdc9c50f8b85b705534b87927d330029a165627a7a7230582057610f10c80d116a772ffd9039b702a61fbd706c83dcb064495befab3ac124980029";

    public static final String FUNC_ACCEPT = "accept";

    public static final String FUNC_KILL = "kill";

    public static final Event OFFERACCEPTED_EVENT = new Event("OfferAccepted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
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

    public List<OfferAcceptedEventResponse> getOfferAcceptedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OFFERACCEPTED_EVENT, transactionReceipt);
        ArrayList<OfferAcceptedEventResponse> responses = new ArrayList<OfferAcceptedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OfferAcceptedEventResponse typedResponse = new OfferAcceptedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.file = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.fileChunk = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OfferAcceptedEventResponse> offerAcceptedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OfferAcceptedEventResponse>() {
            @Override
            public OfferAcceptedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OFFERACCEPTED_EVENT, log);
                OfferAcceptedEventResponse typedResponse = new OfferAcceptedEventResponse();
                typedResponse.log = log;
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.file = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.fileChunk = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OfferAcceptedEventResponse> offerAcceptedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OFFERACCEPTED_EVENT));
        return offerAcceptedEventFlowable(filter);
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

    public static class OfferAcceptedEventResponse {
        public Log log;

        public String newOwner;

        public String file;

        public byte[] fileChunk;
    }
}
