package com.yjy.respChain;

import com.yjy.entity.ApprovalContext;

//组长审批实现
public class GroupLeaderApprovalHandler extends ApprovalHandler {
    @Override
    public void approval(ApprovalContext approvalContext) {
        System.out.println("组长审批");
        //调用下一个处理对象进行处理
        invokeNext(approvalContext);
    }
}
