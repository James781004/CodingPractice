package EndlessCheng.GenreMenu.DP.StateMachine.Stock;

import java.util.Arrays;

public class MaxProfitV {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-v/solutions/3695611/zhuang-tai-ji-dpzai-188-ti-de-ji-chu-sha-aozb/
    private int[] prices;
    private long[][][] memo;

    public long maximumProfit(int[] prices, int k) {
        this.prices = prices;
        int n = prices.length;
        memo = new long[n][k + 1][3];
        for (long[][] mat : memo) {
            for (long[] row : mat) {
                Arrays.fill(row, Long.MIN_VALUE); // MIN_VALUE 表示還沒有計算過
            }
        }
        return dfs(n - 1, k, 0);
    }

    // 在 [0,i] 中完成至多 j 筆交易，第 i 天【結束時】的狀態為 endState 的情況下的最大收益
    // 0=未持有股票，1=持有股票，2=做空中
    private long dfs(int i, int j, int endState) {
        if (j < 0) {
            return Long.MIN_VALUE / 2; // 除 2 防止溢出
        }
        if (i < 0) {
            return endState > 0 ? Long.MIN_VALUE / 2 : 0;
        }
        if (memo[i][j][endState] != Long.MIN_VALUE) { // 之前計算過
            return memo[i][j][endState];
        }
        int p = prices[i];
        if (endState == 0) {
            return memo[i][j][endState] = Math.max(dfs(i - 1, j, 0), Math.max(dfs(i - 1, j, 1) + p, dfs(i - 1, j, 2) - p));
        }
        if (endState == 1) {
            return memo[i][j][endState] = Math.max(dfs(i - 1, j, 1), dfs(i - 1, j - 1, 0) - p);
        }
        return memo[i][j][endState] = Math.max(dfs(i - 1, j, 2), dfs(i - 1, j - 1, 0) + p);
    }


    public long maximumProfitDP(int[] prices, int k) {
        int n = prices.length;
        long[][][] f = new long[n + 1][k + 2][3];
        for (long[][] mat : f) {
            for (long[] row : mat) {
                Arrays.fill(row, Long.MIN_VALUE / 2);
            }
        }
        for (int j = 1; j <= k + 1; j++) {
            f[0][j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            int p = prices[i];
            for (int j = 1; j <= k + 1; j++) {
                f[i + 1][j][0] = Math.max(f[i][j][0], Math.max(f[i][j][1] + p, f[i][j][2] - p));
                f[i + 1][j][1] = Math.max(f[i][j][1], f[i][j - 1][0] - p);
                f[i + 1][j][2] = Math.max(f[i][j][2], f[i][j - 1][0] + p);
            }
        }
        return f[n][k + 1][0];
    }


    public long maximumProfitDP2(int[] prices, int k) {
        long[][] f = new long[k + 2][3];
        for (int j = 1; j <= k + 1; j++) {
            f[j][1] = Long.MIN_VALUE / 2; // 防止溢出
        }
        f[0][0] = Long.MIN_VALUE / 2;
        for (int p : prices) {
            for (int j = k + 1; j > 0; j--) {
                f[j][0] = Math.max(f[j][0], Math.max(f[j][1] + p, f[j][2] - p));
                f[j][1] = Math.max(f[j][1], f[j - 1][0] - p);
                f[j][2] = Math.max(f[j][2], f[j - 1][0] + p);
            }
        }
        return f[k + 1][0];
    }


}
