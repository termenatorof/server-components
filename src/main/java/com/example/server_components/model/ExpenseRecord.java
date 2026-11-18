package com.example.server_components.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ExpenseRecord {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("created_at")
    private String createdAt;

    private BigDecimal amount;

    public ExpenseRecord() {}

    public ExpenseRecord(Long id, Long userId, Long categoryId, String createdAt, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
