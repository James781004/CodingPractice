package EndlessCheng.GenreMenu.DP.Grid.Basic;

public class UniquePaths {

    // https://leetcode.cn/problems/unique-paths/solutions/3062432/liang-chong-fang-fa-dong-tai-gui-hua-zu-o5k32/
    public int uniquePaths(int m, int n) {
        int[][] memo = new int[m][n];
        return dfs(m - 1, n - 1, memo);
    }

    private int dfs(int i, int j, int[][] memo) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (i == 0 && j == 0) {
            return 1;
        }
        if (memo[i][j] != 0) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = dfs(i - 1, j, memo) + dfs(i, j - 1, memo);
    }


    public int uniquePathsDP(int m, int n) {
        int[][] f = new int[m + 1][n + 1];
        f[0][1] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[i + 1][j + 1] = f[i][j + 1] + f[i + 1][j];
            }
        }
        return f[m][n];
    }


    public int uniquePathsDP2(int m, int n) {
        int[] f = new int[n + 1];
        f[1] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                f[j + 1] += f[j];
            }
        }
        return f[n];
    }


}
