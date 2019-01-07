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
public class FileChunkContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516040806102c28339810180604052604081101561003057600080fd5b50805160209091015160018054600160a060020a03938416600160a060020a0319918216179091556000805493909216921691909117905561024b806100776000396000f3fe608060405234801561001057600080fd5b506004361061005d577c01000000000000000000000000000000000000000000000000000000006000350463338cdca181146100625780636a0cc2051461006c578063ea8a1af014610112575b600080fd5b61006a61011a565b005b61006a6004803603602081101561008257600080fd5b81019060208101813564010000000081111561009d57600080fd5b8201836020820111156100af57600080fd5b803590602001918460018302840111640100000000831117156100d157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610168945050505050565b61006a610204565b6001546040805173ffffffffffffffffffffffffffffffffffffffff9092168252517f3e5cd52c5531cb354cd5c5449dae533761ee232111b076f55c012dfbfa2a35619181900360200190a1565b7f57f4f88d1588277c615f44dd27fa72d37eb94d2ac7a329ca214069b6e3fbfc67816040518080602001828103825283818151815260200191508051906020019080838360005b838110156101c75781810151838201526020016101af565b50505050905090810190601f1680156101f45780820380516001836020036101000a031916815260200191505b509250505060405180910390a150565b60005473ffffffffffffffffffffffffffffffffffffffff16fffea165627a7a72305820dc12ca6cacae589d083e4f7a01fa9d1d4488fdc9c50f8b85b705534b87927d330029";

    public static final String FUNC_REQUEST = "request";

    public static final String FUNC_SHARE = "share";

    public static final String FUNC_CANCEL = "cancel";

    public static final Event DOWNLOADREQUEST_EVENT = new Event("DownloadRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event DOWNLOADRESPONSE_EVENT = new Event("DownloadResponse", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event CANCELSHARING_EVENT = new Event("CancelSharing", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected FileChunkContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FileChunkContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FileChunkContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FileChunkContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> request() {
        final Function function = new Function(
                FUNC_REQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> share(byte[] _fileChunk) {
        final Function function = new Function(
                FUNC_SHARE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_fileChunk)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> cancel() {
        final Function function = new Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<DownloadRequestEventResponse> getDownloadRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DOWNLOADREQUEST_EVENT, transactionReceipt);
        ArrayList<DownloadRequestEventResponse> responses = new ArrayList<DownloadRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DownloadRequestEventResponse typedResponse = new DownloadRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DownloadRequestEventResponse> downloadRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DownloadRequestEventResponse>() {
            @Override
            public DownloadRequestEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DOWNLOADREQUEST_EVENT, log);
                DownloadRequestEventResponse typedResponse = new DownloadRequestEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DownloadRequestEventResponse> downloadRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DOWNLOADREQUEST_EVENT));
        return downloadRequestEventFlowable(filter);
    }

    public List<DownloadResponseEventResponse> getDownloadResponseEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DOWNLOADRESPONSE_EVENT, transactionReceipt);
        ArrayList<DownloadResponseEventResponse> responses = new ArrayList<DownloadResponseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DownloadResponseEventResponse typedResponse = new DownloadResponseEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.fileChunk = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DownloadResponseEventResponse> downloadResponseEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DownloadResponseEventResponse>() {
            @Override
            public DownloadResponseEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DOWNLOADRESPONSE_EVENT, log);
                DownloadResponseEventResponse typedResponse = new DownloadResponseEventResponse();
                typedResponse.log = log;
                typedResponse.fileChunk = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DownloadResponseEventResponse> downloadResponseEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DOWNLOADRESPONSE_EVENT));
        return downloadResponseEventFlowable(filter);
    }

    public List<CancelSharingEventResponse> getCancelSharingEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CANCELSHARING_EVENT, transactionReceipt);
        ArrayList<CancelSharingEventResponse> responses = new ArrayList<CancelSharingEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelSharingEventResponse typedResponse = new CancelSharingEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CancelSharingEventResponse> cancelSharingEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CancelSharingEventResponse>() {
            @Override
            public CancelSharingEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCELSHARING_EVENT, log);
                CancelSharingEventResponse typedResponse = new CancelSharingEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<CancelSharingEventResponse> cancelSharingEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCELSHARING_EVENT));
        return cancelSharingEventFlowable(filter);
    }

    @Deprecated
    public static FileChunkContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileChunkContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FileChunkContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileChunkContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FileChunkContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FileChunkContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FileChunkContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FileChunkContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FileChunkContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, String _provider) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_provider)));
        return deployRemoteCall(FileChunkContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<FileChunkContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, String _provider) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_provider)));
        return deployRemoteCall(FileChunkContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<FileChunkContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _provider) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_provider)));
        return deployRemoteCall(FileChunkContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<FileChunkContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _provider) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_owner), 
                new org.web3j.abi.datatypes.Address(_provider)));
        return deployRemoteCall(FileChunkContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DownloadRequestEventResponse {
        public Log log;

        public String owner;
    }

    public static class DownloadResponseEventResponse {
        public Log log;

        public byte[] fileChunk;
    }

    public static class CancelSharingEventResponse {
        public Log log;
    }
}
