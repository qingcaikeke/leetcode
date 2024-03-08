package com.yjy.adapter.simple;

// 客户端

/**
 * 用c口给苹果充电，需要一个转换器
 * 允许接口不兼容的类可以一起工作。适配器模式的主要目标是将一个类的接口转换成另一个接口
 */
public class Client {
    public static void main(String[] args) {
        // 创建被适配对象
        EnglishSpeaker englishSpeaker = new EnglishSpeaker();

        // 创建适配器，传入被适配对象
        Translator chineseAdapter = new ChineseAdapter(englishSpeaker);

        // 客户端通过目标接口调用适配器
        System.out.println(chineseAdapter.translate());
    }
}
