package EndlessCheng.Basic.DP;

public class longestPalindrome {


    // https://leetcode.cn/problems/maximize-palindrome-length-from-subsequences/solutions/2285215/shi-pin-qiao-miao-zhuan-huan-516-bian-xi-jhrt/
    public int longestPalindrome(String word1, String word2) {
        char[] s = (word1 + word2).toCharArray();
        int ans = 0, n = s.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; --i) {
            f[i][i] = 1;
            for (int j = i + 1; j < n; ++j) {
                if (s[i] == s[j]) {
                    f[i][j] = f[i + 1][j - 1] + 2;
                    if (i < word1.length() && j >= word1.length()) {
                        ans = Math.max(ans, f[i][j]); // f[i][j] 一定包含 s[i] 和 s[j]
                    }
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]);
                }
            }
        }
        return ans;
    }


}
