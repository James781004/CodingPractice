package FuckingAlgorithm.DP;

public class Q04_MaxSubArray {
    //    https://leetcode.cn/problems/maximum-subarray/
//    53. 最大子數組和
//    給你一個整數數組 nums ，請你找出一個具有最大和的連續子數組（子數組最少包含一個元素），返回其最大和。
//
//    子數組 是數組中的一個連續部分。

    public int maxSubArray(int[] nums) {
        int left = 0, right = 0;
        int windowSum = 0, maxSum = Integer.MIN_VALUE;
        while (right < nums.length) {
            // 擴大窗口並更新窗口內的元素和
            windowSum += nums[right];
            right++;

            // 更新答案
            maxSum = windowSum > maxSum ? windowSum : maxSum;

            // 判斷窗口是否要收縮
            while (windowSum < 0) {
                // 縮小窗口並更新窗口內的元素和
                windowSum -= nums[left];
                left++;
            }
        }

        return maxSum;
    }


    public int maxSubArrayDP(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // 定義：helper[i] 記錄以 nums[i] 為結尾的「最大子數組和」
        int[] dp = new int[n];
        // base case
        // 第一個元素前面沒有子數組
        dp[0] = nums[0];
        // 狀態轉移方程
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }

        // 得到 nums 的最大子數組
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    public int maxSubArrayDP2(int[] nums) {
        int pre = 0;
        int res = nums[0];
        for (int num : nums) {
            pre = Math.max(pre + num, num);
            res = Math.max(res, pre);
        }
        return res;
    }

    // 前綴和技巧解題
    public int maxSubArrayPrefix(int[] nums) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        preSum[0] = 0;
        // 構造 nums 的前綴和數組
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        int res = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 維護 minVal 是 preSum[0..i] 的最小值
            minVal = Math.min(minVal, preSum[i]);
            // 以 nums[i] 結尾的最大子數組和就是 preSum[i+1] - min(preSum[0..i])
            res = Math.max(res, preSum[i + 1] - minVal);
        }
        return res;
    }
}
