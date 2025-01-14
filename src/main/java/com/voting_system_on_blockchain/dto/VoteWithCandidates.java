package com.voting_system_on_blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteWithCandidates {
    private Vote vote;
    private List<Candidate> candidates;
}
