package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class WaysToMakeFair {

    // https://leetcode.cn/problems/ways-to-make-a-fair-array/solutions/2078340/sheng-cheng-ping-heng-shu-zu-de-fang-an-0mkaj/
    public int waysToMakeFair(int[] nums) {
        int ans = 0, s = 0, n = nums.length;
        for (int i = 0; i < n; i++)
            s += i % 2 == 0 ? nums[i] : -nums[i];
        for (int i = 0; i < n; i++) {
            s -= i % 2 == 0 ? nums[i] : -nums[i];
            ans += s == 0 ? 1 : 0;
            s -= i % 2 == 0 ? nums[i] : -nums[i];
        }
        return ans;
    }

}
