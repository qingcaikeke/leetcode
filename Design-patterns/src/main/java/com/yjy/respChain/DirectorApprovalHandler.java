package com.yjy.respChain;

import com.yjy.entity.ApprovalContext;

//主管审批实现
public class DirectorApprovalHandler extends ApprovalHandler {
    @Override
    public void approval(ApprovalContext approvalContext) {
        System.out.println("主管审批");
        //调用下一个处理对象进行处理
        invokeNext(approvalContext);
    }
}
