package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class SuccessfulPairs {

    // https://leetcode.cn/problems/successful-pairs-of-spells-and-potions/solutions/1595712/by-endlesscheng-1kbp/
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        for (int i = 0; i < spells.length; i++) {
            long target = (success - 1) / spells[i];
            if (target < potions[potions.length - 1]) { // 防止 long 轉成 int 溢出
                spells[i] = potions.length - upperBound(potions, (int) target);
            } else {
                spells[i] = 0;
            }
        }
        return spells;
    }

    // 直接二分 long 是 28ms，改成 int 是 26ms
    private int upperBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] <= target
            // nums[right] > target
            int mid = (left + right) >>> 1;
            if (nums[mid] > target) {
                right = mid; // 二分范圍縮小到 (left, mid)
            } else {
                left = mid; // 二分范圍縮小到 (mid, right)
            }
        }
        return right;
    }


}
