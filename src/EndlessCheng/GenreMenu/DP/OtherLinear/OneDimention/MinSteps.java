package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MinSteps {

    // https://leetcode.cn/problems/2-keys-keyboard/solutions/1006605/tong-ge-lai-shua-ti-la-yi-ti-wu-jie-bao-qg6qr/
    int INF = 10000;

    public int minSteps(int n) {
        int[][] memo = new int[n][n];
        return dfs(n, 1, 0, memo);
    }

    private int dfs(int n, int curr, int clipboard, int[][] memo) {
        if (curr > n) {
            return INF;
        }
        if (curr == n) {
            return 0;
        }

        if (memo[curr][clipboard] != 0) {
            return memo[curr][clipboard];
        }

        int ans = INF;
        if (clipboard > 0) {
            ans = Math.min(ans, dfs(n, curr + clipboard, clipboard, memo));
        }

        if (curr != clipboard) {
            ans = Math.min(ans, dfs(n, curr, curr, memo));
        }

        memo[curr][clipboard] = ++ans;

        return ans;
    }


    public int minStepsDP(int n) {
        int[][] dp = new int[n + 1][n + 1]; // 表示當前字符為 i 個，剪切板字符為 j 個時的最小操作次數
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[1][0] = 0; // 表示初始時只有一個字符，還未做任何操作
        dp[1][1] = 1; // 表示 Copy 一次操作
        for (int i = 2; i <= n; i++) {
            int min = INF;
            for (int j = 1; j <= i; j++) {
                if (i % j == 0) { // 只有當 i % j == 0 的時候，這個狀態才是有效的
                    if (i != j) {
                        dp[i][j] = dp[i - j][j] + 1; // Paste 操作
                        min = Math.min(min, dp[i][j]);
                    } else {
                        dp[i][j] = min + 1; // Copy 操作
                    }
                }
            }
        }

        return IntStream.of(dp[n]).min().getAsInt();
    }


}
