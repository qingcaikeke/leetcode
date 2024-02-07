package com.yjy.strategy;

import com.yjy.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AppMessageNotifier implements MessageNotifier {
    @Override
    public boolean support(int notifyType) {
        return notifyType == 1;
    }

    @Override
    public void notify(User user, String content) {
        //调用通知app通知的api
    }
}
