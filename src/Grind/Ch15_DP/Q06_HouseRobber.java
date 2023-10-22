package Grind.Ch15_DP;

import java.util.Arrays;

public class Q06_HouseRobber {
    // https://leetcode.cn/problems/house-robber/solutions/994214/dai-ma-sui-xiang-lu-dai-ni-xue-tou-dong-ezvt3/
    public int rob(int[] nums) {
        int prev = 0;
        int curr = 0;

        // 每次循環，計算“偷到當前房子為止的最大金額”
        for (int i : nums) {
            // 循環開始時，curr 表示 dp[k-1]，prev 表示 dp[k-2]
            // dp[k] = max{ dp[k-1], dp[k-2] + i }
            int temp = Math.max(curr, prev + i);
            prev = curr;
            curr = temp;
            // 循環結束時，curr 表示 dp[k]，prev 表示 dp[k-1]
        }

        return curr;
    }


    public int rob2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[nums.length - 1];
    }


    // 記憶化搜索
    private int[] nums, memo;

    public int robMemo(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        memo = new int[n];
        Arrays.fill(memo, -1); // -1 表示沒有計算過
        return dfs(n - 1); // 從最後一個房子開始思考
    }

    // dfs(i) 表示從 nums[0] 到 nums[i] 最多能偷多少
    private int dfs(int i) {
        if (i < 0) { // 遞歸邊界（沒有房子）
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int res = Math.max(dfs(i - 1), dfs(i - 2) + nums[i]);
        memo[i] = res; // 記憶化：保存計算結果
        return res;
    }

}
