import org.w3c.dom.Node;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 初始化链表1
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);

// 初始化链表2
        ListNode head2 = new ListNode(2);
        head2.next = new ListNode(5);
        ListNode a = deleteDuplicates(head1);
        while (a!=null){
            System.out.println(a.val);
            a=a.next;
        }
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

        public static ListNode deleteDuplicates(ListNode head) {
//        递归最基本的是要明白递归函数的定义！
        //区分递归终止条件与输入判定
        if(head==null||head.next==null) return head; //头节点或者只有一个节点
         if(head.val==head.next.val) return head.next;
        head.next = deleteDuplicates(head.next);

        return head;
    }

}
