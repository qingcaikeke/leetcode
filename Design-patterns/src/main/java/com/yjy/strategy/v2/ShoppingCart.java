package com.yjy.strategy.v2;

// 环境类
class ShoppingCart {
    private DiscountStrategy discountStrategy;

    // 设置策略
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    // 计算折扣价
    public double calculateDiscount(double originalPrice) {
        if (discountStrategy != null) {
            return discountStrategy.applyDiscount(originalPrice);
        }
        return originalPrice;
    }
}
