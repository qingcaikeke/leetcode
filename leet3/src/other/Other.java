package other;

import jdk.swing.interop.LightweightContentWrapper;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSInput;

import javax.swing.undo.CannotUndoException;
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
        //广度优先搜索求最大深度
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
//        ListNode pres = null;
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
    public String convert1(String s, int numRows) {
        if(numRows==1) return s;
//        用flag实现行数的增减，到边界反转
        List<StringBuilder> rows = new ArrayList<>(); //储存每行的元素
        for(int i=0;i<numRows;i++) rows.add(new StringBuilder());//一定要初始化，上一行相当于创建了一个空的rows
        int flag = -1,i=0;
        for(char c: s.toCharArray()){
            rows.get(i).append(c);
            if(i==0||i==numRows-1) flag = -flag;
            i+=flag;
        }
        StringBuilder res = new StringBuilder();
        for(StringBuilder row:rows) res.append(row);
        return res.toString();
        //stringbulider和string：s1 = s1 + " world"; sb1.append(" world");
        //list中 String是不可变的字符串类，改完之后还得set一下。StringBuilder是可变的字符串类
//        需要存储大量需要频繁修改的字符串时,List<StringBuilder>比List<String>性能更优。但通常情况下List<String>使用更多。
//        单线程环境下,可以选择使用List<StringBuilder>。
//        多线程环境,需要线程安全,则应该使用 List<StringBuffer>。通常情况下是StringBuffer
    }
    public String convert(String s, int numRows){
        if(numRows==1) return s;
        StringBuffer[] mat = new StringBuffer[numRows];
        for(int i=0;i<numRows;i++) mat[i] = new StringBuffer();
        int t =numRows+numRows-2;//一个周期
        int x=0;//行的索引
        for(int i=0;i<s.length();i++){
            mat[x].append(s.charAt(i));
            if(i%t<numRows-1) x++;
            else x--;
        }
        StringBuffer res = new StringBuffer();
        for(StringBuffer row:mat){
            res.append(row);
        }
        return res.toString();
    }
    public int reverse(int x) {
        int res = 0;
        while (x!=0){
            int temp = x%10;
            //32位整数2147483647 - -2147483648
            if(res>214748364||res==214748364&&temp>7) return 0;
//            (2^31不是次方，而是异或<) 可以用(int)Math.pow(2,31)/10（注意两个整数相除的结果依然是整数，小数部分会被截断）;
//            int溢出的结果是环绕的，原值是 2147483647,再加 1,结果会环绕到 -2147483648
            if(res<Integer.MIN_VALUE/10||res==-214743864&&temp<-8) return 0;
//            法2：res每次更新后除10，然后跟上一次的res比较一下，如果不相等，就是溢出
//            int last  = res;
            res = res*10+temp;
//            if(last!=res/10) return 0;
            x=x/10;
        }
        return res;
    }
    public int myAtoi(String s) {
        int i=0,l=s.length();
        while (i<l&&s.charAt(i)==' ') i++; //丢弃无用的前导空格
        int sign=1,res=0;
        int start = i;
        for(;i<l;i++){
            char c = s.charAt(i);
//            在连续的 if-else 结构中,else 语句只对应最后一个 if 语句
            if(s.charAt(i)=='-'&&i==start) sign =-1;
            else if(s.charAt(i)=='+'&&i==start) sign = 1;//符号位判断完成
            else if(Character.isDigit(c)){
                int num = c -'0';
                int last = res;
                res = res*10+num;
                if(last!=res/10) {
                    if(sign==1)return Integer.MAX_VALUE;
                    if(sign==-1) return Integer.MIN_VALUE;
                }
            }
            else break;
        }
        return res*sign;
    }
    public boolean isPalindrome(int x) {
        if(x<0||x%10==0&&x!=0) return false;
        int reverted =0;
        while(x>reverted){
            reverted=x%10+reverted*10;
            x=x/10;
        }
        return x==reverted||x==reverted/10;
    }
    public boolean isPalindrome1(int x){
        String sa = Integer.toString(x);
        for(int i=0,j=sa.length()-1;i<j;i++,j--){
        if(sa.charAt(i)!=sa.charAt(j)) return false;
    }
        return true;
}
    public int maxArea(int[] height) {
        int i = 0,j = height.length-1;
        int res =0 ;
        while (i<j){
            res = height[i]<height[j]?
                    Math.max(res,(j - i)*height[i++]) : Math.max(res,(j - i)*height[j--]);
        }
        return res;
    }
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            while (num>=values[i]){
                res.append(symbols[i]);
                num-=values[i];
            }
        }
        return res.toString();
    }

    public boolean isValid(String s) {
        //括号有多种，仅计数不能判断有效
        if((s.length()&1)==1) return false; //判断奇偶
        Map<Character,Character> pairs = new HashMap<>();
        pairs.put(')','(');
        pairs.put('}','{');
        pairs.put(']','[');
        Stack<Character> stack = new Stack<>();
        for(char ch : s.toCharArray()){
            if(pairs.containsKey(ch)){//如果是右括号，去栈里找
                if(stack.isEmpty()||stack.peek()!=pairs.get(ch)) return false;
                stack.pop();//peak不拿出元素，pop拿出元素
            }
            else stack.push(ch);
        }
        return stack.isEmpty();
    }
//    public int strStr(String haystack, String needle) {
//        /**
//         * kmmp: 主串，模式串，next数组：前j-1个字符，前后缀重合数最大值加1
//         *next【1】=0，next【2】 =1 一开始i=1，j=0 来求next【2】
//         */
//    }
//    public int getNext(char[] ch,int[] next){
//        next[1] = 0;
//        int i =1,j=0;
//        while (i<= ch.length){
//            if(j==0 || ch[j]==ch[i]) {
//                i++;
//                next[i] =  j+1; //next[2] = 1;
//            }
//            else j = next[j];
//        }
//    }
    public void nextPermutation(int[] nums) {
    //1.后面的大数与前面的小数交换2.尽可能靠右，从低位开始找3.剩下的元素升序排列
        for(int i = nums.length-1;i>0;i--) {
            if (nums[i]>nums[i-1]){//找到第一个升序
                Arrays.sort(nums,i, nums.length);
                for(int j= i;j<nums.length;j++){
                    if(nums[j]>nums[i-1]){
                        nums[i-1] = nums[i-1]^nums[j];
                        nums[j] = nums[i-1]^nums[j];
                        nums[i-1] = nums[i-1]^nums[j];
                        return;
                    }
                }
            }
        }
        Arrays.sort(nums);//已经是最大了（全降序），那么应该返回最小的，也就是升序
    }
    public boolean isValidSudoku(char[][] board) {
        int[][] rows = new int[9][9];
        int[][] columns = new int[9][9];
        int[][][] subBox = new int[3][3][9];
        for (int i = 0; i < 9; i++) {
            for(int j=0;j<9;j++){
                char c = board[i][j];
                if(c!='.') {
                    int num = c-'0';
                    rows[i][num-1]++; //第i行该数字出现的次数
                    columns[j][num-1]++;//第j列该数字出现的次数
                    subBox[i/3][j/3][num-1]++;//该数字在第[i/3][j/3]这个方格里出现的次数
                    if(rows[i][num-1]>1||columns[j][num-1]>1||subBox[i/3][j/3][num-1]>1) return false;
                }
            }
        }
        return true;
    }
    public String countAndSay(int n) {
        String str = "1";
        for(int i=2;i<=n;i++){//求第二个到第n个str
            int start=0 ,end =0;
            StringBuffer sb = new StringBuffer();
            while (end<str.length()){
                while (end<str.length() && str.charAt(end)==str.charAt(start)){
                    end++;
                }
                sb.append(Integer.toString(end-start)).append(str.charAt(start));
                start = end;
            }
            str = sb.toString();
        }
        return str;
    }
//    字符串模拟大数乘法的实现
    public String multiply1(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length(),n = num2.length();
        int[] ansArr = new int[m+n];  //两数相乘所得位数为m+n或m+n-1
        for(int i = m-1;i>=0;i--){
            for(int j = n-1;j>0;j--){
                int x = num1.charAt(i) - '0';
                int y = num2.charAt(j) - '0';
                ansArr[i+j+1] += x*y;
            }
        }
        //同一处理进位
        for (int i = m+n-1;i>=0;i--){
            ansArr[i-1]+=ansArr[i]/10;
            ansArr[i] = ansArr[i]%10;
        }
        int index = ansArr[0]==0? 1:0;
        StringBuffer ans = new StringBuffer();
        while (index<m+n){
            ans.append(ansArr[index]);
            index++;
        }
        return ans.toString();
    }
    public String addStrings(String num1, String num2) {
        int l1 = num1.length()-1,l2 = num2.length()-1,carry =0;
        StringBuffer ans = new StringBuffer();
        while (l1>=0 || l2>=0 || carry>0){
            int x = l1>=0 ? num1.charAt(l1) - '0' : 0;
            int y = l2>=0? num2.charAt(l2) - '0' :0;
            int temp = x+y+carry;
            ans.append(temp%10);
            carry = temp/10;
            l2--; l1--;
        }
        ans.reverse();
        return ans.toString();
    }
    public String multiply(String num1, String num2) {
        String res = "0";
        for(int i = num2.length()-1;i>=0;i--){
            int n2 = num2.charAt(i) - '0';
            StringBuffer sb = new StringBuffer();
            //补0
            for(int j=num2.length()-1;j>i;j--){
                sb.append(0);
            }
            int carry = 0;
            for(int j = num1.length();j>=0;j--){
                int n1 = num1.charAt(j) - '0';
                int temp  = n2*n1 + carry;
                sb.append(temp%10);
                carry = temp/10;
            }
            if(carry!=0) sb.append(carry);
            res = addStrings(res,sb.reverse().toString());
        }
        return res;
    }
    public int jump(int[] nums) {
        int start =0 ,end=1;//每一次的开始区间，左闭右开
        int ans =0;
        while (end<nums.length){
            int maxPos = 0;
            for (int i = start;i<end;i++){
                maxPos = Math.max(maxPos,nums[i]+i); //遍历开始区间所有点，求可到达的最远下标
            }
            start = end;
            end = maxPos+1;
            ans++;//记录跳跃次数
        }
        return ans;
    }
    //优化
    public int jump1(int[] nums){
        int ans =0;
        int end =0, maxPos =0;
         for(int i=0;i< nums.length-1;i++){
             maxPos = Math.max(maxPos,i+nums[i]);
             if(i==end){
                 end = maxPos;
                 ans++;
             }
         }
         return ans;
    }

    public void rotate(int[][] matrix) {
        //分奇偶
        int n = matrix.length;
        for(int i =0;i<n/2;i++){
            for(int j =0; j<(n+1)/2;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = temp;
            }
        }
        return;
    }
    public void rotate1(int[][] matrix) {
        //上下反转再沿对角线反转也相当于一次旋转
        int n = matrix.length;
        for(int i =0;i<n/2;i++){
            for(int j=0;j<n;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-i][j];
                matrix[n-1-i][j] = temp;
            }
        }
        for(int i=0;i<n;i++){
            for(int j =0;j<i;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        return;
    }
    public double myPow1(double x, int n) {
        //快速幂
        long N = n; //负数次幂算正数次分之一，但是-min次变成正数会爆int
        return N >=0? quicklyMul(x,N) : 1.0/quicklyMul(x,-N);
    }
    public double quicklyMul(double x, long n){
        if(n==0) return 1.0;
        double y = quicklyMul(x,n/2);
        return n%2==1 ? y*y*x: y*y;
    }
    public double myPow(double x, int N) {
       long n = N;
        if(n<0){
            n = -n;
           x= 1/x;
       }
        //直接平方还是先乘x再平方
        //本质上是算二进制，二进制除二倒取余
        double res =1.0;
        double weight = x;
        while (n>0){
            if(n%2==1){
                res = res * weight;
            }
//            下一位的权重x的1，2，4，8...次方
            weight = weight*weight;
            n = n/2;
        }
        return res;
    }
    public List<List<String>> groupAnagrams1(String[] strs) {
//        1.排序 2.计数
        Map<String,List<String>> map = new HashMap<>();
        for(String str :strs){
            char[] strArray = str.toCharArray();
            Arrays.sort(strArray);
            //Map 的 key 要求必须是不可变类型,才能保证映射关系的稳定。String 是不可变类型,而 char[] 是可变类型。
            String key = Arrays.toString(strArray);
            List<String> list = map.getOrDefault(key,new ArrayList<String>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<>(map.values());
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for(String str:strs){
            int[] count = new int[26];
            int len = str.length();
            for(int i =0;i<len;i++){
                count[str.charAt(i)-'a']++;
            }
            StringBuffer sb = new StringBuffer();
            for(int i =0;i<26;i++){
                if(count[i]!=0){
                    sb.append((char)('a'+i));//char类型加上int类型是一个int类型
                    //char参与其他运算比如乘除法,也会先转成int再计算。c会先转成其Unicode值97
                    sb.append(count[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<>(map.values());
    }
    public int divide(int dividend, int divisor) {
        if(dividend == 0) return 0;
        if(divisor == 1) return dividend;
        if(divisor == -1){
            if(dividend>Integer.MIN_VALUE) return -dividend;// 只要不是最小的那个整数，都是直接返回相反数就好啦
            return Integer.MAX_VALUE;// 是最小的那个，那就返回最大的整数啦
        }
        int sign = (dividend >>> 31)^(divisor>>>31);//右移31位得到符号位
        if(dividend>0) dividend = -dividend;
        if(divisor>0) divisor = -divisor;
        int res = div(dividend,divisor);
        return sign==1? -res: res;
    }
    int div(int a ,int b){ //   求a/b ,先ab都是正数
        if(a>b) return 0;
        int tempb = b;
        int count = 1;
        while (a<= tempb+tempb&&tempb+tempb<0){ //2*tempb可能会超界
            tempb  = tempb+tempb;
            count = count+count;  //除法结果最小是2
        }
        return count+div(a-tempb,b);
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int left = 0, right = matrix[0].length-1;
        int up =0, down = matrix.length-1;
        while (true){
            for(int i=left;i<=right;i++){
                res.add(matrix[up][i]);
            }
            up++;
            if(up>down) break;
            for(int i = up;i<=down;i++){
                res.add(matrix[i][right]);
            }
            right--;
            if(right<left) break;
            for(int i = right;i>=left;i--){
                res.add(matrix[down][i]);
            }
            down--;
            if(up>down) break;
            for (int i = down;i>=up;i--){
                res.add(matrix[i][left]);
            }
            left++;
            if(left>right) break;
        }
        return res;
    }
    public boolean canJump(int[] nums) {
        int far = 0;
        for(int i= 0;i< nums.length;i++){
            if(i>far) return false;
            far = Math.max(far,i+nums[i]);
        }
        return true;
    }
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0],right = newInterval[1];
        List<int[]> resList = new ArrayList<>();
        boolean inserted =false;
        for(int[] interval : intervals){
            if(interval[0]>right){
                //第一次找到一个集合，左端比right还大，就该把合并后的集合插入进去了
                if(!inserted) {
                    resList.add(new int[]{left,right});
                    inserted = true;
                }
                resList.add(interval);
            }
            else if(interval[1]<left){
                resList.add(interval);
            }
            else{
                //求并集
                left = Math.min(left,interval[0]);
                right = Math.max(right,interval[1]);
            }
        }
        //循环完了还没插入
        if (!inserted) {
            resList.add(new int[]{left, right});
        }
        int[][] ans = new int[resList.size()][2];
        for(int i=0 ;i<resList.size();i++){
            ans[i] = resList.get(i);
        }
        return ans;
    }

    public int[][] generateMatrix(int n) {
        int[][] martix= new int[n][n];
        int j=0;
        int left =0 ,right =n-1,up = 0,down = n-1;
        while (j<n*n){ //可使用i<n*n作为循环条件
            for(int i =left;i<=right;i++){
                martix[up][i] = ++j;
            }
            up++;
            for(int i = up;i<=down;i++){
                martix[i][right] = ++j;
            }
            right--;
            for(int i = right;i>=left;i--){
                martix[down][i] = ++j;
            }
            down--;
            for(int i = down;i>=up;i--){
                martix[i][left] = ++j;
            }
            left++;
        }
        return martix;
    }
    public void setZeroes1(int[][] matrix) {
        //复杂度m乘n一般是方阵，m+n一般是两个数组int[m]int[n]
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] column = new boolean[n];//记录该行列是否应该置零
        for(int i =0;i<m;i++){
            for(int j =0;j<n;j++){
                if(matrix[i][j]==0){
                    row[i] = true;
                    column[j] =true;
                }
            }
        }
        for(int i =0;i<m;i++){
            for (int j = 0; j < n; j++) {
                if(row[i]||column[j]) matrix[i][j] = 0;
            }
        }
        return;
    }
    public void setZeroes(int[][] matrix) {
//        方法1需要使用两个数组来储存该行列是否应该置零
//        优化，使用第一行和第一列 来储存是否应该置零,  再采用两个变量储存第一行和第一列是否需要置零
        int m = matrix.length;
        int n = matrix[0].length;
        boolean rowFlag =false,columnFlag = false;
        for(int i =0;i<m;i++){
            if(matrix[i][0]==0) columnFlag =true;
        }
        for (int j=0;j<n;j++){
            if(matrix[0][j]==0) rowFlag = true;
        }
        for(int i =1;i<m;i++){
            for(int j =1;j<n;j++){
                if(matrix[i][j]==0){
                    matrix[i][0]=0;
                    matrix[0][j]=0;
                }
            }
        }
        for(int i =1;i<m;i++){
            for (int j = 1; j < n; j++) {
                if(matrix[i][0]==0||matrix[0][j]==0) matrix[i][j] =0;
            }
        }
        //最后处理第一行和第一列
        if(columnFlag){
            for(int i=0;i<m;i++){
                matrix[i][0] = 0;
            }
        }
        if(rowFlag){
            for(int j=0;j<n;j++){
                matrix[0][j] =0;
            }
        }

    }


    public String addBinary(String a, String b) {
        //法二：二进制转十进制求和再转二进制
        int carry =0,sum =0;
        StringBuilder res = new StringBuilder();
        for(int i =a.length()-1,j=b.length()-1;i>=0||j>=0;i--,j--){
            int ai = i>=0? a.charAt(i)-'0' : 0;
            int bj = j >=0? b.charAt(j)-'0' : 0;
            sum = carry +ai + bj;
            carry = sum/2;
            res.append(sum%2);
        }
        if(carry!=0) res.append(carry);
        return res.reverse().toString();
    }
    public String simplifyPath(String path) {
    //        以“/”字符串来分割path字符串，返回string数组，数组中不再有"/",但可能会有空
        String[] names = path.split("/");
        Deque<String> deque = new ArrayDeque<>();
        for(String name:names){
            if(name.equals("..")){
                //栈非空才出，空的话..相当于没用
                //abcd pollLast出d， pop出a
                if (!deque.isEmpty()) deque.pollLast();
            }
            else if(name.length()>0 && ! name.equals(".")) deque.offerLast(name); //==deque.offerLast
        }
        StringBuffer res = new StringBuffer();
        if(deque.isEmpty()) res.append("/"); //空的话有个/
        while (!deque.isEmpty()){
            res.append("/");
            res.append(deque.pollFirst());//拿的时候从前往后拿，所以没用栈
        }
        return res.toString();
    }

    public ListNode partition(ListNode head, int x) {
        ListNode smallHead = new ListNode();
        ListNode small = smallHead;
        ListNode largeHead = new ListNode();
        ListNode large = largeHead;
        while (head!=null){
            if(head.val<x){
                small.next = head;
                small = small.next;
            }
            else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next =null;
        small.next = largeHead.next;
        return smallHead.next;
    }

    public int removeDuplicates(int[] nums) {
        //数组是排号序的，相同元素必然连续，所以可以用双指针
        int slow =2,fast=2;
        for(fast=2;fast< nums.length;fast++){
            if(nums[fast] != nums[slow-2]){
                nums[slow]=nums[fast];
                slow++;
            }
        }
        return slow;
    }
    public List<Integer> grayCode(int n) {
        List<Integer> res = new ArrayList<>();
        res.add(0);
        for(int i=1;i<=n;i++){ //n位2进制
            int m = res.size();
            for(int j = m-1;j>=0;j--){
                res.add(res.get(j) | (1<< (i-1)));  //将之前的内容倒徐装进去，使用或运算，将高位（i-1位）从0变成1，同时低位不变
            }
        }
        return res;
    }
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null&&q==null) return true;
        else if(p==null||q==null) return false;
        else if(p.val!=q.val) return false;
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
    public boolean isSameTree1(TreeNode p, TreeNode q) {
        if(p==null&&q==null) return true;
        if(p==null||q==null) return false;
        //变长数组扩容涉及重新分配内存和复制数据，容量增长一般倍增
        Queue<TreeNode> queue1 = new LinkedList<>();//linkedlist是一个类，这相当于类的无参构造
        //linkedList是一个双向链表,实现了list接口和deuqe接口
        // 父类 父类对象等于new一个子类 //Aniaml aniaml = new Cat();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(p);queue2.offer(q);
        while (!queue1.isEmpty() && !queue2.isEmpty()){
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();
            if(node1.val!=node2.val) return false;
            TreeNode left1 = node1.left ,right1 = node1.right;
            TreeNode left2 = node2.left ,right2 = node2.right;
            if(left1==null^left2==null) return false;
            if(right1==null^right2==null) return false;
            if(left1!=null) queue1.offer(left1);
            if(right1!=null) queue1.offer(right1);
            if(left2!=null) queue2.offer(left2);
            if(right2!=null)queue2.offer(right2);

        }
        return queue1.isEmpty()&&queue2.isEmpty();
    }
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<List<Integer>> res = new LinkedList<>();
        if(root==null) return res;
        boolean leftToRight = false;
        while (!queue.isEmpty()){
            List<Integer> tempList = new LinkedList<>();
            int size = queue.size();
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();
                tempList.add(node.val);
                if(node.left!=null)queue.add(node.left);
                if(node.right!=null) queue.add(node.right);
            }
            if(leftToRight) Collections.reverse(tempList);
            leftToRight = !leftToRight;
            res.add(tempList);
        }
        return res;
    }
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        //接口 对象 = new 接口实现类,接口类型变量可以指向(引用)任何实现了该接口的对象实例
        List<List<Integer>> res = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()&&root!=null){
            int size = queue.size();
            List<Integer> temp = new LinkedList<>();
            for(int i=0;i<size;i++){
                TreeNode node = queue.poll();
                temp.add(node.val);
                if(node.left!=null) queue.offer(node.left);
                if(node.right!=null) queue.offer(node.right);
            }
            res.add(0,temp);
        }
        return res;
    }
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> temp = new ArrayList<>();
            for(int j =0;j<=i;j++){  //第0行右1个元素，第1行2个...
                if(j==0||j==i) temp.add(1);
                else temp.add(res.get(i-1).get(j-1)+res.get(i-1).get(j));
            }
            res.add(temp);
        }
        return res;
    }
    public List<Integer> getRowa(int rowIndex) {//滚动数组优化空间复杂度
        List<Integer> pre = new ArrayList<>();
        for(int i=0;i<=rowIndex;i++){
            List<Integer> cur = new ArrayList<>();
            for(int j=0;j<=i;j++){
                if(j==0||j==i) cur.add(1);
                else cur.add(pre.get(j-1)+pre.get(j));
            }
            pre = cur;
        }
        return pre;
    }
    public List<Integer> getRow(int rowIndex){
//        每行第i个元素用到上一行i和i-1，所以倒着求保证i-1还是原来的值
        List<Integer> res = new ArrayList<>();
        res.add(1);
        for(int i=0;i<rowIndex;i++){
            res.add(0);//最后一个元素相当于上一行最后一个加0，同时也是为了延长list，否则之后set会出错，同时每行第一个不用set，一定是1
            for(int j= i;j>0;j--){
                res.set(j,res.get(j)+res.get(j-1));
            }
        }
        return res;
    }
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(root,targetSum,res,path);
        return res;
    }
    public void dfs(TreeNode root,int target,List<List<Integer>> res,List<Integer> path){
        //path作为参数传递，传递的是引用，递归回本层的时候值会被改变，所以要remove，（数组，类对象）
        // 而int是值类型，每一层都有一个独立的int变量，回本层的时候值不变（基本数据类型，枚举，string传参时算值类型）
        if(root==null) return;
        target -= root.val;
        path.add(root.val);
        if(root.left==null&& root.right==null&&target==0) res.add(new ArrayList<>(path));
        dfs(root.left,target,res,path); //target作为参数，减当前节点值进入下一层，回来的时候又便会原来的值
        dfs(root.right,target,res,path);
        path.remove(path.size()-1);
    }
    public int maxProfit(int[] prices) {
        //贪心，每次都选择局部最优，通过比较局部最优达到全局最优
        int minPrice = Integer.MAX_VALUE;
        int maxProfit =0;
        for(int i=0;i<prices.length;i++){
            if(prices[i]<minPrice) minPrice = prices[i];
            else if(prices[i]-minPrice>maxProfit) maxProfit =  prices[i]-minPrice;
        }
        return maxProfit;
    }
    class maxProfit{
        public int maxProfit1(int[] prices) {
            //法1，散点图，上升的全要
            int res = 0;
            for(int i=0;i< prices.length-1;i++){
                if(prices[i]<prices[i+1]) res+=prices[i+1] - prices[i];
            }
            return res;
        }
        public int maxProfit2(int[] prices) {
            //法2：动态规划，每天有买或不买两种状态，下一天两个状态的值由前一天的值和今天的决策影响
            //由买或不买也可以推出法3，回溯
            int[][] dp = new int[prices.length][2];
            dp[0][0] = 0;
            dp[0][1] = -prices[0];
            for(int i=1;i<prices.length;i++){
                dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
                dp[i][1] = Math.max(dp[i-1][0]-prices[i],dp[i-1][1]);//持有股票要保证当天一定是钱最多，因为之后是直接加卖出的钱，不考虑成本
            }
            return dp[prices.length-1][0];
        }
        public int maxProfit(int[] prices) {
            int[] pre =  new int[2];
            int[] cur = new int[2];
            pre[0] = 0;pre[1] = -prices[0];
            for(int i=1;i< prices.length;i++){
                cur[0] = Math.max(pre[0],pre[1]+prices[i]);
                cur[1] = Math.max(pre[0]-prices[i],pre[1]);
                pre[0] = cur[0];
                pre[1] = cur[1];
            }
            return cur[0];
        }
        //法四：递归，回溯，也叫暴力搜索。
        int res=0;
        public void dfs(int[] prices,int index,int profit,int status){
            //三种可能：不操作，买入，卖出。卖出前一定要有，买入前手里一定不能有
            //每一天有两种可能，操作或不操作，操作需根据当前的状态，status=0说明手里没有股票，那么操作就是买一张
            if(index == prices.length) {
                res= Math.max(res,profit);
                return;
            }
            dfs(prices,index+1,profit,status);//不操作
            if(status==0) dfs(prices,index+1,profit-prices[index],1);
            if(status==1) dfs(prices,index+1,profit+prices[index],0);
            return;
        }
    }
    public int singleNumbera(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int num:nums){
//            int count  = map.get(i);
//            count == count=null?1:0;
//            map.put(i,count);
//            或map.put(x,map.getOrDefault(x,0)+1)
            if(map.containsKey(num)) map.put(num,2);
            else map.put(num,1);
        }
        for(int i : map.keySet()){
            if(map.get(i)==1) return i;
        }
        return -1;
    }
    public int singleNumberb(int[] nums) {
        int res = 0;
        for (int num:nums){
            res= res^num;  //0异或一个数还等于这个数，两个相同的数异或为0
        }
        return res;
    }
    public int singleNumber(int[] nums) {
        int[] count =  new int[32];
        for(int num:nums){
            for(int j=0;j<32;j++) {
                if (((num >> j) & 1) == 1) count[j]++;//记录每个二进制位 1出现的次数，一个树出现三次，该位一定有三个1
            }
        }int res=0;
        for(int i=0;i<32;i++){
            if(count[i]%3==1) res+=(1<<i);
        }
        return res;
    }
    public void solve(char[][] board) {
        int m = board.length,n=board[0].length;
        for(int i=0;i< m;i++){
            if(board[i][0]=='0') dfs(board,i,0);
            if(board[i][n-1]=='0') dfs(board,i,n-1);//错点：dfs第一列的时候可能会把不是0的
        }
        for(int j=1;j<n-1;j++){
            if(board[0][j]=='0') dfs(board,0,j);
            if(board[m-1][j]=='0') dfs(board,m-1,j);
        }
        for (int i = 0; i < m; i++) {
            for(int j=0;j<n;j++){
                if(board[i][j]=='A') board[i][j] = '0';
                if(board[i][j]=='0') board[i][j]='X';
            }
        }
        return;
    }
    public void dfs(char[][] board,int x,int y){
        if(x<0||x==board[0].length||y<0||y==board.length ||board[x][y]!='0') return; //等于0的话才去看上下左右，否则返回
        board[x][y] = 'A';
        dfs(board,x+1,y);
        dfs(board,x-1,y);
        dfs(board,x,y+1);
        dfs(board,x,y-1);
    }
    public void solve(char[][] board) {
        int m = board.length,n=board[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0;i<m;i++){
            if(board[i][0]=='O'){
                queue.offer(new int[]{i,0});
                board[i][0] = 'A';
            }
            if(board[i][n-1]=='O'){
                queue.offer(new int[]{i,n-1});
                board[i][n-1] = 'A';
            }
        }
        for(int j=1;j<n-1;j++){
            if(board[0][j]=='O'){
                queue.offer(new int[]{0,j});
                board[0][j] = 'A';
            }
            if(board[m-1][j]=='O'){
                queue.offer(new int[]{m-1,j});
                board[m-1][j] = 'A';
            }
        }
        int[] dx = new int[]{1,-1,0,0};
        int[] dy = new int[]{0,0,1,-1};
        while (!queue.isEmpty()){//利用队先入先出的特性，一圈一圈值
            int[] cell = queue.poll();
            int x = cell[0] ,y = cell[1];
            for(int i=0;i<4;i++){
                int nx =x+dx[i];int ny = y+dy[i];
                //看poll出来元素的四个方向，如果是合法并且等于0的，就入队置a
                if(nx<0||nx==board.length||ny<0||ny== board.length||board[nx][ny]!='O') continue;
                else {
                    queue.offer(new int[]{nx,ny});
                    board[nx][ny] = 'A';
                }
            }
        }
        for (int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]=='A') board[i][j] = 'O';
                else if (board[i][j]=='O') {
                    board[i][j] = 'X';
                }
            }
        }
        return;
    }

    boolean[][] dp;
    List<List<String>> res = new ArrayList<List<String>>();
    List<String> path = new ArrayList<String>();
    int n ;
    public List<List<String>> partition(String s) {
        n=s.length();
        dp = new boolean[n][n];//一定要new
        for (int i = 0; i < s.length(); i++) {
            Arrays.fill(dp[i],true);
        }
        for(int i=n-1;i>=0;i++){
            //保证i小于j，所以时矩阵右上部分
            //i+1,j-1在左下，所以应该从下往上算才能递推（当前判断需要找左下，所以左下必须是先找过的）
            for(int j=i+1;j<n;j++){
                dp[i][j]= (s.charAt(i)==s.charAt(j))&&dp[i+1][j-1];
            }
        }
        dfs(s,0);
        return res;
    }
    public void dfs(String s,int start){
        //接收到一个start后，去便利end，看是不是回文找到一个回文就去更深层，返回后移除最后一个，继续遍历end
        if(start==s.length()) res.add(new ArrayList<>(path));
        for(int end = start;end<s.length();end++){
            if(dp[start][end]){
                path.add(s.substring(start,end+1));
                dfs(s,end+1);
                path.remove(path.size()-1);
            }
        }

    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        for(int i=0;i<n;i++){
            int count = 0;//想要回去，一定要走n部
            int sumGas=0,sumCost =0;
            while (count<n){
                int j =(i+count)%n;//回环思路
                sumGas+=gas[j];
                sumCost+=cost[j];
                if(sumGas<sumCost) break;
                count++;
            }
            if(count==n) return i;
            //如果a出发能到b但到不了c，那么b出发一定到不了c
            else i = i+count;//一旦break，count没有加，所以i+count是最后一个能到的地方，下一个地方由for循环+1
        }
        return -1;
    }
    public int canCompleteCircuit(int[] gas, int[] cost){
        //亏空最多的地方最后走，如果所有的油加起来不够花，那么一定到不了
        int los=0,minlos = 0,start=0;
        int n = gas.length;
        for(int i=0;i<n;i++){
            los+=gas[i]-cost[i];
            if(los<minlos){
                start = i+1;
                minlos = los;
            }
        }
        if(los<0) return -1;
        else  return start%n;
    }
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public Node copyRandomList1(Node head) {
        Map<Node,Node> map =  new HashMap<>();
        Node cur = head;
        while (cur!=null){
            map.put(cur,new Node (cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur!=null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }
    Map<Node, Node> map = new HashMap<Node, Node>();
    public Node copyRandomList(Node head){
        //当我们拷贝节点时，「当前节点的随机指针指向的节点」可能还没创建
        //所以选择 进行「当前节点的后继节点」和「当前节点的随机指针指向的节点」拷贝，拷贝完成后将创建的新节点的指针返回
        if(head == null) return head;
        if(!map.containsKey(head)){
            Node newNode = new Node(head.val);
            map.put(head,newNode);
            newNode.next = copyRandomList(head.next);
            newNode.random = copyRandomList(head.random);
        }
        return map.get(head);
    }
    public Node copyRandomList(Node head) {
        //在节点的next后插入他的复制，降低空间复杂度
        for(Node node = head;node!=null;node = node.next.next){
            Node newNode = new Node(node.val);
            newNode.next = node.next;
            node.next = newNode;
        }
        for(Node node = head;node!=null;node = node.next.next){
            node.next.random = node.random==null? null: node.random.next;
        }
        Node newHead = head.next;
//        for(Node node = head;node!=null;node = node.next.next){ 因为修改了next的指向，所以没办法按计划到最后
//            node.next.next = node.next.next==null? null :node.next.next.next;
//        }
        for(Node node =head;node!=null;node = node.next){
            Node newNode = node.next;
            node.next = node.next.next;
            newNode.next = newNode.next==null? null: newNode.next.next;
        }
        return newHead;
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for(int i=1;i<=s.length();i++){//看[0,i-1]能否被表示出来
            for(int j=0;j<i;j++){//拆分成子问题[0,j-1][j,i-1]能否被标示出来
                if(dp[j] && set.contains(s.substring(j,i))) {
                    dp[i] = true;
                    break;//存在一个就行
                }
            }
        }
        return dp[s.length()];
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        boolean[] notFound = new boolean[s.length()];
        return canBreak(s,0,set,notFound);
    }
    public boolean canBreak(String s, int start,Set<String> set,boolean[] notFound){
        if(start==s.length()) return true;
        if(notFound[start]) return false;
        for(int end = start+1;end<=s.length();end++){
            if(set.contains(s.substring(start,end)) && canBreak(s,end,set,notFound)) {
                return true;
            }
        }
        notFound[start] = true;//剪枝
        return false;
    }
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[s.length()];
        queue.offer(0);
        while (!queue.isEmpty()){
            int start = queue.poll();
            if(visited[start]) continue;
            visited[start] = true;
            for(int end = start+1;end<=s.length();end++){//广度优先搜索，每次循环找到当次所有可能的开始位置
                if(set.contains(s.substring(start,end))) {
                    queue.offer(end);
                    if(end == s.length()) return true;
                }
            }
        }
        return false;
    }
    public void reorderList1(ListNode head) {
        //链表不支持下标访问,利用线性表存储该链表
        if(head==null) return;
        List<ListNode> list = new ArrayList<>();
        while (head!=null){
            list.add(head);
            head = head.next;
        }
        int n = list.size();
        int i=0,j = n-1;
        while (i<j){
            list.get(i).next = list.get(j);
            i++;
            if(i==j) break;
            list.get(j).next = list.get(i);
            j--;
        }
        list.get(i).next = null;
    }
    public void reorderList(ListNode head){
        //找到中点
        ListNode slow = head,fast =head;
        while (fast.next!=null&&fast.next.next!=null){
            slow = slow.next;
            fast = fast.next;
        }
        //反转后半链表,非递归使用双指针
        ListNode prev = null , curr = slow.next;
        slow.next=null;//注意，否则链表有环
        while (curr != null){
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        } //现在prev是头节点
        ListNode l1 = head,l2 = prev;
        while (l1!=null && l2 != null ){
            ListNode temp1 = l1.next;
            ListNode temp2 = l2.next;
            l1.next = l2;
            l2.next = temp1;
            l1 = temp1;
            l2 = temp2;
        }
    }

    class LRUCache {
        //需求：快速查，得到一个key能找到对应的缓存内容
        //2.快速增删，改查后要挪到最前面，超范围要删掉最后面，所以需要一个顺序（链表）
        class DLinkedNode{
            //内部类：可以直接访问外部类的成员，包括私有成员
            //隐藏实现细节，提高封装性。    创建内部类实例时需要依赖外部类实例    不依靠修饰符，作用域受限于外部类
            int key;
            int val;
            DLinkedNode prev;
            DLinkedNode next;
            public DLinkedNode(){};
            public DLinkedNode(int key,int val){
                this.key = key;
                this.val = val;
            }
        }
        //找双向链表的头尾
        private DLinkedNode head,tail;//可以被本类中的方法访问 ，子类不能访问
        //只用哈希表的话不好移动，双向链表保证快插头，快删尾。哈希表用于快速定位
        //每次发生移动也不用更新map中val的值，因为改变的是val中的指针
        private Map<Integer,DLinkedNode> map = new HashMap<>();
        private int size;
        private int capacity;
        public LRUCache(int capacity) {
//            DLinkedNode head = new DLinkedNode();  这样创建无法被本类中别的方法访问
            head  = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
            this.capacity = capacity;//把传进来的这个capacity赋值给本类实例的capacity属性
            this.size = 0;
        }
        public int get(int key){
            DLinkedNode node = map.get(key);
            if(node==null) return -1;
            moveToHead(node); //拿到key去map中找节点，通过指针移动这个节点的位置到链表头
            return node.val;
        }
        public void put(int key,int val){
            DLinkedNode node = map.get(key);
            if(node == null){
                DLinkedNode newNode = new DLinkedNode(key,val);
                map.put(key,newNode);
                addToHead(newNode);
                size++;
                if(size>capacity){
                    DLinkedNode needToRemove = removeTail();
//                    注意删除也要删除哈希表中对应的项
                    map.remove(needToRemove.key);
                    size--;
                }
            }
            else {
                node.val = val;
                moveToHead(node);
            }
        }
        private void addToHead(DLinkedNode node){
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
        private DLinkedNode removeTail(){
            DLinkedNode res = tail.prev;
            res.prev.next = tail;
            tail.prev = res.prev;
            return res;
        }
        private void moveToHead(DLinkedNode node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            addToHead(node);
        }

        public ListNode sortList(ListNode head){
            //归并排序，分治思想。注意找到mid后要置null
            //时间复杂度nlogn :分割复杂度logn,每个合并n
            if(head==null||head.next==null) return head;
            ListNode slow =head, fast = head;
            while (fast.next!=null&&fast.next.next!=null){
                slow = slow.next;
                fast = fast.next.next;
            }
            ListNode mid = slow;
            ListNode next = mid.next;
            mid.next = null;//保证下次递归时拥有这却边界
            ListNode l1 = sortList(head);
            ListNode l2 = sortList(next);
            ListNode sorted = merge(l1,l2);
            return sorted;
        }
        public ListNode merge(ListNode l1,ListNode l2){
            if(l1==null) return l2;
            else if(l2==null) return l1;
            else if(l1.val<l2.val) {
                l1.next = merge(l1.next,l2);
                return l1;
            }
            else {
                l2.next = merge(l1,l2.next);
                return l2;
            }
        }
        public ListNode sortList(ListNode head){
            ListNode node = head;
            int len=0;
            while (node!=null){
                node = node.next;
                len++;
            }
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            for(int sublen = 1;sublen<len; sublen *= 2){
                ListNode cur = dummy.next;
                ListNode tail = dummy;
                while (cur !=null){
                    ListNode l1 = cur;
                    ListNode l2 = split(l1,sublen);
                    cur = split(l2,sublen);

                    ListNode merged = merge(l1,l2);
                    tail.next = merged;
                    while (tail.next!=null) tail = tail.next;
                }
            }
            return dummy.next;
        }
        ListNode split(ListNode head, int n) {
//            从head开始进行分割，返回分割后的下一个节点,记得要断链
            for(int i =1;i<n&&head!=null;i++){
                head = head.next;
            }
            ListNode next = null;
            if(head!=null) {
                next = head.next;
                head.next = null;
            }
            return next;
        }
        public int evalRPN(String[] tokens) {
            int n = tokens.length;
            Stack<Integer> stack = new Stack<>();
            for(String token:tokens){
                if(!("+".equals(token)||"-".equals(token)||"*".equals(token)||"/".equals(token))){
                    stack.push(Integer.parseInt(token));
                }
                else {
                    int num2 = stack.pop();//先出的是右值
                    int num1 = stack.pop();
                    //switch相当于if（==），同时swich具有穿透性，匹配到一个后面的全执行
                    switch (token){
                        case "+":
                            stack.push(num1+num2);
                            break;
                        case "-":
                            stack.push(num1-num2);
                            break;
                        case "*":
                            stack.push(num1*num2);
                            break;
                        case  "/":
                            stack.push(num1/num2);
                            break;
                        default:
                    }
                }
            }
            return stack.pop();
        }
        public int maxProduct(int[] nums) {
            int len = nums.length;
            int[] maxF = new int[len];
            int[] minF = new int[len];
            maxF[0] = nums[0];
            minF[0] = nums[0];
            for(int i=1;i<len;i++){
                maxF[i] = Math.max(maxF[i-1]*nums[i], Math.max(minF[i-1]*nums[i],nums[i]) );
                minF[i] = Math.min(maxF[i-1]*nums[i], Math.min(minF[i-1]*nums[i],nums[i]) );
            }
            int ans = maxF[0];
            for(int i=1;i<len;i++){
                ans = Math.max(ans,maxF[i]);
            }
            return ans;
        }
        public int maxProduct(int[] nums) {
            int len = nums.length;
            int mx = nums[0] ,mn = nums[0];
            int ans = mx;
            for(int i=1;i<len;i++){
                int maxF = mx,minF = mn;//只用两个变量的话，求mn时会被mx影响
                mx = Math.max(maxF*nums[i], Math.max(minF*nums[i],nums[i]));
                mn = Math.min(maxF*nums[i], Math.min(minF*nums[i],nums[i]));
                ans = Math.max(mx,ans);
            }
            return ans;
        }
        public String reverseWords(String s) {
            s = s.trim();
            int j = s.length()-1;
            int i = j;
            StringBuffer sb = new StringBuffer();
            while (i>=0){
                while(i>=0 && s.charAt(i) !=' ') i--;
                sb.append(s.substring(i+1,j+1)+" ");
                while (i>=0 && s.charAt(i) ==' ') i--;
                j = i;
            }
            return res.toString().trim();
        }
        public int findMin(int[] nums) {
            //想折线图
            int low =0 ,high = nums.length-1;
            while (low<=high){//如果小于等于，最后相等时会进入else移动low，超过high，而根据if判断，high的右边一定没有解，所以返回high
                int mid = low+(high-low)/2;//只能mid和high比，mid和low比0123要左面3012要右面，本质在于部分有序，不一定从有序那部分继续找而是要找最小的
                if(nums[mid]<nums[high]) high = mid; //不是mid+1，要保证小的哪个被留下来
                else low = mid+1;
            }
            return nums[high];//严格小于返回low或者小于等于返回high
        }
        class MinStack {
            Stack<Integer> stack;
            Stack<Integer> minStack;//辅助栈，每一个栈顶元素都在辅助栈中有一个最小值

            public MinStack() {
                stack = new Stack<>();
                minStack = new Stack<>();
            }

            public void push(int val) {
                stack.push(val);
                minStack.push(Math.min(val,minStack.peek()));
            }

            public void pop() {
                stack.pop();
                minStack.pop();
            }

            public int top() {
                return stack.peek();
            }

            public int getMin() {
                return minStack.peek();
            }
        }
        class MinStack {
            Stack<Long> stack;
            long min;
            public MinStack() {
                stack = new Stack<>();
            }

            public void push(int val) {
                if(stack.isEmpty()){
                    min = val;
                    stack.push(0L);
                }
                else {
                    //这两行不能换顺序，一定是先入栈再更新最小值
                    stack.push((long)val-min);
                    min = Math.min(min,(long)val);
                }
            }

            public void pop() {
                if(stack.peek()<0) min = min - stack.pop();
                else stack.pop();
            }

            public int top() {
                if(stack.peek()<0) return (int)min;
                return (int)(stack.peek()+min);
            }

            public int getMin() {
                return (int)min;
            }
        }
        public String mergeAlternately(String word1, String word2) {
            int m =word1.length();
            int n = word2.length();
            int i=0,j=0;
            StringBuffer sb = new StringBuffer();
            while (i<m||j<n){
                if(i<m) {
                    sb.append(word1.charAt(i));
                    i++;
                }
                if(j<n){
                    sb.append(word2.charAt(j));
                    j++;
                }
            }
            return sb.toString();
        }

    }
    public int trap1(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        for (int i=1;i<n;i++){
            leftMax[i] = Math.max(leftMax[i-1],height[i]);
        }
        rightMax[n-1] = height[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(rightMax[i+1],height[i]);
        }
        int res = 0;
        for(int i=0;i<n;i++){
            res+= Math.min(leftMax[i],rightMax[i]) - height[i];
        }
        return res;
    }
    public int trap2(int[] height) {
        int n = height.length;
        int left =0,right = n-1;
        int leftMax = height[left];
        int rightMax = height[right];
        int res=0;
        while (left<=right){
            leftMax = Math.max(leftMax,height[left]);
            rightMax = Math.max(rightMax,height[right]);
            if(height[left]<height[right]){
                res += leftMax-height[left];
                left++;
            }
            else {
                res+=rightMax-height[right];
                right--;
            }
        }
        return res;
    }
    public int trap3(int[] height) {
        //单调递减栈
        Stack<Integer> stack = new Stack<>();
        int index=0;
        int res =0;
        while (index<height.length){
            while (!stack.isEmpty()&&height[stack.peek()]<height[index]){
                int low = stack.pop();//把low当底，如果拿出low之后栈空了，说明没有左边界，那么也没法接水
                if(stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int wide = index-left-1;
                res+= (Math.min(height[left],height[index])-height[low])*wide;
            }
            stack.push(index);
            index++;
        }
        return res;
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0,j=n-1;
        while (i<m && j>=0){
            if(matrix[i][j]==target) return true;
            if(matrix[i][j]>target) j--;
            else i++;
        }
        return false;
    }
    class DiameterOfBinaryTree{
        int res = 0;
        public int diameterOfBinaryTree(TreeNode root) {
            depth(root);
            return res;
        }
        //将叶节点深度定位1
        public int depth(TreeNode root){
            if(root==null) return 0;
            int leftLen = depth(root.left);
            int rightLen = depth(root.right);
            res = Math.max(leftLen+rightLen,res);
            return Math.max(leftLen,rightLen)+1;
        }
    }
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        while (root!=null || !stack.isEmpty()){
            while (root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            k--;
            if(k==0) break;
//            res.add(root.val);
            root = root.right;
        }
        return root.val;
    }
    public int kthSmallest(TreeNode root, int k) {
        MyBst myBst = new MyBst(root);
        while (root!=null){
            int left = myBst.getNodeNum(root.left);
            if(left==k-1) return root.val;
            if(left<k-1) {
                root = root.right;
                k = k- left-1;
            }
            else {
                root = root.left;
            }
        }
        return 0;
    }
    class MyBst{
        TreeNode root;
        Map<TreeNode,Integer> nodeNum;
        MyBst(TreeNode root){
            this.root = root;
            this.nodeNum = new HashMap<>();
            countNodeNum(root);
        }
        //计算以当前root为根的二叉树有多少节点
        private int countNodeNum(TreeNode root){
            if(root==null) return 0;
            nodeNum.put(root,countNodeNum(root.left)+countNodeNum(root.right)+1);
            return nodeNum.get(root);
        }
        public int getNodeNum(TreeNode root){
            return nodeNum.getOrDefault(root,0);
        }
    }

























}
