package com.example.finance_tracker.dto;

import com.example.finance_tracker.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private String category;
    private String date;

    public static TransactionDTO fromEntity(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(String.valueOf(transaction.getType()));
        dto.setDate(String.valueOf(transaction.getDate()));
        dto.setCategory(transaction.getCategory().getName());
        return dto;
    }

}