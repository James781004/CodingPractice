package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class StrangePrinter {

    // https://leetcode.cn/problems/strange-printer/solutions/731553/qu-jian-dpzhuang-tai-fang-cheng-de-tui-d-gjnk/
    public int strangePrinter(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }
        int[][] dp = new int[n][n];
        for (int i = n; i >= 1; i--) {
            for (int j = i; j <= n; j++) {
                if (i == j) { // 只有一個元素時
                    dp[i - 1][j - 1] = 1;
                } else if (s.charAt(i - 1) == s.charAt(j - 1)) { // s[i]==s[j]
                    dp[i - 1][j - 1] = dp[i - 1][j - 2];
                } else { // 有多個元素，且s[i]!=s[j]
                    int min = Integer.MAX_VALUE;
                    for (int x = i; x < j; x++) {
                        min = Math.min(min, dp[i - 1][x - 1] + dp[x][j - 1]);
                    }
                    dp[i - 1][j - 1] = min;
                }
            }
        }
        return dp[0][n - 1];
    }


}
