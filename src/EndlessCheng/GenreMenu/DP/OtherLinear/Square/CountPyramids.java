package EndlessCheng.GenreMenu.DP.OtherLinear.Square;

public class CountPyramids {

    // https://leetcode.cn/problems/count-fertile-pyramids-in-a-land/solutions/1125747/dong-tai-gui-hua-by-endlesscheng-depp/
    public int countPyramids(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        // 以grid[i][j]為頂端的正金字塔高度
        int[][] dp = new int[2][n]; // dp[i][j] 表示金字塔頂端位於 (i,j) 時的最大層數（1 層也算）
        return countPyramids(grid, dp, -1, m - 2) + countPyramids(grid, dp, 1, 1);
    }

    private int countPyramids(int[][] grid, int[][] dp, int step, int startRow) {
        int m = grid.length;
        int n = grid[0].length;
        int res = 0;
        if (step == -1) {
            System.arraycopy(grid[m - 1], 0, dp[0], 0, n);
        } else {
            System.arraycopy(grid[0], 0, dp[0], 0, n);
        }

        for (int i = startRow; i >= 0 && i < m; i += step) {
            dp[1][0] = grid[i][0];
            dp[1][n - 1] = grid[i][n - 1];
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 1) {
                    dp[1][j] = 1 + Math.min(dp[0][j - 1], Math.min(dp[0][j], dp[0][j + 1]));
                    // 高度-1就是金字塔個數
                    res += dp[1][j] - 1;
                } else {
                    dp[1][j] = 0;
                }
            }
            int[] temp = dp[0];
            dp[0] = dp[1];
            dp[1] = temp;
        }
        return res;
    }

}
