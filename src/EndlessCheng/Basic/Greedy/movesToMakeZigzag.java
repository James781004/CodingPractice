package EndlessCheng.Basic.Greedy;

public class movesToMakeZigzag {

    // https://leetcode.cn/problems/decrease-elements-to-make-array-zigzag/solutions/2135375/mei-you-si-lu-yi-bu-bu-ti-shi-ni-si-kao-cm0h2/
    public int movesToMakeZigzag(int[] nums) {
        int[] s = new int[2];
        for (int i = 0, n = nums.length; i < n; ++i) {
            int left = i > 0 ? nums[i - 1] : Integer.MAX_VALUE;
            int right = i < n - 1 ? nums[i + 1] : Integer.MAX_VALUE;
            s[i % 2] += Math.max(nums[i] - Math.min(left, right) + 1, 0);
        }
        return Math.min(s[0], s[1]);
    }


}
