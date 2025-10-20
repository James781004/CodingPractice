package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;

public class MinPathSum {

    // https://leetcode.cn/problems/minimum-path-sum/solutions/3045828/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-zfb2/
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(m - 1, n - 1, grid, memo);
    }

    private int dfs(int i, int j, int[][] grid, int[][] memo) {
        if (i < 0 || j < 0) {
            return Integer.MAX_VALUE;
        }
        if (i == 0 && j == 0) {
            return grid[i][j];
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = Math.min(dfs(i, j - 1, grid, memo), dfs(i - 1, j, grid, memo)) + grid[i][j];
    }


    public int minPathSumDP(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] f = new int[m + 1][n + 1];
        Arrays.fill(f[0], Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            f[i + 1][0] = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    f[1][1] = grid[i][j];
                } else {
                    f[i + 1][j + 1] = Math.min(f[i + 1][j], f[i][j + 1]) + grid[i][j];
                }
            }
        }
        return f[m][n];
    }


    public int minPathSumDP2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] f = new int[m + 1][n + 1];
        Arrays.fill(f[0], Integer.MAX_VALUE);
        f[0][1] = 0;
        for (int i = 0; i < m; i++) {
            f[i + 1][0] = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                f[i + 1][j + 1] = Math.min(f[i + 1][j], f[i][j + 1]) + grid[i][j];
            }
        }
        return f[m][n];
    }


}
