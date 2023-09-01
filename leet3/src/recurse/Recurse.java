package recurse;

import other.Other;

import java.util.HashMap;
import java.util.Map;

public class Recurse {
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
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    //没想到递归，想的双指针
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //终止条件：当两个链表都为空时，表示我们对链表已合并完成。
        //如何递归：我们判断 l1 和 l2 头结点哪个更小，然后较小结点的 next 指针指向其余结点的合并结果。（调用递归）
        if(list1==null) return list2;
        else if(list2==null) return list1;
        else if(list1.val<list2.val){
            list1.next = mergeTwoLists(list1.next,list2);
            return list1;
        }
        else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
    public ListNode deleteNode(ListNode head, int val){
        //终止条件为找到=val的节点，然后放回该节点的下一个节点
        if(head.val==val) return head.next;
        head.next = deleteNode(head.next,val);
        return head;
    }
    public ListNode deleteDuplicates(ListNode head) {
//        递归最基本的是要明白递归函数的定义！
        //区分递归终止条件与输入判定
        if(head==null||head.next==null) return head; //头节点或者只有一个节点
//        调用函数，确定您传入的这个更短的链表的头节点的下一个节点应该是什么
//        以12223为例，传入2223后，需确定2a的下一个是什么，为此传入223
        //为确定2b的下一个是什么，为此传入23
        //为确定2c的下一个，传入3，返回3得到2c3
        //然后得到2b2c3，传入if判断得到2c3作为223的返回结果
        //因此2a的后面为2c3
        //判断后得2223的返回为2c3
        //确定1的后面是2c3，最终得到123
        head.next = deleteDuplicates(head.next);
        if(head.val==head.next.val) return head.next;
        else return head;
    }
    public ListNode reverseList(ListNode head) {
        if(head==null|| head.next==null) return head;
        //每次传入一个更短的链表,p为最终的头节点
        ListNode p = reverseList(head.next);
        //
        head.next.next=head;
        head.next=null; //这句别忘
        return p;
    }
    public boolean isSymmetric(TreeNode root) {
        return check(root.left, root.right);
    }
    public boolean check(TreeNode l,TreeNode r){
        if(l==null&&r==null) return true;
        if(l==null||r==null||r.val!=l.val) return false;
        return check(l.left,r.right)&&check(l.right,r.left);
    }
    public int maxDepth(TreeNode root) {
//        二叉树最大深度等于左右子树最大深度加1
        if(root==null) return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }
    //从上往下的递归，时间复杂度n^2
//    public boolean isBalanced(TreeNode root) {
//        int l = height(root.left);
//        int r = height(root.right);
//        return Math.abs(l-r)<=1&&isBalanced(root.left)&&isBalanced(root.right);
//    }
//    public int height(TreeNode root) {
//        if(root ==null) return 0;
//        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
//    }
    public boolean isBalanced(TreeNode root){
        return height(root)>=0;
    }
    public int height(TreeNode root) {
        //下到上的递归，时间哦（n），指从叶子开看是否平衡
        if(root==null) return 0;
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        if(Math.abs(rightHeight-leftHeight)>1||leftHeight==-1||rightHeight==-1) return -1;
        return Math.max(leftHeight,rightHeight)+1;
    }
//    public TreeNode invertTree(TreeNode root) {
//        //从上倒下(更好理解),相当于深度优先
//        if(root==null) return null;
//        TreeNode t = root.right;
//        root.right = root.left;
//        root.left = t;
//        invertTree(root.left);
//        invertTree(root.right);
//        return root;
//    }
    public TreeNode invertTree(TreeNode root) {
//        从下道上，相当于深度优先
        if(root==null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.right = left;
        right.left = right;
        //root是子树的根节点，到最后才是根节点
        return root;
    }




}
