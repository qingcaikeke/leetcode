package com.yjy.strategy.v2;

// 具体策略A
class ChristmasDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // 圣诞折扣算法
        return originalPrice * 0.8;
    }
}
