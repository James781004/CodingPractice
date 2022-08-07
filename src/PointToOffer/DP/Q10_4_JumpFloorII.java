package PointToOffer.DP;

import java.util.Arrays;

public class Q10_4_JumpFloorII {

    // 1.知道f(n) = f(n-1) + f(n-2) +....+f(0)，那么f(n-1) = f(n-2) + f(n-3) +......+f(0);
    // 2.可知 f(n) = 2 * f(n-1);
    // 3.初始ans = 1;
    public int JumpFloorIIMath(int target) {
        if (target <= 0) {
            throw new RuntimeException("target必须为正数!");
        }

        int pre1 = 1, cur = 1; // 初始化为一级台阶
        for (int i = 2; i <= target; i++) {
            cur = 2 * pre1;
            pre1 = cur;
        }
        return cur;
    }


    public int jumpFloorIIDP(int target) {
        int[] dp = new int[target];
        Arrays.fill(dp, 1);
        for (int i = 1; i < target; i++)
            for (int j = 0; j < i; j++)
                dp[i] += dp[j];
        return dp[target - 1];
    }
}
