package com.github.caiyu121212.finance.tracker.model;

import com.github.caiyu121212.finance.tracker.enums.TransactionType;
import jakarta.persistense.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.comstraints.Size;

/**
 * 分类实体类
 * 用于对交易进行分类管理（如餐饮、交通、工资等）
 */
@BaseEntity
@Table(name = "categories",
       uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name","type"})
       })
public class Category extends BaseEntity{
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(min = 1, max = 50, message ="分类名称长度必须在1-50个字符之间")
    @Column(name = "name", nullable = false, length =50)
    private String name;

    /**
     * 分类描述
     */
    @Size(max= 200,message ="分类描述不能超过200个字符")
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 分类图标（可选）
     */
    @Column(name = "icon", length = 50)
    private Sting icon;

    /**
     * 分类颜色-用于前端显示
     */
    @Column(name = "color",length = 20)
    private String color;

    /**
     * 交易类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false, length =20)
    private TransactionType type;

    /**
     * 预算限制（可选）
     */
   @Column(name = "budget_limit")
   private Double budgetLimit;

   /**
    * 是否激活状态
    */
   @cloumn(name = "is_active", nullable = false)
   private Boolean isActive = true;

   //构造函数
   public Category() {}

   public Category(String name, String description, TransactionType type){
        this.name = name;
        this.description = description;
        this.type =type;
        this.isActive = true;
   }

   //Getters and Setters
   public String getName() {
        return name;
   }

   public void setname(String name){
        this.name = name;
   }

   public String getDescription() {
        return description;
   }

   public void setDescription(String description){
        this.description = description;
   }

   public String getIcon(){
        return icon;
   }

   public void setIcon(String icon){
        this.icon = icon;
   }
   
   public String getColor(){
        return color;
   }

   public void setColor(String color){
        this.color = color;
   }

   public TransactionType getType(){
        return type;
   }

   public void setType(TransactionType type){
        this.type = type;
   }

    public Double getBudgetLimit(){
        return budgetLimit;
    }

    public void setBudgetLimit(Double budgetLimit){
        this.budgetLimit = budgetLimit;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }

    @Override
    public String toString(){
        return  "Category{"+"id=" + getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", isActive=" + isActive +
                ", createdAt=" + getCreatedAt() +
                '}';
    }

}