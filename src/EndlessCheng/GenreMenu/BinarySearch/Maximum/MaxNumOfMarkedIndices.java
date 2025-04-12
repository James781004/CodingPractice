package EndlessCheng.GenreMenu.BinarySearch.Maximum;

import java.util.Arrays;

public class MaxNumOfMarkedIndices {

    // https://leetcode.cn/problems/find-the-maximum-number-of-marked-indices/solutions/2134078/er-fen-da-an-pythonjavacgo-by-endlessche-t9f5/
    public int maxNumOfMarkedIndices(int[] nums) {
        Arrays.sort(nums); // 由於兩個下標不能相等，所以可以進行排序，方便後面查找下標
        int left = 0;
        int right = nums.length / 2 + 1; // 開區間
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (check(nums, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left * 2; // 最多匹配 left 對，有 left * 2 個數
    }

    // 查看數組中是否能滿足有k個下標能被標記
    private boolean check(int[] nums, int k) {
        for (int i = 0; i < k; i++) {
            // 從前面選取k個數，最後k個數，如果都能匹配，則一定滿足
            if (nums[i] * 2 > nums[nums.length - k + i]) {
                return false;
            }
        }
        return true;
    }


    // 同向雙指針
    // 用 nums 左半部分中的數，去匹配 nums 右半部分中的數
    public int maxNumOfMarkedIndices2(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int i = 0;
        for (int j = (n + 1) / 2; j < n; j++) {
            if (nums[i] * 2 <= nums[j]) { // 找到一個匹配
                i++;
            }
        }
        return i * 2;
    }


}
