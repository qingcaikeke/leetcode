package recurse;

import other.Other;

import javax.swing.*;
import java.time.Period;
import java.util.*;

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
//    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
//        //终止条件：当两个链表都为空时，表示我们对链表已合并完成。
//        //如何递归：我们判断 l1 和 l2 头结点哪个更小，然后较小结点的 next 指针指向其余结点的合并结果。（调用递归）
//        if(list1==null) return list2;
//        else if(list2==null) return list1;
//        else if(list1.val<list2.val){
//            list1.next = mergeTwoLists(list1.next,list2);
//            return list1;
//        }
//        else {
//            list2.next = mergeTwoLists(list1, list2.next);
//            return list2;
//        }
//    }
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //递归还是写不好
            if(list1 == null) return list2;
            else if (list2 ==null) return list1;
            else if(list1.val < list2.val) {
                list1.next = mergeTwoLists(list1.next,list2);
                return list1;
            }
            else{
                list2.next = mergeTwoLists(list1,list2.next);
                return list2;
            }
    }
    public ListNode swapPairs0(ListNode head) {
        if(head==null||head.next==null) return head;
        ListNode temp = head.next.next;
        ListNode newhead = head.next;
        newhead.next = head;
        head.next = swapPairs0(temp);
        return newhead;
    }
    public ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode temp = head.next.next;
        ListNode newhead = head.next;
        head.next.next = head;
        head.next = swapPairs1(temp);
        return newhead;
        }
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next!= null&&cur.next.next!=null){
           ListNode front = cur.next;
           ListNode end = cur.next.next;

           front.next = end.next;
           cur.next = end;
           end.next = front;
           cur = front;
        }
        return dummy.next;
    }
    ListNode successor = null;
    public ListNode reverseNRecursive(ListNode head, int n) {
        if(n==1){
            successor = head.next;//successor 后继节点
            return head;
        }
        ListNode newHead = reverseNRecursive(head.next,n-1);
        head.next.next = head;
        head.next = successor;
        return newHead;
    }
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0,head);
        ListNode pre = dummy;
        for(int i=0;i<left-1;i++){
            pre = pre.next;
        }//此时pre在left的前一个节点
        ListNode leftNode = pre.next;
        ListNode rightNode = pre;
        for(int i=left-1;i<right;i++){
            rightNode = rightNode.next;
        }
        ListNode succ = rightNode.next;
        pre.next = null;
        rightNode.next = null;
        ListNode newHead = reverseLinkedList(leftNode);
        pre.next = newHead;
        leftNode.next = succ;
        return dummy.next;
    }
    public ListNode reverseLinkedList(ListNode head){
        if(head.next ==null) return head;
        ListNode newHead= reverseLinkedList(head.next);
        head.next.next  = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseBetween1(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }//此时pre在left的前一个节点，此后pre永远是整个翻转部分的前一个节点
        //头插法
        ListNode cur = pre.next;//
        for(int i=0 ;i<right-left;i++){
            ListNode next = cur.next;//目标是把next头插到pre后面
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }
    public boolean isValidBST(TreeNode root) {
        //,函数名相同但参数不同的两个方法之间称为方法重载(Method Overloading)
        return isValidBST(root, Long.MIN_VALUE , Long.MAX_VALUE);
    }
    public boolean isValidBST(TreeNode root, long lower,long upper){
        if(root==null) return true;
        if(root.val<=lower || root.val>=upper) return false;
        return isValidBST(root.left,lower, root.val) && isValidBST(root.right, root.val,upper);
    }
    public boolean isValidBSTc(TreeNode root) {
        //二叉搜索树按中序遍历一定严格递增
        Stack<TreeNode> stack = new Stack<>();
        long cmp = Long.MIN_VALUE;
        while (root!=null || !stack.isEmpty()){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(root.val <= cmp) return false;
            cmp = root.val;
            root = root.right;
        }
        return true;
    }
    public List<TreeNode> generateTrees(int n) {
        if(n==0) return new ArrayList<>();
        return generateTrees(1,n);
    }
    public List<TreeNode> generateTrees(int start,int end){
        List<TreeNode> trees = new ArrayList<>();
        if(start>end){
            trees.add(null);
            return trees;
            //这段代码是在trees结果列表中添加一个null节点,然后返回整个trees列表。
//            而return null;这段代码直接返回null,不会向trees中添加节点。
        }
        for(int i=start;i<=end;i++){//start 到end右多少种成树方法，分别以i为根节点
            List<TreeNode> leftTrees = generateTrees(start,i-1);//是i-1，因为可能左面为空，全在右面
            List<TreeNode> rightTrees = generateTrees(i+1,end);
            for(TreeNode left:leftTrees){
                for(TreeNode right :rightTrees){
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    trees.add(currTree);
                }
            }
        }
        return trees;
    }

    public void recoverTreed(TreeNode root) {
        List<Integer> nums = new ArrayList<>();
        inOrder(root,nums);
        int index1 =-1,index2 =-1;
        for(int i=0;i< nums.size()-1;i++){//要么是i和i+1，要么是i和j
            if(nums.get(i+1)<nums.get(i)){
                index2 = i+1;
                if(index1==-1) index1 = i;
                else break;
            }
        }
        int  x = nums.get(index1),y = nums.get(index2);
        recover(root,x,y);
    }
    public void recover(TreeNode root,int x,int y){
        if(root==null) return;
        if(root.val==x||root.val==y) root.val= root.val==x? y:x;
        recover(root.left,x,y);
        recover(root.right,x,y);
    }
    public void inOrder(TreeNode root,List<Integer> nums){
        if(root==null) return;
        inOrder(root.left,nums);
        nums.add(root.val);
        inOrder(root.right,nums);
    }

    public void recoverTree(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode x = null;TreeNode y = null;
        TreeNode pre = null;//这三个变量不能放在循环体里创建，x和y外面要用，pre每次循环不能被充置为null
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            //后值应该比前值大
            if (pre != null && root.val < pre.val) {
                y = root;
                if (x == null) x = pre;
                else break;
            }
            pre = root;
            root = root.right;
        }
        int temp = x.val;
        x.val = y.val;
        y.val = x.val;
    }
/*1.    中序遍历morris，省略了栈的空间
2.    没左子树则直接访问,打印值
3. 有左子树则找到最右节点
4. 最右节点right指针判定是否访问过*/
    public void morrisInOrder(TreeNode root){
//        List<Integer> res = new ArrayList<>();
        TreeNode pre = null;
        TreeNode x =null,y =null;
        while (root!=null){
            if(root.left==null) {//左为空，打根值，根到右
//                res.add(root.val);
                if(pre!=null&& pre.val > root.val){
                    y = root;
                    if(x==null) x = pre;
                }
                pre = root;
                root = root.right;//总忘  这个时候root。right不是null了，他指回到根节点
            }
            else {
                TreeNode predecessor = root.left;
                while (predecessor.right!=null&&predecessor.right!=root) predecessor = predecessor.right;
                if(predecessor.right==null){
                    predecessor.right = root;
                    root = root.left;
                }
                else {
                    predecessor.right = null;
//                    res.add(root.val);
                    if(pre!=null && pre.val>root.val){
                        y = root;
                        if(x==null) x = pre;
                    }
                    pre = root;
                    root = root.right; //总忘
                }
            }
        }
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer,Integer> map = new HashMap<>();//map用于快速定位root在中序遍历中的位置
        for (int i = 0; i < n; i++) {
            map.put(inorder[i],i);
        }
        return myBuildTree1(map,preorder,inorder,0,n-1,0,n-1);
    }
    public TreeNode myBuildTree1(Map<Integer,Integer> map,int[] preorder,int[] inorder,
                                int preorderLeft,int preorderRight ,int inorderLeft,int inorderRight){
        if(preorderLeft>preorderRight) return null; //叶节点的左右节点为null
        //前序[ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
        //[ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
        TreeNode root = new TreeNode(preorder[preorderLeft]);
        int inorderRootIndex = map.get(root.val);
        int leftLen = inorderRootIndex-inorderLeft;
        root.left = myBuildTree(map,preorder,inorder,
                preorderLeft+1, preorderLeft+leftLen,inorderLeft,inorderRootIndex-1);
        root.right = myBuildTree(map,preorder,inorder,
                preorderLeft+leftLen+1,preorderRight,inorderRootIndex+1,inorderRight);
        return root;
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n = inorder.length;
        Map<Integer,Integer> map = new HashMap<>();//map用于快速定位root在中序遍历中的位置
        for (int i = 0; i < n; i++) {
            map.put(inorder[i],i);
        }
        return myBuildTree(map,inorder,postorder,0,n-1,0,n-1);
    }
    public TreeNode myBuildTree(Map<Integer,Integer> map,int[] inorder, int[] postorder,
                                int inorderLeft,int inorderRight, int postOrderLeft,int postOrderRight){
        if(inorderLeft>inorderRight) return null;
        int inorderRootIndex = map.get(postorder[postOrderRight]);
        int leftLen = inorderRootIndex - inorderLeft;
        TreeNode root = new TreeNode(postorder[postOrderRight]);
        root.left = myBuildTree(map,inorder,postorder,
                inorderLeft,inorderRootIndex-1,postOrderLeft,postOrderLeft+leftLen-1);
        root.right = myBuildTree(map,inorder,postorder,
                inorderRootIndex+1,inorderRight,postOrderLeft+leftLen,postOrderRight-1);
        return root;
    }
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper1(nums,0,nums.length-1);
    }
    public TreeNode helper1(int[] nums,int left ,int right){
        if(right<left) return null;
        int mid = (left+right)/2;//奇数选中间，偶数选中间左
        TreeNode root = new TreeNode(nums[mid]);
        root.left = helper1(nums,left,mid-1);
        root.right = helper1(nums,mid+1,right);
        return root;
    }
    class sortedListToBST{
        public TreeNode sortedListToBST(ListNode head) {
            return helper(head,null);
        }
        public TreeNode helper(ListNode left,ListNode right){
            if(left==right) return null;
            ListNode mid = getMid(left,right);
            TreeNode root = new TreeNode(mid.val);
            root.left = helper(left,mid);//没法找mid上一个节点，所以使用左闭右开
            root.right = helper(mid.next,right);
            return root;
        }
        public ListNode getMid(ListNode left,ListNode right){
            ListNode slow = left,fast = left;
            while (fast!=right&&fast.next!=right){
                slow =slow.next;
                fast = fast.next.next;
            }
            return slow;//每次要中间左面的节点
        }
    }
    class sortedListToBST2{
        ListNode cur;//每个参数都是递归函数的一个局部变量,cur回上一层的时候还是原来的值，而非填入左节点移动后的值
        //所以cur只能是全局变量，不能作为递归参数
        public TreeNode sortedListToBST(ListNode head) {
            int len =0;
            while (head!=null){
                head = head.next;
                len++;
            }
            cur = head;
            return helper(0,len-1);
        }
        public TreeNode helper(int left,int right){
            if(left>right) return null;
            int mid = (left+right)/2;
            //先生成这个节点，然后去生成左子树，左子树的值都填完了，cur指向的自然就是根节点的值，
            // 将这个值填入生成的节点，再去生成右子树
            TreeNode root = new TreeNode();
            root.left = helper(left,mid-1);
            root.val = cur.val;
            cur = cur.next;
            root.right = helper(mid+1,right);
            return root;
        }
        public void flatten1(TreeNode root) {
            while (root!=null){
                if(root.left==null){
                    root = root.right;
                }
                else {
                    TreeNode predecessor = root.left;
                    while (predecessor.right!=null) predecessor = predecessor.right;
                    predecessor.right = root.right;
                    root.right = root.left;
                    root.left = null;
                    root = root.right;
                }
            }
            return;
        }
        TreeNode pre = null;
        public void flatten2(TreeNode root) {
            if(root==null) return;//想要按先序根左右的顺序，将上一个节点的右指针更新为本节点
            //但这样会丢失根节点的右节点，所以想到反过来，右左根，然后将下一个节点的右指针更新为本节点
            //但因为是递归，所以需要一个全局变量pre，按递归顺序向前移动
            flatten2(root.right);
            flatten2(root.left);
//            System.out.println(root.val);
            root.right = pre;
            root.left = null;
            pre = root;
        }
        public void flatten(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()){
                TreeNode temp = stack.pop();
                if(pre!=null){
                    pre.right = temp;
                    pre.left = null;
                }
                if(temp.right!=null) stack.push(temp.right);
                if(temp.left!=null) stack.push(temp.left);
                pre = temp;
            }
        }
        class Node {
            public int val;
            public Node left;
            public Node right;
            public Node next;

            public Node() {}

            public Node(int _val) {
                val = _val;
            }

            public Node(int _val, Node _left, Node _right, Node _next) {
                val = _val;
                left = _left;
                right = _right;
                next = _next;
            }
        };
        public Node connect1(Node root) {
            if(root==null) return root;
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()){
                int size = queue.size();
                for (int i=0;i<size;i++){//不用看最后一个，他的next默认置空
                    Node node = queue.poll();
                    if(i!=size-1) node.next = queue.peek();
                    if(node.left!=null) queue.offer(node.left);
                    if(node.right!=null) queue.offer(node.right);
                }
            }
            return root;
        }
        public Node connect2(Node root) {
            Node leftmost = root;
            while (leftmost.left!=null) {//由第i层推i+1层，所以只需要到倒数第二层
                Node node = leftmost;
                while (node!=null){
                    node.left.next = node.right;
                    if(node.next!=null) node.right.next = node.next.left;
                    node = node.next;
                }
                leftmost = leftmost.left;
            }
            return root;
        }
        public Node connect(Node root) {
            if(root==null||root.left==null) return root;
            root.left.next = root.right;
            if(root.next!=null)root.right.next = root.next.left;
            connect(root.left);
            connect(root.right);
            return root;
        }
        int res=0;//要么类变量累计最终的结果，因为递归会把sum回退回去
        //要么将sum作为递归的返回值，计算到叶的sum一直会返回到根上
        //要么回溯，回溯少了temp参数，将temp定义为全局变量，需要回溯时，temp减去root。val再除以十
        public int sumNumbers(TreeNode root) {
            dfs1(root,0);
            return res;
        }
        public void dfs1(TreeNode root,int temp){
            if(root==null) return; //不是叶节点，而是某个只有左子树或者右子树的节点
            temp = temp*10+root.val;
            if(root.left==null&& root.right==null) {
                res+=temp;
                return;
            }
            dfs1(root.left,temp);
            dfs1(root.right,temp);
        }
        public int dfs(TreeNode root,int sum){
            if(root.val==0) return 0;
            sum = sum*10+ root.val;
            if(root.left==null&&root.right==null) return sum;
            return dfs(root.left,sum)+dfs(root.right,sum);
        }
        public int minimumTotal(List<List<Integer>> triangle) {
            int n = triangle.size();
            int[][] dp = new int[n][];//三角形第i行有i+1个元素，下标时0，i闭区间
            for(int i=0;i<n;i++){
                dp[i] = new int[i+1];
            }
            dp[0][0] = triangle.get(0).get(0);
            for(int i=1;i<n;i++){
                dp[i][0] = dp[i-1][0]+triangle.get(i).get(0);
                for(int j=1;j<i;j++){
                    dp[i][j] = Math.min(dp[i-1][j-1],dp[i-1][j])+triangle.get(i).get(j);
                }
                dp[i][i] = dp[i-1][i-1]+triangle.get(i).get(i);
            }
            int min = dp[n-1][0];
            for(int i=0;i<n;i++){
                min = Math.min(min,dp[n-1][i]);
            }
            return min;
        }




















    }






}
