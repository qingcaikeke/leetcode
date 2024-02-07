package com.yjy.factory;

import com.yjy.entity.Animal;
import com.yjy.entity.Dog;
import com.yjy.entity.Food;
import com.yjy.entity.FoodB;

/**
 * 小狗实现
 */
public class DogFactory implements AnimalFactory {
    @Override
    public Animal createAnimal() {
        Dog dog = new Dog();
        //一系列复杂操作
        return dog;
    }

    @Override
    public Food createFood() {
        FoodB foodB = new FoodB();
        return foodB;
    }
}
