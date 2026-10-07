package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class SellingWood {

    // https://leetcode.cn/problems/selling-pieces-of-wood/solutions/1611240/by-endlesscheng-mrmd/
    public long sellingWood(int m, int n, int[][] prices) {
        long[][] dp = new long[m + 1][n + 1];
        for (int[] p : prices) // 先把初始的價格prices 放到dp裡。然後其他沒價格的正好是0
            dp[p[0]][p[1]] = p[2];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 從前推到後，前面小的求出最優了，可以當後面更大問題的子問題。正好可以加法，兩子問題都更小 且都之前遍歷過了。
                for (int k = 1; k < j; k++) // 水平完全切割
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[i][j - k]);

                for (int k = 1; k < i; k++) // 豎直完全切割
                    dp[i][j] = Math.max(dp[i][j], dp[k][j] + dp[i - k][j]);
            }
        }
        return dp[m][n];
    }


    public long sellingWood2(int m, int n, int[][] prices) {
        long[][] f = new long[m + 1][n + 1];
        for (int[] p : prices) { // 直接將 prices 記錄到 f 數組中
            f[p[0]][p[1]] = p[2];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= j / 2; k++) f[i][j] = Math.max(f[i][j], f[i][k] + f[i][j - k]); // 垂直切割
                for (int k = 1; k <= i / 2; k++) f[i][j] = Math.max(f[i][j], f[k][j] + f[i - k][j]); // 水平切割
            }
        }
        return f[m][n];
    }


}
