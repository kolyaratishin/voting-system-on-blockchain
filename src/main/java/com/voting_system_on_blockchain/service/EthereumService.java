package com.voting_system_on_blockchain.service;

import com.voting_system_on_blockchain.contract.VotingContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EthereumService {
    private final VotingContract votingContract;

    public EthereumService(Web3j web3j,
                           @Value("${voting.contract.address}") String contractAddress,
                           @Value("${ethereum.private.key}") String privateKey) {
        ContractGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975));
        Credentials credentials = Credentials.create(privateKey);
        this.votingContract = VotingContract.load(contractAddress, web3j, credentials, gasProvider);
    }

    public void createVoting(String name, List<String> candidateNames) {
        try {
            votingContract.createVoting(name, candidateNames).send();
        } catch (Exception e) {
            throw new RuntimeException("Error creating voting: " + e.getMessage(), e);
        }
    }

    public List<VotingContract.Voting> getAllVotings() {
        try {
            return votingContract.getAllVotings().send();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all votings: " + e.getMessage(), e);
        }
    }

    public List<VotingContract.Candidate> getCandidatesByVotingId(BigInteger votingId) {
        try {
            return votingContract.getAllCandidates(votingId).send();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching candidates: " + e.getMessage(), e);
        }
    }

    public List<BigInteger> getVotersByVotingId(BigInteger votingId) {
        try {
            return votingContract.getVotersByVotingId(votingId).send();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching candidates: " + e.getMessage(), e);
        }
    }

    public VotingContract.Voting getVotingById(BigInteger votingId) {
        try {
            return votingContract.getVotingById(votingId).send();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching voting by ID: " + e.getMessage(), e);
        }
    }

    public Boolean hasUserVoted(BigInteger votingId, BigInteger userId) {
        try {
            return votingContract.hasUserVoted(votingId, userId).send();
        } catch (Exception e) {
            throw new RuntimeException("Error checking if user has voted: " + e.getMessage(), e);
        }
    }

    public void vote(BigInteger votingId, BigInteger candidateId, BigInteger userId) {
        try {
            votingContract.vote(votingId, candidateId, userId).send();
        } catch (Exception e) {
            throw new RuntimeException("Error voting: " + e.getMessage(), e);
        }
    }
}

