package com.example.finance_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private String category;

}