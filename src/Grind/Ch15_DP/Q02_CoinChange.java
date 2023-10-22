package Grind.Ch15_DP;

import java.util.Arrays;

public class Q02_CoinChange {
    // https://leetcode.cn/problems/coin-change/solutions/975451/dai-ma-sui-xiang-lu-dai-ni-xue-tou-wan-q-80r7/
    public int coinChange(int[] coins, int amount) {
        int max = Integer.MAX_VALUE;
        int[] dp = new int[amount + 1];
        // 初始化dp數組為最大值
        Arrays.fill(dp, max);

        // 當金額為0時需要的硬幣數目為0
        dp[0] = 0;
        for (int i = 0; i < coins.length; i++) {
            // 正序遍歷：完全背包每個硬幣可以選擇多次
            for (int j = coins[i]; j <= amount; j++) {
                // 只有dp[j-coins[i]]不是初始最大值時，該位才有選擇的必要
                if (dp[j - coins[i]] != max) {
                    // 選擇硬幣數目最小的情況
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }


    // 記憶化搜索
    // https://leetcode.cn/problems/coin-change/solutions/1099116/ji-yi-hua-di-gui-by-da-li-chu-qi-ji-_juj-df1x/
    public int coinChangeMemo(int[] coins, int amount) {
        // Arrays.sort(coins);
        int[] memo = new int[amount + 1];
        // 初始化緩存數組，-2表示未訪問
        Arrays.fill(memo, -2);
        return dfs(coins, amount, memo);
    }

    public int dfs(int[] coins, int amount, int[] memo) {
        // 從緩存中找
        if (memo[amount] != -2) return memo[amount];
        // terminal
        if (amount == 0) {
            memo[amount] = 0;
            return 0;
        }
        // 對每層amount新建變量，記錄需要的最少硬幣數量
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            // 當前硬幣面額>剩余金額
            if (coins[i] > amount) continue;
            // 取一個當前硬幣
            int restCount = dfs(coins, amount - coins[i], memo);
            // 後續子問題無解
            if (restCount == -1) continue;
            // 後續子問題有最優解
            int currentCount = restCount + 1;
            minCount = Math.min(minCount, currentCount);
        }
        // 當前子問題不存在的最優解
        if (minCount == Integer.MAX_VALUE) {
            memo[amount] = -1;
            return -1;
        }
        // 緩存當前子問題的最優解
        memo[amount] = minCount;
        return minCount;
    }
}
