package other;

import java.util.*;

public class Other {
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
    public int missingNumber(int[] nums) {
        int n = nums.length;
        boolean[] b = new boolean[n+1];
        for (int i  =0;i<n;i++){
            //不是f[i]  false是0，true是1
            b[nums[i]]  = true;
        }
        for (int i=0;i<n;i++){
            //true和false用！
            if(!b[i]) return i;
        }
        return n;
    }
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //一开始prev和prehead是连个指针，指向一个相同的哑结点，后来哑结点指向头节点，然后不断移动prev的指向
        //第一次循环中使prev和prevhead指向头节点，之后迭代prev，改变其指向
        ListNode prevHead = new ListNode(-1); // prehead用于保存最开始的prev地址，用于在迭代后找到链表的头节点
        ListNode prev = prevHead;  //prev用于迭代
        while (list1!=null&&list2!=null){
            if(list1.val<list2.val){
                prev.next = list1;
                list1= list1.next;
            }else {
                prev.next = list2;
                list2= list2.next;
            }
            prev = prev.next;
        }
        if(list1!=null) prev.next = list1;
        if(list2!=null) prev.next = list2;
        return prevHead.next;
    }
    public ListNode deleteNode(ListNode head, int val){
        //尾节点需要删除是否存在bug？不存在
        if(head.val==val) return head.next;
        ListNode cur = head;
        while (cur.next!=null&&cur.next.val!=val){
            if(cur.next.val==val){
                cur.next = cur.next.next;
            }
            else {
            cur = cur.next;
            }
        }
        return head;
    }
    public ListNode deleteDuplicates1(ListNode head) {
        if(head==null) return head;
        //考虑有多个连续重复，使用了双指针
        ListNode slow=head,fast=head.next;
        while(fast!=null) {
            if (slow.val != fast.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        //尾指针指向空
        slow.next = fast;
        return head;
    }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while(cur.next!=null){
            if(cur.val ==cur.next.val) cur.next=cur.next.next;
            else cur = cur.next;
        }
        return head;
    }
    public ListNode detectCycle(ListNode head) {
        ListNode pos = head;
        Set<ListNode> visited = new HashSet<>();
        while (pos!=null){
            if(visited.contains(pos)) return pos;
            visited.add(pos);
            pos= pos.next;
        }
        return null;
    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pa = headA,pb= headB;
        while (pa!=pb){
            pa = pa==null? headB : pa.next;
            pb = pb==null? headA :pb.next;
        }
        return pa;
    }
    public ListNode reverseList(ListNode head) {
        //不能像递归一样写，会找不到后面的节点，递归是因为用了栈储存
        ListNode cur =head ,prev =null;
        while (cur!=null){
            ListNode p =cur.next;
            cur.next = prev;
            prev = cur;
            cur = p;
        }
        return prev;
    }
    class MyQueue {
        Stack<Integer> inStack; //定义两个栈
        Stack<Integer> outStack;
        public MyQueue() {
            inStack = new Stack<>();//在构造函数中初始化两个栈
            outStack = new Stack<>();
        }
        public void push(int x) {
            inStack.push(x);
        }

        public int pop() {
            if(outStack.isEmpty()){
                while (!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }
            return outStack.pop();
        }
        //pop与peak的区别在于peak不把拿出来的元素删除
        public int peek() {
            if(outStack.isEmpty()){
                while (!inStack.isEmpty()){
                    outStack.push(inStack.pop());
                }
            }
            return outStack.peek();
        }

        public boolean empty() {
            return inStack.isEmpty()&& outStack.isEmpty();
        }
    }
    public List<Integer> preorder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root!=null||stack.isEmpty()){
            while(root!=null){
                res.add(root.val);
                stack.push(root);
                root =root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }
    public List<Integer> postorder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev=null;
        while (!stack.isEmpty()||root!=null){
            while (root!=null){
            stack.push(root);
            root =root.left;
            }
            root = stack.pop();
            if(root.right==null||root.right == prev){
                res.add(root.val);
                prev = root;
                root = null;
            }
            else {
                stack.push(root);//把根节点放回栈中（因为左右根），再看右节点
                root = root.right;
            }
        }
        return res;
    }
    public boolean isSymmetric(TreeNode root) {
//        Deque表示双端队列,支持从两端插入和删除元素。它继承自Queue
        Queue<TreeNode> q = new LinkedList<>();
        if(root==null) return true;
        q.add(root.left);
        q.add(root.right);
        while (!q.isEmpty()){
            TreeNode l = q.poll();
            TreeNode r = q.poll();
            if(l==null&&r==null) continue;
            if(l==null||r==null||l.val!=r.val) return false;
            q.add(l.left);
            q.add(r.right);
            q.add(l.right);
            q.add(r.left);
        }
        return true;
    }
    public int maxDepth(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        if(root ==null) return 0;
        q.add(root);
        int ans =0;
        while (!q.isEmpty()){
            int n = q.size();
            for(int i=0;i<n;i++){
                TreeNode node = q.poll();
                if(node.left!=null) q.add(node.left);
                if(node.right!=null) q.add(node.right);
            }
            ans++;
        }
        return ans;
    }
     public TreeNode invertTree(TreeNode root) {
        //迭代相当于广度优先搜索，借用队列，一次完成一层
        Queue<TreeNode> q = new LinkedList<>();
        if(root==null) return null;
        TreeNode res = root;
        q.add(root);
        while (!q.isEmpty()){
            //把循环体里的root改为cur更好
            root  = q.poll();
            TreeNode t = root.right;
            root.right = root.left;
            root.left = t;
            if(root.left!=null) q.add(root.left);
            if(root.right!=null) q.add(root.right);
        }
        return res;
     }
//    对于链表问题，返回结果为头结点时，通常需要先初始化一个预先指针 pre，
//    该指针的下一个节点指向真正的头结点 head。
//    使用预先指针的目的在于链表初始化时无可用节点值，而且链表构造过程需要指针移动，进而会导致头指针丢失，无法返回结果。
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry =0;
        while (l1!=null||l2!=null){
            int x = l1==null? 0:l2.val;
            int y = l2==null? 0:l1.val;
            int sum = x+y+carry;
            carry = sum/10;
            sum = sum%10;
            cur.next = new ListNode(sum);
            cur = cur.next;
            if(l1!=null) l1= l1.next;
            if(l2!=null) l2 = l2.next;
        }
        if(carry!=0) cur.next=new ListNode(carry);
        return pre.next;
    }
    //中心扩散法存在很多重复计算，所以使用动态规划，空间换时间
    //还可以暴力解法，枚举所有字串，然后传入判断函数
    public String longestPalindrome(String s) {
        int len =s.length();
        int right=0,left=0;
        for(int i=0;i<len;i++){
            int len1 = expand(s,i,i);
            int len2 = expand(s,i,i+1);
            int sublen = Math.max(len1,len2);
            if(sublen>right-left){//考虑奇偶可以清晰的看出start和end
                left = i-(sublen-1)/2;
                right = i+sublen/2;
            }
        }
        return s.substring(left,right+1);
    }
    public int expand(String s,int start,int end){
        while (start>=0&&end<s.length()&&s.charAt(start)==s.charAt(end)){
            start--;
            end++;
        }
        return end-start-1; //如果不进入循环返回-1或零，进入则返回end-start-2+1
    }



}
