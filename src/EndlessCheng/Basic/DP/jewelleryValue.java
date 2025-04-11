package EndlessCheng.Basic.DP;

public class jewelleryValue {

    // https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/solutions/2153802/jiao-ni-yi-bu-bu-si-kao-dpcong-hui-su-da-epvl/
    private int[][] grid, memo;

    public int jewelleryValueMemo(int[][] grid) {
        this.grid = grid;
        int m = grid.length, n = grid[0].length;
        memo = new int[m][n];
        return dfs(grid.length - 1, grid[0].length - 1);
    }

    private int dfs(int i, int j) {
        if (i < 0 || j < 0) return 0;
        if (memo[i][j] > 0) // grid[i][j] 都是正數，記憶化的 memo[i][j] 必然為正數
            return memo[i][j];
        return memo[i][j] = Math.max(dfs(i, j - 1), dfs(i - 1, j)) + grid[i][j];
    }


    public int jewelleryValue(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        var f = new int[m + 1][n + 1];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                f[i + 1][j + 1] = Math.max(f[i + 1][j], f[i][j + 1]) + grid[i][j];
        return f[m][n];
    }


    public int jewelleryValue2(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        var f = new int[2][n + 1];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                f[(i + 1) % 2][j + 1] =
                        Math.max(f[(i + 1) % 2][j], f[i % 2][j + 1]) + grid[i][j];
        return f[m % 2][n];
    }

    public int jewelleryValue3(int[][] grid) {
        int n = grid[0].length;
        var f = new int[n + 1];
        for (var row : grid)
            for (int j = 0; j < n; ++j)
                f[j + 1] = Math.max(f[j], f[j + 1]) + row[j];
        return f[n];
    }


}
