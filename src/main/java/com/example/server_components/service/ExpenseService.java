package com.example.server_components.service;

import com.example.server_components.exception.ApiException;
import com.example.server_components.model.Category;
import com.example.server_components.model.ExpenseRecord;
import com.example.server_components.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ExpenseService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final Map<Long, ExpenseRecord> records = new ConcurrentHashMap<>();

    private final AtomicLong userIdGen = new AtomicLong(1);
    private final AtomicLong categoryIdGen = new AtomicLong(1);
    private final AtomicLong recordIdGen = new AtomicLong(1);

    // --- USERS ---
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found", "User with id " + id + " not found");
        }
        return user;
    }

    public User createUser(String name) {
        if (name == null || name.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid_request", "Field 'name' is required");
        }
        Long id = userIdGen.getAndIncrement();
        User user = new User(id, name.trim());
        users.put(id, user);
        return user;
    }

    public void deleteUser(Long id) {
        if (users.remove(id) == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found", "User with id " + id + " not found");
        }
    }

    // --- CATEGORIES ---
    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public Category createCategory(String name) {
        if (name == null || name.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid_request", "Field 'name' is required");
        }
        Long id = categoryIdGen.getAndIncrement();
        Category category = new Category(id, name.trim());
        categories.put(id, category);
        return category;
    }

    public void deleteCategory(Long id) {
        if (categories.remove(id) == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found", "Category with id " + id + " not found");
        }
    }

    // --- RECORDS ---
    public ExpenseRecord createRecord(ExpenseRecord request) {
        if (request.getUserId() == null || request.getCategoryId() == null || request.getAmount() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid_request",
                    "Fields 'user_id', 'category_id', and 'amount' are required");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "invalid_request", "Amount must be greater than 0");
        }
        if (!users.containsKey(request.getUserId())) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found",
                    "User with id " + request.getUserId() + " not found");
        }
        if (!categories.containsKey(request.getCategoryId())) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found",
                    "Category with id " + request.getCategoryId() + " not found");
        }

        Long id = recordIdGen.getAndIncrement();
        String createdAt = (request.getCreatedAt() != null && !request.getCreatedAt().isBlank())
                ? request.getCreatedAt()
                : Instant.now().toString();

        ExpenseRecord saved = new ExpenseRecord(
                id,
                request.getUserId(),
                request.getCategoryId(),
                createdAt,
                request.getAmount()
        );
        records.put(id, saved);
        return saved;
    }

    public ExpenseRecord getRecordById(Long id) {
        ExpenseRecord record = records.get(id);
        if (record == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found", "Record with id " + id + " not found");
        }
        return record;
    }

    public void deleteRecord(Long id) {
        if (records.remove(id) == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "not_found", "Record with id " + id + " not found");
        }
    }

    public List<ExpenseRecord> filterRecords(Long userId, Long categoryId) {
        if (userId == null && categoryId == null) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "invalid_request",
                    "At least one query parameter ('user_id' or 'category_id') is required for filtration"
            );
        }

        return records.values().stream()
                .filter(r -> userId == null || r.getUserId().equals(userId))
                .filter(r -> categoryId == null || r.getCategoryId().equals(categoryId))
                .toList();
    }
}