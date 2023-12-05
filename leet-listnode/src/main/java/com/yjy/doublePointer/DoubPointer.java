package com.yjy.doublePointer;

public class DoubPointer {
    //删除排序数组的重复数字
    public int removeDuplicates(int[] nums){
        int slow=0,fast=1;
        while (fast<nums.length){
            if(nums[fast]!=nums[slow]){
                if(fast-slow>1){//只有出现重复才有复制的必要
                    nums[slow+1] = nums[fast];
                }
                slow++;
            }
            fast++;
        }
        return slow+1;
    }













}
