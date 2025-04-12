package EndlessCheng.GenreMenu.Graph.NetworkFlow;

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

    private int dfs(int i, Integer[] strength, int k, int n, int[] memo) {
        if (i == 0) {
            return 0;
        }
        if (memo[i] != -1) { // 之前計算過
            return memo[i];
        }
        int x = 1 + k * (n - Integer.bitCount(i));
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if ((i >> j & 1) > 0) {
                res = Math.min(res, dfs(i ^ (1 << j), strength, k, n, memo) + (strength[j] - 1) / x + 1);
            }
        }
        return memo[i] = res; // 記憶化
    }


}
