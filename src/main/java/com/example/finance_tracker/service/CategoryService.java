package com.example.finance_tracker.service;

import com.example.finance_tracker.dto.CategoryDTO;
import com.example.finance_tracker.entity.Category;
import com.example.finance_tracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        if (categoryRepo.existsByName(dto.getName())) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists.");
        }
        Category saved = categoryRepo.save(category);
        return new CategoryDTO(saved.getId(), saved.getName());
    }

    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(dto.getName());
        if (categoryRepo.existsByName(dto.getName())) {
            throw new RuntimeException("Category with name '" + dto.getName() + "' already exists.");
        }
        Category updated = categoryRepo.save(category);
        return new CategoryDTO(updated.getId(), updated.getName());
    }
}