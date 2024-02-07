package com.yjy;

import com.yjy.tree.Tree;

import java.beans.beancontext.BeanContextServicesListener;
import java.util.*;

public class text {


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
    //没必要新建一个标记数组，可以直接在原数组上改，标记的目的是为了防止‘1’的重复计算
    //每找到一个1，深度搜索，把周围的1全置成0，然后结果加一
    public int numIslands(char[][] grid) {
        int res=0;
        int m = grid.length;int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for(int j=0;j<n;j++){
                if(grid[i][j]=='1'){
                    dfs(i,j,grid);
                    res++;
                }
            }
        }
        return res;
    }
    public void dfs(int x,int y,char[][] grid){
        int m = grid.length;int n = grid[0].length;
        if(x<0||x>=m||y<0||y>=n||grid[x][y]=='0'){
            return ;
        }
        grid[x][y] = '0';
        dfs(x+1,y,grid);
        dfs(x-1,y,grid);
        dfs(x,y+1,grid);
        dfs(x,y-1,grid);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
       ListNode l1 = headA,l2 = headB;
       while (l1!=l2){
            l1 = l1.next!=null?l1.next:headB;
            l2 = l2.next!=null?l2.next:headA;
        }
       return l1;
    }












    public ListNode reverseKGroup(ListNode head, int k) {
       ListNode dummy = new ListNode(0,head);
       ListNode pre = dummy;
       ListNode end = pre.next;
       while (end!=null){
            for(int i=1;i<k&& end!=null ;i++){
                end = end.next;
            }
            ListNode temp = end.next;
            end.next = null;
            ListNode start = pre.next;
            reverse(start);//会返回end（新的头节点）
            pre.next = end;
            start.next = temp;
            pre = start;
            end = pre.next;
        }
       return dummy.next;
    }
    public ListNode reverse(ListNode head){
       if(head.next==null) return head;
       ListNode newHead = reverse(head.next);
       head.next.next = head;
       return newHead;
    }

}
class text2{
    public static void main(String[] args) {
        int[] height = new int[]{4,2,0,3,2,5};


    }
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));
//        Arrays.sort(intervals,(a,b)->a[0]-b[0]);
        int n = intervals.length;
        List<int[]> res = new ArrayList<>();
        for(int i=0;i<n;i++){
            int left = intervals[i][0],right = intervals[i][1];
            while (i+1<n&&right>=intervals[i+1][0]){
                right = Math.max(right,intervals[i+1][1]);
                i++;
            }
            res.add(new int[]{left,right});
        }
        return res.toArray(new int[][]{});
    }


}
