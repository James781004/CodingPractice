package EndlessCheng.GenreMenu.DP.Divide.Fixed;

public class MinCost {

    // https://leetcode.cn/problems/paint-house-iii/solutions/278783/java-dong-tai-gui-hua-by-gitkid/
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        // dp[i][j][k] 涂完前i個房子，目前有j個街區，且第i個房子的顏色為k的所有方案 cost最小的。
        // 房子從0開始，街區從1開始，顏色從1開始
        final int INF = (int) Math.pow(10, 8);
        // System.out.println(INF);
        int[][][] dp = new int[m + 1][target + 1][n + 1];
        for (int i = 0; i < m + 1; i++) {
            for (int j = 0; j < target + 1; j++) {
                for (int k = 0; k < n + 1; k++) {
                    dp[i][j][k] = INF;
                }
            }
        }
        // 初始化第0個房子
        // 第0個房子已經涂色
        if (houses[0] > 0) {
            dp[0][1][houses[0]] = 0;
        } else {
            // 第0個房子沒有涂色，初始化cost
            for (int i = 1; i <= n; i++) {
                dp[0][1][i] = cost[0][i - 1];
            }
        }
        // 狀態轉移時涂色完第i個房子。
        for (int i = 1; i < m; i++) {
            // 最多target個街區
            for (int j = 1; j <= target; j++) {
                // 分成第i個房子是否涂色
                if (houses[i] > 0) {
                    int temp = houses[i];
                    for (int k = 1; k <= n; k++) {
                        // 分成第i個房子和第i-1的房子
                        // 如果兩個房子顏色相同，那麼街區數就相同
                        // 如果兩個房子顏色不同，那麼第i個房子就獨自成一個街區
                        if (temp == k) {
                            dp[i][j][temp] = Math.min(dp[i][j][temp], dp[i - 1][j][k]);
                        } else {
                            dp[i][j][temp] = Math.min(dp[i][j][temp], dp[i - 1][j - 1][k]);
                        }
                    }
                } else {
                    for (int k = 1; k <= n; k++) {
                        for (int s = 1; s <= n; s++) {
                            if (k == s) {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j][s] + cost[i][k - 1]);
                            } else {
                                dp[i][j][k] = Math.min(dp[i][j][k], dp[i - 1][j - 1][s] + cost[i][k - 1]);
                            }
                        }
                    }

                }
            }
        }
        int res = INF;
        for (int i = 1; i <= n; i++) {
            res = Math.min(res, dp[m - 1][target][i]);
            // System.out.println(res);
        }
        if (res == INF) return -1;
        return res;
    }


}
