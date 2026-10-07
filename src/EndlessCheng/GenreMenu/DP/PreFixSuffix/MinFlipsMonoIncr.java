package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinFlipsMonoIncr {

    // https://leetcode.cn/problems/flip-string-to-monotone-increasing/solutions/1593815/jiang-zi-fu-chuan-fan-zhuan-dao-dan-diao-r91b/
    public int minFlipsMonoIncr(String s) {
        int n = s.length();

        // 多加一列初始狀態
        // dp[i][0]表示第i個字符是0的變換次數，dp[i][1]表示第i個字符是1的變換次數。
        int[][] dp = new int[n + 1][2];

        // 初始狀態都是0
        dp[0][0] = 0;
        dp[0][1] = 0;

        char[] chars = s.toCharArray();

        // 計算變換次數
        for (int i = 0; i < n; i++) {
            // 把第i個字符變成0
            dp[i + 1][0] = dp[i][0] + (chars[i] == '1' ? 1 : 0);
            // 把第i個字符變成1
            dp[i + 1][1] = Math.min(dp[i][0], dp[i][1]) + (chars[i] == '0' ? 1 : 0);
        }
        return Math.min(dp[n][0], dp[n][1]);
    }

    public int minFlipsMonoIncr2(String s) {
        int n = s.length();
        //初始狀態都是0
        int dp0 = 0;
        int dp1 = 0;
        char[] chars = s.toCharArray();
        //計算變換次數
        for (int i = 0; i < n; i++) {
            //把第i個字符變成0
            int curDp0 = dp0 + (chars[i] == '1' ? 1 : 0);
            //把第i個字符變成1
            int curDp1 = Math.min(dp0, dp1) + (chars[i] == '0' ? 1 : 0);
            //保存當次狀態
            dp0 = curDp0;
            dp1 = curDp1;
        }
        return Math.min(dp0, dp1);
    }

}
