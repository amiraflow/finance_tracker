package com.example.finance_tracker.service;

import com.example.finance_tracker.dto.TransactionDTO;
import com.example.finance_tracker.entity.*;
import com.example.finance_tracker.enums.TransactionType;
import com.example.finance_tracker.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final CategoryRepository categoryRepo;

    public TransactionService(TransactionRepository t, CategoryRepository c) {
        this.transactionRepo = t;
        this.categoryRepo = c;
    }

    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    public Transaction addTransaction(Transaction tx) {
        return transactionRepo.save(tx);
    }

    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
    }

    public BigDecimal getBalance() {
        List<Transaction> all = transactionRepo.findAll();
        BigDecimal income = all.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = all.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return income.subtract(expense);
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        try {
            TransactionType type = TransactionType.valueOf(dto.getType().toUpperCase());
            transaction.setType(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + dto.getType());
        }

        Category category = categoryRepo.findByName(dto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        transaction.setCategory(category);

        Transaction saved = transactionRepo.save(transaction);

        TransactionDTO result = new TransactionDTO();
        result.setId(saved.getId());
        result.setAmount(saved.getAmount());
        result.setType(String.valueOf(saved.getType()));
        result.setCategory(saved.getCategory().getName());

        return result;
    }
}