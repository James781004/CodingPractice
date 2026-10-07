package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinimumAverageDifference {

    // https://leetcode.cn/problems/minimum-average-difference/solutions/1460905/qian-hou-zhui-fen-jie-by-endlesscheng-a47e/
    public int minimumAverageDifference(int[] nums) {
        long suf = 0, pre = 0;
        for (int num : nums) {
            suf += num; // 後綴和
        }
        int ans = -1;
        long average = (long) 1e12;
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i]; // 前綴和
            suf -= nums[i]; // 後綴和
            long d = i == nums.length - 1 ? pre / nums.length : Math.abs(pre / (i + 1) - suf / (nums.length - i - 1));
            if (d < average) {
                ans = i;
                average = d;
            }
        }
        return ans;
    }
}
