package com.github.caiyu121212.finance.tracker.dto;

import com.github.caiyu121212.finance.tracker.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO{
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private LocalDate transactionDate;
    private String paymentMethod;
    private String referenceNumber;
    private boolean isSettled;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //关联信息
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
}