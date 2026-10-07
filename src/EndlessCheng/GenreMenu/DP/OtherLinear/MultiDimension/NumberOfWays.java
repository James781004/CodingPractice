package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class NumberOfWays {

    // https://leetcode.cn/problems/number-of-ways-to-select-buildings/solutions/1390143/mei-ju-by-endlesscheng-xp5r/
    public long numberOfWays(String s) {
        char[] c = s.toCharArray();
        long[] dp = new long[6];
        for (char value : c) {
            if (value == '0') {
                dp[0]++;
                dp[2] += dp[1];
                dp[4] += dp[3];
            } else {
                dp[1] += dp[0];
                dp[3]++;
                dp[5] += dp[4];
            }
        }
        return dp[2] + dp[5];
    }

}
