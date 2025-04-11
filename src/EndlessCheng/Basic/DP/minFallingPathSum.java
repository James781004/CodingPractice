package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class minFallingPathSum {

    // https://leetcode.cn/problems/minimum-falling-path-sum/solutions/2341851/cong-di-gui-dao-di-tui-jiao-ni-yi-bu-bu-2cwkb/
    private int[][] matrix, memo;

    public int minFallingPathSum(int[][] matrix) {
        this.matrix = matrix;
        int n = matrix.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], Integer.MIN_VALUE);

        int ans = Integer.MAX_VALUE;
        for (int c = 0; c < n; c++)
            ans = Math.min(ans, dfs(n - 1, c));
        return ans;
    }

    private int dfs(int r, int c) {
        if (c < 0 || c >= matrix.length) return Integer.MAX_VALUE; // 出界
        if (r == 0) return matrix[0][c]; // 到達第一行
        if (memo[r][c] != Integer.MIN_VALUE) return memo[r][c]; // 之前算過了
        return memo[r][c] = Math.min(Math.min(
                dfs(r - 1, c - 1), dfs(r - 1, c)), dfs(r - 1, c + 1)) + matrix[r][c];
    }


    public int minFallingPathSumDP(int[][] matrix) {
        int n = matrix.length;
        var f = new int[n][n + 2];
        System.arraycopy(matrix[0], 0, f[0], 1, n);
        for (int r = 1; r < n; r++) {
            f[r - 1][0] = f[r - 1][n + 1] = Integer.MAX_VALUE;
            for (int c = 0; c < n; c++)
                f[r][c + 1] = Math.min(Math.min(f[r - 1][c], f[r - 1][c + 1]), f[r - 1][c + 2]) + matrix[r][c];
        }
        int ans = Integer.MAX_VALUE;
        for (int c = 1; c <= n; c++)
            ans = Math.min(ans, f[n - 1][c]);
        return ans;
    }

    
}
