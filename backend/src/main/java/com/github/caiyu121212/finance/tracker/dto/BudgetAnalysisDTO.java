package com.github.caiyu121212.finance.tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Data
@Builder
public class BudgetAnalysisDTO{
    private YearMonth analysisMonth;
    private BigDecimal totalBudget;
    private BigDecimal totalUsed;
    private BigDecimal utilizationRate;
    private int overBudgetCount;
    private int nearBudgetCount;

    private List<BudgetStatusDTO> budgetStatuses;

    @Data
    @Builder
    public static class BudgetStatusDTO{
        private String categoryName;
        private BigDecimal budgetAmount;
        private BigDecimal usedAmount;
        private BigDecimal remainingAmount;
        private BigDecimal usagePercentage;
        private String status; //"UNDER","NEAR","OVER"
        private String color;
    }

}
