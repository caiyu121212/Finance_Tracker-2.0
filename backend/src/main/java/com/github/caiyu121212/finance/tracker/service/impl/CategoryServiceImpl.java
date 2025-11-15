package com.github.caiyu121212.finance.tracker.service.impl;

import  com.github.caiyu121212.finance.tracker.model.Category;
import  com.github.caiyu121212.finance.tracker.enums.TransactionType;
import  com.github.caiyu121212.finance.tracker.dto.CategoryDTO;
import  com.github.caiyu121212.finance.tracker.exception.BusinessException;
import  com.github.caiyu121212.finance.tracker.exception.ResourceNotFoundException;
import  com.github.caiyu121212.finance.tracker.repository.CategoryRepository;
import  com.github.caiyu121212.finance.tracker.service.CategoryService;
import  lombok.RequiredArgsConstructor;
import  lombok.extern.slf4j.Slf4j;
import  org.springframework.data.domain.Page;
import  org.springframework.data.domain.Pageable;
import  org.springframework.stereotype.Service;
import  org.springframework.transaction.annotation.Transactional;
import  java.time.LocalDateTime;
import  java.util.List;
import  java.util.Map;
import  java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category){
        log.info("Creating new category:{}", category.getName());
        return categoryRepository.save(category);
    }
    
    @Override
    public Category createCategory(CategoryDTO categoryDTO){
        log.info("Creating new category from DTO:{}", categoryDTO.getName());

        //检查分类名称是否已存在
        if(categoryRepository.existsByNameIgnoreCaseAndTypeAndIsActiveTrue(categoryDTO.getName(),categoryDTO.getType())){
            throw new BusinessException(String.format("Category with name '%s' and type '%s' already exists",
            categoryDTO.getName(),categoryDTO.getType()));
        }

        Category category = Category.builder()
            .name(categoryDTO.getName())
            .type(categoryDTO.getType())
            .color(categoryDTO.getColor())
            .description(categoryDTO.getDescription())
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    }
}
