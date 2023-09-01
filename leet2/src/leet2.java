import java.util.*;

public class leet2 {
    /**
     * Definition for singly-linked list.*/
    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public class ListNode {
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

    public boolean stoneGame(int[] piles) {
        int a = maxScore(piles, 0, piles.length - 1);
        int sum = 0;
        for (int p : piles) {
            sum += p;
        }
//        int b = Arrays.stream(piles).sum()-a;
        return a > sum - a;
    }

    //从当前区间最多能取多少分
    public int maxScore(int[] arr, int l, int r) {
        if (l == r) return arr[l];
        if (r - l == 1) return Math.max(arr[l], arr[r]);
        //如果从左侧取，最多能得多少分和从右侧
        int lResult = 0, rResult = 0;
        //取完左侧后，对方取，给你剩下的一定是分少的
        lResult = arr[l] + Math.min(maxScore(arr, l + 2, r), maxScore(arr, l + 1, r - 1));
        rResult = arr[r] + Math.min(maxScore(arr, l + 1, r + 1), maxScore(arr, l, r - 2));
        return Math.max(lResult, rResult);
    }

    //当前采取最优策略能领先多少分
    public int maxScore1(int[] arr, int l, int r) {
        if (l == r) return arr[l];
        int lResult = arr[l] - maxScore1(arr, l + 1, r);
        int rResult = arr[r] - maxScore1(arr, l, r - 1);
        return Math.max(lResult, rResult);
    }
    //递归
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return sum == root.val;
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
    //广度优先搜索
    public boolean hasPathSum1(TreeNode root, int sum) {
        Queue<TreeNode> qNode = new LinkedList<>();
        Queue<Integer> qVal = new LinkedList<>();
        if(root==null) return false;
        qNode.add(root);
        qVal.add(root.val);
        while (!qNode.isEmpty()) {
                root = qNode.poll();
                int temp = qVal.poll();
                if(root.left==null && root.right==null){
                    if(temp==sum) return true;
                    continue;
                }
                if(root.left!=null){
                    qNode.add(root.left);
                    qVal.add(temp+ root.left.val);
                }
                if(root.right!=null){
                    qNode.add(root.right);
                    qVal.add(temp+ root.right.val);
                }
        }
        return false;
    }
    public ListNode deleteNode1(ListNode head, int val) {
        //因为要删除节点所以需要双指针  prev.next= curr.next
        //否则找到要删除的节点后，没办法找到前驱节点
        if(head.val==val) return head.next;
        ListNode cur = head.next , prev = head;
        while (cur.val!=val && cur!=null){
            prev = cur;
            cur = cur.next;
        }
        prev.next = cur.next;
        return head;
    }
    public ListNode deleteNode(ListNode head, int val){
//        if(head == null) return head;
        //终止条件
        if(head.val==val) return head.next;
        //相当于如果找到了，head.next = head.next.next;
        //跳过当前等于val的节点,直接将head.next指向递归调用返回的结果。即删除当前节点
        head.next = deleteNode(head.next,val);
        return head;
    }
    //KMP
    void getNext(char[] ch,int[] next){
        //找前缀和后缀相同的最大数
        next[1] = 0;
        int i=1,j=0; //i:当前主串正在匹配的字符位置，也是next数组的索引
        while(i <= next.length){
            if(j==0||ch[i]==ch[j])  next[++i] = ++j;
            else j= next[j];
        }
    }
        //递归 - 》动态规划
    public int rob(int[] nums) {
        int index = nums.length-1;
        return maxMoney(nums,index);
    }
    public int maxMoney(int[] nums,int index){
        if(index==0) return nums[0];
        if(index==1) return Math.max(nums[0],nums[1]);
        return Math.max(maxMoney(nums,index-2)+nums[index],maxMoney(nums,index-1));
    }
//    public int rob(int[] nums){
//        int[] dp = new int[nums.length];
//        if(nums.length==1) return nums[0];
//        dp[0] = nums[0];
//        dp[1] = Math.max(nums[1],nums[0]);
//        for(int i=2;i< nums.length;i++){
//            dp[i] = Math.max(nums[i]+dp[i-2],dp[i-1]);
//        }
//        //优化：用两个变量储存即可
//        return dp[nums.length-1];
//    }
//    public int rob(int[] nums){
//        if (nums.length==1) return nums[0];
//        if (nums.length==2) return Math.max(nums[0],nums[1]);
//        return Math.max(maxMoney(nums,0, nums.length-2),maxMoney(nums,1, nums.length-1));
//    }
    public int maxMoney(int[] nums,int start, int end){
        int first = nums[start] , second = Math.max(nums[start],nums[start+1]);
        for(int i=start+2;i<=end;i++){
            int temp = second;
            second = Math.max(nums[i]+first,second);
            first = temp;
        }
        return second;
    }
    public int rob(TreeNode root) {
        int[] i  = dfs(root);
        return Math.max(i[0],i[1]);
    }
    public int[] dfs(TreeNode node){
//       int[] {select 最优解，notselect 最优解}
        if(node == null) return new int[] {0,0};
        int[] l = dfs(node.left);
        int[] r = dfs(node.right);
        //选了当前节点，那么左右子节点都不能选了
        //l[0]是选，l[1]是不选
        int select = node.val + l[1] + r[1];
        int notSelect = Math.max(l[0],l[1]) + Math.max(r[0],r[1]);
        //当前节点选了能偷多少钱，不选能偷多少钱
        return new int[] {select,notSelect};
    }
    public String predictPartyVictory(String senate) {
        int n = senate.length();
        Queue<Integer> rqueue = new LinkedList<>();
        Queue<Integer> dqueue = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(senate.charAt(i)=='R'){
                rqueue.add(i);
            }
            else dqueue.add(i);
        }
        while (!rqueue.isEmpty() && !dqueue.isEmpty()){
            int rPoll = rqueue.poll();
            int dPoll = dqueue.poll();
            //如果先是r那么d被禁
            if(rPoll<dPoll){
                rqueue.add(rPoll+n);
            }
            else dqueue.add(dPoll+n);
        }
        return dqueue.isEmpty()?"R":"D";
    }
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Integer[] idx1 = new Integer[n];
        Integer[] idx2 = new Integer[n];
        for(int i = 0; i<n; i++){
            idx1[i] = i;
            idx2[i] = i;
        }
        //使用了Arrays.sort方法对idx1数组进行自定义排序
        //若nums1[i]-nums1[j] > 0,则i再j后面
        //把idx储存的元素当作索引，就可以升序的找到nums的值
        Arrays.sort(idx1, (i,j) ->nums1[i]-nums1[j]);
        Arrays.sort(idx2, (i,j) ->nums2[i]-nums2[j]);
        int left =0,right = n-1;
        int[] ans = new int[n];
        for (int i =0;i<n;i++){
            //nums1[idx1[i]]:nums1中最小的元素
            if(nums1[idx1[i]]>nums2[idx2[left]]){
                //让这个最小的找到自己该去的位置，即可以与nums2最小元素对应的位置
                ans[idx2[left]] = nums1[idx1[i]];
                left++;
            }
            else {
                //费元素放到nums中最大元素对应的位置
               ans[idx2[right]] = nums1[idx1[i]];
               right--;
            }
        }
        return ans;
    }
//    进一步优化，nums1可以直接排序，把它放到对应nums2的位置即可
//    再优化,直接把nums2对应位置的元素替换，降低空间复杂度
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Integer[] idx2 = new Integer[n];
        for(int i = 0; i<n; i++) idx2[i] = i;
        Arrays.sort(nums1);
        Arrays.sort(idx2, (i,j) ->nums2[i]-nums2[j]);
        int left =0,right = n-1;
        int[] ans = new int[n];
        for (int i =0;i<n;i++){
            if(nums1[i]>nums2[idx2[left]]){
                //让这个最小的找到自己该去的位置，即可以与nums2最小元素对应的位置
                ans[idx2[left]] = nums1[i];
                left++;
            }
            else {
                //费元素放到nums中最大元素对应的位置
                ans[idx2[right]] = nums1[i];
                right--;
            }
        }
        return ans;
    }
    public int[] advantageCount(int[] nums1, int[] nums2){
        int n =nums1.length;
        int[] sortedb = nums2.clone();
        Arrays.sort(sortedb);
        //使用queue是因为有的元素可能相同
        Map<Integer,Queue<Integer>> bmap= new HashMap<>();
        for(int b : nums2){
            bmap.put(b,new LinkedList<>());
        }
        Arrays.sort(nums1); int j =0 ;
        Queue<Integer> aq = new LinkedList<>();
        for(int a: nums1){
            if(a > sortedb[j]){
                bmap.get(sortedb[j]).add(a);
                j++;
            }
            else aq.add(a);
        }
        int[] ans = new int[n];
        for(int i=0;i<n;i++){
            if(bmap.get(nums2[i]).size()>0){
                ans[i] = bmap.get(nums2[i]).poll();
            }
            else ans[i] = aq.poll();
        }
        return ans;
    }
    public int[] advantageCount(int[] nums1, int[] nums2){
        int n =nums1.length;
        int[] sortedb = nums2.clone();
        Arrays.sort(sortedb);
        Map<Integer,Integer> bmap= new HashMap<>();
        int i=0;
        //用map储存下标
        for(int b : nums2){
            bmap.put(b,i);
            i++;
        }
        int[] ans = new int[n];
        Arrays.sort(nums1); int j =0 ;            int right =n-1;
        for(int a: nums1){
            if(a > sortedb[j]){
                ans[bmap.get(sortedb[j])] = a;
                j++;
            }
            else {
                ans[bmap.get(sortedb[right])] = a;
                right--;
            }
        }
        return ans;
    }
    public int[] InsertionSort(int[] nums) {
//        在组内排序过的数据中，倒序找合适的位置
        for(int i=1;i<nums.length;i++){
            int j = i-1;
            int currentValue = nums[i];
            while (j>=0&&nums[j]>currentValue){
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = currentValue;
        }
        return nums;
    }
    public ListNode insertionSortList(ListNode head) {
        if(head == null) return head;
//        哑节点是为了便于在 head 节点之前插入节点
        ListNode dummyHead  = new ListNode(0);
        dummyHead.next = head;
        ListNode lastSorted = head;
        ListNode curr = head.next;
        while (curr!= null){
//            链表只能从前往后查找，没办法直接和排好序的最后一个比然后从前往后找
            if(lastSorted.val<=curr.val){
//                lastSorted移动到下一个节点
                lastSorted = lastSorted.next;
            }else {
                ListNode prev = dummyHead;
                while (prev.next.val<curr.val){
                    prev = prev.next;
                }
                //注意插入操作，沃出现了死循环
//                此时已找到curr应该在的位置，即prev的下一个节点
//                进行curr的插入操作，要先把prev的下一个节点原来的储存起来
                lastSorted.next = curr.next;//curr的下一个节点,这个节点是在本次for中没有变化过的,注意也要在curr改变(插入操作之前)进行
                curr.next = prev.next;
                prev.next = curr;
//                不能使用底下注释掉这一行的方法，因为原来lastsorted的下一个是原来的curr，而经过插入操作后，curr发生了变化
//                直接把下面这一行拿到插入之前也不行,理由是一样的
//                拿到插入之前,还是相当于找curr作为新的lastsoted,进行插入操作后cur会发生变化
//                lastSorted = lastSorted.next;
            }
            curr = lastSorted.next;
        }
        return dummyHead.next;
    }
    //归并排序
    public void mergeSort(int[] nums){
        if(nums.length <2) return;
        int mid = nums.length /2;
        int[] left = Arrays.copyOfRange(nums,0,mid); //包含头(0)不包含尾mid
        int[] right = Arrays.copyOfRange(nums,mid, nums.length);
        mergeSort(left); //传入一个数组后做两件事 1.拆分成左右 2. 左右合并再赋值到传入的那个数组
        mergeSort(right);
        //分到最小后调用merge合并作为新的left传入
        merge(nums,left,right);
    }
    public void merge(int nums[],int[] left,int[] right){
        int i=0,j=0,k=0;
        while (i< left.length&&j< right.length){
            if(left[i]<right[j]) nums[k++] = left[i++];
            else nums[k++] = right[j++];
        }
        while (i< left.length){
            nums[k++] = left[i++];
        }
        while (j< right.length){
            nums[k++] = right[j++];
        }
    }
    //基数排序
    public int[] radixSort(int nums[]){
        int n = nums.length;
        int max = nums[0];
        for (int i=1;i<n;i++){
            max = Math.max(max,nums[i]);
        }
//        找出最大数的位数
        int maxDigit=0;
        while (max!=0){
            max = max/10;
            maxDigit++;
        }
        ArrayList<Integer>[] bucketList = new ArrayList[10];
        for(int i =0;i<10;i++){
            bucketList[i] = new ArrayList<>();
        }
        int mod = 1;//用于取出对应位上的数字
        //i相当于第i位数字
        for(int i=0;i<maxDigit;i++,mod*=10){
//            遍历原始数据，入桶
            for(int j =0;i<nums.length;j++){
                //除一百再取余100就能得到百位数字
                int digit = (nums[j]/mod)% 10;
                bucketList[digit].add(nums[j]);
            }
            int index=0;
            //取出第k个桶里的元素放入原数组,再清空原桶
            for(int k =0;k<10;k++){
                for(int value:bucketList[k])
                    nums[index++] = value;
                bucketList[k].clear();
            }
        }
        return nums;
    }
    //计数排序
    public static int[] countingSort(int[] nums){
        int max =nums[0],min=nums[0];
        for(int i=1;i< nums.length;i++){
            if(nums[i]<min) min = nums[i];
            if(nums[i]>max) max = nums[i];
        }
        int bias = 0-min;
        int[] countArr = new int[max-min+1];
        for(int i=0;i< nums.length;i++){
            countArr[nums[i]+bias]++;
        }
        int index = 0;//计数数组下标索引
        for(int i=0;i< nums.length;i++){
            while(countArr[index]==0){
                index++;
            }
            //break 会跳出当前整个代码块的执行。
            //continue 只会跳出当前循环中的本次迭代。
                nums[i] = index-bias;
                countArr[index]--;
        }
        return nums;
    }
    //      桶排序
    public void bucketSort(int[] nums, int bucketSize) {
        //      获得桶的数量
        int max =nums[0],min=nums[0];
        for(int i=1;i< nums.length;i++){
            if(nums[i]<min) min = nums[i];
            if(nums[i]>max) max = nums[i];
        }
        int bucketCount  = (max-min)/bucketSize+1;
        //List是一个接口,ArrayList是一个实现了List接口的类,提供了List接口定义的各种方法的具体实现,如*数组扩容、索引访问等。
        //      构建桶
        //范型,可以添加类型检查
        //ArrayList区别Array 1. 动态扩容 2.有一系列便利的方法来添加、删除、定位元素,如add(), remove(), get()等
        //ArrayList可以高效地索引任意元素,时间复杂度为O(1)。相比链表LinkedList的O(n),查找更快。
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<>();
        for(int i=0; i< bucketCount;i++){
//            使用add()方法往ArrayList里添加元素时,不需要指定索引,它会自动添加到末尾,所以i变量是可以省略的
            buckets.add(new ArrayList<>());
        }
        //      将原始数据分配到桶中
        for (int i=0;i<nums.length;i++){
            int index = (nums[i]-min)/bucketSize;
            buckets.get(index).add(nums[i]);
        }
//        桶中数据内部排序
        for(ArrayList<Integer> bucket : buckets){
//            Collection.sort()可以对实现List接口的不同集合进行排序
            Collections.sort(bucket);
        }
        int index =0 ;
        for(ArrayList<Integer> bucket :buckets){
            for(int value :bucket){
                nums[index++] = value;
            }
        }
    }
//    冒泡排序，每次排序找到当前剩余元素的最大元素，移到最后
    public void bubbleSort(int[] nums){
        int n = nums.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-1-i;j++){
                if(nums[j]>nums[j+1]) {
                    int temp = nums[j+1];
                    nums[j+1] =nums[j];
                    nums[j] =temp;
                }
            }
        }
    }
    public void quickSort(int[] arr, int low, int high) {
        if(low>=high) return;
        int pivotIndex = partition(arr,low,high);
        quickSort(arr,low,pivotIndex-1);
        quickSort(arr,pivotIndex+1,high);
    }
    public int partition(int[] arr,int start,int end){
        //使用pivot(枢轴)作为基准进行分割
//        int pivot =(int)(start+ Math.random() *(end - start + 1));
//        swap(arr,pivot,end);  //现在end位置存放基准pivot
        int pivot = arr[end];
        int i=start-1; //i:分区指示器（可以认为有多少个元素小于等于pivot, j:遍历指示器
        for(int j=start;j<=end;j++){
            if(arr[j]<=pivot){
                i++;
                swap(arr,i,j);
            }
        }
        return i;//i存放pivot的位置
    }
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public void heapSort(int[] arr){
        int n = arr.length;
        //从最后一个非叶子节点开始往前找
        //构造一个最大堆
        for(int i= (n/2)-1;i>=0;i--){
            heapify(arr,n,i);
        }
//        排序，每次取出当前最大值放到尾部
        for(int i=n-1;i>0;i--){
            //现在根一定是最大值，交换根和最后一个元素，然后把最后一个元素拿出二叉树
            //当前的最后一个元素的索引就是i
            swap(arr,0,i);
            //交换后重新调整，构造二叉堆
            heapify(arr,i,0);
        }

    }
    public void heapify(int[] arr,int n,int i){
        int largest = i;
        //找到i的左右子节点
        int l = 2*i+1;
        int r = 2*i+2;
        if(l<n && arr[largest] < arr[l]){
            largest = l;
        }
        if(r<n && arr[largest] < arr[r]){
            largest = r;
        }
        if(largest != i){
            swap(arr,largest,i);
            //交换后，被换下去那个节点处在一个新的位置，为了保证最大堆，还需要调整他的子树
            heapify(arr, n, largest);
        }
    }

    public void shellSort(int[] arr){
        //o(n^2)，希尔排序基于插入排序，实际上是分组使用插入排序
//        分组方法，一开始一组两个元素，0和n/2
        int n = arr.length;
        int increment = n/2;//缩小增量排序
        int currentval;
        while(increment>0) {
            for (int i = increment; i < n; i++) {
                currentval = arr[i];
//            进行插入排序
                int preIndex = i - increment;
                while (preIndex >= 0 && arr[preIndex] > arr[i]) {
                    arr[i] = arr[preIndex];
                    preIndex -= increment;
                }
                arr[preIndex + increment] = currentval;
            }
            increment = increment / 2;
        }
    }
    //选择排序，每次选择剩余数组中当前最小的，放到当前数组最前面
    public void choiceSort(int[] arr){
        for(int i =0 ;i< arr.length;i++){
            int minIndex = i;
            for(int j = i;i< arr.length;j++){
                if(arr[j]<arr[minIndex]){
                    minIndex = j;
                }
            }
            swap(arr,i,minIndex);
        }
    }



}
