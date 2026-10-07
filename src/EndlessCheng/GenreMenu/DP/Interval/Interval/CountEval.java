package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class CountEval {

    // https://leetcode.cn/problems/boolean-evaluation-lcci/solutions/508838/qu-jian-dpfen-zhi-suan-fa-by-whbsurpass-bsry/
    public int countEval(String s, int result) {
        // 特例
        if (s.isEmpty()) {
            return 0;
        }
        if (s.length() == 1) {
            return (s.charAt(0) - '0') == result ? 1 : 0;
        }
        char[] ch = s.toCharArray();
        // 定義狀態: dp[i][j][result=0/1]表示第i到j個數字計算結果為result的方案數
        int[][][] dp = new int[ch.length][ch.length][2];
        // base case
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '0' || ch[i] == '1') {
                dp[i][i][ch[i] - '0'] = 1;
            }
        }
        // 套區間dp模板
        // 枚舉區間長度len，跳步為2，一個數字一個符號
        for (int len = 2; len <= ch.length; len += 2) {
            // 枚舉區間起點，數字位，跳步為2
            for (int i = 0; i <= ch.length - len; i += 2) {
                // 區間終點，數字位
                int j = i + len;
                // 枚舉分割點，三種 '&','|', '^'，跳步為2
                for (int k = i + 1; k <= j - 1; k += 2) {
                    if (ch[k] == '&') {
                        // 結果為0 有三種情況： 0 0, 0 1, 1 0
                        // 結果為1 有一種情況： 1 1
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][1];
                    }
                    if (ch[k] == '|') {
                        // 結果為0 有一種情況： 0 0
                        // 結果為1 有三種情況： 0 1, 1 0, 1 1
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];
                    }
                    if (ch[k] == '^') {
                        // 結果為0 有兩種情況： 0 0, 1 1
                        // 結果為1 有兩種情況： 0 1, 1 0
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1];
                    }
                }
            }
        }
        return dp[0][ch.length - 1][result];
    }


}
