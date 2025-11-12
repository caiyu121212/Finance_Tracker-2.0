package com.github.caiyu121212.finance.tracker.repository;

import com.github.caiyu121212.finance.tracker.model.Transaction;
import com.github.caiyu121212.finance.tracker.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.date.domian.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.reposttory.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 交易数据访问接口
 */
@Repository
public interface  TransactionRepository extends BaseRepository<Transaction, Long>{
    
    //基础查询方法
    List<Transaction> findByTransactionDateBetweenAndIsActiveTrue(LocalDate startDate, LocalDate endDate);

    Page<Transaction> findByTransactionDateBetweeenAndIsActiveTrue(LocalDate startDate, LocalDate endDate, Pageable pageable)
    
    List<Transaction> findByTypeAndIsActiveTrue(Transaction type);

    List<Transaction> findByCategoryIdAndIsActiveTrue(Long categoryId);

    List<Transaction> findByCategoryIdAndTransactionDateBetweenAndIsActiveTrue(Long categoryId, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByAmountBetweenAndIsActiveTrue(BigDecimal minAmount, BigDecimal maxAmount);

    List<Transaction> findByAmountGreaterThanAndIsActiveTrue(BigDecimal amount);

    List<Transaction> findByAmountLessThanAndIsActiveTrue(BigDecimal amount);

    List<Transaction> findByDescriptionContainingIgnoreCaseAndIsActiveTrue(String description);

    Page<Transaction> findByDecriptionContainingIgnoreCaseAndIsActiveTrue(String description, Pageable pageable);

    //财务统计查询
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type = 'INCOME' AND t.transactionDate BETWEEN :startDate AND :endDate AND t.isActive = true")
    BigDecimal getTotalIncomeBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate)

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERER t.type = 'EXPENSE' AND t.transactionDate BETWEEN :startDate AND :endDate AND t.isActive = true")
    BigDecimal getTotalExpenseBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") Local endDate);

    //月度收支统计
    @Query("""
        SELECT YEAR(t.transactionDate) as year, MONTH(t.transactionDate) as month, t.type, COALESCE(sum(t.amount),0) as total
        FROM Transaction t
        WHERE t.isActive = true
        AND t.transactionDate BETWEEN :startDate AND :endDate
        GROUP BY YEAR(t.transactionDate), MONTH(t.transactionDate), t.type
        ORDER BY year DESC, month DESC
    """)
    List<Object[]> getMonthlySummary(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //分类支出统计
    @Query("""
        SELECT c.name, COALSECE(SUM(t.amount),0) as total
        FROM Transaction t
        JOIN t.category c
        WHRER t.type = 'EXPENSE' AND t.isActive = true AND t.transactionDate BETWEEN :startDate AND :endDate
        GROUP BY c.id, c.name
        ORDER BY total DESC
    """)
List<Object[]> getCategoryExpenseSummary(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // 最近交易查询
    @Query("SELECT t FROM Transaction t WHERE t.isActive = true ORDER BY t.transactionDate DESC, t.createdAt DESC")
    List<Transaction> findRecentTransactions(Pageable pageable);
    
    // 未结算交易查询
    List<Transaction> findByIsSettledFalseAndIsActiveTrue();
    
    // 支付方式统计
    @Query("""
        SELECT t.paymentMethod, COUNT(t) as count, COALESCE(SUM(t.amount), 0) as total
        FROM Transaction t 
        WHERE t.isActive = true 
        AND t.paymentMethod IS NOT NULL
        AND t.transactionDate BETWEEN :startDate AND :endDate
        GROUP BY t.paymentMethod
        ORDER BY total DESC
    """)
    List<Object[]> getPaymentMethodSummary(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // 高级搜索查询
    @Query("""
        SELECT t FROM Transaction t 
        WHERE t.isActive = true 
        AND (:categoryId IS NULL OR t.category.id = :categoryId)
        AND (:type IS NULL OR t.type = :type)
        AND (:startDate IS NULL OR t.transactionDate >= :startDate)
        AND (:endDate IS NULL OR t.transactionDate <= :endDate)
        AND (:minAmount IS NULL OR t.amount >= :minAmount)
        AND (:maxAmount IS NULL OR t.amount <= :maxAmount)
        AND (:description IS NULL OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%')))
        ORDER BY t.transactionDate DESC, t.createdAt DESC
    """)
    Page<Transaction> searchTransactions(
        @Param("categoryId") Long categoryId,
        @Param("type") TransactionType type,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount,
        @Param("description") String description,
        Pageable pageable
    );
    
    // 预算使用情况查询
    @Query("""
        SELECT COALESCE(SUM(t.amount), 0) 
        FROM Transaction t 
        WHERE t.category.id = :categoryId 
        AND t.type = 'EXPENSE' 
        AND t.isActive = true 
        AND YEAR(t.transactionDate) = :year 
        AND MONTH(t.transactionDate) = :month
    """)
    BigDecimal getCategoryExpenseForMonth(
        @Param("categoryId") Long categoryId,
        @Param("year") int year,
        @Param("month") int month
    );
    
    // 年度趋势分析
    @Query("""
        SELECT YEAR(t.transactionDate) as year, 
               COALESCE(SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), 0) as income,
               COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END), 0) as expense
        FROM Transaction t 
        WHERE t.isActive = true 
        GROUP BY YEAR(t.transactionDate)
        ORDER BY year DESC
    """)
    List<Object[]> getYearlyTrend();

}
 
