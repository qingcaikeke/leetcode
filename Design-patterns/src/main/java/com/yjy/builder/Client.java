package com.yjy.builder;

public class Client {
    public static void main(String[] args) {
        //无参构造，对象的表示
        HighBuilding highBuilding = new HighBuilding();
        //由director去构造对象
        HouseDirector houseDirector = new HouseDirector(highBuilding);
        houseDirector.constructHouse();
    }

}
