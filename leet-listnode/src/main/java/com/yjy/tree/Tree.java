package com.yjy.tree;

import com.yjy.Main;
import com.yjy.practice.Practice;

import javax.lang.model.util.ElementFilter;
import java.util.*;
import java.util.concurrent.BlockingDeque;

public class Tree {
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
    public List<Integer> postOrder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            res.add(node.val);
            if(node.right!=null) stack.push(node.right);
            if(node.left!=null) stack.push(node.left);
        }
        return res;
    }
    public List<Integer> postOrderMorris(TreeNode root){
        List<Integer> res = new ArrayList<>();
        TreeNode cur = root;
        while (cur!=null){
            if(cur.left==null){
                res.add(cur.val);
                cur = cur.right;
                continue;
                //这个continue一定要有，如果直接向下进行的话，缺少了对新cur.left是否等于null的判断，如果是null，求predecessor会npe
            }
            TreeNode predecessor = cur.left;
            while (predecessor.right!=null && predecessor.right!=cur){
                predecessor = predecessor.right;
            }
            if(predecessor.right==null){
                predecessor.right = cur;
                res.add(cur.val);
                cur = cur.left;
            }
            else {
                predecessor.right = null;
                cur = cur.right;
            }
        }
        return res;
    }
    //计算二叉树深度 1.递归（相当于深度优先搜索） 2.广度优先搜索
    public int calculateDepth(TreeNode root) {
        if(root == null) return 0;
        int leftDepth = calculateDepth(root.left);
        int rightDepth = calculateDepth(root.right);
        return Math.max(leftDepth,rightDepth)+1;
    }
    //二叉树的最小深度：没做出来
    //1.直接用求深度的方法，变为min为什么不行，因为比方说一个节点只有右子树，这样求出来的不是他的深度
    //所以第一种思路，如果有一个子树为空，就取max
    //2.广度优先搜索，记录层的深度，根节点为1，找到叶节点时返回
    public int minDepth(TreeNode root) {
        if(root==null) return 0;
        int leftD = minDepth(root.left);
        int rightD = minDepth(root.right);
        if(root.left!=null && root.right!=null)
            return Math.min(leftD,rightD)+1;
        else return Math.max(leftD,rightD)+1;
    }
    //二叉树剪枝，删除所有节点全为0的子树，没做出来
    //后续遍历，因为要先处理左右子树，才能确定根要不要删除，删除一个节点：root.left=null
    public TreeNode pruneTree(TreeNode root) {
        if(root==null) return null;
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        //这一行不能挪到撒谎给你面，必须把子树处理完，进行了连接才能判断这棵树要不要删除，或者可以按后续遍历理解
        if(root.left==null&& root.right==null&& root.val==0) return null;
        return root;
    }
    //二叉树的序列化与反序列化：如何保存二叉树的结构，使用null
    //遍历二叉树（先序），因为先序反序列化容易
    //如果是层序，反序列化过程，需要借助一个队列，因为拼完一层的左右节点还需要回左节点去拼下一层
    public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if(root==null) return "#";
            String leftString = serialize(root.left);
            String rightString = serialize(root.right);
            //每层递归做的都是同样的事，以根左右的顺序把字符串拼接起来
            return String.valueOf(root.val)+","+leftString+","+rightString;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] strings = data.split(",");
            return dfs(strings,new int[]{0});
        }
        public TreeNode dfs(String[] strings,int[] i){
            if(strings[i[0]].equals("#")) {
                i[0]++;
                return null;
            }
            //不能用int，必须用数组或者list，因为数组传递的是引用，对下层做的改变递归回来，上层也会发生变化
            TreeNode node = new TreeNode(Integer.valueOf(strings[i[0]]));
            i[0]++;
            node.left = dfs(strings,i);
            node.right = dfs(strings,i);
            return node;
        }
    }
    public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            String treeString = "";
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                if(node!=null){
                    treeString += String.valueOf(node.val)+",";
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
                else {
                    treeString = treeString + "#"+",";
                }
            }
            return treeString;
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String[] strings = data.split(",");
            Queue<TreeNode> queue = new LinkedList<>();
            if(strings[0].equals("#")) return null;
            TreeNode root = new TreeNode(Integer.valueOf(strings[0]));
            queue.add(root);
            int index = 1;
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                if(!strings[index].equals("#")){
                    TreeNode newNode = new TreeNode(Integer.valueOf(strings[index]));
                    node.left = newNode;
                    queue.offer(newNode);
                }
                index++;
                if(!strings[index].equals("#")){
                    TreeNode newNode = new TreeNode(Integer.valueOf(strings[index]));
                    node.right = newNode;
                    queue.offer(newNode);
                }
                index++;
            }
            return root;
        }
    }
    //求根到叶数字之和,1.回溯(dfs) ，实际上是前序遍历，一般与路径相关首先想到dfs，
    //难点在于前序后如何反序列化，使用dfs，读到null后连接并返回
    // 2.广度优先搜索，维护两个队列，一个储存节点，一个储存对应的值
    public int sumNumbers(TreeNode root) {
        return getSumNumbers(root,0);
    }
    public int getSumNumbers(TreeNode root,int path) {
        //1.简单但不好理解
        if(root==null) return 0;//可能没有左节点只有右节点
        path = path*10+ root.val;
        if(root.left==null && root.right==null) return path;
        return getSumNumbers(root.left,path)+getSumNumbers(root.right,path);
//        2.引入全局变量sum;
//        if(root.left==null&& root.right ==null){
//            sum += path;
//            return ;
//        }
//        if(root.left!=null) getSumNumbers(root.left,path*10+ root.left.val);
//        if(root.right!=null) getSumNumbers(root.right,path*10+ root.left.val);
    }
    public int sumNumbers1(TreeNode root) {
        if (root==null) return 0;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        nodeQueue.offer(root);
        numQueue.offer(root.val);
        int sum = 0;
        while (!nodeQueue.isEmpty()){
            TreeNode node = nodeQueue.poll();
            int value = numQueue.poll();
            if(node.left==null&&node.right==null){
                sum+=value;
            }
            else {
                if(node.left!=null){
                    nodeQueue.offer(node.left);
                    numQueue.offer(value*10+node.left.val);
                }
                if(node.right!=null){
                   nodeQueue.offer(node.right);
                   numQueue.offer(value*10+node.right.val);
                }
            }
        }
        return sum;
    }
    //向下的路径之和：求有几条路径之和等于固定值，不一定从根节点开始
    //1.分别求从某个节点开始，满足要求的路径数
    //2.计算每个节点的前缀和，看（当前节点的前缀和）和（之前计算过的节点的前缀和）相减能不能等于target；
    //前缀和包含自己，路径不包含被减的那个  相减也要要求，被减的节点必须是当前看的这个节点的祖先节点
    //状态恢复代码的作用：遍历完一个节点所有的子节点，将其从map中除去
    //传入一个节点，返回的是，以他的所有子孙节点结尾凑出的路径总数
    Map<Long,Integer> prefixMap = new HashMap<>();
    public int pathSum(TreeNode root, int targetSum) {
        prefixMap.put(0L,1);
        return count(root,0L,targetSum);
    }
    public int count(TreeNode node,long curNum,int targetSum){
        if(node==null) return 0;
        int res =0;
        curNum += node.val;//这个才是前缀和，传进来的没有加当前节点
        res += prefixMap.getOrDefault(curNum-targetSum,0);//一定要先计算res，后put，因为如果target=0的话，会多一个
        prefixMap.put(curNum,prefixMap.getOrDefault(curNum,0)+1);
        int leftCount = count(node.left,curNum,targetSum);
        int rightCount = count(node.right,curNum,targetSum);
        prefixMap.put(curNum, prefixMap.get(curNum)-1);//状态恢复代码，此时这个节点的前缀和之后不可能再用到，因为之后是在别的路径上
        res = res + leftCount+rightCount;
        return res;
    }
    public int pathSum(TreeNode root, int targetSum) {
        if(root==null) return 0;
        int ret = rootSum(root,targetSum);
        ret+=pathSum(root.left,targetSum);
        ret+=pathSum(root.right,targetSum);
        return ret;
    }
    public int rootSum(TreeNode root,int target){
        int ret=0;
        if(root==null) return 0;
        if(root.val==target) {
            ret++;
        }
        ret+=rootSum(root.left,target-root.val);
        ret+=rootSum(root.right,target-root.val);
        return ret;
    }
    //展平二叉树:相当于先序遍历，1.先序遍历储存在链表里，然后遍历链表
    //2.使用迭代，先右后左的顺序入栈，同时维护一个prev，把弹出的节点连到prev上
    //3.降低空间复杂度，morris，将左子树连接到右节点上后，右节点信息会丢失，所以找左子树的最右节点，让他的右节点指向右子树
    public void flatten1(TreeNode root) {
        if(root==null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode pre = null;
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            if(pre!=null){
                pre.right = node;
                pre.left = null;
            }
            if(node.right!=null) stack.push(node.right);
            if(node.left!=null) stack.push(node.left);
            pre = node;
        }
    }
    public void flatten(TreeNode root){
        TreeNode cur = root;
        while (cur!=null){
            if(cur.left==null){
                cur = cur.right;
                continue;
            }//别忘了
            TreeNode predecessor = cur.left;
            while (predecessor.right!=null){
                predecessor = predecessor.right;
            }
            predecessor.right = cur.right;
            cur.right = cur.left;
            cur.left = null;
            cur = cur.right;
        }
    }
    //二叉树的最大路径和:递归做两件事，1.计算向下的路径之和最大值，小于0取零，
    //2.更新结果sum，要注意区分返回值和求sum
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxPath(root);
        return maxSum;
    }
    public int maxPath(TreeNode root){
        //传入节点，计算向下的路径之和的最大值
        if(root==null) return 0;
        int left = Math.max(maxPath(root.left),0);
        int right = Math.max(maxPath(root.right),0);
        maxSum = Math.max(maxSum,left+right+root.val);
        return root.val+ Math.max(left,right);//也可叫节点的最大贡献值
    }
    //二叉搜索树的下一个节点，找到一个比他大的节点，赋值给res，去他左面，看能不能找到一个仍然比他大的节点，找到了更新res
    // 搜索树查找，比当前小去左子树，大去右子树，复杂度o(h)
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode cur = root;
        TreeNode res = null;
        while (cur!=null){
            if(p.val<cur.val){
                res = cur;
                cur = cur.left;
            }
            else {
                cur = cur.right;
            }
        }
        return res;
    }
    //展平二叉搜索树：中序遍历,区分好中序与前序遍历的迭代形式
    public TreeNode increasingBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode prev = null;
        TreeNode newRoot = null;
        while (cur!=null || !stack.isEmpty()){
            while (cur!=null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if(prev==null){
                newRoot = cur;
            }
            else {
                prev.right = cur;
            }
            prev = cur;
            cur.left = null;//这句不能放在else里，需要保证最后一个元素的left也置成null
            cur = cur.right;
        }
        return newRoot;
    }
    //将二叉搜索树的节点值改为所有大于等于他的节点值之和
    //1.遍历两次二叉树，第一次求和，第二次中序遍历，计算当前节点前面节点的和，然后用总和减去该和
    //2.从大到小遍历一次即可，右根左的顺序
    public TreeNode convertBST1(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        int sum = 0;
        while (!stack.isEmpty() || cur!=null){
            while (cur!=null){
                stack.push(cur);
                cur = cur.right;
            }
            cur = stack.pop();
            sum += cur.val;
            cur.val = sum;
            cur = cur.left;
        }
        return root;
    }
    int sum=0;
    public TreeNode convertBST(TreeNode root){
        if(root==null) return null;
        convertBST(root.right);
        sum+=root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }
    //二叉树搜索树迭代器：1.扁平化，初始化的时候计算所哟结果，存在数组里
    //2.遍历，用栈或morris;
    class BSTIterator1 {
        TreeNode cur;
        Stack<TreeNode> stack;
        public BSTIterator1(TreeNode root) {
            stack = new Stack<>();
            cur = root;
        }

        public int next() {
            while (cur!=null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            int res = cur.val;
            cur = cur.right;
            return res;
        }

        public boolean hasNext() {
            return cur!=null || !stack.isEmpty();
        }
    }
    class BSTIterator {
        TreeNode cur ;
        public BSTIterator(TreeNode root) {
            cur = root;
        }

        public int next() {
            while (cur!=null){
                if(cur.left==null){
                    int res = cur.val;
                    cur = cur.right;
                    return res;
                }
                TreeNode predecessor = cur.left;
                while (predecessor.right!=null&&predecessor.right!=cur){
                    predecessor = predecessor.right;
                }
                if(predecessor.right==null){
                    predecessor.right = cur;
                    cur = cur.left;
                }
                else {
                    int res =  cur.val;
                    predecessor.right = null;
                    cur = cur.right;
                    return res;
                }
            }
            return 0;
        }

        public boolean hasNext() {
            return cur!=null;
        }
    }
    //二叉树锯齿形遍历：1.正常层序遍历，输出到list，奇数层reverse
    //2.每层的结果加入list的时候，偶数层往前加 linkedelist<integer> temp = new linkdelist. temp.addfirst
    //3.双栈，栈1出，子节点入栈2，入先入左节点后又，这样出来的就是从右到左，然后栈2出，从右到左入栈1，栈1出来的就是正序。好
    //4.双端队列，从左到右，向后入队，出队从后出，这就是倒序
        //从右到左，向前入队，从前出队，这就是正序
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        while (!deque.isEmpty()){
            List<Integer> temp = new ArrayList<>();
            int size = deque.size();
            for (int i=0;i<size;i++){
                TreeNode node = deque.pollFirst();
                temp.add(node.val);
                if(node.left!=null)deque.addLast(node.left);
                if(node.right!=null)deque.addLast(node.right);
            }
            if(deque.isEmpty()) break;
            res.add(temp);
            temp = new ArrayList<>();
            size = deque.size();
            for(int i=0;i<size;i++){
                TreeNode node = deque.pollLast();
                temp.add(node.val);
                if(node.right!=null)deque.addFirst(node.right);
                if(node.left!=null)deque.addFirst(node.left);
            }
            res.add(temp);
        }
        return res;

    }
    // 二叉搜索树第k小的元素：1.中序遍历迭代法 2.构造数据结构，每个节点储存自己的子树有几个节点（没必要）

    //前序和中序构建二叉树:两个数组各找到一个指针进行拆分，前序找到左子树的最后一个节点，中序找到根节点
    //如果有重复元素则二叉树不唯一，不考虑    可以用两个map找指针，也可以用一个map加长度    左闭右开
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int len = preorder.length;
        Map<Integer,Integer> preMap = new HashMap<>();
        Map<Integer,Integer> inMap = new HashMap<>();
        for(int i=0;i<len;i++){
            inMap.put(inorder[i],i);
        }
        return buildTree(preorder,inorder,inMap,0,len,0,len);
    }
    public TreeNode buildTree(int[] preorder, int[] inorder,Map<Integer,Integer> inMap,
                              int preLeft,int preright,int inLeft,int inRight){
        if(preLeft==preright) return null;
        TreeNode root = new TreeNode(preorder[preLeft]);
        int inIndex = inMap.get(root.val);
        int leftLen = inIndex-inLeft;
        root.left = buildTree(preorder,inorder,inMap,preLeft+1,preLeft+leftLen+1,inLeft,inIndex);
        root.right = buildTree(preorder,inorder,inMap,preLeft+leftLen+1,preright,inIndex+1,inRight);
        return root;
    }
    //给两棵树，判断b是不是a的子结构
    //不能遍历a看是否有和b的根节点相等的，要看值
    //双层递归，一个看当前节点是否存在子树与b结构相等 ，而判断结构相等也需要递归
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(A==null||B==null) return false;
        if(recur(A,B)) return true;
        return isSubStructure(A.left,B) || isSubStructure(A.right,B);
    }
    //用于判断两棵树是否结构一样
    public boolean recur(TreeNode a, TreeNode b){
        if(b==null) return true; //b没有a还有这种结构认为是可以的
        if((a==null&&b!=null)||a.val!=b.val) return false;
        return recur(a.left,b.left)&&recur(a.right,b.right);
    }
    //翻转二叉树 1.传入一个节点，将左子树全翻转好，再将右子树全翻转好，然后交换左右，返回根节点，相当于从上到下；
    //2.传入一个节点，交换左右子节点，然后再分别去处理这两个子节点，相当于从上到下
    //3.用队列，取出一个节点，交换左右，然后再入队子节点
    public TreeNode invertTree(TreeNode root) {
        if(root==null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
    //  判断是否为二叉搜索树的后续遍历
    //1.划分子树，看子树是否满足，然后再看子树的子树,分治思想
    public boolean verifyTreeOrder(int[] postorder) {
        return verify(postorder,0,postorder.length-1);
    }
    public boolean verify(int[] postorder,int left,int right){
        if(left>=right) return true;
        int rootValue = postorder[right];
        int index = left;//右子树的起始位置
        while (postorder[index]<rootValue) index++;
        int rightIndex = index;
        while (postorder[rightIndex]>rootValue){
            rightIndex++;
        }
        if(rightIndex!=right) return false;
        return verify(postorder, left,index-1) &&
                verify(postorder,index,right-1);
    }
    //判断是否存在根到叶路径之和等于sum 1.递归，找到叶节点2.两个队列，一个储存节点一个储存当前值，找到叶节点看值等不等于target
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        if(root.left==null && root.right==null) return root.val==targetSum;
        return hasPathSum(root.left,targetSum- root.val) ||
        hasPathSum(root.right,targetSum-root.val);
    }
    //路径总和2：找到所有路径 回溯搜索
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        pathSumDfs(res,path,targetSum,root);
        return res;
    }
    public void pathSumDfs(List<List<Integer>> res,List<Integer>path,int targetSum,TreeNode node){
        if(node==null) return;
        path.add(node.val);
        targetSum -= node.val;
        if(node.left==null&&node.right==null&&targetSum==0) res.add(new ArrayList<>(path));
        pathSumDfs(res,path,targetSum-node.val,node.left);
        pathSumDfs(res,path,targetSum-node.val,node.right);
        path.remove(path.size()-1);
    }
    //二叉搜索树就地转换为有序双向链表 : 中序遍历 维护cur和prev 1.递归 2.迭代
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };
    public Node treeToDoublyList1(Node root) {
        if(root==null) return null;
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        Node pre = null;
        Node head = null;
        while (!stack.isEmpty()||cur!=null){
            while (cur!=null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if(pre == null) {
                head = cur;
                pre = cur;
            }
            else {
                pre.right = cur;
                cur.left = pre;
                pre = cur;
            }
            cur = cur.right;
        }
        head.left = pre;//这块没想到用pre
        pre.right = head;
        return head;
    }
    Node pre = null,head = null;
    public Node treeToDoublyList(Node root){
        if(root==null) return null;
        toDoubly(root);
        head.left = pre;
        pre.right = head;
        return head;
    }
    public void toDoubly(Node root){
        if(root==null) return;
        toDoubly(root.left);
        if(pre==null){
            head = root;
        }
        else {
            pre.right = root;
            root.left = pre;
        }
        pre = root;
        toDoubly(root.right);
    }
    //找到二叉树的中序下一个节点  分类讨论1.有右子树：返回右子树的最左节点
    // 2.没右子树 ，自己是父节点的左子，返回父节点
    //3.没右子树，是父节点的右子，一直向上找（找父节点的父节点），直到成为某个节点的左子，返回这个节点，如果没找到，说明他是尾节点

    //判断是否为平衡二叉树：如何保存高度的同时又返回true或false,只能分成两个函数
    //递归有分自顶向下o(n2),分两个函数，传入一个节点计算左右子高度，看是否平衡，然后再看左右子是否平衡
    // 自下向上o(n) 传入一个节点，看左右子是否平衡，再看自己平不平衡，平衡的话返回高度，不平衡返回-1，相当于进行了剪枝
    public boolean isBalanced(TreeNode root) {
        return dept(root)>=0;
    }
    public int dept(TreeNode root){
        if(root==null) return 0;
        int leftHeight = dept(root.left);
        int rightHeight = dept(root.right);
        if(leftHeight==-1 ||rightHeight==-1|| Math.abs(leftHeight-rightHeight)>1) return -1;
        else return Math.max(dept(root.left),dept(root.right))+1;
    }
    //判断是不是对称二叉树 1.层序遍历,队列一次存取两个元素，左的左子右的右子/左的右子右的左子
    // 2.递归,注意条件判断，不是要求每个节点的左右子节点的值必须都一样
    public boolean checkSymmetricTree(TreeNode root) {
        if(root==null) return true;
        return check(root.left,root.right);
    }
    public boolean check(TreeNode left,TreeNode right){
        if(left==null&&right==null) return true;
        if(left==null || right==null || right.val!=left.val) return false;
        return check(left.left,right.right)&&check(left.right,right.left);
    }
    //二叉搜索树的最近公共祖先
    //1.遍历两次，分别找对应节点的路径，再比较这两条路径
    //2.利用二叉搜索树的性质，一次遍历，如果两个点在同一颗子树上，那么他们一定同时比根节点大或者小
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = getPath(root,p);
        List<TreeNode> pathQ = getPath(root,q);
        int i;
        for(i=0;i< Math.min(pathQ.size(),pathP.size());i++){
            if(pathQ.get(i) == pathP.get(i)) break;
        }
        return pathP.get(i-1);
    }
    public List<TreeNode> getPath(TreeNode root,TreeNode node){
        List<TreeNode> path = new ArrayList<>();
        while (root.val!=node.val){
            path.add(root);
            if(root.val > node.val) root = root.left;
            else root = root.right;
        }
        path.add(node);
        return path;
    }
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        while (true){
            if(root.val>p.val&& root.val>q.val){
                root = root.left;
            }
            else if(root.val<p.val&& root.val<q.val){
                root = root.right;
            }
            else {
                break;
            }
        }
        return root;
    }
    //正常二叉树找公共祖先 通过路径的方式不好，没法快速确定路径
    //1.搜索所有节点，看两个节点是否同在一侧，是就继续往下，不是这个就是结果
    //2.中序遍历得到数组，将数组和下标存入map，去map里找两个节点得下标
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //判断pq中是否存在一个或以上在这个节点中，在的话返回这个节点，不在返回null
        if(root==null||root.val==p.val||root.val==q.val) {
            return root;
        }
        TreeNode l = lowestCommonAncestor(root.left,p,q);
        TreeNode r = lowestCommonAncestor(root.right,p,q);
        if(l==null&&r!=null) return r;
        if(l!=null&&r==null) return l;
        if(l!=null&&r!=null) return root;
        return null;
    }
    //对称二叉树:1.对称性递归2.搞两个队列
    //同类问题：翻转二叉树，二叉树深度
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null)return true;
        if(p==null||q==null||p.val!=q.val) return false;
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
    //求二叉树直径,即最多经过几条边 1.双层递归，一层求深度，一层用深度求最大路径
    // 2.优化，求深度的同时求出最大路径,因为知道一个节点左右节点的深度就已经可以求出经过这个节点的路径了
    int sum = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return sum;
    }
    public int depth(TreeNode root){
        if(root==null) return 0;
        int leftd = depth(root.left);
        int rightd = depth(root.right);
        sum = Math.max(leftd+rightd,sum);
        return Math.max(leftd, rightd)+1;
    }
    //合并二叉树：深度优先搜索，先把根合并了，左右使用递归后的结果，再返回根
    //2.广度优先搜索，三个队列，一个root1，一个2，一个储存结果//注意区分，正常bfs处理poll出的节点，然后左右入，本题处理poll出的节点的左右，然后入
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1==null) return root2;
        if(root2==null) return root1;
        TreeNode node = new TreeNode(root1.val+root2.val);
        node.left = mergeTrees(root1.left,root2.left);
        node.right = mergeTrees(root1.right, root2.right);
        return node;
    }
    //打家劫舍3，二叉树相邻节点不能偷，经典树形动态规划
    //每个节点有两个状态，偷的最大或不偷的最大，需要先知道子节点的值才能求父节点的，所以是后续遍历
    public int rob(TreeNode root) {
        int[] arr = dfsRob(root);
        return Math.max(arr[0],arr[1]);
    }
    public int[] dfsRob(TreeNode root){
        if(root==null) return new int[]{0,0};
        int[] l = dfsRob(root.left);
        int[] r = dfsRob(root.right);
        int selected = root.val + l[0]+r[0];
        int notSelected = Math.max(l[0],l[1])+ Math.max(r[0],r[1]);
        return new int[]{notSelected,selected};
    }


































    public void postOrder(TreeNode root,List<Integer> res){
        if(root == null) return;
        postOrder(root.left,res);
        postOrder(root.right,res);
        res.add(root.val);
    }










}
