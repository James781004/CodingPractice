package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

import java.util.Arrays;

public class NumSubseq {

    // https://leetcode.cn/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/solutions/1130574/1498-man-zu-tiao-jian-de-zi-xu-lie-shu-m-ax5w/
    public int numSubseq(int[] nums, int target) {
        // 1. 預處理2的n次方
        int MOD = (int) 1e9 + 7, n = nums.length;
        int[] f = new int[n];
        f[0] = 1;
        for (int i = 1; i < f.length; ++i) {
            f[i] = (f[i - 1] << 1) % MOD;
        }
        // 2. 雙指針l和r表示最小元素和最大元素下標
        // 例如l為1，r為5，則l的右邊有5-1=4個元素，以l為最小元素的子序列，就是這4個元素的子集，共有2的4次方個
        Arrays.sort(nums);
        int l = 0, r = n - 1;
        long ans = 0;
        while (l <= r) {
            int sum = nums[l] + nums[r];
            if (sum > target) {
                r--;
            } else {
                ans = (ans + f[r - l]) % MOD;
                l++;
            }
        }
        return (int) ((ans + MOD) % MOD);
    }


}
