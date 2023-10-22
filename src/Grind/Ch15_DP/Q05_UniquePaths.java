package Grind.Ch15_DP;

public class Q05_UniquePaths {
    // https://leetcode.cn/problems/unique-paths/solutions/856968/dai-ma-sui-xiang-lu-dong-gui-wu-bu-qu-xi-1vkb/

    /**
     * 1. 確定dp數組下表含義 dp[i][j] 到每一個坐標可能的路徑種類
     * 2. 遞推公式 dp[i][j] = dp[i-1][j] dp[i][j-1]
     * 3. 初始化 dp[i][0]=1 dp[0][i]=1 初始化橫豎就可
     * 4. 遍歷順序 一行一行遍歷
     * 5. 推導結果 。。。。。。。。
     *
     * @param m
     * @param n
     * @return
     */
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // 初始化
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
}
