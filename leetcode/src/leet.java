import javax.management.Query;
import java.lang.management.MemoryType;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class leet {
/**
 * Definition for singly-linked list.*/
/**Definition for a binary tree node.*/
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
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> m = new HashMap<>();
        for(int i =0 ;i<nums.length;i++){
            if(m.containsKey(target-nums[i])){
               return new int[] {m.get(target-nums[i]),i};
            }
            m.put(nums[i],i);
        }
        return new int[0];
    }
}
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> m = new HashMap<>();
        for(int i =0 ;i<nums.length;i++){
            if(m.containsKey(target-nums[i])){
                return new int[] {m.get(target-nums[i]),i};
            }
            m.put(nums[i],i);
        }
        return new int[0];
    }

    public int missingNumber(int[] nums) {
        int n  = nums.length;
        boolean[] b= new boolean[n+1];
        for(int i=0;i<n;i++){
            b[nums[i]] = true;
        }
        for (int i =0 ;i<=n;i++){
            if(!b[i]) return i;
        }
        return 0;
    }

    /**
     * node.next既可以指当前节点的指针也可以指下一个结点
     * 链表天生有递归特性，转为处理第一个节点以及剩下所有节点构成的新节点，进行递归
     * 因为你传入的是head。next作为参数head，所以他会一直递推，到终止条件
     * 要考虑空链表
     */
    public ListNode reverseList(ListNode head) {
        if(head ==null||head.next==null) return head;
        ListNode p = reverseList(head.next);
        head.next.next=head;
        head.next=null;
        return p;
    }
    //迭代
    public ListNode reverseList2(ListNode head) {
        ListNode prev = null;
        ListNode curr  = head;
        while (curr != null) {
            ListNode temp = curr.next;
            //把curr。next设置为prev
            curr.next = prev;
            prev =curr;
            curr = temp;
        }
        return curr;
    }
    public int maximumProduct(int[] nums) {
        int max1=Integer.MIN_VALUE,max2=Integer.MIN_VALUE,max3=Integer.MIN_VALUE;
        int min1= Integer.MAX_VALUE,min2= Integer.MAX_VALUE;
        for(int n:nums){
            if(n>max1){
                max3 = max2;
                max2 = max1;
                max1 =n;
            }
            else if(n>max2){
                max3 = max2;
                max2 = n;
            }
            else if(n>max3){
                max3= n;
            }
            if(n<min1){
                min2 = min1;
                min1 = n;
            }
            else if(n<min2){
                min2 = n;
            }
        }
        return Integer.max(max1*max2*max3,max1*min1*min2);
    }
    public int mySqrt(int x) {
        int ans =-1,l=0,r=x;
        while (l <= r){
            //mid可能超界，两种办法，一种强转long
            //一种l+（r-l）/2
            int mid =(l+r)/2;
            if((long)mid*mid<=x){
                ans = mid;
                l=mid+1;
            }
            else r=mid-1;
        }
        return ans;
    }
    public int mySqrt1(int x){
        double c= x,x0=x;
        if(x==0) return 0;
        while(true){
            double xi = 0.5*(x0+c/x0);
            if(Math.abs(xi-x0)<1e-7){
                break;
            }  
            x0=xi;
        }
        return (int)x0;
    }
    public int pivotIndex(int[] nums) {
        //加法的逻辑耗时是固定的
        int total = Arrays.stream(nums).sum();
        int leftTotal =0;
        for(int i=0;i<nums.length;i++){
            leftTotal+=nums[i];
            if(leftTotal==total){
                return i;
            }
            total-=nums[i];
        }
        return -1;
    }
    public int removeDuplicates(int[] nums) {
        int j=0;
        for(int i=1;i<nums.length;i++){
            if(nums[j]!=nums[i]){
                //j++先用j的值再加，++j先加改变了值再用j
                nums[j+1]=nums[i];
                j++;
            }
        }
        return j+1;
    }
    public int Eratosthenes(int n){
        boolean[] isPrime = new boolean[n];
        int count =0;
        for(int i=2;i<n;i++){
            if(!isPrime[i]){
                count++;
                for(int j=2*i;j<n;j+=i){
                    isPrime[j] = true;
                }
            }
        }
        return count;
    }
    //两数之和，数组已经升序排号，可使用二分查找或者双指针
    public int[] twoSearch(int[] numbers,int target){
        for(int i=0;i<numbers.length;i++){
            int low =i,high =numbers.length;
            while(low<=high){
                int mid = low+(high-low)/2;
                if(numbers[i]+numbers[mid]==target)
                {
                    return new int[]{i,mid};
                } else if (numbers[i]+numbers[mid]>target) {
                    high =mid-1;
                }else low=mid+1;
            }
        }return new int[0];
    }
    public int[] twoPoint(int[] numbers,int target){
        int low=0,high=numbers.length-1;
        while (low<high){
            if(numbers[low]+numbers[high]==target) return new int[]{low,high};
            else if (numbers[low]+numbers[high]>target) {
                high--;
            }else low++;
        }return new int[0];

    }
    //1.暴力递归
    public int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        return fib(n-1)+fib(n-2);
        //相当于二叉树，时间复杂度二的n次方
    }
    //2.去重递归
    public int fib1(int n) {
        int[] arr = new int[n+1];
        return recurse(arr,n);
        //相当于二叉树，时间复杂度二的n次方
    }
    //    recurse:递归
    private static int recurse(int[] arr,int n){
        if(n==0) return 0;
        if(n==1) return 1;
        if(arr[n]!=0) return arr[n];
        arr[n] = recurse(arr,n-1)+recurse(arr,n-2);
        return arr[n];
    }
    //双指针迭代

    /**
     * 双指针有快慢（链表），前后（两数之和，扫描），左右指针（二分查找，逼近）
     * 核心思想就是用两个变量储存两个值
     * 也可以叫状态位，不断地更换两个状态的值
     * @return
     */
    public int fib2(int n){
        int low=0,high=1;
        for(int i=2;i<=n;i++){
            int sum = low+high;
            low =high;
            high=sum;
        }
        return high;
    }
    //迭代
    public int arrangeCoins(int n) {
        for(int i=1;i<=n;i++){
            n=n-i;
            if(n<=i){
                return i;
            }
        }
        return 0;
    }
    public static int arrangeCoins1(int n){
        int left=1,right=n;
        while(left<=right){
            int mid = left+(right-left)/2;
            if((1+mid)*(long)mid==(long)2*n) {
                return mid;
            }
            else if((1+mid)*(long)mid > (long)2*n) {
                right = mid-1 ;
            }
            else {left =mid+1;}
        }
        return right;
    }
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast =head;
        while(fast!=null&&fast.next!=null&&fast.next.next!=null){
            fast = fast.next.next;
            slow =slow.next;
            if(fast==slow) return true;
        }
        return false;
    }
    public boolean hasCycle1(ListNode head){
        Set<ListNode> visited = new HashSet<>();
        while(head!=null){
            if(visited.contains(head)) return true;
            else {
                visited.add(head);
                head = head.next;
            }
        }return false;
    }
    public ListNode getKthFromEnd(ListNode head, int k) {
        int n=0;
        ListNode node = head;
        while(node!=null){
            n++;
            node=node.next;
        }
        for(node = head;n>k;n--){
            node = node.next;
        }
        return node;
    }
    public ListNode getKthFromEnd1(ListNode head, int k){
        ListNode former=head,later=head;
        for(int i=0;i<k;i++){
            former=former.next;
        }
        while (former!=null){
            former=former.next;
            later=later.next;
        }
        return later;
    }
    public ListNode middleNode(ListNode head) {
        ListNode fast=head,slow=head;
        while(fast!=null&&fast.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
        return slow;
    }
    public ListNode middleNode1(ListNode head){
        ListNode cur = head;
        int n=0;
        while(cur!=null){
            cur=cur.next;
            n++;
        }
        int k=0;
        cur=head;
        while (k < n/2){
            cur=cur.next;
            k++;
        }
        return cur;
    }
    public void merge(int[] A, int m, int[] B, int n) {
        for(int i=0;i<n;i++){
            A[m+i]=B[i];
        }
        //快排复杂度nlog(n)
        Arrays.sort(A);
    }
    //没想到可以直接整合AB啊，再快排
    //双指针定义pa，pb而非i，j
    public void merge1(int[] A, int m, int[] B, int n) {
        int[] sorted = new int[m+n];
        int pa=0,pb=0,p=0;
        while(pa<m||pb<n){
            if(pa==m){sorted[p++]=B[pb++];}
            else if(pb==n){sorted[p++]=A[pa++];}
            else if(A[pa]<=B[pb]) {
                sorted[p++]=A[pa++];
            }
            else {
                sorted[p++]=B[pb++];
            }
        }
        for(int i =0;i<p;i++){
            A[i]=sorted[i];
        }
    }
    public void merge2(int[] A, int m, int[] B, int n) {
        int pa=m-1,pb=n-1,p=m+n-1;
        while(pa!=-1||pb!=-1){
            if(pa==-1) A[p--] = B[pb--];
            else if (pb==-1) {
                A[p--]=A[pa--];
            } else if (A[pa]>B[pb]) {
                A[p--]=A[pa--];
            }else {
                A[p--]=B[pb--];
            }
        }
    }

    /**
     *动态规划，最大连续子数组的和
     * 重点在状态转移方程
     */
    public int maxSubArray(int[] nums) {
        int n =nums.length;
        int[] dp = new int[n];
        //dp[i]（状态数组）:以nums【i】结尾的最大连续子数组的和
        //固定住了不确定的因素，使状态转移变得更容易
        //每个子问题只求解一次，无后效性
        dp[0] = nums[0];
        int res = dp[0];
        for(int i=1;i<n;i++){
            dp[i]=Math.max(dp[i-1]+nums[i],nums[i]);
            res = Math.max(res,dp[i]);
        }
        return res;
    }

    /**
     * 深度优先搜索
     */
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        //这道题递归条件里分为三种情况
        //1.左孩子和有孩子都为空的情况，说明到达了叶子节点，直接返回1即可
        if(root.left == null && root.right == null) return 1;
        //2.如果左孩子和由孩子其中一个为空，那么需要返回比较大的那个孩子的深度
        int m1 = minDepth(root.left);
        int m2 = minDepth(root.right);
        //这里其中一个节点为空，说明m1和m2有一个必然为0，所以可以返回m1 + m2 + 1;
        //也可以 return math.max(m1,m2)+1
        if(root.left == null || root.right == null) return m1 + m2 + 1;
        //3.最后一种情况，也就是左右孩子都不为空，返回最小深度+1即可
        return Math.min(m1,m2) + 1;
    }
    public int minDepth1(TreeNode root){
        if(root.left==null&&root.right==null) return 1;
        int mind = Integer.MAX_VALUE;
        //实际上就是如果有一个为零的话要不为零那个，都不为零的话要更小哪个
        if(root.left!=null) mind = Math.min(minDepth(root.left),mind);
        if(root.right!=null) mind = Math.min(minDepth(root.right),mind);
        return mind+1;
    }
    public int minDepth3(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level=1;
        while(!q.isEmpty()){
            //每次队列入队出队后size会变，所以先用一个量储存
            int size = q.size();
            for(int i=0;i<size;i++){
                TreeNode node = q.poll();
                if(node.left==null && node.right==null) return level;
                if(node.left!=null) q.offer(node.left);
                if(node.right!=null) q.offer(node.right);
            }
            level++;
        }
        return level;
    }

    /**
     * 贪心：找局部最优解
     * 动态规划会保存以前的运算结果,并根据以前的结果对当前进行选择,有回溯的过程
     * 贪心算法是纯粹基于当前状态做选择,不能回溯。
     */
    public int findLengthOfLCIS(int[] nums) {
        int start =0,ans=0;
        if(nums.length==1) return 1;
        for(int i =1;i< nums.length;i++){
            if(nums[i]<=nums[i-1]){
                start=i;
            }
            ans = Math.max(ans,i-start+1);
        }
        return ans;
    }
    public boolean lemonadeChange(int[] bills) {
        int five =0,ten=0;
        for(int bill :bills){
            if(bill==5){
                five++;
            }
            if(bill==10){
                if(five>0){
                    five--;
                    ten++;
                }
                else return false;
            }
            if(bill==20){
                if(ten>0&&five>0) {
                    ten--;
                    five--;
                } else if (five>=3) {
                    five = five-3;
                }
                else return false;
            }
        }
        return true;
    }
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for(int i= nums.length-1;i>1;i--){
            if(nums[i]<nums[i-1]+nums[i-2]) return nums[i]+nums[i-1]+nums[i-2];
        }
        return 0;
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preOrder(res,root);
        return res;
    }
    public void preOrder(List<Integer> res,TreeNode root){
        if(root==null) return;
        res.add(root.val);
        preOrder(res,root.left);
        preOrder(res,root.right);
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inOrder(res,root);
        return res;
    }
    public void inOrder(List<Integer> res,TreeNode root){
        if(root==null) return;
        inOrder(res,root.left);
        res.add(root.val);
        inOrder(res,root.right);
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postOrder(res,root);
        return res;
    }
    public void postOrder(List<Integer> res,TreeNode root){
        if(root==null) return;
        postOrder(res,root.left);
        postOrder(res,root.right);
        res.add(root.val);
    }

    /**
     * 递归的时候隐式地维护了一个栈，而我们在迭代的时候需要显式地将这个栈模拟出来
     */
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || root!=null){
            while (root!=null){
                res.add(root.val);
                stack.push(root);
                root =root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty()||root!=null){
            while(root!=null){
                stack.push(root);
                root =root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root =root.right;
        }
        return res;
    }
    //后续遍历是难点
    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        //初始化一个prev来记录  访问历史
        TreeNode prev = null;
        while(!stack.isEmpty()||root!=null){
            while(root!=null){
                stack.push(root);
                root =root.left;
            }
            //从栈中弹出的元素，左子树一定是访问完了的
            //找到最左子节点不一定能直接打印，可能叶在右边
            root =stack.pop();
            if(root.right==null || root.right ==prev){
                res.add(root.val);
                prev = root;
                root = null;
            }else {
                stack.push(root);
                root=root.right;
            }

        }
        return res;
    }
    public List<Integer> postorderTraversal2(TreeNode root){
//        根右左
        //list是一个有序结合，可以通过链表实现，可以通过数组实现
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        while(!stack.isEmpty() || root!=null){
            while (root!=null){
                res.add(root.val);
                stack.push(root);
                root= root.right;
            }
            root = stack.pop();
            root = root.left;
        }
        Collections.reverse(res);
        return res;
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null) return res;
        queue.add(root);
        while (!queue.isEmpty()){
            int n = queue.size();
            List<Integer> level = new ArrayList<>();
            //一层一个集合所以才需要for循环
            for(int i=0;i<n;i++){
                root = queue.poll();
                level.add(root.val);
                if(root.left!=null)    queue.add(root.left);
                if(root.right!=null)    queue.add(root.right);
            }
            res.add(level);
        }
        return res;
    }

    /**深度优先搜索
     */
    public int findCircleNum(int[][] isConnected) {
        int citys = isConnected.length;
        //打标为了确认是否已经归属某一个省份，即是否访问过了
        boolean[] visited = new boolean[citys];
        int province = 0;
        //竖着遍历所有城市
        for(int i=0;i<citys;i++){
            if(!visited[i]){
                //通过深度优先，将所有与该城市相连的城市置为true
                dfs(i,citys,isConnected,visited);
                province++;
            }
        }
        return province;
    }
    private void dfs(int i, int citys, int[][] isConnected, boolean[] visited) {
        //横着遍历，相当于访问与第i个城市左右直接相连的城市
        for(int j=0;j<citys;j++){
            //第j个城市与第i城市直接相连，且未被遍历过
            //如果j被遍历过，一定是与他直接相连的所有城市都遍历过了
            if(isConnected[i][j]==1&&!visited[j]){
                visited[j] = true;
                //递归，继续网深了找，找与第j个城市直接相连的
                dfs(j,citys,isConnected,visited);
            }
            //这个方法不能使用j=i+1，因为不是顺序遍历的
            //i先找到第一个j之后，从j开始借着往下找，
            // i，j之间的还没找过，所以递归调用时j等于新的i（也就是上次的j）+1会出现问题
        }
    }

    /**
     * 广度优先，借用队列，实现先入先出
     * 先找到直接相连的一圈，再找到与圈中元素直接相连的下一圈
     */
    public int findCircleNum1(int[][] isConnected){
        int citys = isConnected.length;
        //打标为了确认是否已经归属某一个省份，即是否访问过了
        boolean[] visited = new boolean[citys];
        int province = 0;
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<citys;i++) {
            if (!visited[i]){
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int k = queue.poll();
                    visited[k] = true;
                    //找k周围的一圈
                    for (int j = 0; j < citys; j++) {
                        if (isConnected[k][j] == 1 && !visited[j]) {
                            queue.offer(j);
                        }
                    }
                }
                province++;
            }
        }
        return province;
    }
    /**
     *并查集
     */
    public int findCircleNum2(int[][] isConnected){
        int citys = isConnected.length;
        int[] head = new int[citys];
        int[] level = new int[citys];
        for(int i=0;i<citys;i++){
            head[i] = i;
            level[i] = 1;
        }
        for(int i=0;i<citys;i++){
            for(int j=i+1;j<citys;j++){
                if(isConnected[i][j]==1){
                    merge(i,j,head,level);
                }
            }
        }
        int count =0;
        for(int i=0;i<citys;i++){
            if(head[i]==i) count++;
        }
        return count;
    }
    //将 x,y两个城市合并
    public void merge(int x, int y ,int[] head,int[] level){
        int xhead = find(x,head);
        int yhead = find(y,head);
        if(xhead ==yhead) return;
        else{
            if(level[xhead]>=level[yhead]){
                head[yhead] = xhead;
                if(level[xhead]==level[yhead])   level[xhead]++;
            }
            else head[xhead] = yhead;
        }
    }
    public int find(int x,int[] head){
        if(x==head[x]) return x;
        //递归，每次找上一级的head，一直找到最根上，返回
        head[x] = find(head[x],head);
        return head[x];
    }
    //并查集2
    class UnionFind{
        int[] head;
        UnionFind(int n){
            head = new int[n];
            for(int i=0;i<n;i++){
                head[i] = i;
            }
        }
//        int find1(int x){
//            while(head[x]!=x){
//                head[x] = head[head[x]];
//                x = head[x];
//            }
//        }
        int find(int x){
            if(head[x]==x) return x;
            head[x] = find(head[x]);
            return head[x];
        }
        void union(int x,int y){
            int xhead = find(x);
            int yhead = find(y);
//            if(xhead==yhead) return;
            head[yhead] = xhead;
        }
    }
    public int findCircleNum3(int[][] isConnected){
        int citys = isConnected.length;
        UnionFind unionFind = new UnionFind(citys);
        for(int i=0;i<citys;i++){
            for(int j=i+1;j<citys;j++){
                if(isConnected[i][j]==1){
                    unionFind.union(i,j);
                }
            }
        }
        int count=0;
        for (int i=0;i<citys;i++){
            if(unionFind.head[i]==i) count++;
        }
        return count;
    }




}
