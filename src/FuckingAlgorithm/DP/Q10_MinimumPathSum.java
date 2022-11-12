package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q10_MinimumPathSum {
//    https://leetcode.cn/problems/minimum-path-sum/
//    64. 最小路徑和
//    給定一個包含非負整數的 m x n 網格 grid ，請找出一條從左上角到右下角的路徑，使得路徑上的數字總和為最小。
//
//    說明：每次只能向下或者向右移動一步。


    int[][] memo;

    public int minPathSumMemo(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        memo = new int[m][n];

        // 構造備忘錄，初始值全部設為 -1
        memo = new int[m][n];
        for (int[] row : memo)
            Arrays.fill(row, -1);

        return process(grid, m - 1, n - 1);
    }

    private int process(int[][] grid, int i, int j) {
        // base case
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        // 避免重復計算
        if (memo[i][j] != -1) return memo[i][j];

        // 將計算結果記入備忘錄
        memo[i][j] = Math.min(process(grid, i - 1, j), process(grid, i, j - 1)) + grid[i][j];

        return memo[i][j];
    }


    // https://leetcode.cn/problems/minimum-path-sum/solution/zui-xiao-lu-jing-he-dong-tai-gui-hua-gui-fan-liu-c/
    public int minPathSumDP(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // dp 數組的定義: dp[i][0] = sum(grid[0..i][0]), dp[0][j] = sum(grid[0][0..j])
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        for (int i = 0; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }

    // 空間優化
    public int minPathSumDP2(int[][] grid) {
        int len = grid[0].length;
        int[] dp = new int[len];
        dp[0] = grid[0][0];
        for (int i = 1; i < len; i++)
            dp[i] = dp[i - 1] + grid[0][i];
        for (int i = 1; i < grid.length; i++) {
            dp[0] = dp[0] + grid[i][0];
            for (int j = 1; j < len; j++)
                dp[j] = Math.min(dp[j - 1] + grid[i][j], dp[j] + grid[i][j]);
        }
        return dp[len - 1];
    }
}
