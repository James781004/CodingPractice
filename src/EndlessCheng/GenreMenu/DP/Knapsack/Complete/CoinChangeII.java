package EndlessCheng.GenreMenu.DP.Knapsack.Complete;

import java.util.Arrays;

public class CoinChangeII {

    // https://leetcode.cn/problems/coin-change-ii/solutions/2706227/shi-pin-wan-quan-bei-bao-cong-ji-yi-hua-o3ew0/
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] memo = new int[n][amount + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        return dfs(n - 1, amount, coins, memo);
    }

    private int dfs(int i, int c, int[] coins, int[][] memo) {
        if (i < 0) {
            return c == 0 ? 1 : 0;
        }
        if (memo[i][c] != -1) { // 之前算過了
            return memo[i][c];
        }
        if (c < coins[i]) { // 只能不選
            return memo[i][c] = dfs(i - 1, c, coins, memo);
        }
        // 不選 + 繼續選
        return memo[i][c] = dfs(i - 1, c, coins, memo) + dfs(i, c - coins[i], coins, memo);
    }


    public int changeDP(int amount, int[] coins) {
        int n = coins.length;
        int[][] f = new int[n + 1][amount + 1];
        f[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int c = 0; c <= amount; c++) {
                if (c < coins[i]) {
                    f[i + 1][c] = f[i][c];
                } else {
                    f[i + 1][c] = f[i][c] + f[i + 1][c - coins[i]];
                }
            }
        }
        return f[n][amount];
    }


    public int changeDP2(int amount, int[] coins) {
        int[] f = new int[amount + 1];
        f[0] = 1;
        for (int x : coins) {
            for (int c = x; c <= amount; c++) {
                f[c] += f[c - x];
            }
        }
        return f[amount];
    }


}
