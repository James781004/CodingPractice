package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class MaximumProduct {

    // https://leetcode.cn/problems/maximum-product-of-first-and-last-elements-of-a-subsequence/solutions/3700555/nao-jin-ji-zhuan-wan-mei-ju-you-wei-hu-z-93zo/
    public long maximumProduct(int[] nums, int m) {
        long ans = Long.MIN_VALUE;
        int mn = Integer.MAX_VALUE;
        int mx = Integer.MIN_VALUE;
        for (int i = m - 1; i < nums.length; i++) {
            // 維護左邊 [0,i-m+1] 中的最小值和最大值
            int y = nums[i - m + 1];
            mn = Math.min(mn, y);
            mx = Math.max(mx, y);
            // 枚舉右
            long x = nums[i];
            ans = Math.max(ans, Math.max(x * mn, x * mx));
        }
        return ans;
    }

}
