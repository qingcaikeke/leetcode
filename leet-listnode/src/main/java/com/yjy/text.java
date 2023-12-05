package com.yjy;

import java.beans.beancontext.BeanContextServicesListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class text {


   public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    public ListNode reverseKGroup(ListNode head, int k) {
       ListNode dummy = new ListNode(0,head);
       ListNode pre = dummy;
       ListNode end = pre.next;
       while (end!=null){
            for(int i=1;i<k&& end!=null ;i++){
                end = end.next;
            }
            ListNode temp = end.next;
            end.next = null;
            ListNode start = pre.next;
            reverse(start);//会返回end（新的头节点）
            pre.next = end;
            start.next = temp;
            pre = start;
            end = pre.next;
        }
       return dummy.next;
    }
    public ListNode reverse(ListNode head){
       if(head.next==null) return head;
       ListNode newHead = reverse(head.next);
       head.next.next = head;
       return newHead;
    }



}
