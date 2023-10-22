package Grind.Ch04_BinarySearch;

public class Q03_SearchRotatedSortedArray {
    // https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/22068/ji-bai-liao-9983de-javayong-hu-by-reedfan/
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 前半部分有序,注意此處用小於等於
            if (nums[start] <= nums[mid]) {
                // target在前半部分
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (target <= nums[end] && target > nums[mid]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }

        }
        return -1;
    }
}
