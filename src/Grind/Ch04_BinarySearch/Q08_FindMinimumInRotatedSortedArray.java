package Grind.Ch04_BinarySearch;

public class Q08_FindMinimumInRotatedSortedArray {
    // https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/solutions/707851/gong-shui-san-xie-yan-ge-olognyi-qi-kan-6d969/
    public int findMin(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r + 1 >> 1;
            if (nums[mid] >= nums[0]) { // nums[0] 不小於當前 nums[mid]，表示左邊是旋轉後比較大的那段
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return r + 1 < n ? nums[r + 1] : nums[0];
    }
}
