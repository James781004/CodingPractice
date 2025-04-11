package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxSumAfterPartitioning {

    // https://leetcode.cn/problems/partition-array-for-maximum-sum/solutions/2234242/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-rq5i/
    private int[] arr, memo;
    private int k;

    public int maxSumAfterPartitioning(int[] arr, int k) {
        this.arr = arr;
        this.k = k;
        int n = arr.length;
        memo = new int[n];
        Arrays.fill(memo, -1); // -1 表示還沒有計算過
        return dfs(n - 1);
    }

    private int dfs(int i) {
        if (i < 0) return 0;
        if (memo[i] != -1) return memo[i]; // 之前計算過了
        int res = 0;
        for (int j = i, mx = 0; j > i - k && j >= 0; --j) {
            mx = Math.max(mx, arr[j]); // 一邊枚舉 j，一邊計算子數組的最大值
            res = Math.max(res, dfs(j - 1) + (i - j + 1) * mx);
        }
        return memo[i] = res; // 記憶化
    }

    public int maxSumAfterPartitioningDP(int[] arr, int k) {
        int n = arr.length;
        var f = new int[n + 1];
        for (int i = 0; i < n; ++i)
            for (int j = i, mx = 0; j > i - k && j >= 0; --j) {
                mx = Math.max(mx, arr[j]); // 一邊枚舉 j，一邊計算子數組的最大值
                f[i + 1] = Math.max(f[i + 1], f[j] + (i - j + 1) * mx);
            }
        return f[n];
    }

    
}
