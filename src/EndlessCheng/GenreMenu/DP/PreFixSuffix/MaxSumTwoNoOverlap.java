package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MaxSumTwoNoOverlap {

    // https://leetcode.cn/problems/maximum-sum-of-two-non-overlapping-subarrays/solutions/2245647/tu-jie-mei-you-si-lu-yi-zhang-tu-miao-do-3lli/
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = s[i] + nums[i]; // 計算 nums 的前綴和
        return Math.max(f(s, firstLen, secondLen), f(s, secondLen, firstLen));
    }

    private int f(int[] s, int firstLen, int secondLen) {
        int maxSumA = 0, res = 0;
        for (int i = firstLen + secondLen; i < s.length; ++i) {
            maxSumA = Math.max(maxSumA, s[i - secondLen] - s[i - secondLen - firstLen]);
            res = Math.max(res, maxSumA + s[i] - s[i - secondLen]);
        }
        return res;
    }

}
