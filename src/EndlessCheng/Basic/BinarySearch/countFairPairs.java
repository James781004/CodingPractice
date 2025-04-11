package EndlessCheng.Basic.BinarySearch;

import java.util.Arrays;

public class countFairPairs {
    // https://leetcode.cn/problems/count-the-number-of-fair-pairs/solutions/2107079/er-fen-cha-zhao-de-ling-huo-yun-yong-by-wplbj/

    // 方法一：排序 + 二分
    public long countFairPairs(int[] nums, int lower, int upper) {
        long ans = 0;
        Arrays.sort(nums);
        for (int j = 0; j < nums.length; ++j) {
            int r = lowerBound(nums, j, upper - nums[j] + 1); // <= upper-nums[j] 的 nums[i] 的個數
            int l = lowerBound(nums, j, lower - nums[j]); // < lower-nums[j] 的 nums[i] 的個數
            ans += r - l;
        }
        return ans;
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
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


    // 方法二：三指針
    public long countFairPairs2(int[] nums, int lower, int upper) {
        long ans = 0;
        Arrays.sort(nums);
        int left = nums.length;
        int right = nums.length;
        for (int j = 0; j < nums.length; j++) {
            while (right > 0 && nums[right - 1] > upper - nums[j]) {
                right--;
            }
            while (left > 0 && nums[left - 1] >= lower - nums[j]) {
                left--;
            }
            ans += Math.min(right, j) - Math.min(left, j);
        }
        return ans;
    }


}
