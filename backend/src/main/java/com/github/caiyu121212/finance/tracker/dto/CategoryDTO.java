package com.github.caiyu121212.finance.tracker.dto;

import com.github.caiyu121212.finance.tracker.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDTO{
    private Long id;
    private String name;
    private TransactionType type;
    private String description;
    private String color;
    private String icon;
    private Integer sortOrder;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long transactionCount;
    private BigDecimal totalAmount;
}