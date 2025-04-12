package EndlessCheng.GenreMenu.BinarySearch.Basic;

import java.util.Arrays;

public class CountFairPairs {

    // https://leetcode.cn/problems/count-the-number-of-fair-pairs/solutions/2107079/er-fen-cha-zhao-de-ling-huo-yun-yong-by-wplbj/
    public long countFairPairs(int[] nums, int lower, int upper) {
        long ans = 0;
        Arrays.sort(nums); // 排序不影響答案，可以先排序

        // 枚舉 nums[j] 看看 nums[i] 可以在哪些位置
        // nums[i] 需要滿足 lower−nums[j]≤nums[i]≤upper−nums[j]
        // 並且 0≤i<j
        for (int j = 0; j < nums.length; ++j) {
            int r = lowerBound(nums, j, upper - nums[j] + 1); // <= upper-nums[j] 的 nums[i] 的個數
            int l = lowerBound(nums, j, lower - nums[j]); // < lower-nums[j] 的 nums[i] 的個數
            ans += r - l;
        }
        return ans;
    }

    private int lowerBound(int[] nums, int right, int target) {
        int left = -1; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = (left + right) >>> 1;
            if (nums[mid] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right;
    }


}
