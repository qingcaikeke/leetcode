package com.yjy.strategy.v2;

// 具体策略CShoppingCart
class NewYearDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // 新年折扣算法
        return originalPrice * 0.9;
    }
}
