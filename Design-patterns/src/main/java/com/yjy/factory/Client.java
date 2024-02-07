package com.yjy.factory;

import com.yjy.entity.Animal;
import com.yjy.entity.Food;


public class Client {
    /**
     * 将创建对象的过程延迟到子类
     * 工厂可以用于创建一系列相关或相互依赖的对象，无需指定具体实现
     */
    AnimalFactory animalFactory = new CatFactory();
    Animal cat = animalFactory.createAnimal();
    Food foodA = animalFactory.createFood();
}
