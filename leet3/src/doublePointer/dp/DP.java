package doublePointer.dp;

public class DP {
    public String longestPalindrome1(String s) {
        int n = s.length();
        boolean[][] dp=new boolean[n][n];
        int maxLen=1,index=0;
        for(int i=0;i<n;i++) dp[i][i] = true;//ture是1
        for(int len=2;len<=n;len++){//枚举子串长度，更长串的判断会有到短串的数据
            for(int i=0;i<n;i++){//枚举左边界
                int j=i+len-1;//根据长度求右边界
                if(j>=n) break;
                else if(s.charAt(i)!=s.charAt(j)) dp[i][j] = false;
                else {
                    if(len==2) dp[i][j]=true;
                    //下面这部是核心，因为状态转移需要用到左下的值，所以初始化对角线后，斜线往上
                    else dp[i][j] = dp[i+1][j-1];//len=3时，所以这部需要初始化dpii
                }
                if(dp[i][j]&&len>maxLen){
                    maxLen = len;
                    index = i;
                }
            }
        }
        return s.substring(index,index+maxLen);//不包含尾
    }
    public String longestPalindrome(String s){
        int n = s.length();
        boolean[][] dp=new boolean[n][n];
        int maxLen=1,maxStart=0;
        for(int r=1;r<n;r++){//按列算才能递推
            for(int l=0;l<r;l++){
                if(s.charAt(l)==s.charAt(r)&&(r-l<=2||dp[l+1][r-1])){
                    dp[l][r] = true;
                    if(r-l+1>maxLen) {
                        maxLen = r-l+1;
                        maxStart = l;
                    }
                }
            }
        }
        return s.substring(maxStart,maxStart+maxLen);
    }
}
