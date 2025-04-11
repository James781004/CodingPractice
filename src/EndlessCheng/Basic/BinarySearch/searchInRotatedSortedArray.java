package EndlessCheng.Basic.BinarySearch;

public class searchInRotatedSortedArray {
    // https://leetcode.cn/problems/search-in-rotated-sorted-array/solutions/1987503/by-endlesscheng-auuh/
    public int search(int[] nums, int target) {
        int left = -1, right = nums.length - 1; // 開區間 (-1, n-1)
        while (left + 1 < right) { // 開區間不為空
            int mid = left + (right - left) / 2;
            if (isBlue(nums, target, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return nums[right] == target ? right : -1;
    }

    private boolean isBlue(int[] nums, int target, int i) {
        int x = nums[i];
        int end = nums[nums.length - 1];
        if (x > end) {
            return target > end && x >= target;
        }
        return target > end || x >= target;
    }

}
