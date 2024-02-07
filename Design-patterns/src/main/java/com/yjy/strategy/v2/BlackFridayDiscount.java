package com.yjy.strategy.v2;

// 具体策略B
class BlackFridayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // 黑色星期五折扣算法
        return originalPrice * 0.7;
    }
}
