package com.yjy.practice;

import java.lang.annotation.Target;
import java.util.*;

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
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {

    }











}
