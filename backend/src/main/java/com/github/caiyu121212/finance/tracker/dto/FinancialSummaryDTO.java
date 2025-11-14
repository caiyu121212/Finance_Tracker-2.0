package com.github.caiyu121212.finance.tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
public classs FinancialSummaryDTO{
    private LocalDate startDate;
    private LocalDate endDate;

    //收支汇总
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netAmount;

    //预算信息
    private BigDecimal totalBudget;
    private BigDecimal totalBudgetUsed;
    private BigDecimal budgetUtilizationRate;

    //分类支出
    private List<CategoryExpenseDTO> categoryExpenses;

    //月度趋势
    private List<MonthlyTrendDTO> monthlyTrends;

    @Data
    @Builder
    public static class CategoryExpenseDTO{
        private String categoryName;
        private BigDecimal amount;
        private BigDecimal percentage;
        private String color;
    }
    
    @Data
    @Builder
    public static class MonthlyTrendDTO{
        private String period;
        private BigDecimal incomg;
        private BigDecimal expense;
        private BigDecimal net;
    }
}
