package EndlessCheng.GenreMenu.DP.JumpGame;

public class MinJump {

    // https://leetcode.cn/problems/zui-xiao-tiao-yue-ci-shu/solutions/209071/jian-dan-yi-dong-de-dong-tai-gui-hua-wan-fa-by-don/
    public int minJump(int[] jump) {
        int[] dp = new int[jump.length];
        dp[jump.length - 1] = 1;
        for (int i = jump.length - 2; i > -1; i--) {
            dp[i] = jump[i] + i >= jump.length ? 1 : dp[jump[i] + i] + 1;
            //遍歷當前位置更新後影響到的後面的位置，只需要更新到dp[j] >= dp[i]+1即可
            //如果遍歷到某dp[j]<dp[i]+1就不需要向右遍歷了,因為j到dp.length的值會被當前遍歷到的dp[j]更新而不是dp[i]+1
            for (int j = i + 1; j < dp.length && dp[j] >= dp[i] + 1; j++) {
                dp[j] = dp[i] + 1;
            }
        }
        return dp[0];
    }

}
