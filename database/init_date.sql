--创建数据库
CREATE DATABASE IF NOT EXISTS finance_tracker
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE  finance_tracker;

--创建表结构

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type ENUM('INCOME', 'EXPENSE') NOT NULL,
    icon VARCHAR(10),
    color VARCHAR(7),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


--插入默认分类数据
INSERT INTO categories(name, description, type, icon, color, is_active, created_at, updated_at,) VALUES
--收入分类
('工资', '主要工作收入', 'INCOME', '💰', '#22c55e', true, NOW(), NOW()),
('奖金', '绩效奖金、年终奖等', 'INCOME', '🎁', '#10b981', true, NOW(), NOW()),
('投资收入', '股票、基金等投资收益', 'INCOME', '📈', '#059669', true, NOW(), NOW()),
('兼职收入', '副业、兼职收入', 'INCOME', '💼', '#047857', true, NOW(), NOW()),
('其他收入', '其他各类收入', 'INCOME', '📥', '#065f46', true, NOW(), NOW()),

-- 支出分类
('餐饮', '日常饮食开销', 'EXPENSE', '🍔', '#ef4444', true, NOW(), NOW()),
('交通', '公共交通、打车、油费等', 'EXPENSE', '🚗', '#f97316', true, NOW(), NOW()),
('购物', '服装、日用品等购物', 'EXPENSE', '🛍️', '#8b5cf6', true, NOW(), NOW()),
('娱乐', '电影、游戏、旅游等', 'EXPENSE', '🎮', '#ec4899', true, NOW(), NOW()),
('住房', '房租、房贷、水电费等', 'EXPENSE', '🏠', '#06b6d4', true, NOW(), NOW()),
('医疗', '看病、买药等医疗开销', 'EXPENSE', '🏥', '#6366f1', true, NOW(), NOW()),
('教育', '学习资料、课程培训等', 'EXPENSE', '📚', '#14b8a6', true, NOW(), NOW()),
('通讯', '话费、网费等通讯费用', 'EXPENSE', '📱', '#f59e0b', true, NOW(), NOW()),
('其他支出', '其他各类支出', 'EXPENSE', '📤', '#6b7280', true, NOW(), NOW());