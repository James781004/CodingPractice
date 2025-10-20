package EndlessCheng.GenreMenu.DP.Grid.Advanced;

public class LenOfVDiagonal {

    // https://leetcode.cn/problems/length-of-longest-v-shaped-diagonal-segment/solutions/3076747/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-jrjj/
    private static final int[][] DIRS = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};

    public int lenOfVDiagonal(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 開太多維度影響效率，這裡把 k 和 canTurn 壓縮成一個 int
        int[][][] memo = new int[m][n][1 << 3];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 1) {
                    continue;
                }
                int[] maxs = {m - i, j + 1, i + 1, n - j}; // 理論最大值（走到底）
                for (int k = 0; k < 4; k++) { // 枚舉起始方向
                    // 優化一：如果理論最大值沒有超過 ans，那麼不遞歸
                    if (maxs[k] > ans) {
                        ans = Math.max(ans, dfs(i, j, k, 1, 2, grid, memo) + 1);
                    }
                }
            }
        }
        return ans;
    }

    private int dfs(int i, int j, int k, int canTurn, int target, int[][] grid, int[][][] memo) {
        i += DIRS[k][0];
        j += DIRS[k][1];
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != target) {
            return 0;
        }
        int mask = k << 1 | canTurn;
        if (memo[i][j][mask] > 0) {
            return memo[i][j][mask];
        }
        int res = dfs(i, j, k, canTurn, 2 - target, grid, memo) + 1;
        if (canTurn == 1) {
            int[] maxs = {grid.length - i, j + 1, i + 1, grid[i].length - j}; // 理論最大值（走到底）
            k = (k + 1) % 4;
            // 優化二：如果理論最大值沒有超過 res，那麼不遞歸
            if (Math.min(maxs[k], maxs[(k + 3) % 4]) > res) {
                res = Math.max(res, dfs(i, j, k, 0, 2 - target, grid, memo) + 1);
            }
        }
        return memo[i][j][mask] = res;
    }


}
