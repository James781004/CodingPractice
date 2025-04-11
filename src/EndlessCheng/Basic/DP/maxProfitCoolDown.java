package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxProfitCoolDown {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/solutions/2201415/shi-pin-jiao-ni-yi-bu-bu-si-kao-dong-tai-0k0l/
    private int[] prices;
    private int[][] memo;

    public int maxProfitMemo(int[] prices) {
        this.prices = prices;
        int n = prices.length;
        memo = new int[n][2];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示還沒有計算過
        return dfs(n - 1, 0);
    }

    private int dfs(int i, int hold) {
        if (i < 0) return hold == 1 ? Integer.MIN_VALUE : 0;
        if (memo[i][hold] != -1) return memo[i][hold];
        if (hold == 1)
            return memo[i][hold] = Math.max(dfs(i - 1, 1), dfs(i - 2, 0) - prices[i]);
        return memo[i][hold] = Math.max(dfs(i - 1, 0), dfs(i - 1, 1) + prices[i]);
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] f = new int[n + 2][2];
        f[1][1] = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            f[i + 2][0] = Math.max(f[i + 1][0], f[i + 1][1] + prices[i]);
            f[i + 2][1] = Math.max(f[i + 1][1], f[i][0] - prices[i]);
        }
        return f[n + 1][0];
    }

    public int maxProfit2(int[] prices) {
        int pre0 = 0, f0 = 0, f1 = Integer.MIN_VALUE;
        for (int p : prices) {
            int newF0 = Math.max(f0, f1 + p); // f[i+2][0]
            f1 = Math.max(f1, pre0 - p); // f[i+2][1]
            pre0 = f0;
            f0 = newF0;
        }
        return f0;
    }

}
