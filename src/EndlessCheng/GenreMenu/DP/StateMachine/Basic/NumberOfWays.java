package EndlessCheng.GenreMenu.DP.StateMachine.Basic;

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


    // 枚舉中間
    public long numberOfWays2(String s) {
        int n = s.length();
        int postZero = 0, postOne = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '0') ++postZero;
        }
        postOne = n - postZero;
        long validCnt = 0;
        int preZero = 0, preOne = 0;
        for (int m = 0; m < n; m++) {
            char ch = s.charAt(m);
            if (ch == '1') {
                validCnt += preZero * (long) postZero;
                ++preOne;
                --postOne;
            } else {
                validCnt += preOne * (long) postOne;
                ++preZero;
                --postZero;
            }
        }
        return validCnt;
    }

}
