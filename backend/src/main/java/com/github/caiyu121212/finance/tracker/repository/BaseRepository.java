package com.github.caiyu121212.finance.tracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 基础 Repository 接口
 * 提供通用的数据访问方法
 */

@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T,TD> {
    
    //查找活跃的记录
    Optional<T> findByIdAndIsActiveTrue(ID id);

    List<T> findAllByIsActiveTrue(ID id);

    Page<T> findAllByIsActiveTrue(Pageable pageable);

    //分页查询活跃记录
    @Query("SELECT e FROM #{#entityName} e WHERE e.isActive = true")
    List<T> findActiveAll();

    @Query("SELECT e FROM #{#entityName} e WHERE e.isActive = true")
    Page<T> findActiveAll(Pageable pageable);

    long countByIsActiveTrue();

    //软删除
    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.isActive = false, e.updatedAt = :now WHERE e.id = :id")
    int softDelete(@Param("id") ID id, @Param("now") LocalDateTime now);

    //批量软删除
    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.isActive = false, e.updateAt = :now WHERE e.id in :ids")
    int softDeleteAll(@param("ids") List<ID> ids, @param("now") LocalDateTime now);

    //恢复软删除
    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.isActive = true, e.updatedAt = :now WHERE e.id = :id")
    int restore(@Param("id") ID id, @Param("now") LocalDateTime now);


    //检查是否存在活跃记录
    boolean existsByIdAndIsActiveTrue(ID id);

    //批量查找活跃记录
    @Query("SELECT e FROM #{#entityName} e WHERE e.id IN :ids AND e.isActive = true")
    List<T> findAllActiveByIds(@Param("ids") List<ID> ids);
}