package com.github.caiyu121212.finance.tracker.repository;

import com.github.caiyu121212.finance.tracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * 预算数据访问接口
 */
@Repository
public interface BudgetRepository extends BaseRepository<Budget, Long> {

    // 基础查询方法
    Optional<Budget> findByCategoryIdAndBudgetMonthAndIsActiveTrue(Long categoryId, YearMonth budgetMonth);
    
    List<Budget> findByBudgetMonthAndIsActiveTrue(YearMonth budgetMonth);
    
    List<Budget> findByCategoryIdAndIsActiveTrue(Long categoryId);
    
    List<Budget> findByCategoryIdAndBudgetMonthBetweenAndIsActiveTrue(Long categoryId, YearMonth startMonth, YearMonth endMonth);
    
    // 预算统计查询
    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Budget b WHERE b.budgetMonth = :month AND b.isActive = true")
    BigDecimal getTotalBudgetForMonth(@Param("month") YearMonth month);
    
    @Query("SELECT COALESCE(SUM(b.usedAmount), 0) FROM Budget b WHERE b.budgetMonth = :month AND b.isActive = true")
    BigDecimal getTotalUsedAmountForMonth(@Param("month") YearMonth month);
    
    // 预算使用情况查询
    @Query("""
        SELECT b, (b.usedAmount / b.amount * 100) as usagePercentage 
        FROM Budget b 
        WHERE b.budgetMonth = :month 
        AND b.isActive = true 
        ORDER BY usagePercentage DESC
    """)
    List<Object[]> findBudgetsWithUsagePercentage(@Param("month") YearMonth month);
    
    // 超预算查询
    @Query("SELECT b FROM Budget b WHERE b.usedAmount > b.amount AND b.isActive = true AND b.budgetMonth = :month")
    List<Budget> findOverBudgetCategories(@Param("month") YearMonth month);
    
    // 预算执行情况统计
    @Query("""
        SELECT b.budgetMonth, 
               COALESCE(SUM(b.amount), 0) as totalBudget,
               COALESCE(SUM(b.usedAmount), 0) as totalUsed,
               COUNT(b) as budgetCount
        FROM Budget b 
        WHERE b.isActive = true 
        AND b.budgetMonth BETWEEN :startMonth AND :endMonth
        GROUP BY b.budgetMonth
        ORDER BY b.budgetMonth DESC
    """)
    List<Object[]> getBudgetExecutionSummary(
        @Param("startMonth") YearMonth startMonth,
        @Param("endMonth") YearMonth endMonth
    );
    
    // 更新已使用金额
    @Modifying
    @Query("UPDATE Budget b SET b.usedAmount = :usedAmount WHERE b.id = :id")
    int updateUsedAmount(@Param("id") Long id, @Param("usedAmount") BigDecimal usedAmount);
    
    // 批量更新已使用金额（基于实际交易）
    @Modifying
    @Query("""
        UPDATE Budget b 
        SET b.usedAmount = (
            SELECT COALESCE(SUM(t.amount), 0) 
            FROM Transaction t 
            WHERE t.category.id = b.category.id 
            AND t.type = 'EXPENSE' 
            AND t.isActive = true 
            AND YEAR(t.transactionDate) = YEAR(:month) 
            AND MONTH(t.transactionDate) = MONTH(:month)
        )
        WHERE b.budgetMonth = :month 
        AND b.isActive = true
    """)
    int refreshUsedAmounts(@Param("month") YearMonth month);
    
    // 查找最近设置的预算
    @Query("SELECT b FROM Budget b WHERE b.isActive = true ORDER BY b.budgetMonth DESC, b.updatedAt DESC")
    List<Budget> findRecentBudgets(org.springframework.data.domain.Pageable pageable);
}
