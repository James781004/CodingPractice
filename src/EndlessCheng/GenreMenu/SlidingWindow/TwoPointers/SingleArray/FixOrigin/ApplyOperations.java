package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class ApplyOperations {

    // https://leetcode.cn/problems/apply-operations-to-an-array/solutions/1951944/o1-kong-jian-xie-fa-by-endlesscheng-ji12/
    public int[] applyOperations(int[] nums) {
        int len = nums.length, idx = 0;

        for (int i = 0; i < len - 1; i++) {
            if (nums[i] == 0) {
                continue;
            }
            if (nums[i] == nums[i + 1]) {
                nums[i + 1] = 0;
                nums[idx++] = nums[i] * 2;
            } else {
                nums[idx++] = nums[i];
            }

        }
        if (nums[len - 1] != 0) {
            nums[idx++] = nums[len - 1];
        }
        for (int i = idx; i < len; i++) {
            nums[i] = 0;
        }
        return nums;
    }

}
