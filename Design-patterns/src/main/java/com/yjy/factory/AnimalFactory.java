package com.yjy.factory;

import com.yjy.entity.Animal;
import com.yjy.entity.Food;

/**
 * 工厂接口
 * 提供一个创建一系列相关或相互依赖对象的接口,
 */
public interface AnimalFactory {
    Animal createAnimal();
    Food createFood();
}
