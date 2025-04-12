package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

public class MergeArrays {

    // https://leetcode.cn/problems/merge-sorted-array/
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 兩個指針分別初始化在兩個數組的最後一個元素（類似拉鏈兩端的鋸齒）
        int i = m - 1, j = n - 1;
        // 生成排序的結果（類似拉鏈的拉鎖）
        int p = nums1.length - 1;
        // 從後向前生成結果數組，類似合並兩個有序鏈表的邏輯
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[p] = nums1[i];
                i--;
            } else {
                nums1[p] = nums2[j];
                j--;
            }
            p--;
        }
        // 可能其中一個數組的指針走到盡頭了，而另一個還沒走完
        // 因為我們本身就是在往 nums1 中放元素，所以只需考慮 nums2 是否剩元素即可
        while (j >= 0) {
            nums1[p] = nums2[j];
            j--;
            p--;
        }
    }

}
