package com.example.server_components.controller;

import com.example.server_components.model.User;
import com.example.server_components.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final ExpenseService expenseService;

    public UserController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(expenseService.getAllUsers());
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") Long userId) {
        return ResponseEntity.ok(expenseService.getUserById(userId));
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User request) {
        User created = expenseService.createUser(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Long userId) {
        expenseService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}