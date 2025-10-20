package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

public class LastStoneWeightII {

    // https://leetcode.cn/problems/last-stone-weight-ii/solutions/818247/java-01bei-bao-jing-dian-li-ti-er-wei-yi-6fec/
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int sum = 0;
        for (int num : stones) {
            sum += num;
        }
        //背包容量上限為石頭總重量的一半
        int[][] dp = new int[n + 1][sum / 2 + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= sum / 2; j++) {
                // 能把這塊石頭放進背包時，比較放或不放，選擇最大值
                if (j >= stones[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i - 1]] + stones[i - 1]);
                } else {
                    // 這塊石頭放不進背包時，只能跳過
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // dp[n][sum/2]的最大值為sum/2，因此最理想的結果為0
        return sum - dp[n][sum / 2] * 2;
    }


    public int lastStoneWeightII2(int[] stones) {
        int n = stones.length;
        int sum = 0;
        for (int num : stones) {
            sum += num;
        }
        int cap = sum / 2;
        // 背包容量上限為石頭總重量的一半
        int[] dp = new int[cap + 1];
        for (int i = 0; i < n; i++) {
            // 內層倒序遍歷
            for (int j = cap; j >= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }
        return sum - dp[cap] * 2;
    }


}
