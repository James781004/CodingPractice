package EndlessCheng.GenreMenu.DP.Grid.Advanced;

import java.util.Arrays;

public class CherryPickupII {

    // https://leetcode.cn/problems/cherry-pickup-ii/solutions/2768158/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-i70v/
    public int cherryPickup(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] memo = new int[m][n][n];
        for (int[][] me : memo) {
            for (int[] r : me) {
                Arrays.fill(r, -1); // -1 表示沒有計算過
            }
        }
        return dfs(0, 0, n - 1, grid, memo);
    }

    private int dfs(int i, int j, int k, int[][] grid, int[][][] memo) {
        int m = grid.length;
        int n = grid[0].length;
        if (i == m || j < 0 || j >= n || k < 0 || k >= n) {
            return 0;
        }
        if (memo[i][j][k] != -1) { // 之前計算過
            return memo[i][j][k];
        }
        int res = 0;
        for (int j2 = j - 1; j2 <= j + 1; j2++) {
            for (int k2 = k - 1; k2 <= k + 1; k2++) {
                res = Math.max(res, dfs(i + 1, j2, k2, grid, memo));
            }
        }
        res += grid[i][j] + (k != j ? grid[i][k] : 0);
        memo[i][j][k] = res; // 記憶化
        return res;
    }


    public int cherryPickupDP(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][][] f = new int[m + 1][n + 2][n + 2];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < Math.min(n, i + 1); j++) {
                for (int k = Math.max(j + 1, n - 1 - i); k < n; k++) {
                    f[i][j + 1][k + 1] = max(
                            f[i + 1][j][k], f[i + 1][j][k + 1], f[i + 1][j][k + 2],
                            f[i + 1][j + 1][k], f[i + 1][j + 1][k + 1], f[i + 1][j + 1][k + 2],
                            f[i + 1][j + 2][k], f[i + 1][j + 2][k + 1], f[i + 1][j + 2][k + 2]
                    ) + grid[i][j] + grid[i][k];
                }
            }
        }
        return f[0][1][n];
    }

    private int max(int x, int... y) {
        for (int v : y) {
            x = Math.max(x, v);
        }
        return x;
    }


    public int cherryPickupDP2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] pre = new int[n + 2][n + 2];
        int[][] cur = new int[n + 2][n + 2];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < Math.min(n, i + 1); j++) {
                for (int k = Math.max(j + 1, n - 1 - i); k < n; k++) {
                    cur[j + 1][k + 1] = max2(
                            pre[j][k], pre[j][k + 1], pre[j][k + 2],
                            pre[j + 1][k], pre[j + 1][k + 1], pre[j + 1][k + 2],
                            pre[j + 2][k], pre[j + 2][k + 1], pre[j + 2][k + 2]
                    ) + grid[i][j] + grid[i][k];
                }
            }
            int[][] tmp = pre;
            pre = cur; // 下一個 i 的 pre 是 cur
            cur = tmp;
        }
        return pre[1][n];
    }

    private int max2(int x, int... y) {
        for (int v : y) {
            x = Math.max(x, v);
        }
        return x;
    }


}
