package Grind.Ch15_DP;

import java.util.Arrays;

public class Q08_LongestIncreasingSubsequence {
    // https://leetcode.cn/problems/longest-increasing-subsequence/solutions/24173/zui-chang-shang-sheng-zi-xu-lie-dong-tai-gui-hua-2/
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length]; // dp[i] 的值代表 nums 以 nums[i] 結尾的最長子序列長度
        int res = 0;
        Arrays.fill(dp, 1);

        // 考慮每輪計算新 dp[i] 時，遍歷 [0,i) 列表區間，做以下判斷：
        // 當 nums[i]>nums[j] 時： nums[i] 可以接在 nums[j] 之後（此題要求嚴格遞增），此情況下最長上升子序列長度為 dp[j]+1 ；
        // 當 nums[i]<=nums[j] 時： nums[i] 無法接在 nums[j] 之後，此情況上升子序列不成立，跳過。
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 動態規劃 + 二分查找
    public int lengthOfLIS2(int[] nums) {
        // 維護一個列表 tails，其中每個元素 tails[k] 的值代表 長度為 k+1 的子序列尾部元素的值
        // 如 [1,4,6] 序列，長度為 1,2,3 的子序列尾部元素值分別為 tails=[1,4,6]
        int[] tails = new int[nums.length];

        //  設 res 為 tails 當前長度，代表直到當前的最長上升子序列長度
        int res = 0;

        // 區間中存在 tails[i]>nums[k]： 將第一個滿足 tails[i]>nums[k] 執行 tails[i]=nums[k] ；因為更小的 nums[k] 後更可能接一個比它大的數字。
        // 區間中不存在 tails[i]>nums[k]： 意味著 nums[k] 可以接在前面所有長度的子序列之後，因此肯定是接到最長的後面（長度為 res ），新子序列長度為 res+1。
        for (int num : nums) {
            int i = 0, j = res;
            while (i < j) {
                int m = (i + j) / 2;
                if (tails[m] < num) i = m + 1;
                else j = m;
            }
            tails[i] = num;
            if (res == j) res++;
        }
        return res;
    }
}
