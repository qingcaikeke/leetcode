package com.yjy.graph;

import java.util.*;

public class Graph {

    //695最大岛屿，想到了深度优先搜索，但是打算采取额外的矩阵表示当前元素是否访问过，没想到可以把访问过的直接置成0
    //法2：栈 法3：队列（广度优先搜索）
    public int maxAreaOfIsland(int[][] grid) {
        int res=0;
        for (int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    res = Math.max(res,dfsMaxArea(i,j,grid));
                }
            }
        }
        return res;
    }
    public int dfsMaxArea(int i,int j,int[][] grid){
        if(i<0||i== grid.length||j<0||j==grid[0].length//边界写在这判断比较好，而不是if(i+1!=grid.len)dfs(i+1...)
        ||grid[i][j]==0) return 0;
        grid[i][j]=0;
        int ans=1;
        ans+=dfsMaxArea(i+1,j,grid);
        ans+=dfsMaxArea(i-1,j,grid);
        ans+=dfsMaxArea(i,j+1,grid);
        ans+=dfsMaxArea(i,j-1,grid);
        return ans;
    }
    //785二分图：也需要搜索图:使用图搜索算法从各个连通域的任一顶点开始遍历整个连通域
    //具体细节：我想到的，两个set储存；题解给的，染色法，每个节点三个状态
    //深度优先搜索会结合遍历，因为只搜索不能保证找到所有点，只能保证找到所有与最开始联通的
    //也不能遍历所有点去置色，因为你不知道一个新来的点应该置红还是黑，可能与他相连的已经被置过色了他就没有选择了
    //graph = [[],[2,4,6],[1,4,8,9],[7,8],[1,2,8,9],[6,9],[1,5,7,8,9],[3,6,9],[2,3,4,6,9],[2,4,5,6,7,8]]
    public boolean isBipartite(int[][] graph) {
        int[] color = new int[graph.length];//0代表未染色，1代表第1组，2代表第二组
        boolean res = true;
        for(int i=0;i<graph.length;i++) {//循环还是得要，可能有得点是孤立的，对最终结果没有影响
            if(color[i]==0){
                color[i] = 1;
                if(!dfsPart(i,1,graph,color)){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean dfsPart(int i,int curColor,int[][] graph,int[] color){
        int nextColor = curColor==1?2:1;
        for(int node:graph[i]){
            if(color[node]==0){
                color[node] = nextColor;
                if(!dfsPart(node,nextColor,graph,color)){//只有下一层返回false才做处理，否则不用管
                    return false;
                }
            }
            else if(color[node]==curColor){
                return false;
            }
        }
        return true;
    }
    //广度优先搜索
    public boolean isBipartite1(int[][] graph) {
        int n= graph.length;
        int[] color = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<n;i++){
            // 因为图中可能含有多个连通域，所以我们需要判断是否存在顶点未被访问，若存在则从它开始再进行一轮 bfs 染色。
            if(color[i]==0){
                queue.add(i);
                color[i] = 1;
                while (!queue.isEmpty()){
                    int node = queue.poll();
                    int nextColor = color[node]==1?2:1;
                    for(int neighbor: graph[node]){
                        if(color[neighbor]==0){//一个节点被染色过了，他一定已经入队过了，不需要把这个节点再次入队了
                            color[neighbor] = nextColor;
                            queue.add(neighbor);
                        }
                        else if(color[neighbor]!=nextColor){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    //法3：一种数据结构，并查集，说能维护两个操作（快速确认元素属于哪个集合，能将两个集合进行合并）
    public boolean isBipartite2(int[][] graph) {
        UnionFind uf = new UnionFind(graph.length);
        for(int i=0;i<graph.length;i++){
            for(int neighbor:graph[i]){
                if(uf.isConnected(i,neighbor)){
                    return false;
                }
                uf.union(neighbor,graph[i][0]);
            }
        }
        return true;
    }
    class UnionFind{
        int[] roots;
        public UnionFind(int n){
            roots = new int[n];
            for(int i=0;i<n;i++){
                roots[i] = i;//初始化每个节点的根都是自己
            }
        }
        public int find(int i){
            //指向自己的才是根节点
            if(roots[i]==i){
                return i;
            }
            roots[i] = find(roots[i]);//递归的去找根节点，并进行压缩
            return roots[i];
        }
        public void union(int p,int q){
            //合并p，q两节点，让p的根节点指向q的根节点
            roots[find(p)] = find(q);
        }
        public boolean isConnected(int p,int q){
            return find(p)==find(q);
        }

    }
    //lcr107:矩阵中的距离，明显广度优先搜索，没想到
    //一种新方法，广度优先搜索加类似动态规划，找到所有的0，往外推1层置1，再从1往外推一层置2
    public int[][] updateMatrix(int[][] mat) {
        int m = mat.length,n = mat[0].length;
        int[][] res = new int[m][n];
        Queue<int[]> queue = new LinkedList<>();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(mat[i][j]==0){
                    res[i][j]=0;
                    queue.add(new int[]{i,j});
                }
                else res[i][j] = Integer.MAX_VALUE/2;
            }
        }
        int[][] diraction = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        while (!queue.isEmpty()){
            int[] cell = queue.poll();
            int x = cell[0],y = cell[1];
            for(int i=0;i<4;i++){
                int nx = x+diraction[i][0];
                int ny = y+diraction[i][1];
                if(nx<0||nx==m||ny<0||ny==n||res[nx][ny]<=res[x][y]+1){
                    continue;
                }
                res[nx][ny] = Math.min(res[x][y]+1,res[nx][ny]);
                queue.add(new int[]{nx,ny});
            }
        }
        return res;
    }
    //LCR109打开转盘锁：广度优先搜索
    class OpenLock{
        public int openLock(String[] deadends, String target) {
            if("0000".equals(target)) return 0;
            Set<String> dead = new HashSet<>();
            for(String deadend:deadends){
                dead.add(deadend);
            }
            if(dead.contains("0000")) return -1;
            int res=0;
            Queue<String> queue = new LinkedList<>();
            Set<String> seen = new HashSet<>();
            queue.add("0000");
            seen.add("0000");
            while (!queue.isEmpty()){
                int size = queue.size();
                res++;
                for(int i=0;i<size;i++){
                    String status = queue.poll();
                    List<String> next = getNext(status);
                    for(String nextStatus:next){
                        if(!dead.contains(nextStatus)&&!seen.contains(nextStatus)){
                            if(nextStatus.equals(target)){
                                return res;
                            }
                            queue.offer(nextStatus);
                            seen.add(nextStatus);
                        }
                    }
                }
            }
            return -1;
        }
        public char numPre(char x){
            return x=='0'?'9':(char)(x-1);
        }
        public char numSuc(char x){
            return x=='9'?'0':(char)(x+1);
        }
        public List<String> getNext(String status){
            char[] ch  = status.toCharArray();
            List<String> next = new ArrayList<>();
            for(int i=0;i<4;i++){
                char curChar = ch[i];
                ch[i] = numPre(curChar);
//            next.add(Arrays.toString(ch));输出"[a,b,c]",获得字符数组可读形式的字符串，一般用于调试
                next.add(new String(ch));//输出“abc"//将字符数组拼接
                ch[i] = numSuc(curChar);
                next.add(new String(ch));
                ch[i] = curChar;
            }
            return next;
        }
    }
    //797所有可能的路径：深度搜索找所有路径
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path =  new ArrayList<>();
        path.add(0);
        dfsAllPath(res,path,graph,0);
        return res;
    }
    public void dfsAllPath(List<List<Integer>> res,List<Integer> path ,int[][] graph,int n){
        if(n== graph.length){
            res.add(new ArrayList<>(path));
            return;
        }
        for(int node:graph[n]){
            path.add(node);
            dfsAllPath(res,path,graph,node);
            path.remove(path.size()-1);
        }
    }































}
