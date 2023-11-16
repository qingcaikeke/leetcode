package doublePointer;

import java.util.*;

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //哑结点可以找到删除节点的前一个节点，也可以防止头节点被删除的情况
        ListNode dummy = new ListNode(0,head);
        ListNode first = head;
        for(int i=0;i<n;i++){
            first = first.next;
        }
        ListNode second = dummy;
        while (first!=null){
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int first = 0;first<n-2;first++){
            if(first!=0&&nums[first]==nums[first-1]) continue;//去重
            int second = first+1,third = n-1;
            while (second<third){
                if(nums[second]+nums[third]==-nums[first]) {
                    res.add(Arrays.asList(nums[first], nums[second], nums[third]));
                    second++;third--;
                    while (second < third && nums[second] == nums[second - 1]) second++;
                    while (second < third && nums[third] == nums[third + 1]) third--;
                }if(nums[second]+nums[third]>-nums[first]) third--;
                if(nums[second]+nums[third]<-nums[first]) second++;
            }
        }
        return res;
    }
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        int best = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < n-2; i++) {
            int left = i+1,right = n-1;
            while (left<right){
                int sum = nums[i]+nums[left]+nums[right];
                if(Math.abs(sum-target) < Math.abs(best-target)){
                    best = sum;
                }
                if(sum==target) return sum;
                if(sum>target) right--;
                if(sum<target) left++;
            }
        }
        return best;
    }
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<len-3;i++){
            if(i!=0 && nums[i]==nums[i-1]) continue;
            for(int j =i+1 ;j<len-2;j++){
                if(j!=i+1 && nums[j]==nums[j-1]) continue;
                int left = j+1,right = len-1;
                while (left<right){
                    long sum  = (long) nums[i]+nums[j]+nums[left]+nums[right];
                    if(sum==target){
                        res.add(Arrays.asList(nums[i],nums[j],nums[right],nums[left]));
                        left++; right--;
                        while (left<right && nums[left]==nums[left-1]) left++;
                        while (left<right && nums[right]==nums[right+1]) right--;
                    }
                    else if(sum<target){
                        left++;
                    }
                    else {
                        right--;
                    }
                }
            }
        }
        return res;
    }

    public ListNode rotateRight(ListNode head, int k) {
        int len =1;
        ListNode pos = head;
        if(head==null) return null;
        while (pos.next!=null){
            pos = pos.next;
            len++;
        }
        pos.next = head;
        //链表倒数第k个节点的下标为n-k；
        int i =0;
        while (i!=len-k%len){
            pos = pos.next;
            i++;
        }
        ListNode newHead = pos.next;
        pos.next = null;
        return newHead;
    }
    public void sortColors1(int[] nums) {
        //法2：计数后，重写数组
        int zeroIndex=0,twoIndex = nums.length-1;
        for (int i = 0; i <= twoIndex; i++) {
            if(nums[i]==0){
                swap(nums,i,zeroIndex);
                zeroIndex++;
            }
            if(nums[i]==2){
                swap(nums,i,twoIndex);
                twoIndex--;//不知道换过来什么，还得再判断，否则换过来的元素被跳过了
                i--;
            }
        }
        return;
    }
    public void sortColors(int[] nums) {
        int zeroIndex =0 ,oneIndex=0;
        for(int i=0;i< nums.length;i++){
            //从前往后扫的关键在于 ，0往前换的时候会换下来一个1
            if(nums[i]==1) {
                swap(nums,i,oneIndex);
                oneIndex++;
            }
            else if(nums[i]==0){
                swap(nums,i,zeroIndex);
                if(zeroIndex<oneIndex){
                    swap(nums,i,oneIndex);
                }
                zeroIndex++;
                oneIndex++;
            }
        }
    }
    public ListNode deleteDuplicates(ListNode head) {
//        1. 一开始,dummy 和 head 引用都指向同一个头节点对象。
//        2. 在循环中,我们通过 head = head.next 改变了 head 引用所指向的对象,让它从当前节点移到了下一个节点。
//        没有改变链表头节点对象本身,而是改变了 head 和 dummy 这两个变量所持有的引用
        ListNode dummy = new ListNode();
        dummy.next = head; //这种写法是因为head可能被删
        ListNode cur = dummy;
        while (cur.next!=null&&cur.next.next!=null){
            if(cur.next.val==cur.next.next.val){
                int x = cur.next.val;
                while (cur.next!=null && cur.next.val==x){
                    cur.next = cur.next.next;
                }
            }
            else cur = cur.next;
        }
        return dummy.next;
    }
    public void mergea(int[] nums1, int m, int[] nums2, int n) {
        //优化，若p2拍完了，p1就不用排了，因为他本来就有序
        //若p1排完了p2还有，就直接放到剩下的位置就行
        int p1 = m-1,p2 = n-1;
        int p = m+n-1;  //p可以被优化掉，使用p1+p2+1
        while (p1>=0&&p2>=0){
            nums1[p--] = nums1[p1]>nums2[p2] ? nums1[p1--] : nums2[p2--];
        }
        while (p2>=0){
            nums1[p--] = nums2[p2--];
        }
    }
    public boolean isPalindrome1(String s) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<s.length();i++){
            if(Character.isLetterOrDigit(s.charAt(i))) stringBuffer.append(Character.toLowerCase(s.charAt(i)));
        }
        int left =0,right = stringBuffer.length()-1;
        while (left<right){
            if(stringBuffer.charAt(left)!=stringBuffer.charAt(right)) return false;
            left++;right--;
        }
        return true;
    }
    public boolean isPalindrome(String s) {
        int left=0,right =s.length()-1;
        while (left<right){
            while (left<right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left<right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            //移动left和right找有效字符的时候可能会越界，即不再满足left<right
            if(left<right && Character.toLowerCase(s.charAt(left))!=Character.toLowerCase(s.charAt(right))) return false;
            left++;right--;
        }
        return true;
    }
    public int longestConsecutive1(int[] nums) {
        int maxLen=0;
        Set<Integer> set = new HashSet<>();//x存在，用set去找x+1是否存在
        for(int num:nums) set.add(num);
        for (int i=0;i< nums.length;i++){//在一个循环里改变循环变量的值后，一定要再次检验这个变量是否合法
            if(!set.contains(nums[i]-1)){
                //如果这个数的前一个数在集合里就不管他，后续或之前找到这个前一个数，这个数也就被考虑了
                int len=1;
                int curNum = nums[i];
                while (set.contains(curNum+1)){
                    len++;
                    curNum++;
                }
                maxLen = Math.max(maxLen,len);
            }
        }
        return maxLen;
    }
    public int longestConsecutive(int[] nums){
        if(nums.length==0) return 0;
        Arrays.sort(nums);
        int res=1;int curlen=1;
        for(int i=1;i< nums.length;i++){
            if(nums[i]-nums[i-1]==1){
                curlen ++;
                res = Math.max(res,curlen);
            }else if(nums[i]==nums[i-1]){
                continue;
            }else {
                curlen=1;
            }
        }
        return res;
    }




}
