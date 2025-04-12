package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class NumTriplets {

    // https://leetcode.cn/problems/number-of-ways-where-square-of-number-is-equal-to-product-of-two-numbers/solutions/2914714/java-shuang-zhi-zhen-pai-xu-by-dlycaei0s-g5bs/
    public int numTriplets(int[] nums1, int[] nums2) {
        int ans = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        for (int num : nums1) {
            ans += numTripletsOne((long) num * num, nums2);
        }
        for (int num : nums2) {
            ans += numTripletsOne((long) num * num, nums1);
        }
        return ans;
    }

    public int numTripletsOne(long pow, int[] nums) {
        int left = 0, right = nums.length - 1, ans = 0;
        while (left < right) {
            long k = (long) nums[left] * nums[right];
            if (k > pow) {
                right--;
            } else if (k < pow) {
                left++;
            } else {
                if (nums[left] == nums[right]) {
                    ans += (right - left + 1) * (right - left) / 2;
                    break;
                }
                int m = 1, n = 1;
                while (left < right && nums[left + 1] == nums[left]) {
                    left++;
                    m++;
                }
                while (left < right && nums[right - 1] == nums[right]) {
                    right--;
                    n++;
                }
                ans += m * n;
                left++;
                right--;
            }
        }
        return ans;
    }


}
