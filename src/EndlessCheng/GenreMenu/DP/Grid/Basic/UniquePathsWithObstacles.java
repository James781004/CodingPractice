package EndlessCheng.GenreMenu.DP.Grid.Basic;

import java.util.Arrays;

public class UniquePathsWithObstacles {

    // https://leetcode.cn/problems/unique-paths-ii/solutions/3059862/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-451i/
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] memo = new int[m][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(m - 1, n - 1, obstacleGrid, memo);
    }

    private int dfs(int i, int j, int[][] obstacleGrid, int[][] memo) {
        if (i < 0 || j < 0 || obstacleGrid[i][j] == 1) {
            return 0;
        }
        if (i == 0 && j == 0) {
            return 1;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        return memo[i][j] = dfs(i - 1, j, obstacleGrid, memo) + dfs(i, j - 1, obstacleGrid, memo);
    }


    public int uniquePathsWithObstaclesDP(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] f = new int[m + 1][n + 1];
        f[0][1] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    f[i + 1][j + 1] = f[i][j + 1] + f[i + 1][j];
                }
            }
        }
        return f[m][n];
    }


    public int uniquePathsWithObstaclesDP2(int[][] obstacleGrid) {
        int n = obstacleGrid[0].length;
        int[] f = new int[n + 1];
        f[1] = 1;
        for (int[] row : obstacleGrid) {
            for (int j = 0; j < n; j++) {
                if (row[j] == 0) {
                    f[j + 1] += f[j];
                } else {
                    f[j + 1] = 0;
                }
            }
        }
        return f[n];
    }


}
