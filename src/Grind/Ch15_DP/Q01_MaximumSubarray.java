package Grind.Ch15_DP;

public class Q01_MaximumSubarray {
    // https://leetcode.cn/problems/maximum-subarray/solutions/847114/dai-ma-sui-xiang-lu-53-zui-da-zi-xu-he-b-xqus/
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int[] dp = new int[nums.length];  // dp[i]表示包括i之前的最大連續子序列和
        dp[0] = nums[0];
        ans = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]); // 狀態轉移公式
            ans = Math.max(ans, dp[i]); // 保存dp[i]的最大值
        }

        return ans;
    }


    // 滾動數組
    public int maxSubArray2(int[] nums) {
        int max = nums[0], maxEnd = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxEnd = Math.max(nums[i], maxEnd + nums[i]);
            max = Math.max(maxEnd, max);
        }

        return max;
    }


    // 貪心
    public int maxSubArray3(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int sum = Integer.MIN_VALUE;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count += nums[i];
            sum = Math.max(sum, count); // 取區間累計的最大值（相當於不斷確定最大子序終止位置）
            if (count <= 0) {
                count = 0; // 相當於重置最大子序起始位置，因為遇到負數一定是拉低總和
            }
        }
        return sum;
    }
}
