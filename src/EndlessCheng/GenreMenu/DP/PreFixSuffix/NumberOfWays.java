package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.Arrays;

public class NumberOfWays {

    // https://leetcode.cn/problems/number-of-ways-to-select-buildings/solutions/1390143/mei-ju-by-endlesscheng-xp5r/
    public long numberOfWays(String s) {
        // 求固定長度的方案數，那麼長度就是動態規劃的參數
        int k = 3;
        // dp[i][j]: 長度為i，結尾為j的子序列的方案數
        long[][] dp = new long[k + 1][2];
        Arrays.fill(dp[0], 1);
        long res = 0;
        for (char c : s.toCharArray()) {
            int j = c - '0';
            // 由於轉移方程依賴i - 1的結果，因此倒序遍歷
            for (int i = k; i > 0; i--) {
                dp[i][j] += dp[i - 1][j ^ 1];
            }
        }
        for (long cnt : dp[k]) {
            res += cnt;
        }
        return res;
    }
}
