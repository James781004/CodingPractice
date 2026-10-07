package EndlessCheng.GenreMenu.DP.Graph;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FindCheapestPrice {

    // https://leetcode.cn/problems/cheapest-flights-within-k-stops/solutions/956113/tong-ge-lai-shua-ti-la-yi-ti-si-jie-bfs-deqpt/
    int INF = 1000007;

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // k表示經過的節點，我們轉成邊數（步數），這樣好計算一些
        int[][] memo = new int[n][k + 2];
        int ans = dfs(flights, src, dst, k + 1, memo);
        return ans >= INF ? -1 : ans;
    }

    // 表示從 i 到 dst 的走 k 步的最小價格
    private int dfs(int[][] flights, int i, int dst, int k, int[][] memo) {
        if (k < 0) {
            return INF;
        }

        if (i == dst) {
            return 0;
        }

        if (memo[i][k] != 0) {
            return memo[i][k];
        }

        int min = INF;
        for (int[] flight : flights) {
            // 遍歷 i 的下一個節點
            if (flight[0] == i) {
                min = Math.min(min, dfs(flights, flight[1], dst, k - 1, memo) + flight[2]);
            }
        }

        memo[i][k] = min;

        return min;
    }

    private int dp(int n, int[][] flights, int src, int dst, int K) {
        // dp[i][k]表示從i點到dst走k步的最少價格
        // dp[i][k]=min(dp[i_next][k-1] + g[i][j])
        int[][] dp = new int[n][K + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[dst][0] = 0;
        for (int k = 1; k <= K + 1; k++) {
            for (int[] flight : flights) {
                dp[flight[0]][k] = Math.min(dp[flight[0]][k], dp[flight[1]][k - 1] + flight[2]);
            }
        }

        int ans = IntStream.of(dp[src]).min().getAsInt();

        return ans >= INF ? -1 : ans;
    }

    private int dp2(int n, int[][] flights, int src, int dst, int K) {
        // dp[i][k]表示從i點到dst走k步的最少價格
        // dp[i][k]=min(dp[i_next][k-1] + g[i][j])
        int ans = INF;

        int[][] dp = new int[2][n];
        Arrays.fill(dp[0], INF);
        dp[0][dst] = 0;
        for (int k = 1; k <= K + 1; k++) {
            // 防止之前的值干擾，每次都要初始化
            Arrays.fill(dp[k & 1], INF);
            for (int[] flight : flights) {
                dp[k & 1][flight[0]] = Math.min(dp[k & 1][flight[0]], dp[(k - 1) & 1][flight[1]] + flight[2]);
            }
            ans = Math.min(ans, dp[k & 1][src]);
        }

        return ans >= INF ? -1 : ans;
    }

}
