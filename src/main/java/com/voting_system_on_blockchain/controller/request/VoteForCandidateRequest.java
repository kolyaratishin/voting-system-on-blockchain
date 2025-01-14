package com.voting_system_on_blockchain.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteForCandidateRequest {
    private BigInteger votingId;
    private BigInteger candidateId;
}
