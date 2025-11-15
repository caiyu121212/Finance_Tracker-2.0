package com.github.caiyu121212.finance.tracker.enums;


/**
 * 交易类型枚举
 * 定义系统中支持的交易类型
 */

public enum TransactionType {
    /**
     * 收入类型-表示资金流入
     */
    INCOME("收入"),

    /**
     * 支出类型-表示资金流出
     */
    EXPENSE("支出");

    private final String displayName;

    /**
     * 枚举构造函数
     * @param displayName 显示名称
     */

    TransactionType(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * 获取显示名称
     * @return 中文显示名称
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 根据字符串的值获取枚举实例
     * @param value 字符串值
     * @return 对应的枚举实例
     */
    public static TransactionType fromValue(String value){
        for (TransactionType type:values()){
            if (type.name().equalsIgnoreCase(value)){
                return type;
            }
        }
        throw new IllegalArgumentException("无效的交易类型："+ value);
    }
}