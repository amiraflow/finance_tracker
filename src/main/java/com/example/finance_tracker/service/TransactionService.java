package com.example.finance_tracker.service;

import com.example.finance_tracker.dto.TransactionDTO;
import com.example.finance_tracker.entity.*;
import com.example.finance_tracker.enums.TransactionType;
import com.example.finance_tracker.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public Page<TransactionDTO> getFiltered(TransactionType type, String categoryName,
                                            BigDecimal minAmount, BigDecimal maxAmount,
                                            LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        List<Transaction> transactions = transactionRepo.findAll();

        return transactions.stream()
                .filter(t -> type == null || t.getType() == type)
                .filter(t -> categoryName == null || t.getCategory().getName().equalsIgnoreCase(categoryName))
                .filter(t -> minAmount == null || t.getAmount().compareTo(minAmount) >= 0)
                .filter(t -> maxAmount == null || t.getAmount().compareTo(maxAmount) <= 0)
                .filter(t -> dateFrom == null || !t.getDate().isBefore(dateFrom.atStartOfDay()))
                .filter(t -> dateTo == null || !t.getDate().isAfter(dateTo.atStartOfDay()))
                .map(TransactionDTO::fromEntity)
                .toList();
    }

    public Page<TransactionDTO> getPagedTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Transaction> paged = transactionRepo.findAll(pageable);
        return paged.map(TransactionDTO::fromEntity);
    }
}