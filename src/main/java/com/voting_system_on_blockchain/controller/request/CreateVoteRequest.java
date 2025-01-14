package com.voting_system_on_blockchain.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoteRequest {
    private String name;
    private List<String> candidates;
}
