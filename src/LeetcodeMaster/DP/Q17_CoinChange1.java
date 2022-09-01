package LeetcodeMaster.DP;

public class Q17_CoinChange1 {
//    322. 零錢兌換
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0322.%E9%9B%B6%E9%92%B1%E5%85%91%E6%8D%A2.md
//
//    給定不同面額的硬幣 coins 和一個總金額 amount。
//    編寫一個函數來計算可以湊成總金額所需的最少的硬幣個數。如果沒有任何一種硬幣組合能組成總金額，返回 -1。
//
//    你可以認為每種硬幣的數量是無限的。
//
//    示例 1： 輸入：coins = [1, 2, 5], amount = 11 輸出：3 解釋：11 = 5 + 5 + 1
//
//    示例 2： 輸入：coins = [2], amount = 3 輸出：-1
//
//    示例 3： 輸入：coins = [1], amount = 0 輸出：0
//
//    示例 4： 輸入：coins = [1], amount = 1 輸出：1
//
//    示例 5： 輸入：coins = [1], amount = 2 輸出：2


    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        int max = Integer.MAX_VALUE;

        // 初始化dp數組為最大值
        for (int i = 0; i < dp.length; i++) {
            dp[i] = max;
        }

        dp[0] = 0; // 當金額為0時需要的硬幣數目為0

        // 本題求錢幣最小個數，那麽錢幣有順序和沒有順序都可以，都不影響錢幣的最小個數。
        // 所以本題並不強調集合是組合還是排列，誰先遍歷都可以。
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) { // 正序遍歷：完全背包每個硬幣可以選擇多次
                // 只有dp[j-coins[i]]不是初始最大值時，該位才有選擇的必要
                if (dp[j - coins[i]] != max) {
                    // 選擇硬幣數目最小的情況
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }
}
