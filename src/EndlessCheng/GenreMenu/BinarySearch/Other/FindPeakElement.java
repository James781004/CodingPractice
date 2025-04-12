package EndlessCheng.GenreMenu.BinarySearch.Other;

public class FindPeakElement {

    // https://leetcode.cn/problems/find-peak-element/solutions/1987497/by-endlesscheng-9ass/
    public int findPeakElement(int[] nums) {
        int left = -1;
        int right = nums.length - 1; // 開區間 (-1, n-1)
        while (left + 1 < right) { // 開區間不為空
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }


}
