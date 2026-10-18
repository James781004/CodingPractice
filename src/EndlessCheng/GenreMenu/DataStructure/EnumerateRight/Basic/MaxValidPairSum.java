package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class MaxValidPairSum {

    // https://leetcode.cn/problems/maximum-valid-pair-sum/solutions/3991766/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-lcla/
    public int maxValidPairSum(int[] nums, int k) {
        int ans = 0;
        int mx = 0;
        for (int j = k; j < nums.length; j++) {
            mx = Math.max(mx, nums[j - k]); // nums[i] 的最大值
            ans = Math.max(ans, mx + nums[j]);
        }
        return ans;
    }

}
