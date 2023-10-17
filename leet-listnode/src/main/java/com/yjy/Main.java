package com.yjy;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    public static class ListNode {
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
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0,head);//还是得有dummy，要不不好返回
        ListNode cur = head;
        for(int i=1;i<left-1;i++){
            cur = cur.next;
        }
        ListNode begin = cur;//begin为left的前一个点
        for(int i=0;i<=right-left;i++){
            cur = cur.next;
        }
        ListNode behind = cur.next;//behind为right的后一个点
        cur.next = null;
        ListNode node = reverseList(begin.next);
        begin.next.next = behind;
        begin.next = node;
        return dummy.next;
    }
        public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}