package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LeftRightDifference {

    // https://leetcode.cn/problems/left-and-right-sum-differences/solutions/2134217/mo-ni-jian-ji-xie-fa-by-endlesscheng-utdr/
    public int[] leftRightDifference(int[] nums) {
        int total = 0;
        for (int x : nums) {
            total += x;
        }

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            nums[i] = Math.abs(leftSum * 2 + x - total);
            leftSum += x;
        }
        return nums;
    }

}
