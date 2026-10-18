package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class MaximumDifference {

    // https://leetcode.cn/problems/maximum-difference-between-increasing-elements/solutions/1018320/on-zuo-fa-by-endlesscheng-o2wp/
    public int maximumDifference(int[] nums) {
        int ans = 0;
        int preMin = Integer.MAX_VALUE;
        for (int x : nums) {
            ans = Math.max(ans, x - preMin); // 把 x 当作 nums[j]
            preMin = Math.min(preMin, x);    // 把 x 当作 nums[i]
        }
        return ans > 0 ? ans : -1;
    }

}
