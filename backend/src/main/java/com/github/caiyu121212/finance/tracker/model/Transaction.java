package com.github.caiyu121212.finance.tracker.model;

import com.finance.tracker.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 交易实体类
 * 记录每一笔收入和支出的详细信息
 */

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity{
    /**
     * 交易金额
     */
    @NotBlank(message = "交易描述不能为空")
    @DecimalMin(value = "0.01",message ="交易金额必须大于0")
    @Digits(integer = 10, fraction =2, message ="交易金额格式不正确")
    @Column(name = "amount", nullable = false, precision =10, scale =2)
    private BigDecimal amount;

    /**
     * 交易描述
     */
    @NotBlank(message="交易描述不能为空")
    @Size(min =1, max = 200,message = "交易描述长度必须在1-200个字符之间")
    @Column(name = "description", nullable = false, length =200)
    private String description;

    /**
     * 交易日期
     */
    @NotNull(message ="交易日期不能为空")
    @column(name ="transaction_date", nullable = false)
    private LocalDate transactionDate;

    /**
     * 交易类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length =10)
    private TransactionType type;

    /**
     * 关联的分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    /**
     * 支付方式
     */
    @Size(max = 50, message ="支付方式长度不能超过50个字符")
    @Column(name = "payment_method", length 50)
    private String paymentMethod;

    /**
     * 交易地点
     */
    @Size(max =100, message ="交易地点长度不能超过100个字符")
    @Column(name = "location",length =100)
    private String location;

    /**
     * 备注信息
     */
    @Siza(max = 500, message = "备注信息长度不能超过500个字符")
    @Column(name = "notes", length =500)
    private String notes;

    /**
     * 是否已经结算
    */
   @Column(name = "is_settled", nullable = false)
   private Boolean isSettled = true;

   /**
    * 标签-用于搜索和分类
    */
   @Size(max = 100, message = "标签长度不能超过100个字符")
   @Column(name = "tags", length =100)
   private String tags;

   //构造函数
    public Transaction() {}

    public Transaction(BigDecimal amount, String description, LocalDate transactionDate, TransactionType type, Category category) {
        this.amount = amount;
        this.description = description;
        this.tansaxtionDate = transactionDate;
        this.type = type;
        this.category = category;
        this.isSettled = true;
    }

    //Getters and Setters
    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public LocalDate getTransactionDate(){
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate){
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType(){
        return type;
    }

    public void setType(TransactionType type){
        this.type = type;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public Boolean getIsSettled(){
        return isSettled;
    }

    public void setIsSettled(Boolean isSettled){
        this.isSettled = isSettled;
    }

    public String getTags(){
        return tags;
    }

    public void setTags(String tags){
        this.tags = tags;
    }

    /**
     * 获取交易金额的绝对值
     */
    @Transient
    public BigDecimal getAbsoluteAmount(){
        return amount.abs()
    }

    @Override
    public String toString(){
        return  "Transaction{" +
                "id=" + getId() +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", category=" + (category != null ? category.getName() : "null") +
                ", isSettled=" + isSettled +
                ", createdAt=" + getCreatedAt() +
                '}';
    }

}