package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

public class MinimumXORSum {

    // https://leetcode.cn/problems/minimum-xor-sum-of-two-arrays/solutions/802324/zhuang-ya-dp-by-endlesscheng-d8sw/
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums2.length;
        int size = 1 << n;
        int[] dp = new int[size];
        // 枚舉剩余可選數字的集合
        for (int s = 1; s < size; s++) {
            // 當前選擇的數字下標
            int i = n - Integer.bitCount(s);
            int minSum = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                // 枚舉可選的數字
                if ((s >> j & 1) == 1) {
                    minSum = Math.min(minSum, (nums1[i] ^ nums2[j]) + dp[s ^ (1 << j)]);
                }
            }
            dp[s] = minSum;
        }
        return dp[size - 1];
    }

}
