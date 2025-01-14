package com.voting_system_on_blockchain.controller;

import com.voting_system_on_blockchain.controller.request.CreateVoteRequest;
import com.voting_system_on_blockchain.controller.request.VoteForCandidateRequest;
import com.voting_system_on_blockchain.dto.Candidate;
import com.voting_system_on_blockchain.dto.Vote;
import com.voting_system_on_blockchain.dto.VoteWithCandidates;
import com.voting_system_on_blockchain.entity.User;
import com.voting_system_on_blockchain.repository.UserRepository;
import com.voting_system_on_blockchain.service.VotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VotesController {

    private final VotesService votesService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createVoting(@RequestBody CreateVoteRequest createVoteRequest) {
        votesService.createVoting(createVoteRequest.getName(), createVoteRequest.getCandidates());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = votesService.getAllVotes();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/candidates/{votingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Candidate>> getCandidatesByVotingId(@PathVariable BigInteger votingId) {
        List<Candidate> candidates = votesService.getCandidatesByVotingId(votingId);
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/{votingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VoteWithCandidates> getVotesById(@PathVariable BigInteger votingId) {
        VoteWithCandidates vote = votesService.getVotesById(votingId);
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/hasVoted")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> hasUserVoted(@RequestParam BigInteger votingId) {
        Long userId = getCurrentUserId();
        Boolean hasVoted = votesService.hasUserVoted(votingId, BigInteger.valueOf(userId));
        return ResponseEntity.ok(hasVoted);
    }

    @PostMapping("/vote")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> vote(@RequestBody VoteForCandidateRequest request) {
        Long userId = getCurrentUserId();
        votesService.vote(request.getVotingId(), request.getCandidateId(), BigInteger.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return user.getId();
    }
}
