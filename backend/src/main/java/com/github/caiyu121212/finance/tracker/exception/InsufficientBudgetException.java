package com.github.caiyu121212.finance.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientBudgetException extends BusinessException{

    private final BigDecimal budgetAmount;
    private final BigDecimal usedAmount;
    private final BigDecimal transactionAmount;

    public InsufficientBudgetException(String categoryName, BigDecimal budgetAmount,BigDecimal usedAmount, BigDecimal transactionAmount)
    {
        super(String.format("Insufficient budget for category '%s'.Budget: %s, Used: %s, Transaction: %s",categoryName,budgetAmount,usedAmount, transactionAmount));
        this.budgetAmount = budgetAmount;
        this.usedAmount = usedAmount;
        this.transactionAmount = transactionAmount;
    }
        //Getters
        public BigDecimal getBudgetAmount() { return budgetAmount;}
        public BigDecimal getUsedAmount() {return usedAmount;}
        public BigDecimal getTransactionAmount() { return transactionAmount;}
        
    }


