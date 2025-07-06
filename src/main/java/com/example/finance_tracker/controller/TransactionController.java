package com.example.finance_tracker.controller;

import com.example.finance_tracker.dto.TransactionDTO;
import com.example.finance_tracker.entity.Transaction;
import com.example.finance_tracker.enums.TransactionType;
import com.example.finance_tracker.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TransactionDTO> all(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            Pageable pageable
    ) {
        return service.getFiltered(type, category, minAmount, maxAmount, dateFrom, dateTo, pageable);
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction tx) {
        return service.addTransaction(tx);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTransaction(id);
    }

    @GetMapping("/balance")
    public BigDecimal balance() {
        return service.getBalance();
    }

    @GetMapping("/paged")
    public Page<TransactionDTO> getPagedTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getPagedTransactions(page, size);
    }
}