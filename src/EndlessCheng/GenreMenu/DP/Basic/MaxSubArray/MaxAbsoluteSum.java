package EndlessCheng.GenreMenu.DP.Basic.MaxSubArray;

public class MaxAbsoluteSum {

    // https://leetcode.cn/problems/maximum-absolute-sum-of-any-subarray/solutions/2377930/liang-chong-fang-fa-dong-tai-gui-hua-qia-dczr/
    public int maxAbsoluteSum(int[] nums) {
        int ans = 0, fMax = 0, fMin = 0;
        for (int x : nums) {
            fMax = Math.max(fMax, 0) + x;
            fMin = Math.min(fMin, 0) + x;
            ans = Math.max(ans, Math.max(fMax, -fMin));
        }
        return ans;
    }


}
