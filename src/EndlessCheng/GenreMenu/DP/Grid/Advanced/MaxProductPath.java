package EndlessCheng.GenreMenu.DP.Grid.Advanced;

public class MaxProductPath {

    // https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/solutions/3026256/dong-tai-gui-hua-kong-jian-you-hua-ling-cu59h/
    public int maxProductPath(int[][] grid) {
        final int MOD = 1000000000 + 7;
        int m = grid.length, n = grid[0].length;
        // 初始化 maxgt 和 minlt 數組，保存第一行的值
        long[] maxgt = new long[n];
        long[] minlt = new long[n];
        maxgt[0] = minlt[0] = grid[0][0];
        // 初始化第一的 maxgt 和 minlt
        for (int i = 1; i < n; i++) {
            maxgt[i] = maxgt[i - 1] * grid[0][i];
            minlt[i] = minlt[i - 1] * grid[0][i];
        }
        // 從第二行開始處理
        for (int i = 1; i < m; i++) {
            // 更新第一列的 maxgt 和 minlt
            maxgt[0] *= grid[i][0];
            minlt[0] *= grid[i][0];
            for (int j = 1; j < n; j++) {
                if (grid[i][j] >= 0) {
                    maxgt[j] = Math.max(maxgt[j], maxgt[j - 1]) * grid[i][j];
                    minlt[j] = Math.min(minlt[j], minlt[j - 1]) * grid[i][j];
                } else {
                    long tempMax = maxgt[j]; // 暫存當前值
                    maxgt[j] = Math.min(minlt[j], minlt[j - 1]) * grid[i][j];
                    minlt[j] = Math.max(tempMax, maxgt[j - 1]) * grid[i][j];
                }
            }
        }
        // 最終結果為右下角的 maxgt
        if (maxgt[n - 1] < 0) {
            return -1;
        } else {
            return (int) (maxgt[n - 1] % MOD);
        }
    }


}
