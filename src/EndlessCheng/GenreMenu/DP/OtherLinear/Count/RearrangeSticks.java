package EndlessCheng.GenreMenu.DP.OtherLinear.Count;

public class RearrangeSticks {

    // https://leetcode.cn/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/solutions/779065/fen-lei-tao-lun-dong-tai-gui-hua-by-shan-vxa7/
    private int mod = (int) 1e9 + 7;
    long[][] dp;

    public int rearrangeSticks(int n, int k) {
        dp = new long[n + 1][k + 1]; // dp[i][j]代表i根棍子能看到j根
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                dp[i][j] = dp[i - 1][j - 1];
                if (i - 1 >= j) dp[i][j] += (i - 1) * dp[i - 1][j];
                dp[i][j] %= mod;
            }
        }
        return (int) dp[n][k];
    }

}
