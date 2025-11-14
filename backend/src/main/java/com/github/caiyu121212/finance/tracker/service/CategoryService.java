package com.github.caiyu121212.finance.tracker.service;

import com.github.financetracker.model.Category;
import com.github.financetracker.model.TransactionType;
import com.github.financetracker.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 分类业务逻辑接口
 */

public interface CategoryService extends BaseService<Category, Long>{
    
    //分类管理
    Category createCategory(CategoryDTO categoryDTO);
    
    Category updateCategory(Long id, CategoryDTO categoryDTO);

}