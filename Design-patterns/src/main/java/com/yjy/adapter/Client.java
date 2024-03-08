package com.yjy.adapter;

public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        //所需参数必须是Ivoltage5,但是现在的类只有一个20，需要通过adapt将20转换为5
        phone.charging(new VoltageAdapter());
    }
}
