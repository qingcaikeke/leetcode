package com.yjy.adapter.simple;

// 适配器类
class ChineseAdapter implements Translator {
    private EnglishSpeaker englishSpeaker;

    // 适配器持有被适配对象的引用
    public ChineseAdapter(EnglishSpeaker englishSpeaker) {
        this.englishSpeaker = englishSpeaker;
    }

    // 适配器实现目标接口
    @Override
    public String translate() {
        // 适配器将英文翻译成中文
        return "你好！";
    }
}
