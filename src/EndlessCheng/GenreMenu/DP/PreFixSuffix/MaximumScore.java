package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaximumScore {

    // https://leetcode.cn/problems/maximum-score-of-a-split/solutions/3867597/qian-hou-zhui-fen-jie-o1-kong-jian-you-h-ytf3/
    public long maximumScore(int[] nums) {
        long preSum = 0;
        for (int x : nums) {
            preSum += x;
        }

        long ans = Long.MIN_VALUE;
        int sufMin = Integer.MAX_VALUE;
        for (int i = nums.length - 1; i > 0; i--) { // 保證前綴至少有一個數
            preSum -= nums[i]; // 撤銷
            sufMin = Math.min(sufMin, nums[i]);
            ans = Math.max(ans, preSum - sufMin);
        }
        return ans;
    }

}
