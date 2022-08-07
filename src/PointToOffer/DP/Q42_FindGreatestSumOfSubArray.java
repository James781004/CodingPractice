package PointToOffer.DP;

public class Q42_FindGreatestSumOfSubArray {
    public int FindGreatestSumOfSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int res = Integer.MIN_VALUE;
        int sum = 0;
        for (int val : nums) {
            sum = sum <= 0 ? val : sum + val;
            res = Math.max(res, sum);
        }

        return res;
    }
}
