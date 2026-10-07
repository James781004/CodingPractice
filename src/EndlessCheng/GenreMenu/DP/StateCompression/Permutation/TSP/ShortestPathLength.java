package EndlessCheng.GenreMenu.DP.StateCompression.Permutation.TSP;

import java.util.Arrays;

public class ShortestPathLength {

    // https://leetcode.cn/problems/shortest-path-visiting-all-nodes/solutions/632218/lu-xing-shang-wen-ti-by-small_town_nerd-xslj/
    private static final int INF = 1000;

    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // use floyd-warshall algorithm to calculate the shortest distance between any two different nodes
        int[][] dis = new int[n][n];
        for (int[] x : dis) {
            // use 1000 to represent INF in this problem
            Arrays.fill(x, INF);
        }

        for (int i = 0; i < n; ++i) {
            dis[i][i] = 0;
            for (int j : graph[i]) {
                dis[i][j] = 1;
            }
        }

        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[k][j]);
                }
            }
        }

        // travel salesman problem(TSP)
        int[][] dp = new int[1 << n][n];
        for (int[] x : dp) {
            Arrays.fill(x, INF);
        }
        for (int i = 0; i < n; ++i) {
            dp[1 << i][i] = 0;
        }
        for (int i = 0; i < (1 << n); ++i) {
            for (int j = 0; j < n; ++j) {
                if ((i & (1 << j)) != 0) {
                    for (int k = 0; k < n; ++k) {
                        if ((i & (1 << k)) != 0) {
                            dp[i][j] = Math.min(dp[i][j], dis[j][k] + dp[i ^ (1 << j)][k]);
                        }
                    }
                }
            }
        }
        return Arrays.stream(dp[(1 << n) - 1]).min().getAsInt();
    }


}
