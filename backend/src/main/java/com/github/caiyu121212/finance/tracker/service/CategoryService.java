package com.github.caiyu121212.finance.tracker.service;

import com.github.caiyu121212.finance.tracker.model.Category;
import com.github.financetracker.model.TransactionType;
import com.github.financetracker.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

/**
 * 分类业务逻辑接口
 */

public interface CategoryService extends BaseService<Category, Long>{
    
    //分类管理
    Category createCategory(CategoryDTO categoryDTO);
    
    Category updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO getCategoryDTO(Long id);

    List<CategoryDTO> getAllCategoryDTOs();

    Page<CategoryDTO> getAllCategoryDTOS(Pageable pageable);

    //查询方法
    List<CategoryDTO> findByType(TransactionType type);

    List<CategoryDTO> findByNameContaining(String name);

    CategoryDTO findbyNameAndType(String name, TransactionType type);

    boolean existsByNameAndType(String name, TransactionType type);

    //业务逻辑

    boolean isCategoryInUse(Long CategoryId);

    Map<TransactionType, Long> getCategoryCountByType();

    List<CategoryDTO> getCategoriesByUsage();

    List<CategoryDTO> getCategoriesWithBudgetForMonth(String month);

    //批量操作
    void updateCategoryStatus(List<Long> categoryIds, boolean active);

    //统计信息
    Map<String, Object> getCategoryStatistics();

}