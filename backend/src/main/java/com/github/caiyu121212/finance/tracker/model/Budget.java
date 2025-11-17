package com.github.caiyu121212.finance.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * 预算实体类
 * 用于设置和管理月度预算
 */
@Entity
@Table(name = "budgets", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"category_id", "budget_month"})
       })
public class Budget extends BaseEntity {

    /**
     * 关联的分类
     */
    @NotNull(message = "分类不能为空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * 预算月份 (格式: YYYY-MM)
     */
    @NotNull(message = "预算月份不能为空")
    @Column(name = "budget_month", nullable = false)
    private YearMonth budgetMonth;

    /**
     * 预算金额
     */
    @NotNull(message = "预算金额不能为空")
    @DecimalMin(value = "0.00", message = "预算金额不能为负数")
    @Digits(integer = 10, fraction = 2, message = "预算金额格式不正确")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * 当前已使用金额（计算字段）
     */
    @Column(name = "used_amount", precision = 10, scale = 2)
    private BigDecimal usedAmount = BigDecimal.ZERO;

    /**
     * 预算描述
     */
    @Size(max = 200, message = "预算描述长度不能超过200个字符")
    @Column(name = "description", length = 200)
    private String description;

    // 构造函数
    public Budget() {}

    public Budget(Category category, YearMonth budgetMonth, BigDecimal amount) {
        this.category = category;
        this.budgetMonth = budgetMonth;
        this.amount = amount;
        this.usedAmount = BigDecimal.ZERO;
    }

    // Getters and Setters
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public YearMonth getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(YearMonth budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取剩余预算金额
     */
    @Transient
    public BigDecimal getRemainingAmount() {
        return amount.subtract(usedAmount);
    }

    /**
     * 获取预算使用百分比
     */
    @Transient
    public BigDecimal getUsagePercentage() {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return usedAmount.divide(amount, 4, BigDecimal.ROUND_HALF_UP)
                         .multiply(BigDecimal.valueOf(100));
    }

    /**
     * 检查是否超支
     */
    @Transient
    public boolean isOverBudget() {
        return usedAmount.compareTo(amount) > 0;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + getId() +
                ", category=" + (category != null ? category.getName() : "null") +
                ", budgetMonth=" + budgetMonth +
                ", amount=" + amount +
                ", usedAmount=" + usedAmount +
                ", remainingAmount=" + getRemainingAmount() +
                ", createdAt=" + getCreatedAt() +
                '}';
    }
}