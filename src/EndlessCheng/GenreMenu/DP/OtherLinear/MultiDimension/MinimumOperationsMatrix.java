package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.Arrays;

public class MinimumOperationsMatrix {

    // https://leetcode.cn/problems/minimum-number-of-operations-to-satisfy-conditions/solutions/2749283/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-8i0e/
    public int minimumOperations(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] cnt = new int[n][10]; // cnt[i][x] 表示第 i 列元素 x 的出現次數
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                cnt[j][row[j]]++;
            }
        }
        int[][] memo = new int[n][11];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return m * n - dfs(n - 1, 10, cnt, memo);
    }

    // dfs(i,j) 表示考慮前 i 列，且第 i+1 列都變成 j 時的最大保留不變元素個數
    private int dfs(int i, int j, int[][] cnt, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        int res = 0;

        // 枚舉第 i 列的元素都變成 k
        for (int k = 0; k < 10; ++k) {
            if (k != j) {
                res = Math.max(res, dfs(i - 1, k, cnt, memo) + cnt[i][k]);
            }
        }
        return memo[i][j] = res; // 記憶化
    }


    public int minimumOperationsDP(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int f0 = 0, f1 = 0, pre = -1;
        for (int i = 0; i < n; i++) {
            int[] cnt = new int[10];
            for (int[] row : grid) {
                cnt[row[i]]++;
            }
            int mx = -1, mx2 = 0, x = -1;
            for (int v = 0; v < 10; v++) {
                int res = (v != pre ? f0 : f1) + cnt[v];
                if (res > mx) {
                    mx2 = mx;
                    mx = res;
                    x = v;
                } else if (res > mx2) {
                    mx2 = res;
                }
            }
            f0 = mx;
            f1 = mx2;
            pre = x;
        }
        return m * n - f0;
    }


}
