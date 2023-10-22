package Grind.Ch04_BinarySearch;

public class Q06_MedianTwoSortedArrays {
    // https://leetcode.cn/problems/median-of-two-sorted-arrays/solutions/15086/he-bing-yi-hou-zhao-gui-bing-guo-cheng-zhong-zhao-/
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 始終保證nums1為較短的數組，nums1過長可能導致j為負數而越界
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;

        // m+n 為奇數，分割線要求左側有 (m+n)/2 + 1 個元素
        // m+n 為偶數，分割線要求左側有 (m+n)/2     個元素
        // 兩種情況其實可以統一寫作 (m+n+1)/2，表示對(m+n)/2向上取整
        // 對整數來說，向上取整等於：(被除數 + (除數 - 1)) / 除數
        // 也可以使用Math類中提供的庫函數
        int leftTotal = (m + n + 1) / 2;
        int left = 0, right = m;
        while (left < right) {
            // +1 向上取整避免 left + 1 = right 時可能無法繼續縮小區間而陷入死循環
            int i = left + (right - left + 1) / 2;
            int j = leftTotal - i;

            // 要找最大i，使得nums1[i-1] <= nums2[j]
            // 使用對立面縮小區間
            if (nums1[i - 1] > nums2[j]) {
                // [i+1, m]均不滿足
                right = i - 1;
            } else {
                // i滿足說明[0, i-1]均不為滿足條件的最大i，舍去以縮小區間
                left = i;
            }
        }

        // 退出循環時left=right，表示最終nums1中分割線的位置
        int i = left;
        // nums2中分割線的位置
        int j = leftTotal - left;
        System.out.println(i);

        // 判斷極端情況
        int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];  // nums1分割線左邊沒有元素
        int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];  // nums2分割線左邊沒有元素
        int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];     // nums1分割線右邊沒有元素
        int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];     // nums2分割線右邊沒有元素

        if ((m + n) % 2 == 0) {
            return (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2.0;
        } else {
            return Math.max(nums1LeftMax, nums2LeftMax);
        }
    }
}
