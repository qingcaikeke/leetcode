package com.yjy.strategy.v2;

// 客户端代码
public class Client {
    public static void main(String[] args) {
        // 创建环境对象
        ShoppingCart shoppingCart = new ShoppingCart();

        // 选择不同的折扣策略
        shoppingCart.setDiscountStrategy(new ChristmasDiscount());
        double discountedPrice1 = shoppingCart.calculateDiscount(100.0);
        System.out.println("Discounted Price with Christmas Discount: " + discountedPrice1);

        shoppingCart.setDiscountStrategy(new BlackFridayDiscount());
        double discountedPrice2 = shoppingCart.calculateDiscount(100.0);
        System.out.println("Discounted Price with Black Friday Discount: " + discountedPrice2);

        shoppingCart.setDiscountStrategy(new NewYearDiscount());
        double discountedPrice3 = shoppingCart.calculateDiscount(100.0);
        System.out.println("Discounted Price with New Year Discount: " + discountedPrice3);
    }
}
