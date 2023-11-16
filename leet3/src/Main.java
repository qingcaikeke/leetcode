import org.w3c.dom.Node;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        // 初始化链表1
//        ListNode head1 = new ListNode(1);
//        head1.next = new ListNode(2);
//
//// 初始化链表2
//        ListNode head2 = new ListNode(2);
//        head2.next = new ListNode(5);
//        ListNode a = deleteDuplicates(head1);
//        while (a!=null){
//            System.out.println(a.val);
//            a=a.next;
//        }
        char[][] board = new char[4][4];//初始化数组要用大括号，type[][] arr = new type[][]{{...},{...}};
            board[0] = new char[]{'X','X','X','X'};
            board[1] = new char[]{'X','0','0','X'};
            board[2] = new char[]{'X','x','0','X'};
            board[3] = new char[]{'X','0','X','X'};
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        solve(board);
        System.out.println(board[2][2]);
    }

    public static void solve(char[][] board) {
        int m = board.length,n=board[0].length;
        for(int i=0;i< m;i++){
            if(board[i][0]=='0') dfs(board,i,0);
            if(board[i][n-1]=='0') dfs(board,i,n-1);//
        }
        for(int j=1;j<n-1;j++){
            if(board[0][j]=='0') dfs(board,0,j);
            if(board[m-1][j]=='0') dfs(board,m-1,j);
        }
        for (int i = 0; i < m; i++) {
            for(int j=0;j<n;j++){
                if(board[i][j]=='A') board[i][j] = '0';
                else if(board[i][j]=='0') board[i][j]='X';
            }
        }
        return;
    }
    public static void dfs(char[][] board,int x,int y){
        if(x<0||x==board[0].length||y<0||y==board.length ||board[x][y]!='0') return; //等于0的话才去看上下左右，否则返回
        board[x][y] = 'A';
        dfs(board,x+1,y);
        dfs(board,x-1,y);
        dfs(board,x,y+1);
        dfs(board,x,y-1);
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
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

}
