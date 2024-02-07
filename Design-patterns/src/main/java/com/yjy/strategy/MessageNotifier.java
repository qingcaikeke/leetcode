package com.yjy.strategy;

import com.yjy.entity.User;

public interface MessageNotifier {
    /**
     * 是否支持改类型的通知的方式
     *
     * @param notifyType 0:短信 1:app
     * @return
     */
    boolean support(int notifyType);

    /**
     * 通知
     * 根据不同的平台类型进行判断，调用对应的api发送消息
     * @param user
     * @param content
     */
    void notify(User user, String content);
}
