package com.voting_system_on_blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long id;
    private String name;
    private BigInteger candidatesCount;
    private BigInteger votersCount;
}
