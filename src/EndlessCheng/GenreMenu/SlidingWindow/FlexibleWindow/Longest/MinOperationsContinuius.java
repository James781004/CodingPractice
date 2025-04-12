package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.Arrays;

public class MinOperationsContinuius {

    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-array-continuous/solutions/1005398/on-zuo-fa-by-endlesscheng-l7yi/
    // 正難則反，考慮最多保留多少個元素不變
    public int minOperations(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int m = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[m++] = nums[i]; // 原地去重
            }
        }

        int ans = 0;
        int left = 0;
        for (int i = 0; i < m; i++) {
            while (nums[left] < nums[i] - n + 1) { // nums[left] 不在窗口內 (nums 中最大元素與最小元素的差必須等於 n - 1)
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return n - ans;
    }


}
