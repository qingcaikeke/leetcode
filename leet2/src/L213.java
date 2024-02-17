public class L213 {
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
    /**
     * 61.
     * 62.
     * 63.
     * 64.
     * 65.
     * 66.
     * 67.
     * 68.
     * 69.
     * 70.
     * 71.
     * 72.
     * 73.
     * 74.
     * 75.
     * 76.
     * 77.
     * 78.子集:多解法 1.两种回溯（for/选或不选）2.选或不选构成二叉树左右节点，使用二叉树遍历 3.枚举二进制，第i位为1就加入path 4.递推，将之前的path复制，一个加入i，一个不加i
     * 79.
     * 80.删除有序数组中的重复项 II,引申到元素可以出现k次，通过双指针构造一个窗口大小为k的滑动窗口
     */









    public int removeDuplicates(int[] nums) {
        int behind = 0;
        for(int i=1;i< nums.length;i++){
            if(nums[i]==nums[behind]){
                if(behind!=0 && nums[behind-1]==nums[i]){
                    continue;
                }
            }
            behind++;
            nums[behind] = nums[i];
        }
        return behind+1;
    }
    public int removeDuplicates2(int[] nums) {
        //法2：构造一个窗口大小为2的滑动窗口，slow,slow-1,slow-2
        int behind = 2;//指向最后一个不保留的元素//2->k
        for(int i=2;i< nums.length;i++){//2->k
            //如何判断当前元素i是否应该保留？
            if(nums[i]!=nums[behind-2]){//可认为窗口是behind-1,behind-2,因为数组递增，如果i等于behind-2，i>=behind-1>=behind-2，必有i是第三次出现
                nums[behind] = nums[i];
                behind++;
            }
        }
        return behind;
    }






























}
