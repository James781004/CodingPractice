package EndlessCheng.GenreMenu.DP.Interval.Interval;

public class LongestPalindromeSubstring {

    // https://leetcode.cn/problems/longest-palindromic-substring/solutions/2958179/mo-ban-on-manacher-suan-fa-pythonjavacgo-t6cx/
    public String longestPalindrome(String s) {
        // 二維dp
        int n = s.length();
        boolean[][] f = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            f[i][i] = true;
        }
        int resBegin = 0, l = 1;
        // i,j表示該位置是否是回文串
        // 由於計算的是f[i+1][j-1]所以i必須是從後往前遍歷 避免漏算
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                // 相鄰的兩個相同的字母或者中間有回文串
                if (s.charAt(i) == s.charAt(j) && (j - i == 1 || f[i + 1][j - 1])) {
                    f[i][j] = true;
                    if ((j - i + 1) > l) {
                        resBegin = i;
                        l = j - i + 1;
                    }
                }
            }
        }
        return s.substring(resBegin, resBegin + l);
    }

}
