package com.github.caiyu121212.finance.tracker.repository.java;

import com.github.caiyu121212.finance.tracker.model.Category;
import com.github.caiyu121212.finance.tracker.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 分类数据访问接口
 */

@Repository

public interface CategoryRepository extends BaseRepository<Category, Long>{
    //基本查询方法
    Optional<Catagory> findByNameAndTypeAndIsActiveTrue(String name, TransactionType type);
    
    List<Category> findTypeAndIsActiveTrue(Transactional type);

    Page<Category> findByTypeAndIsActiveTrueOrderByNameAsc(TransactionType type);

    List<Category> findByTypeAndIsActiveTrueOrderByNameAsc(TransactionType type);

    List<category> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    Page<Category> findByNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);

    boolean existsByNameIgnoreCaseAndTypeAndIsActiveTrue(String name, TransactionType type);

    List<Category> findAllByIsActiveTrueOrderByTypeAscNameAsc();

    //自定义查询：统计分类使用情况
    @Query("SELECT c.type, COUNT(c) FROM Category c WHERE c.isActive = true GROUP BY c.type")
    List<Object[]> countActiveCategoriesByType();

    @Query("SELECT c FROM Category c WHERE c.type = :type AND c.description IS NOT NULL AND c.description <> '' AND c.isActive = true")
    List<Category> findByTypeWithDescription(@Param("type") TransactionType type);
    
    @Query("SELECT c FROM Category c WHERE c.id IN :ids AND c.isActive = true ORDER BY c.name")
    List<Category> findActiveByIds(@Param("ids"),List<Long> ids);

    //分类使用频率统计
    @Qurey("""
        SELECT c, COUNT(t) as usageCount
        FROM Category c
        LEFT JOIN Transaction t ON t.category = c AND t.isActive = true
        WHERE c.isActive = true
        GROUP BY c
        ORDER BY usageCount DESC
    """)
    List<Object[]> findCategoriesByUsageCount();

    @Query("SELECT COUNT(t) >0 FROM Transaction t WHERE t.category.id = :categoryId AND t.isActive =true")
    boolean isCategoryInUse(@Param("categoryId") Long categoryId );

    //预算相关查询
    @Query("""
        SELECT c, COALESCE(SUM(b.amount),0) as totalBudget
        FROM Category c
        LEFT JOIN Budget b ON b.category=c AND b.budgetMonth = :month AND b.isActive = true
        WHERE c.isActve = true AND c.type = 'EXPENSE'
        GROUP BY c
    """)
    List<Object[]> findExpenseCategoriesWithBudget(@Param("month") Srting month);

    //批量操作
    @Modifying
    @Query("UPDATE Category c SET c.isActive = : active, c.updatedAt = CURRENT_TIMESTAMP WHERE c.id IN :ids")
    int updateActiveStatus(@Param("ids") List<Long> ids, @Param("active") boolean active);

    //高级统计查询
    @Query("""
        SELECT c.name, COUNT(t) as transactionCount,
            COALESCE(SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END),0) as totalIncome,
            COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END),0) as totalExpense
        FROM Category c
        LEFT JOIN Transaction t ON t.category = c AND t.isActive = true
        WHERE c.isActive = true
        GROUP BY c.id, c.name
        GROUP BY transationCount DESC
        """)
    List<Object[]> getCategoryStatistics();

    //查找有预算设置的分类
    @Query("""
        SELECT DISTINCT c
        FROM Category c
        JOIN Budget b ON b.category = c
        WHERE b.isActive = true AND c.isActive = true
        AND b.budgetMonth = :month
    """)
        List<Category> findCategoriesWithBudgetForMonth(@Param("month") String month);


}


