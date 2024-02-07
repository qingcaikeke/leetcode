public class tst {
    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        trap4(height);
    }
    public static int trap4(int[] height) {
        int n = height.length;
        int res =0;
        int left = 1 ,right =n-2 ;
        int leftMax = height[0],rightMax=height[n-1];
        while (left<=right){//=?
            if(leftMax<rightMax){
                res += leftMax-height[left];
                System.out.println(res+"/n");
                leftMax = Math.max(leftMax,height[left]);
                left++;
            }else {
                res+=rightMax-height[right];
                System.out.println(res+"/n");
                rightMax = Math.max(rightMax,height[right]);
                right--;
            }
        }
        return res;
    }
}
