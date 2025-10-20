package EndlessCheng.GenreMenu.DP.Grid.Advanced;

import java.util.Arrays;

public class CountPaths {

    // https://leetcode.cn/problems/number-of-increasing-paths-in-a-grid/solutions/1641515/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-xecc/
    private static final int MOD = 1_000_000_007;
    private static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int countPaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }

        long ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans += dfs(i, j, grid, memo);
            }
        }
        return (int) (ans % MOD);
    }

    private int dfs(int i, int j, int[][] grid, int[][] memo) {
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        long res = 1;
        for (int[] d : DIRS) {
            int x = i + d[0];
            int y = j + d[1];
            if (0 <= x && x < grid.length && 0 <= y && y < grid[i].length && grid[x][y] > grid[i][j]) {
                res += dfs(x, y, grid, memo);
            }
        }
        return memo[i][j] = (int) (res % MOD); // 記憶化
    }


}
