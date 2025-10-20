package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;

public class MaximumAmount {

    // https://leetcode.cn/problems/maximum-amount-of-money-robot-can-earn/solutions/3045103/wang-ge-tu-dp-by-endlesscheng-g96j/
    public int maximumAmount(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        int[][][] memo = new int[m][n][3];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, Integer.MIN_VALUE);
            }
        }
        return dfs(m - 1, n - 1, 2, coins, memo);
    }

    private int dfs(int i, int j, int k, int[][] coins, int[][][] memo) {
        if (i < 0 || j < 0) {
            return Integer.MIN_VALUE;
        }
        int x = coins[i][j];
        if (i == 0 && j == 0) {
            return k > 0 ? Math.max(x, 0) : x;
        }
        if (memo[i][j][k] != Integer.MIN_VALUE) { // 之前計算過
            return memo[i][j][k];
        }
        int res = Math.max(dfs(i - 1, j, k, coins, memo), dfs(i, j - 1, k, coins, memo)) + x; // 選
        if (k > 0 && x < 0) {
            res = Math.max(res, Math.max(dfs(i - 1, j, k - 1, coins, memo), dfs(i, j - 1, k - 1, coins, memo))); // 不選
        }
        return memo[i][j][k] = res; // 記憶化
    }


    public int maximumAmountDP(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        int[][][] f = new int[m + 1][n + 1][3];
        for (int[] row : f[0]) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        Arrays.fill(f[0][1], 0);
        for (int i = 0; i < m; i++) {
            Arrays.fill(f[i + 1][0], Integer.MIN_VALUE);
            for (int j = 0; j < n; j++) {
                int x = coins[i][j];
                f[i + 1][j + 1][0] = Math.max(f[i + 1][j][0], f[i][j + 1][0]) + x;
                f[i + 1][j + 1][1] = Math.max(
                        Math.max(f[i + 1][j][1], f[i][j + 1][1]) + x,
                        Math.max(f[i + 1][j][0], f[i][j + 1][0])
                );
                f[i + 1][j + 1][2] = Math.max(
                        Math.max(f[i + 1][j][2], f[i][j + 1][2]) + x,
                        Math.max(f[i + 1][j][1], f[i][j + 1][1])
                );
            }
        }
        return f[m][n][2];
    }


    public int maximumAmountDP2(int[][] coins) {
        int n = coins[0].length;
        int[][] f = new int[n + 1][3];
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        Arrays.fill(f[1], 0);
        for (int[] row : coins) {
            for (int j = 0; j < n; j++) {
                int x = row[j];
                f[j + 1][2] = Math.max(
                        Math.max(f[j][2], f[j + 1][2]) + x,
                        Math.max(f[j][1], f[j + 1][1])
                );
                f[j + 1][1] = Math.max(
                        Math.max(f[j][1], f[j + 1][1]) + x,
                        Math.max(f[j][0], f[j + 1][0])
                );
                f[j + 1][0] = Math.max(f[j][0], f[j + 1][0]) + x;
            }
        }
        return f[n][2];
    }


}
