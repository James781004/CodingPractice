package EndlessCheng.GenreMenu.SlidingWindow.ThreePointers;

import java.util.Arrays;

public class CountFairPairs {

    // https://leetcode.cn/problems/count-the-number-of-fair-pairs/solutions/2107079/er-fen-cha-zhao-de-ling-huo-yun-yong-by-wplbj/
    // 由於隨著 nums[j] 的變大，upper−nums[j] 和 lower−nums[j] 都在變小，有單調性，可以用三指針實現。
    public long countFairPairs(int[] nums, int lower, int upper) {
        long ans = 0;
        Arrays.sort(nums);
        int left = nums.length;
        int right = nums.length;

        // 枚舉 nums[j] 看看 nums[i] 可以在哪些位置
        for (int j = 0; j < nums.length; j++) {
            // right最終指向nums[right] + nums[i] <= upper的位置
            while (right > 0 && nums[right - 1] > upper - nums[j]) {
                right--;
            }

            // left最終指向nums[left] + nums[i] < lower的位置
            while (left > 0 && nums[left - 1] >= lower - nums[j]) {
                left--;
            }
            ans += Math.min(right, j) - Math.min(left, j);
        }
        return ans;
    }


}
