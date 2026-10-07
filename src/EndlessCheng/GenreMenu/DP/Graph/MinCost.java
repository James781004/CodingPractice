package EndlessCheng.GenreMenu.DP.Graph;

import java.util.Arrays;

public class MinCost {

    // https://leetcode.cn/problems/minimum-cost-to-reach-destination-in-time/solutions/2937759/dan-yuan-zui-duan-lu-dijkstra-bellmanfor-dp88/
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        // Bellman-Ford算法
        int n = passingFees.length;
        int INF = Integer.MAX_VALUE / 2;
        // dp[t][u] 表示花費 t 時間到點 u 的最小花費
        int[][] dp = new int[maxTime + 1][n];
        for (int i = 0; i <= maxTime; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = passingFees[0];  // 初始條件：從起點出發時的花費

        for (int t = 1; t <= maxTime; t++) {  // 第一維枚舉時間
            for (int[] edge : edges) {  // 第二維遍歷圖
                int u = edge[0], v = edge[1], w = edge[2];
                if (t >= w) {
                    // 轉移方程，更新從 u 到 v 和從 v 到 u 的最小花費
                    dp[t][v] = Math.min(dp[t][v], dp[t - w][u] + passingFees[v]);
                    dp[t][u] = Math.min(dp[t][u], dp[t - w][v] + passingFees[u]);
                }
            }
        }

        // 找到到達終點 n-1 的最小花費
        int ans = INF;
        for (int i = 1; i <= maxTime; i++) {
            ans = Math.min(ans, dp[i][n - 1]);
        }
        return ans == INF ? -1 : ans;
    }

}
