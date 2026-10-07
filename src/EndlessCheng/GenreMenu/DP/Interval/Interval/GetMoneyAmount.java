package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class GetMoneyAmount {

    // https://leetcode.cn/problems/guess-number-higher-or-lower-ii/solutions/1097490/tong-ge-lai-shua-ti-la-yi-ti-san-jie-bao-t4od/
    public int getMoneyAmount(int n) {
        // dp[i][j]表示選擇的數在[i,j]之間時能夠確保獲勝的最小錢數
        // dp[i][j]=min{k=i->j}(max(dp[i][k-1], dp[i][k+1]) + k)
        int[][] dp = new int[n + 2][n + 2];
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i <= n - len + 1; i++) {
                int j = i + len - 1;
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    min = Math.min(min, Math.max(dp[i][k - 1], dp[k + 1][j]) + k);
                }
                dp[i][j] = min;
            }
        }
        return dp[1][n];
    }


}
