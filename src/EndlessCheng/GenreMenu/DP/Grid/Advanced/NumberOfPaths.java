package EndlessCheng.GenreMenu.DP.Grid.Advanced;

import java.util.Arrays;

public class NumberOfPaths {

    // https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/solutions/1878910/dong-tai-gui-hua-pythonjavacgo-by-endles-94wq/
    private static final int MOD = (int) 1e9 + 7;
    int[][] grid;
    int[][][] cache;
    int k = 0;

    public int numberOfPaths(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
        int n = grid.length, m = grid[0].length;
        cache = new int[n][m][k];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                Arrays.fill(cache[i][j], -1);
        return dfs(n - 1, m - 1, grid[n - 1][m - 1] % k);
    }

    // 定義dfs(i, j, sum) 表示從(n,m)到(i,j)的和為sum，能被k整除的路徑個數
    public int dfs(int i, int j, int sum) {
        if (i < 0 || j < 0) return 0;
        if (i == 0 && j == 0) {
            return sum % k == 0 ? 1 : 0;
        }
        if (cache[i][j][sum] >= 0) return cache[i][j][sum];
        int res = 0;
        if (i > 0) res = (res + dfs(i - 1, j, (sum + grid[i - 1][j]) % k)) % MOD;
        if (j > 0) res = (res + dfs(i, j - 1, (sum + grid[i][j - 1]) % k)) % MOD;
        return cache[i][j][sum] = res;
    }

    public int numberOfPathsDP(int[][] grid, int k) {
        final var mod = (int) 1e9 + 7;
        int m = grid.length, n = grid[0].length;
        var f = new int[m + 1][n + 1][k]; // f[i][j][v] 表示從左上走到 (i,j)，且路徑和模 k 的結果為 v 時的路徑數
        f[0][1][0] = 1; // 初始值
        for (var i = 0; i < m; ++i)
            for (var j = 0; j < n; ++j)
                for (var v = 0; v < k; ++v)
                    f[i + 1][j + 1][(v + grid[i][j]) % k] = (f[i + 1][j][v] + f[i][j + 1][v]) % mod;
        return f[m][n][0];
    }


}
