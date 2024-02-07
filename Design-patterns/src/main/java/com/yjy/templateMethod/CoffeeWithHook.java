package com.yjy.templateMethod;

// 具体类
class CoffeeWithHook extends CoffeeTemplate {
    @Override
    void boilWater() {
        System.out.println("Boiling water");
    }

    @Override
    void brewCoffeeGrounds() {
        System.out.println("Brewing coffee grounds");
    }

    @Override
    void pourInCup() {
        System.out.println("Pouring coffee into cup");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding sugar and milk");
    }
}