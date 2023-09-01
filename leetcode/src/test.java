import org.w3c.dom.Node;

import java.util.LinkedList;
import java.util.Queue;

public class test {
    static class TreeNode {
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
    public static void main(String[] args) {

        TreeNode node7 = new TreeNode(7,null,null);
        TreeNode node6 = new TreeNode(6,node7,null);
        TreeNode node5 = new TreeNode(5,null,null);
        TreeNode node4 = new TreeNode(4,null,null);
        TreeNode node3 = new TreeNode(3,node6,null);
        TreeNode node2 = new TreeNode(2,node4,node5);
        TreeNode node1 = new TreeNode(1, node2,node3);
        int a = minDeptha(node1);
    }

    public static int minDeptha(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level=1;
        while(!q.isEmpty()){
            int a =q.size();
            for(int i=0;i<a;i++){

                TreeNode node = q.poll();
                if(node.left==null && node.right==null) return level;
                if(node.left!=null) q.offer(node.left);
                if(node.right!=null) q.offer(node.right);
            }
            level++;
        }
        return level;
    }
}


