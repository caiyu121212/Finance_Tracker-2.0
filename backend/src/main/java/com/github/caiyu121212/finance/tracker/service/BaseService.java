package com.github.caiyu121212.finance.tracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageble;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 基础Service 接口
 * 定义通用的业务操作方法
 */

public interface BaseService<T, TD>{
    
    //基础CRUD操作
    T create(T entity);

    T update(ID id, T entity);

    void delete(ID id);

    void softDelete(ID id);

    T restore(ID id);

    Optional<T> findById(ID id);
    
    T restore (ID id);

    Optional<T> findById(ID id);

    T getById(ID id);

    List<T> findAll();

    List<T>findAllActive();

    Page<T>findAllActive(Pageable pageable);

    long countAllActive();

    boolean existsById(ID id);

    //批量操作
    List<T> createAll(List<T> entities);
    
    void deleteAll(List<ID> ids);

    void softDeleteAll(List<ID> ids)

    List<T> restoreAll(List<ID> ids);


}