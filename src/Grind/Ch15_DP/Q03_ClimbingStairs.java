package Grind.Ch15_DP;

public class Q03_ClimbingStairs {
    // https://leetcode.cn/problems/climbing-stairs/solutions/854668/dai-ma-sui-xiang-lu-dong-tai-gui-hua-jin-y1hw/
    public int climbStairs(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2, sum = 0;

        for (int i = 3; i <= n; i++) {
            sum = a + b;  // f(i - 1) + f(i - 2)
            a = b;        // 記錄f(i - 1)，即下一輪的f(i - 2)
            b = sum;      // 記錄f(i)，即下一輪的f(i - 1)
        }
        return b;
    }


    // 常規方式
    public int climbStairs2(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
