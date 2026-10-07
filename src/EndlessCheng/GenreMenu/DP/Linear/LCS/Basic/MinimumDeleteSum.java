package EndlessCheng.GenreMenu.DP.Linear.LCS.Basic;

public class MinimumDeleteSum {

    // https://leetcode.cn/problems/minimum-ascii-delete-sum-for-two-strings/solutions/1629453/by-lfool-lfpu/
    public int minimumDeleteSum(String s1, String s2) {
        int n1 = s1.length(), n2 = s2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];
        // base case
        for (int i = 1; i <= n1; i++) dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        for (int i = 1; i <= n2; i++) dp[0][i] = dp[0][i - 1] + s2.charAt(i - 1);
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                int c1 = s1.charAt(i - 1);
                int c2 = s2.charAt(j - 1);
                // 相等情況
                if (c1 == c2) dp[i][j] = dp[i - 1][j - 1];
                    // 不等情況
                else dp[i][j] = Math.min(dp[i][j - 1] + c2, dp[i - 1][j] + c1);
            }
        }
        return dp[n1][n2];
    }


}
