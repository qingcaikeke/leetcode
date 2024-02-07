package com.yjy;

import java.util.Stack;

public class Other {
    //动态规划，单调栈，滑动窗口，双指针
    //1.按列求，每列的水取决于左边最高的和右边最高的两者中的较小的
    public int trap1(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for(int i=1;i<n;i++){
            leftMax[i] = Math.max(leftMax[i-1],height[i]);
        }
        int[] rightMax = new int[n];
        rightMax[n-1] = height[n-1];
        for(int i=n-2;i>=0;i--){
            rightMax[i] = Math.max(rightMax[i+1],height[i]);
        }
        int res = 0;
        for(int i=0;i<n;i++){
            res+= Math.min(rightMax[i],leftMax[i])-height[i];
        }
        return res;

    }
    //单调递减栈,本质上是按行算，栈顶为底，只要观察的元素比栈顶高，就能接水
    public int trap3(int[] height){
        //栈储存下标
        Stack<Integer> stack = new Stack<>();
        int i=0,res = 0;
        while (i<height.length){
            while (height[i]>height[stack.peek()]){
                int low = stack.pop();
                if(stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                res+=Math.min(height[left],height[i])-height[low];
            }
            stack.push(height[i]);
            i++;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        trap4(height);
    }
    public static int trap4(int[] height) {
        int n = height.length;
        int res =0;
        int left = 1 ,right =n-2 ;
        int leftMax = height[0],rightMax=height[n-1];
        //能否接水取决于左面最大和右面最大中较小的那个
        while (left<=right){//=?
            if(leftMax<rightMax){
                //如果左面最大更小，说明他是短板，而右面最大向左移动一定是会变得更大，所以能接多少取决左面
                if(leftMax-height[left]>0)
                    res += leftMax-height[left];
                leftMax = Math.max(leftMax,height[left]);
                left++;
            }else {
                //如果右面是短板，可以先算right，因为left移动到right这个过程leftMax一定是递增的
                if(rightMax-height[right]>0)
                    res+=rightMax-height[right];
                rightMax = Math.max(rightMax,height[right]);
                right--;
            }
        }
        return res;
    }
    public static int trap5(int[] height) {
        //4的改进，算左最大和右最小的时候把自己也算上就不会出现负数了
        int n = height.length;
        int res =0;
        int left = 0 ,right =n-1 ;
        int leftMax = 0,rightMax=0;
        //每次算left和right其中一个的位置
        //能否接水取决于左面最大和右面最大中较小的那个
        while (left<=right){//=?
            leftMax = Math.max(leftMax,height[left]);
            rightMax = Math.max(rightMax,height[right]);
            if(leftMax<rightMax){
                //如果左面最大更小，说明他是短板，而右面最大向左移动一定是会变得更大，所以能接多少取决左面
                res += leftMax-height[left];
                left++;
            }else {
                //如果右面是短板，可以先算right，因为left移动到right这个过程leftMax一定是递增的
                res+=rightMax-height[right];
                right--;
            }
        }
        return res;
    }
    //2.按行求，遇到高度小于h的，就给temp+1，否则把temp加到res上
    public int trap2(int[] height) {
        int n = height.length;
        int maxHeight = 0;
        for(int i=0;i<n;i++){
            maxHeight = Math.max(maxHeight,height[i]);
        }
        int res =0 ;
        for(int h=1;h<=maxHeight;h++){
            int temp=0;
            boolean isStart = false;
            for(int i=0;i<n;i++){
                if(isStart && height[i]<h){
                    temp++;
                }
                if(height[i]>=h){
                    res+=temp;
                    temp=0;
                    isStart = true;
                }
            }
        }
        return res;
    }

}