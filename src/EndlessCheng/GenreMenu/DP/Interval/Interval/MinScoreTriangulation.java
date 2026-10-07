package EndlessCheng.GenreMenu.DP.Interval.Interval;

import java.util.Arrays;

public class MinScoreTriangulation {

    // https://leetcode.cn/problems/minimum-score-triangulation-of-polygon/solutions/2203005/shi-pin-jiao-ni-yi-bu-bu-si-kao-dong-tai-aty6/
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(0, n - 1, values, memo);
    }

    private int dfs(int i, int j, int[] v, int[][] memo) {
        if (i + 1 == j) {
            return 0; // 只有兩個點，無法組成三角形
        }

        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }

        int res = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) { // 枚舉頂點 k
            int subRes = dfs(i, k, v, memo) + dfs(k, j, v, memo) + v[i] * v[j] * v[k];
            res = Math.min(res, subRes);
        }

        return memo[i][j] = res; // 記憶化
    }


    public int minScoreTriangulationDP(int[] v) {
        int n = v.length;
        int[][] f = new int[n][n];
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                f[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++) {
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j] + v[i] * v[j] * v[k]);
                }
            }
        }
        return f[0][n - 1];
    }


}
