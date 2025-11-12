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

    List<Transaction>findByCategoryIdAndTransactionDateBetweenAndIsActiveTrue(Long categoryId, LocalDate startDate, LocalDate endDate);
    



}
 
