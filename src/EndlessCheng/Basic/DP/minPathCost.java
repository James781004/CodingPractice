package EndlessCheng.Basic.DP;

public class minPathCost {

    // https://leetcode.cn/problems/minimum-path-cost-in-a-grid/solutions/2536856/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-bd25/
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        int[][] memo = new int[m][n];
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) { // 枚舉起點
            ans = Math.min(ans, dfs(0, j, memo, grid, moveCost));
        }
        return ans;
    }

    private int dfs(int i, int j, int[][] memo, int[][] grid, int[][] moveCost) {
        if (i == grid.length - 1) { // 遞歸邊界
            return grid[i][j];
        }
        if (memo[i][j] != 0) { // 之前計算過
            return memo[i][j];
        }
        int res = Integer.MAX_VALUE;
        for (int k = 0; k < grid[0].length; k++) { // 移動到下一行的第 k 列
            res = Math.min(res, dfs(i + 1, k, memo, grid, moveCost) + moveCost[grid[i][j]][k]);
        }
        return memo[i][j] = res + grid[i][j]; // 記憶化
    }


    public int minPathCostDP(int[][] grid, int[][] moveCost) {
        int m = grid.length, n = grid[0].length;
        int[][] f = new int[m][n];
        f[m - 1] = grid[m - 1];
        for (int i = m - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                f[i][j] = Integer.MAX_VALUE;
                for (int k = 0; k < n; k++) { // 移動到下一行的第 k 列
                    f[i][j] = Math.min(f[i][j], f[i + 1][k] + moveCost[grid[i][j]][k]);
                }
                f[i][j] += grid[i][j];
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int res : f[0]) {
            ans = Math.min(ans, res);
        }
        return ans;
    }


}
