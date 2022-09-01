package LeetcodeMaster.DP;

public class Q16_ClimbStairs2 {
    // https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0070.%E7%88%AC%E6%A5%BC%E6%A2%AF%E5%AE%8C%E5%85%A8%E8%83%8C%E5%8C%85%E7%89%88%E6%9C%AC.md
//    一步一個台階，兩個台階，三個台階，.......，直到 m個台階。問有多少種不同的方法可以爬到樓頂呢？
//
//            1階，2階，.... m階就是物品，樓頂就是背包。
//
//    每一階可以重覆使用，例如跳了1階，還可以繼續跳1階。
//
//    問跳到樓頂有幾種方法其實就是問裝滿背包有幾種方法。

    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        int[] weight = {1, 2};
        dp[0] = 1;
        
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < weight.length; j++) {
                if (i >= weight[j]) dp[i] += dp[i - weight[j]];
            }
        }

        return dp[n];
    }
}
