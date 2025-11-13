package com.github.caiyu121212.finance.tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDTO{
    private Long id;
    private BigDecimal amount;
    private BigDecimal usedAmount;
    private YearMonth budgetAmount;
    private String notes;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //关联信息
    private Long categoryId;
    private String categoryName;
    private TransactionType categoryType;
    private String categoryColor;

    //计算字段
    private BigDecimal remainingAmount;
    private BigDecimal usagePercentage;
    private boolean isOverBudget;
}