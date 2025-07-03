package com.example.finance_tracker.controller;

import com.example.finance_tracker.entity.Transaction;
import com.example.finance_tracker.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> all() {
        return service.getAll();
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
}