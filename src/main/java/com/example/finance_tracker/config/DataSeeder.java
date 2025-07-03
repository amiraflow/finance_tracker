package com.example.finance_tracker.config;

import com.example.finance_tracker.entity.Category;
import com.example.finance_tracker.entity.Transaction;
import com.example.finance_tracker.enums.TransactionType;
import com.example.finance_tracker.repository.CategoryRepository;
import com.example.finance_tracker.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(CategoryRepository categoryRepo, TransactionRepository transactionRepo) {
        return args -> {
            if (categoryRepo.count() == 0) {
                Category groceries = new Category("Groceries");
                Category salary = new Category("Salary");
                categoryRepo.save(groceries);
                categoryRepo.save(salary);

                Transaction t1 = new Transaction("Billa groceries", BigDecimal.valueOf(45.90), LocalDateTime.now(), TransactionType.EXPENSE, groceries);
                Transaction t2 = new Transaction("Monthly Salary", BigDecimal.valueOf(3000), LocalDateTime.now().minusDays(2), TransactionType.INCOME, salary);

                transactionRepo.save(t1);
                transactionRepo.save(t2);
            }
        };
    }
}