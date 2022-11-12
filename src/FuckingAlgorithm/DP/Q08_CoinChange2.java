package FuckingAlgorithm.DP;

public class Q08_CoinChange2 {
//    https://leetcode.cn/problems/coin-change-2/
//    給定不同面額的硬幣和一個總金額。寫出函數來計算可以湊成總金額的硬幣組合數。假設每一種面額的硬幣有無限個。

    public int change(int amount, int[] coins) {
        int n = coins.length;

        // 只使用 coins 中的前 i 個（i 從 1 開始計數）硬幣的面值，若想湊出金額 j，有 dp[i][j] 種湊法。
        int[][] dp = new int[n + 1][amount + 1];

        // base case
        // i = 0 代表不使用任何硬幣面值，這種情況下顯然無法湊出任何金額；
        // j = 0 代表需要湊出的目標金額為 0，那麼什麼都不做就是唯一的一種湊法。
        for (int i = 0; i <= n; i++) dp[i][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - coins[i] >= 0) {
                    dp[i][j] = dp[i - 1][j]
                            + dp[i][j - coins[i - 1]];
                }
            }
        }

        return dp[n][amount];
    }


    public int change1(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= amount; j++) {
                if (j - coins[i] >= 0) dp[j] = dp[j] + dp[j - coins[i]];
            }
        }

        return dp[amount];
    }
}
