package com.yjy.heap;

public class Heap {
    //最大堆（大根堆）：根节点大于左右儿子
    //因为是满二叉树，数组储存，下标为i，左子节点2*i+1,右子2*i+2，根节点（i—1）/2
    public void heapSort(int[] arr){
        int n = arr.length;
        //第一次建堆，从下到上建堆，使以任一节点为根的子树满足堆的性质
        for(int i = (n-1-1)/2;i>=0;i--){
            heapify(arr,i,n);
        }//至此建堆完成
        //排序，堆只保证最上面的元素（下标为0）的值是最大的，其他不保证
        for(int i=n-1;i>0;i--){
            swap(arr,i,0);//最大元素已经挪到了数组最后
            heapify(arr,0,i);//从上到下建堆，为了满足换上来的新的下标为0的元素的下沉操作
        }
    }
    //堆化
    //三个参数：数组，下标（只堆化当前为根的节点的树的部分），元素总数（用于控制左右子节点下标不超界）
    //一旦发生交换，被交换的节点的子树可能不满足堆的定义，因此需要递归的去进行
    public void heapify(int[] arr,int i,int n){
        int largest = i;
        int lson = 2*i+1;
        int rson = 2*i+2;
        //不用两边都换，换个最大的上去就行，所以需要找到最大的下标
        if(lson<n && arr[lson]>arr[largest]){
            largest = lson;
        }
        if(rson<n && arr[rson]>arr[largest]){
            largest = rson;
        }
        //说明需要交换
        if(largest!=i){
            swap(arr,i,largest);
            heapify(arr,largest,n);
        }
    }
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    //215寻找第k个最大元素法2：堆排序1.建堆2.调整3.删除
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        for(int i = (n-2)/2;i>=0;i--){
            heapify(nums,i,n);
        }
        for(int i=n-1;i>=n-k;i--){
            swap(nums,i,0);
            heapify(nums,0,i);
        }
        return nums[n-k];
    }





















}
