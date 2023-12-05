package com.yjy.partionAndSort;

import com.yjy.text;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartionAndSort {
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
    public void swap(int[] nums,int x,int y){
        int temp = nums[x];
        nums[x] = nums[y];
        nums[y] = temp;
    }
    //手撕快排:原理为，先选择一个数，把比他小的放前面，比他大的放后面，然后递归的去排前后两部分
    //分区指示器，其指示的元素及之前的所有元素一定比pivot小，遍历指示器，遍历当前组所有元素
    //具体步骤：1.随机选择pivot和最右元素交换，或者直接选择最右为pivot，初始化分区指示器为-1
    //2.遍历，如果当前元素比pivot小，分区指示器加1，交换当前元素与遍历到的元素
    public void quickSort(int[] arr,int low,int high){
        if(low>=high) return;
        int index = partition(arr,low, high);
        quickSort(arr,low,index-1);
        quickSort(arr,index+1,high);
    }
    public int partition(int[] arr,int start,int end){
        //快排性能与划分的子数组长度密切相关，所有有时候采取随机选取pivot的方式，防止每次都划分成1和n-1
        int pivot = arr[end];
        int back = start-1;
        for(int front = start;front<=end;front++){
            if(arr[front]<=pivot){
                back++;
                int temp = arr[back];
                arr[back] = arr[front];
                arr[front] = temp;
            }
        }
        return back;
    }
    //寻找数组中第k大元素：快速排序，找到pivot看前后有几个元素，只需要去排一部分就行了
    public int findKthLargest(int[] nums, int k) {
        int index = quickSort1(nums,0,nums.length-1,k);
        return nums[index];
    }
    public int quickSort1(int[] nums,int low,int high,int k){
        int index = partition(nums,low, high);
        if(index==nums.length-k) return index;
        else if(index>nums.length-k){
            index = quickSort1(nums,low,index-1,k);
        }
        else if(index<nums.length-k){
            index = quickSort1(nums,index+1,high,k);
        }
        return index;
    }
    //56合并区间：先按左端点排序，然后合并一直到不能合，开启下一轮循环
    public int[][] merge(int[][] intervals) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals,(a,b)->(a[0]-b[0]));
        for(int i=0;i<intervals.length;i++){
            int l  = intervals[i][0],r = intervals[i][1];
            while (i<intervals.length-1 && intervals[i+1][0] <= r){
                r = Math.max(intervals[i+1][1],r);
                i++;
            }
            res.add(new int[]{l,r});
        }
        return res.toArray(new int[0][]);
    }
    //1122.数组相对排序，用了计数排序的思想
    //计数排序也叫基于不比较的排序，适用于元素范围不大，重复数量较多
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length];
        int[] frequency = new int[1001];
        for(int arr: arr1){
            frequency[arr]++;
        }
        int index=0;
        for(int arr:arr2){
            while (frequency[arr]!=0){
                res[index++] = arr;
                frequency[arr]--;
            }
        }
        for(int i=0;i<1000;i++){
            while (frequency[i]!=0){
                res[index++] = i;
                frequency[i]--;
            }
        }
        return res;
    }
    //插入排序
    public int[] insertionSort(int[] nums){
        for(int i=1;i<nums.length;i++){
            int j = i-1;//从后往前移动元素更方便
            int curVal = nums[i];
            while (j>=0 && nums[j]>curVal){
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = curVal;
        }
        return nums;
    }
    //147链表排序：1.插入排序的思想,复杂度o(n2)
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0,head);
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur!=null){
            if(cur.val>=pre.val){
                pre = pre.next;
            }
            else{
                ListNode preIndex = dummy;
                while (preIndex.next.val<cur.val){
                    preIndex = preIndex.next;
                }
                pre.next = cur.next;
                cur.next = preIndex.next;
                preIndex.next = cur;
            }
            cur = pre.next;
        }
        return dummy.next;
    }
    //148归并排序，o（nlogn）分三个步骤：分，排(排是通过最后只剩两个节点，然后合来实现的)，合
    public ListNode sortList(ListNode head){
        if(head==null|| head.next==null) return head;
        ListNode slow = head,fast = head;
        //因为本质上是取了slow.next为中间节点而不是slow，所以循环条件要注意
        //如果是fast和fast.next会出现最后两个节点一直没法分割，栈溢出，或者fast初始化的时候初始为head.next
        while (fast.next!=null&&fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }//slow.next// 为分割后第二个链表的头节点,因为需要断链
        ListNode mid = slow.next;
        slow.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(mid);
        ListNode sorted = merge(l1,l2);
        return sorted;
    }
    //合：合并两个有序链表：递归(没写出来)，迭代
    public ListNode merge(ListNode head1,ListNode head2){
        ListNode dummy = new ListNode(0);
        ListNode pre = dummy;
        ListNode l1 = head1,l2 = head2;
        while (l1!=null&&l2!=null){
            if(l1.val<=l2.val){
                pre.next = l1;
                l1 = l1.next;
            }else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
        if(l1==null) pre.next = l2;
        if(l2==null) pre.next = l1;
        return dummy.next;

    }

    public ListNode mergeKLists(ListNode[] lists) {

    }










}
