package EndlessCheng.Basic.Prefix;

public class maxSumTwoNoOverlap {

    // https://leetcode.cn/problems/maximum-sum-of-two-non-overlapping-subarrays/solutions/2245647/tu-jie-mei-you-si-lu-yi-zhang-tu-miao-do-3lli/
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        int n = nums.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++)
            s[i + 1] = s[i] + nums[i]; // 計算 nums 的前綴和
        int ans = 0, maxSumA = 0, maxSumB = 0;
        for (int i = firstLen + secondLen; i <= n; i++) { // 起始點 i 從 firstLen + secondLen 開始計算
            maxSumA = Math.max(maxSumA, s[i - secondLen] - s[i - secondLen - firstLen]); // 找出最大 A 區間
            maxSumB = Math.max(maxSumB, s[i - firstLen] - s[i - firstLen - secondLen]); // 找出最大 B 區間
            ans = Math.max(ans, Math.max(maxSumA + s[i] - s[i - secondLen],  // 左 a 右 b
                    maxSumB + s[i] - s[i - firstLen])); // 左 b 右 a
        }
        return ans;
    }


}
