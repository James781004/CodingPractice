package LeetcodeMaster.DP;

public class Q13_CompletePack {
    //    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%98%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80%E5%AE%8C%E5%85%A8%E8%83%8C%E5%8C%85.md

    private static void testCompletePack() {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWeight = 4;
        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weight.length; i++) { // 遍歷物品
            for (int j = weight[i]; j <= bagWeight; j++) { // 遍歷背包容量
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        for (int maxValue : dp) {
            System.out.println(maxValue + "   ");
        }
    }


    // 先遍歷背包，再遍歷物品
    private static void testCompletePackAnotherWay() {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagWeight = 4;
        int[] dp = new int[bagWeight + 1];
        for (int i = 1; i <= bagWeight; i++) { // 遍歷背包容量
            for (int j = 0; j < weight.length; j++) { // 遍歷物品
                if (i - weight[j] >= 0) {
                    dp[i] = Math.max(dp[i], dp[i - weight[j]] + value[j]);
                }
            }
        }
        for (int maxValue : dp) {
            System.out.println(maxValue + "   ");
        }
    }

}
