package com.yjy.respChain;

import com.yjy.entity.ApprovalContext;

/**
 * 责任链模式里，很多对象由每一个对象对其下家的引用而连接起来形成一条链。
 * 请求在这个链上传递，由该链上的某一个对象或者某几个对象决定处理此请求，每个对象在整个处理过程中值扮演一个小小的角色。
 *
 * 现在有个请假的审批流程，根据请假的人的级别审批到的领导不同，比如有有组长、主管、HR、分管经理等等
 */

public abstract class ApprovalHandler {

    /**
     * 责任链中的下一个处理对象
     */
    protected ApprovalHandler next;

    /**
     * 设置下一个处理对象
     *
     * @param approvalHandler
     */
    public void nextHandler(ApprovalHandler approvalHandler) {
        this.next = approvalHandler;
    }

    /**
     * 处理
     *
     * @param approvalContext
     */
    public abstract void approval(ApprovalContext approvalContext);

    /**
     * 调用下一个处理对象
     *
     * @param approvalContext
     */
    protected void invokeNext(ApprovalContext approvalContext) {
        if (next != null) {
            next.approval(approvalContext);
        }
    }

}
