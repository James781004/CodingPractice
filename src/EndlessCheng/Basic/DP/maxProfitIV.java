package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxProfitIV {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/solutions/2201488/shi-pin-jiao-ni-yi-bu-bu-si-kao-dong-tai-kksg/
    private int[] prices;
    private int[][][] memo;

    public int maxProfitMemo(int k, int[] prices) {
        this.prices = prices;
        int n = prices.length;
        memo = new int[n][k + 1][2];
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= k; ++j)
                Arrays.fill(memo[i][j], -1); // -1 表示還沒有計算過
        return dfs(n - 1, k, 0);
    }

    private int dfs(int i, int j, int hold) {
        if (j < 0) return Integer.MIN_VALUE / 2; // 防止溢出
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        if (memo[i][j][hold] != -1) return memo[i][j][hold]; // 之前計算過
        if (hold == 1)
            return memo[i][j][hold] = Math.max(dfs(i - 1, j, 1), dfs(i - 1, j - 1, 0) - prices[i]);
        return memo[i][j][hold] = Math.max(dfs(i - 1, j, 0), dfs(i - 1, j, 1) + prices[i]);
    }

    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][][] f = new int[n + 1][k + 2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k + 1; j++) {
                Arrays.fill(f[i][j], Integer.MIN_VALUE / 2); // 防止溢出
            }
        }
        for (int j = 1; j <= k + 1; j++) {
            f[0][j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                f[i + 1][j][0] = Math.max(f[i][j][0], f[i][j][1] + prices[i]);
                f[i + 1][j][1] = Math.max(f[i][j][1], f[i][j - 1][0] - prices[i]);
            }
        }
        return f[n][k + 1][0];
    }

    public int maxProfit2(int k, int[] prices) {
        int[][] f = new int[k + 2][2];
        for (int j = 1; j <= k + 1; j++) {
            f[j][1] = Integer.MIN_VALUE / 2; // 防止溢出
        }
        f[0][0] = Integer.MIN_VALUE / 2;
        for (int p : prices) {
            for (int j = k + 1; j > 0; j--) {
                f[j][0] = Math.max(f[j][0], f[j][1] + p);
                f[j][1] = Math.max(f[j][1], f[j - 1][0] - p);
            }
        }
        return f[k + 1][0];
    }

}
