package com.voting_system_on_blockchain.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
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
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class VotingContract extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b5061182d8061001c5f395ff3fe608060405234801561000f575f5ffd5b5060043610610086575f3560e01c8063e4c41bb411610059578063e4c41bb414610136578063e582bebd14610154578063ebec4a1f14610184578063f9ded646146101a057610086565b8063011f0b921461008a5780638a6655d6146100ba57806396854134146100d6578063bd5fc97414610106575b5f5ffd5b6100a4600480360381019061009f9190610aa0565b6101d0565b6040516100b19190610baa565b60405180910390f35b6100d460048036038101906100cf9190610bca565b6102a8565b005b6100f060048036038101906100eb9190610c1a565b610479565b6040516100fd9190610c72565b60405180910390f35b610120600480360381019061011b9190610c1a565b6104af565b60405161012d9190610cd8565b60405180910390f35b61013e61058d565b60405161014b9190610e13565b60405180910390f35b61016e60048036038101906101699190610aa0565b610703565b60405161017b9190610edb565b60405180910390f35b61019e60048036038101906101999190611109565b61076a565b005b6101ba60048036038101906101b59190610aa0565b610896565b6040516101c79190611287565b60405180910390f35b6101d8610a18565b60015f8381526020019081526020015f206040518060800160405290815f820154815260200160018201805461020d906112d4565b80601f0160208091040260200160405190810160405280929190818152602001828054610239906112d4565b80156102845780601f1061025b57610100808354040283529160200191610284565b820191905f5260205f20905b81548152906001019060200180831161026757829003601f168201915b50505050508152602001600282015481526020016003820154815250509050919050565b60035f8481526020019081526020015f205f8281526020019081526020015f205f9054906101000a900460ff1615610315576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161030c90611384565b60405180910390fd5b60015f8481526020019081526020015f2060020154821061036b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610362906113ec565b60405180910390fd5b60025f8481526020019081526020015f205f8381526020019081526020015f206002015f81548092919061039e90611437565b9190505550600160035f8581526020019081526020015f205f8381526020019081526020015f205f6101000a81548160ff02191690831515021790555060015f8481526020019081526020015f206003015f8154809291906103ff90611437565b919050555060045f8481526020019081526020015f2081908060018154018082558091505060019003905f5260205f20015f90919091909150557f5fe8f1cc066896edbe87bedf2e870c54f6311a7d6668c860949e2e1224a0955e83838360405161046c9392919061148d565b60405180910390a1505050565b5f60035f8481526020019081526020015f205f8381526020019081526020015f205f9054906101000a900460ff16905092915050565b6104b7610a3d565b60025f8481526020019081526020015f205f8381526020019081526020015f206040518060600160405290815f82015481526020016001820180546104fb906112d4565b80601f0160208091040260200160405190810160405280929190818152602001828054610527906112d4565b80156105725780601f1061054957610100808354040283529160200191610572565b820191905f5260205f20905b81548152906001019060200180831161055557829003601f168201915b50505050508152602001600282015481525050905092915050565b60605f5f5467ffffffffffffffff8111156105ab576105aa610f03565b5b6040519080825280602002602001820160405280156105e457816020015b6105d1610a18565b8152602001906001900390816105c95790505b5090505f600190505b5f5481116106fb5760015f8281526020019081526020015f206040518060800160405290815f820154815260200160018201805461062a906112d4565b80601f0160208091040260200160405190810160405280929190818152602001828054610656906112d4565b80156106a15780601f10610678576101008083540402835291602001916106a1565b820191905f5260205f20905b81548152906001019060200180831161068457829003601f168201915b5050505050815260200160028201548152602001600382015481525050826001836106cc91906114c2565b815181106106dd576106dc6114f5565b5b602002602001018190525080806106f390611437565b9150506105ed565b508091505090565b606060045f8381526020019081526020015f2080548060200260200160405190810160405280929190818152602001828054801561075e57602002820191905f5260205f20905b81548152602001906001019080831161074a575b50505050509050919050565b5f5f81548092919061077b90611437565b91905055505f60015f5f5481526020019081526020015f2090505f54815f0181905550828160010190816107af91906116c2565b50815181600201819055505f5f90505b82518110156108565760405180606001604052808281526020018483815181106107ec576107eb6114f5565b5b602002602001015181526020015f81525060025f5f5481526020019081526020015f205f8381526020019081526020015f205f820151815f0155602082015181600101908161083b91906116c2565b506040820151816002015590505080806001019150506107bf565b507fe9722cdfea16904f7c251713498b56fbb15389ba08f367afd2503b89c260e1b85f54846040516108899291906117c9565b60405180910390a1505050565b60605f60015f8481526020019081526020015f206002015490505f8167ffffffffffffffff8111156108cb576108ca610f03565b5b60405190808252806020026020018201604052801561090457816020015b6108f1610a3d565b8152602001906001900390816108e95790505b5090505f5f90505b82811015610a0d5760025f8681526020019081526020015f205f8281526020019081526020015f206040518060600160405290815f8201548152602001600182018054610958906112d4565b80601f0160208091040260200160405190810160405280929190818152602001828054610984906112d4565b80156109cf5780601f106109a6576101008083540402835291602001916109cf565b820191905f5260205f20905b8154815290600101906020018083116109b257829003601f168201915b505050505081526020016002820154815250508282815181106109f5576109f46114f5565b5b6020026020010181905250808060010191505061090c565b508092505050919050565b60405180608001604052805f8152602001606081526020015f81526020015f81525090565b60405180606001604052805f8152602001606081526020015f81525090565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b610a7f81610a6d565b8114610a89575f5ffd5b50565b5f81359050610a9a81610a76565b92915050565b5f60208284031215610ab557610ab4610a65565b5b5f610ac284828501610a8c565b91505092915050565b610ad481610a6d565b82525050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f610b1c82610ada565b610b268185610ae4565b9350610b36818560208601610af4565b610b3f81610b02565b840191505092915050565b5f608083015f830151610b5f5f860182610acb565b5060208301518482036020860152610b778282610b12565b9150506040830151610b8c6040860182610acb565b506060830151610b9f6060860182610acb565b508091505092915050565b5f6020820190508181035f830152610bc28184610b4a565b905092915050565b5f5f5f60608486031215610be157610be0610a65565b5b5f610bee86828701610a8c565b9350506020610bff86828701610a8c565b9250506040610c1086828701610a8c565b9150509250925092565b5f5f60408385031215610c3057610c2f610a65565b5b5f610c3d85828601610a8c565b9250506020610c4e85828601610a8c565b9150509250929050565b5f8115159050919050565b610c6c81610c58565b82525050565b5f602082019050610c855f830184610c63565b92915050565b5f606083015f830151610ca05f860182610acb565b5060208301518482036020860152610cb88282610b12565b9150506040830151610ccd6040860182610acb565b508091505092915050565b5f6020820190508181035f830152610cf08184610c8b565b905092915050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f608083015f830151610d365f860182610acb565b5060208301518482036020860152610d4e8282610b12565b9150506040830151610d636040860182610acb565b506060830151610d766060860182610acb565b508091505092915050565b5f610d8c8383610d21565b905092915050565b5f602082019050919050565b5f610daa82610cf8565b610db48185610d02565b935083602082028501610dc685610d12565b805f5b85811015610e015784840389528151610de28582610d81565b9450610ded83610d94565b925060208a01995050600181019050610dc9565b50829750879550505050505092915050565b5f6020820190508181035f830152610e2b8184610da0565b905092915050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f610e678383610acb565b60208301905092915050565b5f602082019050919050565b5f610e8982610e33565b610e938185610e3d565b9350610e9e83610e4d565b805f5b83811015610ece578151610eb58882610e5c565b9750610ec083610e73565b925050600181019050610ea1565b5085935050505092915050565b5f6020820190508181035f830152610ef38184610e7f565b905092915050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b610f3982610b02565b810181811067ffffffffffffffff82111715610f5857610f57610f03565b5b80604052505050565b5f610f6a610a5c565b9050610f768282610f30565b919050565b5f67ffffffffffffffff821115610f9557610f94610f03565b5b610f9e82610b02565b9050602081019050919050565b828183375f83830152505050565b5f610fcb610fc684610f7b565b610f61565b905082815260208101848484011115610fe757610fe6610eff565b5b610ff2848285610fab565b509392505050565b5f82601f83011261100e5761100d610efb565b5b813561101e848260208601610fb9565b91505092915050565b5f67ffffffffffffffff82111561104157611040610f03565b5b602082029050602081019050919050565b5f5ffd5b5f61106861106384611027565b610f61565b9050808382526020820190506020840283018581111561108b5761108a611052565b5b835b818110156110d257803567ffffffffffffffff8111156110b0576110af610efb565b5b8086016110bd8982610ffa565b8552602085019450505060208101905061108d565b5050509392505050565b5f82601f8301126110f0576110ef610efb565b5b8135611100848260208601611056565b91505092915050565b5f5f6040838503121561111f5761111e610a65565b5b5f83013567ffffffffffffffff81111561113c5761113b610a69565b5b61114885828601610ffa565b925050602083013567ffffffffffffffff81111561116957611168610a69565b5b611175858286016110dc565b9150509250929050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f606083015f8301516111bd5f860182610acb565b50602083015184820360208601526111d58282610b12565b91505060408301516111ea6040860182610acb565b508091505092915050565b5f61120083836111a8565b905092915050565b5f602082019050919050565b5f61121e8261117f565b6112288185611189565b93508360208202850161123a85611199565b805f5b85811015611275578484038952815161125685826111f5565b945061126183611208565b925060208a0199505060018101905061123d565b50829750879550505050505092915050565b5f6020820190508181035f83015261129f8184611214565b905092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f60028204905060018216806112eb57607f821691505b6020821081036112fe576112fd6112a7565b5b50919050565b5f82825260208201905092915050565b7f596f75206861766520616c726561647920766f74656420696e207468697320765f8201527f6f74696e672e0000000000000000000000000000000000000000000000000000602082015250565b5f61136e602683611304565b915061137982611314565b604082019050919050565b5f6020820190508181035f83015261139b81611362565b9050919050565b7f496e76616c69642063616e6469646174652e00000000000000000000000000005f82015250565b5f6113d6601283611304565b91506113e1826113a2565b602082019050919050565b5f6020820190508181035f830152611403816113ca565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61144182610a6d565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036114735761147261140a565b5b600182019050919050565b61148781610a6d565b82525050565b5f6060820190506114a05f83018661147e565b6114ad602083018561147e565b6114ba604083018461147e565b949350505050565b5f6114cc82610a6d565b91506114d783610a6d565b92508282039050818111156114ef576114ee61140a565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f6008830261157e7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611543565b6115888683611543565b95508019841693508086168417925050509392505050565b5f819050919050565b5f6115c36115be6115b984610a6d565b6115a0565b610a6d565b9050919050565b5f819050919050565b6115dc836115a9565b6115f06115e8826115ca565b84845461154f565b825550505050565b5f5f905090565b6116076115f8565b6116128184846115d3565b505050565b5b818110156116355761162a5f826115ff565b600181019050611618565b5050565b601f82111561167a5761164b81611522565b61165484611534565b81016020851015611663578190505b61167761166f85611534565b830182611617565b50505b505050565b5f82821c905092915050565b5f61169a5f198460080261167f565b1980831691505092915050565b5f6116b2838361168b565b9150826002028217905092915050565b6116cb82610ada565b67ffffffffffffffff8111156116e4576116e3610f03565b5b6116ee82546112d4565b6116f9828285611639565b5f60209050601f83116001811461172a575f8415611718578287015190505b61172285826116a7565b865550611789565b601f19841661173886611522565b5f5b8281101561175f5784890151825560018201915060208501945060208101905061173a565b8683101561177c5784890151611778601f89168261168b565b8355505b6001600288020188555050505b505050505050565b5f61179b82610ada565b6117a58185611304565b93506117b5818560208601610af4565b6117be81610b02565b840191505092915050565b5f6040820190506117dc5f83018561147e565b81810360208301526117ee8184611791565b9050939250505056fea264697066735822122038683ad03ffb10e151a42ba2bcd5e89d05f354b59fa53a986b0f57028db86d7364736f6c634300081c0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CREATEVOTING = "createVoting";

    public static final String FUNC_GETALLCANDIDATES = "getAllCandidates";

    public static final String FUNC_GETALLVOTINGS = "getAllVotings";

    public static final String FUNC_GETCANDIDATEBYID = "getCandidateById";

    public static final String FUNC_GETVOTERSBYVOTINGID = "getVotersByVotingId";

    public static final String FUNC_GETVOTINGBYID = "getVotingById";

    public static final String FUNC_HASUSERVOTED = "hasUserVoted";

    public static final String FUNC_VOTE = "vote";

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event VOTINGCREATED_EVENT = new Event("VotingCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected VotingContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VotingContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VotingContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VotingContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.votingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.userId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotedEventResponse getVotedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTED_EVENT, log);
        VotedEventResponse typedResponse = new VotedEventResponse();
        typedResponse.log = log;
        typedResponse.votingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.candidateId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.userId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<VotedEventResponse> votedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotedEventFromLog(log));
    }

    public Flowable<VotedEventResponse> votedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventFlowable(filter);
    }

    public static List<VotingCreatedEventResponse> getVotingCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VOTINGCREATED_EVENT, transactionReceipt);
        ArrayList<VotingCreatedEventResponse> responses = new ArrayList<VotingCreatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VotingCreatedEventResponse typedResponse = new VotingCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.votingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static VotingCreatedEventResponse getVotingCreatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VOTINGCREATED_EVENT, log);
        VotingCreatedEventResponse typedResponse = new VotingCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.votingId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.name = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<VotingCreatedEventResponse> votingCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getVotingCreatedEventFromLog(log));
    }

    public Flowable<VotingCreatedEventResponse> votingCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTINGCREATED_EVENT));
        return votingCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> createVoting(String name,
            List<String> candidateNames) {
        final Function function = new Function(
                FUNC_CREATEVOTING, 
                Arrays.<Type>asList(new Utf8String(name),
                        new DynamicArray<>(
                                Utf8String.class,
                                org.web3j.abi.Utils.typeMap(candidateNames, Utf8String.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllCandidates(BigInteger votingId) {
        final Function function = new Function(FUNC_GETALLCANDIDATES, 
                Arrays.<Type>asList(new Uint256(votingId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Candidate>>() {}));
        return new RemoteFunctionCall<List>(function,
                () -> {
                    List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                    return convertToNative(result);
                });
    }

    public RemoteFunctionCall<List> getAllVotings() {
        final Function function = new Function(FUNC_GETALLVOTINGS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Voting>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Candidate> getCandidateById(BigInteger votingId,
            BigInteger candidateId) {
        final Function function = new Function(FUNC_GETCANDIDATEBYID, 
                Arrays.<Type>asList(new Uint256(votingId),
                new Uint256(candidateId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Candidate>() {}));
        return executeRemoteCallSingleValueReturn(function, Candidate.class);
    }

    public RemoteFunctionCall<List> getVotersByVotingId(BigInteger votingId) {
        final Function function = new Function(FUNC_GETVOTERSBYVOTINGID, 
                Arrays.<Type>asList(new Uint256(votingId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Voting> getVotingById(BigInteger votingId) {
        final Function function = new Function(FUNC_GETVOTINGBYID, 
                Arrays.<Type>asList(new Uint256(votingId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Voting>() {}));
        return executeRemoteCallSingleValueReturn(function, Voting.class);
    }

    public RemoteFunctionCall<Boolean> hasUserVoted(BigInteger votingId, BigInteger userId) {
        final Function function = new Function(FUNC_HASUSERVOTED, 
                Arrays.<Type>asList(new Uint256(votingId),
                new Uint256(userId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger votingId, BigInteger candidateId,
            BigInteger userId) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new Uint256(votingId),
                new Uint256(candidateId),
                new Uint256(userId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static VotingContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VotingContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VotingContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VotingContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new VotingContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VotingContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VotingContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VotingContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VotingContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<VotingContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VotingContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VotingContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VotingContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Candidate extends DynamicStruct {
        public BigInteger id;

        public String name;

        public BigInteger voteCount;

        public Candidate(BigInteger id, String name, BigInteger voteCount) {
            super(new Uint256(id),
                    new Utf8String(name),
                    new Uint256(voteCount));
            this.id = id;
            this.name = name;
            this.voteCount = voteCount;
        }

        public Candidate(Uint256 id, Utf8String name, Uint256 voteCount) {
            super(id, name, voteCount);
            this.id = id.getValue();
            this.name = name.getValue();
            this.voteCount = voteCount.getValue();
        }
    }

    public static class Voting extends DynamicStruct {
        public BigInteger id;

        public String name;

        public BigInteger candidatesCount;

        public BigInteger votersCount;

        public Voting(BigInteger id, String name, BigInteger candidatesCount,
                BigInteger votersCount) {
            super(new Uint256(id),
                    new Utf8String(name),
                    new Uint256(candidatesCount),
                    new Uint256(votersCount));
            this.id = id;
            this.name = name;
            this.candidatesCount = candidatesCount;
            this.votersCount = votersCount;
        }

        public Voting(Uint256 id, Utf8String name, Uint256 candidatesCount, Uint256 votersCount) {
            super(id, name, candidatesCount, votersCount);
            this.id = id.getValue();
            this.name = name.getValue();
            this.candidatesCount = candidatesCount.getValue();
            this.votersCount = votersCount.getValue();
        }
    }

    public static class VotedEventResponse extends BaseEventResponse {
        public BigInteger votingId;

        public BigInteger candidateId;

        public BigInteger userId;
    }

    public static class VotingCreatedEventResponse extends BaseEventResponse {
        public BigInteger votingId;

        public String name;
    }
}
