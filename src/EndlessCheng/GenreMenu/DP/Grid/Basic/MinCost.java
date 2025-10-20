package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-path-with-alternating-directions-ii/solutions/3716283/wang-ge-tu-dpji-yi-hua-sou-suo-di-tui-ko-82o9/
    public long minCost(int m, int n, int[][] waitCost) {
        long[][] memo = new long[m][n];
        return dfs(m - 1, n - 1, waitCost, memo) - waitCost[m - 1][n - 1]; // 終點不需要等待
    }

    private long dfs(int i, int j, int[][] waitCost, long[][] memo) {
        if (i < 0 || j < 0) {
            return Long.MAX_VALUE;
        }
        if (i == 0 && j == 0) {
            return 1; // 起點只有進入成本，不需要等待
        }
        if (memo[i][j] != 0) { // 之前計算過
            return memo[i][j];
        }
        long cost = waitCost[i][j] + (long) (i + 1) * (j + 1);
        return memo[i][j] = Math.min(dfs(i, j - 1, waitCost, memo), dfs(i - 1, j, waitCost, memo)) + cost;
    }


    public long minCostDP(int m, int n, int[][] waitCost) {
        long[][] f = new long[m + 1][n + 1];
        Arrays.fill(f[0], Long.MAX_VALUE);
        f[0][1] = -waitCost[0][0]; // 計算 f[1][1] 的時候抵消掉
        for (int i = 0; i < m; i++) {
            f[i + 1][0] = Long.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                f[i + 1][j + 1] = Math.min(f[i + 1][j], f[i][j + 1]) + waitCost[i][j] + (long) (i + 1) * (j + 1);
            }
        }
        return f[m][n] - waitCost[m - 1][n - 1];
    }


    public long minCostDP2(int m, int n, int[][] waitCost) {
        long[] f = new long[n + 1];
        Arrays.fill(f, Long.MAX_VALUE);
        f[1] = -waitCost[0][0];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[j + 1] = Math.min(f[j], f[j + 1]) + waitCost[i][j] + (long) (i + 1) * (j + 1);
            }
        }
        return f[n] - waitCost[m - 1][n - 1];
    }


}
