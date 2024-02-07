package com.yjy.templateMethod;

/**
 * 定义了一个算法的骨架，
 * 将一些步骤延迟到子类中实现。
 * 这种模式使得子类可以在不改变算法结构的情况下重新定义算法的某些步骤。
 * 用途：Interceptor，Sentinel，Gateway网关，Dubbo，MyBatis
 */
// 模板类
abstract class CoffeeTemplate {
    // 模板方法，定义冲泡咖啡的算法骨架
    final void prepareCoffee() {
        boilWater();
        brewCoffeeGrounds();
        pourInCup();
        addCondiments();
    }

    // 具体步骤由子类实现
    abstract void boilWater();
    abstract void brewCoffeeGrounds();
    abstract void pourInCup();
    abstract void addCondiments();
}
