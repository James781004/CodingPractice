package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

import java.util.Arrays;

public class MinimumAddedInteger {

    // https://leetcode.cn/problems/find-the-integer-added-to-array-ii/solutions/2759118/onlogn-pai-xu-shuang-zhi-zhen-pythonjava-rdj9/
    public int minimumAddedInteger(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 枚舉保留 nums1[2] 或者 nums1[1] 或者 nums1[0]
        // 倒著枚舉是因為 nums1[i] 越大答案越小，第一個滿足的就是答案
        for (int i = 2; i > 0; i--) {
            int x = nums2[0] - nums1[i];
            // 在 {nums1[i] + x} 中找子序列 nums2
            int j = 0;
            for (int k = i; k < nums1.length; k++) {
                if (nums2[j] == nums1[k] + x && ++j == nums2.length) {
                    // nums2 是 {nums1[i] + x} 的子序列
                    return x;
                }
            }
        }
        // 題目保證答案一定存在
        return nums2[0] - nums1[0];
    }


}
