package com.example.finance_tracker.controller;

import com.example.finance_tracker.dto.CategoryDTO;
import com.example.finance_tracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryDTO create(@Valid @RequestBody CategoryDTO dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}