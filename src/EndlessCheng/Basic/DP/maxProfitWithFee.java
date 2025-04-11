package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxProfitWithFee {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/solutions/2469505/shi-pin-gu-piao-mai-mai-tong-yong-fang-f-0u38/
    private int[] prices;
    private int[][] memo;
    private int fee;

    public int maxProfitMemo(int[] prices, int fee) {
        this.prices = prices;
        this.fee = fee;
        int n = prices.length;
        memo = new int[n][2];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(n - 1, 0);
    }

    private int dfs(int i, int hold) {
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE / 2 : 0; // 防止溢出
        if (memo[i][hold] != -1) return memo[i][hold]; // 之前計算過
        if (hold == 1)
            return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 1, 0) - prices[i]);
        return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i] - fee);
    }


    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] f = new int[n + 1][2];
        f[0][1] = Integer.MIN_VALUE / 2;
        for (int i = 0; i < n; i++) {
            f[i + 1][0] = Math.max(f[i][0], f[i][1] + prices[i] - fee);
            f[i + 1][1] = Math.max(f[i][1], f[i][0] - prices[i]);
        }
        return f[n][0];
    }


    public int maxProfit2(int[] prices, int fee) {
        int f0 = 0, f1 = Integer.MIN_VALUE / 2;
        for (int p : prices) {
            int newF0 = Math.max(f0, f1 + p - fee);
            f1 = Math.max(f1, f0 - p);
            f0 = newF0;
        }
        return f0;
    }

}
