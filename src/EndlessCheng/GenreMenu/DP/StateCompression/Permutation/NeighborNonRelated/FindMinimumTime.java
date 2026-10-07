package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.NeighborNonRelated;

import java.util.Arrays;
import java.util.List;

public class FindMinimumTime {

    // https://leetcode.cn/problems/minimum-time-to-break-locks-i/solutions/3014389/san-chong-fang-fa-pai-lie-xing-hui-su-zh-cnpe/
    public int findMinimumTime(List<Integer> strength, int k) {
        int n = strength.size();
        int[] memo = new int[1 << n];
        Arrays.fill(memo, -1);
        return dfs((1 << n) - 1, strength.toArray(Integer[]::new), k, n, memo);
    }

    // 定義 dfs(i) 表示尚未開鎖的下標集合為 i 時，打開所有鎖需要的最少時間
    private int dfs(int i, Integer[] strength, int k, int n, int[] memo) {
        if (i == 0) {
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int x = 1 + k * (n - Integer.bitCount(i)); // 當前的 x=1+k⋅(n−∣i∣)，其中 ∣i∣ 是集合 i 的大小
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if ((i >> j & 1) > 0) {
                res = Math.min(res, dfs(i ^ (1 << j), strength, k, n, memo) + (strength[j] - 1) / x + 1);
            }
        }
        return memo[i] = res; // 記憶化
    }


    public int findMinimumTimeDP(List<Integer> strength, int k) {
        Integer[] a = strength.toArray(Integer[]::new);
        int n = a.length;
        int m = 1 << n;
        int[] f = new int[m];
        Arrays.fill(f, 1, m, Integer.MAX_VALUE);
        for (int i = 1; i < m; i++) {
            int x = 1 + k * (n - Integer.bitCount(i));
            for (int j = 0; j < n; j++) {
                if ((i >> j & 1) > 0) {
                    f[i] = Math.min(f[i], f[i ^ (1 << j)] + (a[j] - 1) / x + 1);
                }
            }
        }
        return f[m - 1];
    }


}
