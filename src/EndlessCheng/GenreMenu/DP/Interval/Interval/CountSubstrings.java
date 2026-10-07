package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class CountSubstrings {

    // https://leetcode.cn/problems/palindromic-substrings/solutions/917242/dai-ma-sui-xiang-lu-dai-ni-xue-tou-dpzi-vidge/
    public int countSubstrings(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];

        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
                    res++;
                    dp[i][j] = true;
                }
            }
        }
        return res;
    }


}
