package com.yjy.strategy;

import com.yjy.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SMSMessageNotifier implements MessageNotifier {
    @Override
    public boolean support(int notifyType) {
        return notifyType == 0;
    }

    @Override
    public void notify(User user, String content) {
        //调用短信通知的api发送短信
    }
}
