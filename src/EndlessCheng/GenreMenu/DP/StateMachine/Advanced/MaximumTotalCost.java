package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

import java.util.Arrays;

public class MaximumTotalCost {

    // https://leetcode.cn/problems/maximize-total-cost-of-alternating-subarrays/solutions/2819339/zhuang-tai-ji-dpcong-ji-yi-hua-sou-suo-d-mtr9/
    public long maximumTotalCost(int[] a) {
        int n = a.length;
        long[][] memo = new long[n][2];
        for (long[] row : memo) {
            Arrays.fill(row, Long.MIN_VALUE);
        }
        return dfs(0, 0, a, memo);
    }

    // 「考慮 a[i] 到 a[n−1]，如果 a[i] 不是子數組的第一個數，則不需要/需要變號，在這種情況下的最大成本和」
    //  j=0 表示不變號，j=1 表示變號
    private long dfs(int i, int j, int[] a, long[][] memo) {
        if (i == a.length) {
            return 0;
        }
        if (memo[i][j] != Long.MIN_VALUE) { // 之前計算過
            return memo[i][j];
        }

        // 1. 分割，把 a[i] 作為子數組的第一個數: dfs(i + 1, 1, a, memo) + a[i]
        // 2. 不分割，考慮 a[i+1] 到 a[n−1]，且 a[i+1] 如果不是子數組的第一個數，
        //    則變號情況與 a[i] 相反: dfs(i + 1, j ^ 1, a, memo) + (j == 0 ? a[i] : -a[i])
        return memo[i][j] = Math.max(dfs(i + 1, 1, a, memo) + a[i],
                dfs(i + 1, j ^ 1, a, memo) + (j == 0 ? a[i] : -a[i]));
    }


    public long maximumTotalCostDP(int[] a) {
        int n = a.length;
        long[][] f = new long[n + 1][2];
        for (int i = n - 1; i >= 0; i--) { // f[i][0] 代表變號，f[i][1] 代表不變號
            f[i][0] = f[i + 1][1] + a[i];
            f[i][1] = Math.max(f[i + 1][1] + a[i], f[i + 1][0] - a[i]);
        }
        return f[0][0];
    }


    public long maximumTotalCostDP2(int[] a) {
        long f0 = 0;
        long f1 = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            long newF0 = f1 + a[i];
            f1 = Math.max(f1 + a[i], f0 - a[i]);
            f0 = newF0;
        }
        return f0;
    }

}
