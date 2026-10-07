package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class WaysToSplitArray {

    // https://leetcode.cn/problems/number-of-ways-to-split-array/solutions/1496445/by-endlesscheng-nufi/
    public int waysToSplitArray(int[] nums) {
        long total = 0;
        for (int x : nums) {
            total += x;
        }

        int ans = 0;
        long s = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            s += nums[i];
            if (s * 2 >= total) {
                ans++;
            }
        }
        return ans;
    }

}
