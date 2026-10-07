package EndlessCheng.GenreMenu.DP.Divide.Best;

import java.util.Arrays;

public class MaximumTotalCost {

    // https://leetcode.cn/problems/maximize-total-cost-of-alternating-subarrays/solutions/2819339/zhuang-tai-ji-dpcong-ji-yi-hua-sou-suo-d-mtr9/
    public long maximumTotalCost(int[] a) {
        int n = a.length;
        long[] memo = new long[n];
        Arrays.fill(memo, Long.MIN_VALUE); // Long.MIN_VALUE 表示沒有計算過
        return dfs(n - 1, a, memo);
    }

    private long dfs(int i, int[] a, long[] memo) {
        if (i < 0) {
            return 0;
        }
        if (i == 0) {
            return a[0];
        }
        if (memo[i] != Long.MIN_VALUE) { // 之前計算過
            return memo[i];
        }

        // 分成長為 1 的子數組，即 a[i] 單獨作為一個長為 1 的子數組，
        // 接下來需要解決的問題為：a[0] 到 a[i−1] 的最大成本和，即 dfs(i)=dfs(i−1)+a[i]。
        // 分成長為 2 的子數組，即 a[i−1] 和 a[i] 作為一個長為 2 的子數組，
        // 接下來需要解決的問題為：a[0] 到 a[i−2] 的最大成本和，即 dfs(i)=dfs(i−2)+a[i−1]−a[i]。
        return memo[i] = Math.max(dfs(i - 1, a, memo) + a[i], dfs(i - 2, a, memo) + a[i - 1] - a[i]);
    }


    public long maximumTotalCostDP(int[] a) {
        int n = a.length;
        long[] f = new long[n + 1]; // 表示 a[0] 到 a[i] 的最大成本和
        f[1] = a[0];
        for (int i = 1; i < n; i++) {
            f[i + 1] = Math.max(f[i] + a[i], f[i - 1] + a[i - 1] - a[i]);
        }
        return f[n];
    }


    public long maximumTotalCostDP2(int[] a) {
        long f0 = 0;
        long f1 = a[0];
        for (int i = 1; i < a.length; i++) {
            long tmp = f1;
            f1 = Math.max(f1 + a[i], f0 + a[i - 1] - a[i]);
            f0 = tmp;
        }
        return f1;
    }

}
