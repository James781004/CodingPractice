package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.Arrays;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-cut-a-stick/solutions/2982639/jiao-ni-yi-bu-bu-si-kao-qu-jian-dpcong-j-f8px/
    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts);
        int m = cuts.length + 2;
        int[] newCuts = new int[m];
        System.arraycopy(cuts, 0, newCuts, 1, m - 2);
        newCuts[m - 1] = n;

        int[][] memo = new int[m][m];
        return dfs(0, m - 1, newCuts, memo);
    }

    // 切割一根左端點為 cuts[i]，右端點為 cuts[j] 的棍子的最小成本
    private int dfs(int i, int j, int[] cuts, int[][] memo) {
        if (i + 1 == j) { // 無需切割
            return 0;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }
        int res = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) {
            res = Math.min(res, dfs(i, k, cuts, memo) + dfs(k, j, cuts, memo));
        }
        return memo[i][j] = res + cuts[j] - cuts[i];
    }


    public int minCostDP(int n, int[] cuts) {
        Arrays.sort(cuts);
        int m = cuts.length + 2;
        int[] newCuts = new int[m];
        System.arraycopy(cuts, 0, newCuts, 1, m - 2);
        newCuts[m - 1] = n;

        int[][] f = new int[m][m];
        for (int i = m - 3; i >= 0; i--) {
            for (int j = i + 2; j < m; j++) {
                int res = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    res = Math.min(res, f[i][k] + f[k][j]);
                }
                f[i][j] = res + newCuts[j] - newCuts[i];
            }
        }
        return f[0][m - 1];
    }


}
