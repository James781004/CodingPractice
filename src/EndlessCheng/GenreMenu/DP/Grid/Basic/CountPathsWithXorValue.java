package EndlessCheng.GenreMenu.DP.Grid.Basic;

public class CountPathsWithXorValue {

    // https://leetcode.cn/problems/count-paths-with-the-given-xor-value/solutions/3026905/wang-ge-tu-dpcong-ji-yi-hua-sou-suo-dao-k1bpm/
    private static final int MOD = 1_000_000_007;

    public int countPathsWithXorValue(int[][] grid, int k) {
        int mx = 0;
        for (int[] row : grid) {
            for (int val : row) {
                mx = Math.max(mx, val);
            }
        }
        int u = 1 << (32 - Integer.numberOfLeadingZeros(mx));
        if (k >= u) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][][] memo = new int[m][n][u];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return dfs(grid, m - 1, n - 1, k, memo);
    }

    private int dfs(int[][] grid, int i, int j, int x, int[][][] memo) {
        if (i < 0 || j < 0) {
            return 0;
        }
        int val = grid[i][j];
        if (i == 0 && j == 0) {
            return x == val ? 1 : 0;
        }
        if (memo[i][j][x] != -1) {
            return memo[i][j][x];
        }
        int left = dfs(grid, i, j - 1, x ^ val, memo);
        int up = dfs(grid, i - 1, j, x ^ val, memo);
        return memo[i][j][x] = (left + up) % MOD;
    }


    public int countPathsWithXorValueDP(int[][] grid, int k) {
        final int MOD = 1_000_000_007;
        int mx = 0;
        for (int[] row : grid) {
            for (int val : row) {
                mx = Math.max(mx, val);
            }
        }
        int u = 1 << (32 - Integer.numberOfLeadingZeros(mx));
        if (k >= u) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        int[][][] f = new int[m + 1][n + 1][u];
        f[0][1][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int val = grid[i][j];
                for (int x = 0; x < u; x++) {
                    f[i + 1][j + 1][x] = (f[i + 1][j][x ^ val] + f[i][j + 1][x ^ val]) % MOD;
                }
            }
        }
        return f[m][n][k];
    }


}
