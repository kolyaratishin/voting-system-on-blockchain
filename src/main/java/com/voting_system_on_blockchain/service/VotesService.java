package com.voting_system_on_blockchain.service;

import com.voting_system_on_blockchain.contract.VotingContract;
import com.voting_system_on_blockchain.dto.Candidate;
import com.voting_system_on_blockchain.dto.Vote;
import com.voting_system_on_blockchain.dto.VoteWithCandidates;
import com.voting_system_on_blockchain.entity.User;
import com.voting_system_on_blockchain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VotesService {

    private final EthereumService ethereumService;
    private final UserRepository userRepository;

    public void createVoting(String name, List<String> candidateNames) {
        ethereumService.createVoting(name, candidateNames);
    }

    public List<Vote> getAllVotes() {
        try {
            List<VotingContract.Voting> allVotings = ethereumService.getAllVotings();
            return allVotings.stream()
                    .map(VotesService::mapToVotes)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error fetching all votings: " + e.getMessage(), e);
        }
    }

    @NotNull
    private static Vote mapToVotes(VotingContract.Voting voting) {
        Vote vote = new Vote();
        vote.setId(voting.id.longValue());
        vote.setName(voting.name);
        vote.setCandidatesCount(voting.candidatesCount);
        vote.setVotersCount(voting.votersCount);
        return vote;
    }

    @NotNull
    private static List<Candidate> mapToCandidate(List<VotingContract.Candidate> candidates) {
        return candidates.stream()
                .map(candidate -> Candidate.builder()
                        .id(candidate.id.longValue())
                        .name(candidate.name)
                        .votesCount(candidate.voteCount.longValue())
                        .build())
                .toList();
    }

    public List<Candidate> getCandidatesByVotingId(BigInteger votingId) {
        List<VotingContract.Candidate> candidatesByVotingId = ethereumService.getCandidatesByVotingId(votingId);
        return mapToCandidate(candidatesByVotingId);
    }

    public VoteWithCandidates getVotesById(BigInteger votingId) {
        VotingContract.Voting votingById = ethereumService.getVotingById(votingId);
        List<Candidate> candidates = getCandidatesByVotingId(votingId);
        return new VoteWithCandidates(mapToVotes(votingById), candidates);
    }

    public Boolean hasUserVoted(BigInteger votingId, BigInteger userId) {
        return ethereumService.hasUserVoted(votingId, userId);
    }

    public void vote(BigInteger votingId, BigInteger candidateId, BigInteger userId) {
        ethereumService.vote(votingId, candidateId, userId);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return user.getId();
    }
}
