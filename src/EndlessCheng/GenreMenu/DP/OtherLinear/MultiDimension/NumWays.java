package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class NumWays {

    // https://leetcode.cn/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/solutions/773589/shu-ju-jie-gou-he-suan-fa-di-gui-ge-dong-glyo/
    public int numWays(int steps, int arrLen) {
        final int mod = 1_000_000_007;
        int maxLen = Math.min(steps / 2, arrLen - 1); // 要想從原點再回到原點那麼走的最遠距離不能超過steps / 2，否則走太遠一樣回不來
        long[][] dp = new long[steps + 1][maxLen + 1]; // dp[i][j]表示移動i步，走到的數組位置為j的方案數
        dp[1][0] = 1;
        dp[1][1] = 1;
        for (int i = 1; i <= steps; i++) {
            for (int j = 0; j <= maxLen; j++) {
                //要想走到位置j，可以有3個方向過來

                //從上面下來
                dp[i][j] += dp[i - 1][j];
                //從右邊過來，如果右邊靠牆了，不能從右邊的牆外面過來
                if (j < maxLen)
                    dp[i][j] += dp[i - 1][j + 1];
                //從左邊過來，但前提是j不能是最左邊那一列，
                //否則靠牆了，沒法從左邊過來
                if (j > 0) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
                dp[i][j] %= mod;
            }
        }
        return (int) dp[steps][0];
    }


    public int numWays2(int steps, int arrLen) {
        final int mod = 1_000_000_007;
        int maxLen = Math.min(steps / 2, arrLen - 1);
        long[] dp = new long[maxLen + 1];
        dp[0] = 1;
        for (int i = 1; i <= steps; i++) {
            long last = 0;
            for (int j = 0; j <= maxLen; j++) {
                //因為左邊的值在計算之後會被覆蓋，我們提前把它保存下來，
                //供下次運算的時候使用
                long temp = dp[j];
//                //要想走到位置j，可以有3個方向過來
//                //從上面下來，這一步可以省略
//                dp[j] = dp[j];
                //從右邊過來，如果右邊靠牆了，不能從右邊的牆外面過來
                if (j < maxLen)
                    dp[j] += dp[j + 1];
                //從左邊過來，但前提是j不能是最左邊那一列，
                //否則靠牆了，沒法從左邊過來
                if (j > 0) {
                    dp[j] += last;
                }
                dp[j] %= mod;
                last = temp;
            }
        }
        return (int) dp[0];
    }


}
