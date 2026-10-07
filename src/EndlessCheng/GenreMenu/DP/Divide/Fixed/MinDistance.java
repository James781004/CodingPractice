package EndlessCheng.GenreMenu.DP.Divide.Fixed;

import java.util.Arrays;

public class MinDistance {

    // https://leetcode.cn/problems/allocate-mailboxes/solutions/287482/dp-by-henrylee4/
    public int minDistance(int[] houses, int k) {
        int n = houses.length;
        Arrays.sort(houses);
        // dp[i][j]表示前i個房子擺j個郵筒時的最短距離
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }
        // dis[i][j] 表示范圍[i,j]的房子共用一個郵筒時的最短距離
        int[][] dis = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                for (int x = i, y = j; x < y; x++, y--) {
                    dis[i][j] += houses[y - 1] - houses[x - 1];
                }
            }
        }
        dp[0][0] = 0;
        // 求解DP，進行狀態轉移
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i && j <= k; j++) {
                for (int p = i; p >= 1; p--) {
                    if (dp[p - 1][j - 1] != -1 && (dp[i][j] == -1 || dp[i][j] > dp[p - 1][j - 1] + dis[p][i])) {
                        dp[i][j] = dp[p - 1][j - 1] + dis[p][i];
                    }
                }
            }
        }
        return dp[n][k];
    }


}
