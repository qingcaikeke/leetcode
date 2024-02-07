package com.yjy.strategy;

import com.yjy.entity.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * 策略模式，原来是if-else
 */
public class Client {
    @Resource
    private List<MessageNotifier> messageNotifiers;

    /**
     * 根据不同的平台类型进行判断，调用对应的api发送消息
     *
     * 如果现在需要支持通过邮件通知，只需要实现MessageNotifier接口，
     * 注入到Spring容器就行，其余的代码根本不需要有任何变动
     */
    public void notifyMessage(User user, String content, int notifyType) {
        for (MessageNotifier messageNotifier : messageNotifiers) {
            if (messageNotifier.support(notifyType)) {
                messageNotifier.notify(user, content);
            }
        }
    }
}
