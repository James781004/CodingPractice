package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;

public class MaxMoves {

    // https://leetcode.cn/problems/maximum-number-of-moves-in-a-grid/solutions/2269244/cong-ji-yi-hua-sou-suo-dao-di-tui-by-end-pgq3/
    private int[][] dir = new int[][]{{-1, 1}, {0, 1}, {1, 1}};
    private int[][] cnt;
    private int ans = 0, m, n;

    public int maxMoves(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        cnt = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(cnt[i], -1);
        for (int i = 0; i < m; i++) ans = Math.max(ans, dfs(i, 0, grid));
        return ans;
    }

    private int dfs(int x, int y, int[][] grid) {
        if (cnt[x][y] != -1) return cnt[x][y];
        int max = 0;
        for (int i = 0; i < 3; i++) {
            int dx = x + dir[i][0], dy = y + dir[i][1];
            if (dx < 0 || dx >= m || dy < 0 || dy >= n) continue;
            if (grid[dx][dy] > grid[x][y]) {
                max = Math.max(max, dfs(dx, dy, grid) + 1);
            }
        }
        cnt[x][y] = max;
        return max;
    }


    public int maxMovesDP(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int j = n - 2; j >= 0; j--) {
            for (int i = 0; i < m; i++) {
                for (int k = Math.max(i - 1, 0); k < Math.min(i + 2, m); k++) {
                    if (grid[k][j + 1] > grid[i][j]) dp[i][j] = Math.max(dp[i][j], dp[k][j + 1] + 1);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) ans = Math.max(ans, dp[i][0]);
        return ans;
    }

}
