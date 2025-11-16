package com.github.caiyu121212.finance.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 所有实体类的父类，包含公共字段
 */

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name="create_at", nullable=false,updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at",nullable=false)
    private LocalDateTime updatedAt;

    /**
     * 持久化前回调方法-设置创建和更新时间
     */

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 更新前的回调方法-设置更新时间
     */
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    //Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName()+"{"+"id="+id+",createAt="+createdAt+",updateAt="+updatedAt+
        "}";
    }
    
}