package com.example.server_components.controller;

import com.example.server_components.model.Category;
import com.example.server_components.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final ExpenseService expenseService;

    public CategoryController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(expenseService.getAllCategories());
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category request) {
        Category created = expenseService.createCategory(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/category/{category_id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("category_id") Long categoryId) {
        expenseService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}