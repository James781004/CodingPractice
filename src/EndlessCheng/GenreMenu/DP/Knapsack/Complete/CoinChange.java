package EndlessCheng.GenreMenu.DP.Knapsack.Complete;

import java.util.Arrays;

public class CoinChange {

    // https://leetcode.cn/problems/coin-change/solutions/2119065/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-21m5/
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[][] memo = new int[n][amount + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        int ans = dfs(n - 1, amount, coins, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int c, int[] coins, int[][] memo) {
        if (i < 0) {
            return c == 0 ? 0 : Integer.MAX_VALUE / 2; // 除 2 防止下面 + 1 溢出
        }
        if (memo[i][c] != -1) { // 之前計算過
            return memo[i][c];
        }
        if (c < coins[i]) { // 只能不選
            return memo[i][c] = dfs(i - 1, c, coins, memo);
        }
        // 不選 vs 繼續選
        return memo[i][c] = Math.min(dfs(i - 1, c, coins, memo), dfs(i, c - coins[i], coins, memo) + 1);
    }


    public int coinChangeDP(int[] coins, int amount) {
        int n = coins.length;
        int[][] f = new int[n + 1][amount + 1];
        Arrays.fill(f[0], Integer.MAX_VALUE / 2); // 除 2 防止下面 + 1 溢出
        f[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= amount; c++) {
                if (c < coins[i]) {
                    f[i + 1][c] = f[i][c];
                } else {
                    f[i + 1][c] = Math.min(f[i][c], f[i + 1][c - coins[i]] + 1);
                }
            }
        }
        int ans = f[n][amount];
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }


    public int coinChangeDP2(int[] coins, int amount) {
        int[] f = new int[amount + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int x : coins) {
            for (int c = x; c <= amount; c++) {
                f[c] = Math.min(f[c], f[c - x] + 1);
            }
        }
        int ans = f[amount];
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }


}
