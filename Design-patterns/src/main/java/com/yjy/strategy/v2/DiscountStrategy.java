package com.yjy.strategy.v2;

// 抽象策略接口
interface DiscountStrategy {
    double applyDiscount(double originalPrice);
}