package doublePointer;

import java.util.ArrayList;

public class DoublePointer {
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
    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
    public void merge(int[] A, int m, int[] B, int n) {
        //没想到可以直接整合AB啊，再快排
        //双指针定义pa，pb而非i，j
        int pa = m-1, pb = n-1,p = m+n-1;
        while (pa>=0||pb>=0){
            if(pa<0){
                A[p--] = B[pb--];
            }
            else if(pb < 0){
                A[p--] = A[pa--];
            }
            else if(A[pa]>B[pb]) A[p--] = A[pa--];
            else A[p--] = B[pb--];
        }
    }
    public void moveZeroes(int[] nums) {
        int slow  = 0,fast =0;
//        while(fast< nums.length),fast一直步长为一自增，用for更好
        for(int i=0;i< nums.length;i++){
            if(nums[i] != 0) nums[slow++] = nums[i];
        }
        for (int i =slow;i< nums.length;i++){
            nums[i] = 0;
        }
    }
    public void moveZeroes1(int[] nums) {
        int index = 0;//遍历指示器
        for(int i=0;i< nums.length;i++){
            if(nums[i]!=0) {
                swap(nums,i,index);
                index++;
            }
        }
    }
    public boolean hasCycle(ListNode head) {
        if(head==null) return false;
        ListNode slow = head ,fast =head.next;
        while (slow!=fast){
            if(fast==null||fast.next==null) return false;
            slow =slow.next;
            fast =fast.next.next;
        }
            return true;
//        ListNode slow=head,fast=head;
//        while (fast!=null){
//            slow = slow.next;
//            if(fast.next == null) return false;
//            else fast= fast.next.next;
//            if(fast==slow) return true;
//        }
//        return false;
    }
    public ListNode detectCycle(ListNode head) {
//            假设第一次相遇时，环外距离a，在环b处相遇，剩余c
//        快慢相遇时慢指针一定未走完一圈，因为慢指针到入环点时，快慢指针的距离小于环长，快指针一定能在时间内追上差值
//            a+b+(n)(b+c) = 2*(a+b) （n-1）(b+c)+c = a  b+c为一圈，从相遇点开始，找一个指针从头节点出发，他们相遇的位置就是入环点
        ListNode slow=head,fast =head;
        while (fast!=null){
            slow =slow.next;
            if(fast.next!=null) {
                fast = fast.next.next;
            }
            else return null;
            if(fast==slow){
                ListNode p =head;
                while (p!=slow){
                    slow = slow.next;
                    p = p.next;
                }
                return p;
            }
        }
        return null;
    }
    public boolean isPalindrome(ListNode head) {
        ArrayList<Integer> vals = new ArrayList<>();
        ListNode cur =head;
        while (cur!=null){
            vals.add(cur.val);
            cur = cur.next;
        }
        int left = 0,right = vals.size()-1;
        while (left<right){
            if(vals.get(left)!= vals.get(right)) return false;
            else {
                left++;
                right--;
            }
        }
        return true;
    }
    public boolean isPalindrome1(ListNode head){
        ListNode fast = head,slow = head;
    //用slow找到链表中间
        while (fast!=null&&fast.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
    //    将链表后半部分反转
        ListNode curr = slow ,prev =null;
        while (curr!=null){
            ListNode p= curr.next;
            curr.next  = prev;
            prev = curr;
            curr = p;
        }
        while(prev!=null){
            if(prev.val!=head.val) return false;
            prev = prev.next;
            head = head.next;
        }
        return true;
    }
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode former = head,latter =head;
        while(k!=0){
            former = former.next;
            k--;
        }
        while (former!=null){
            former = former.next;
            latter = latter.next;
        }
        return latter;
    }


}
