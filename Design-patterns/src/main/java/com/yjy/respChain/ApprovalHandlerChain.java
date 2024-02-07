package com.yjy.respChain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApprovalHandlerChain {

    @Autowired
    private GroupLeaderApprovalHandler groupLeaderApprovalHandler;
    @Autowired
    private DirectorApprovalHandler directorApprovalHandler;
    @Autowired
    private HrApprovalHandler hrApprovalHandler;

    public ApprovalHandler getChain() {
        //组长处理完下一个处理对象是主管
        groupLeaderApprovalHandler.nextHandler(directorApprovalHandler);
        //主管处理完下一个处理对象是hr
        directorApprovalHandler.nextHandler(hrApprovalHandler);

        //返回组长，这样就从组长开始审批，一条链就完成了
        return groupLeaderApprovalHandler;
    }

}
