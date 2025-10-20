package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class MaximumsSplicedArray {

    // https://leetcode.cn/problems/maximum-score-of-spliced-array/solutions/1626030/by-endlesscheng-fm8l/
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        return Math.max(solve(nums1, nums2), solve(nums2, nums1));
    }

    private int solve(int[] nums1, int[] nums2) {
        int s1 = 0;
        int maxSum = 0;
        int f = 0;
        for (int i = 0; i < nums1.length; i++) {
            s1 += nums1[i];
            f = Math.max(f, 0) + nums2[i] - nums1[i];
            maxSum = Math.max(maxSum, f);
        }
        return s1 + maxSum;
    }


}
