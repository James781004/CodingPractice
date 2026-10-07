package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class LargestVariance {

    // https://leetcode.cn/problems/substring-with-largest-variance/solutions/1494890/by-endlesscheng-5775/
    public int largestVariance(String s) {
        /*
        枚舉+dp:
        1.出現次數最多與最少的字母,必定是a-z中的一對不同字母的組合,記出現次數最多的為x,出現次數少的為b
        2.枚舉x與y的二維組合,記字符串中的x=1,y=1,然後其余的字母為0,那麼其出現次數的差轉化為子數組的和
            參考:最大子數組的和就可以用動態規劃進行處理!
        3.有一點主要注意的是,x與y在子串中均需要出現(其實是保證y要出現),因此其轉移方程就有不同
            記:dp[i][0]為以s[i-1]結尾的子串和的最大值
                dp[i][1]為以s[i-1]結尾的(且包含y)子串和的最大值
                記s[i-1]對應的數字為v,即s[i]=x,v=1;s[i]=y,v=-1;s[i]=其他,v=0
            3.1 顯然:dp[i][0]=max(dp[i-1][0]+v,v)
            3.2 而dp[i-1]的轉移就需要看s[i-1]是否為y決定的
                3.2.1 當s[i-1]==y時,有兩種轉移途徑,要麼自成一體,要麼拉上前面的dp[i-1][0],兩者取最大值
                (這種情況前面有沒有y都行,必定是沒有y的會更大,因此可以直接舍棄dp[i-1][1])
                    即dp[i][1]=max(dp[i-1][0]+v,v)
                3.2.2 當s[i-1]!=y時,就只能從前面有y的繼承過來,dp[i][1]=dp[i-1][1]+v
            遍歷過程中維護最大的dp[i][1]就是組合x與y的最大波動值max
        4.最後維護好每種組合對應的max就是答案
         */
        // 空間優化
        int len = s.length();
        int res = 0;
        int dp0, dp1;
        // 枚舉x與y的字母組合
        for (int x = 'a'; x <= 'z'; x++) {
            for (int y = 'a'; y <= 'z'; y++) {
                if (x == y) continue;
                // 初始化為dp[i][0]與dp[i][1]不可能達到的值
                dp0 = -len;
                dp1 = -len;
                int max = 0;
                // 遍歷字符串s
                for (int i = 1; i <= len; i++) {
                    // s[i-1]對應的數字
                    int v = (s.charAt(i - 1) == x) ? 1 : (s.charAt(i - 1) == y) ? -1 : 0;
                    if (v == -1) {
                        // s[i-1]==y
                        dp1 = Math.max(dp0 + v, v);
                    } else {
                        // s[i-1]!=y
                        dp1 = dp1 + v;
                    }
                    // 注意dp1運算需要用到舊的dp0因此dp0必須後面算
                    dp0 = Math.max(dp0 + v, v);
                    max = Math.max(max, dp1);
                }
                res = Math.max(res, max);
            }
        }
        return res;
    }


}
