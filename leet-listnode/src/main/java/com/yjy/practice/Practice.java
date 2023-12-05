package com.yjy.practice;

import com.yjy.list.ListProblems;


public class Practice {
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

    int sum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root){
        //存在重复计算，一个递归即可解决
        //计算后面节点的最大路径，都需要重新计算向下的最大路径
        if(root==null) return 0;
        int res = root.val;
        int left = maxPath(root.left) ;
        int right = maxPath(root.right);
        res =  res+ left+right;
        sum = Math.max(sum,res);
        maxPathSum(root.left);
        maxPathSum(root.right);
        return sum;
    }
    public int maxPath(TreeNode root){
        if(root==null) return 0;
        int left = Math.max(maxPath(root.left),0);
        int right = Math.max(maxPath(root.right),0);
        return root.val+ Math.max(left,right);
    }
    public static void main(String[] args) {
        int[] array = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};

        System.out.println("Original Array: " + Arrays.toString(array));

        quickSort(array, 0, array.length - 1);

        System.out.println("Sorted Array: " + Arrays.toString(array));
    }
    public static void quickSort(int[] arr,int low,int high){
        if(low>=high) return;
        int index = partition(arr,low, high);
        quickSort(arr,low,index-1);
        quickSort(arr,index+1,high);
    }
    public static int partition(int[] arr,int start,int end){
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











}
