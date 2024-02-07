package com.yjy.factory;

import com.yjy.entity.Animal;
import com.yjy.entity.Cat;
import com.yjy.entity.Food;
import com.yjy.entity.FoodA;

public class CatFactory implements AnimalFactory{
    @Override
    public Animal createAnimal() {
        Cat cat = new Cat();
        //一系列复杂操作
        return cat;
    }

    @Override
    public Food createFood() {
        FoodA foodA = new FoodA();
        return foodA;
    }


}
