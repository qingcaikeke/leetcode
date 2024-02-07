package com.yjy.respChain;

import com.yjy.entity.ApprovalContext;

//hr审批实现
public class HrApprovalHandler extends ApprovalHandler {
    @Override
    public void approval(ApprovalContext approvalContext) {
        System.out.println("hr审批");
        //调用下一个处理对象进行处理
        invokeNext(approvalContext);
    }
}
