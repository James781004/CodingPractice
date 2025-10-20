package EndlessCheng.GenreMenu.DP.Grid.Advanced;

public class MaxPoints {

    // https://leetcode.cn/problems/maximum-number-of-points-with-cost/solutions/882598/dong-tai-gui-hua-jian-zhi-by-shauna-vayn-t9o7/
    public long maxPoints(int[][] points) {
        int n = points.length;
        int m = points[0].length;
        long[][] dp = new long[n][m];
        for (int i = 0; i < m; i++) {
            dp[0][i] = points[0][i];
        }

        for (int i = 1; i < n; i++) {
            long max = 0;
            for (int j = 0; j < m; j++) {
                // 從左邊遍歷
                // max 為 i - 1 行中，j 這一列前面的列的最大值
                max = Math.max(max - 1, dp[i - 1][j]);
                dp[i][j] = max + points[i][j];
            }
            max = 0;
            for (int j = m - 1; j >= 0; j--) {
                // 從右邊遍歷
                // 考慮到i - 1這一行，有可能最大值出現在後綴列，
                // 所以通過同樣的方法，出後綴列與dp[i - 1][j] 作比較，
                // 同時與前綴列的 dp[i][j] 作比較
                max = Math.max(max - 1, dp[i - 1][j]);
                dp[i][j] = Math.max(dp[i][j], max + points[i][j]);
            }
        }
        long max = 0;
        for (int i = 0; i < m; i++) {
            max = Math.max(max, dp[n - 1][i]);
        }
        return max;
    }


}
