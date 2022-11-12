package FuckingAlgorithm.DP;

import java.util.Arrays;

public class Q05_LCS {
//    https://www.cnblogs.com/labuladong/p/13945482.html
//    https://leetcode.cn/problems/longest-common-subsequence/
//    1143. 最長公共子序列
//    給定兩個字符串 text1 和 text2，返回這兩個字符串的最長 公共子序列 的長度。如果不存在 公共子序列 ，返回 0 。
//
//    一個字符串的 子序列 是指這樣一個新的字符串：它是由原字符串在不改變字符的相對順序的情況下刪除某些字符（也可以不刪除任何字符）後組成的新字符串。
//
//    例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
//    兩個字符串的 公共子序列 是這兩個字符串所共同擁有的子序列。


    // 計畫遞歸
    public int longestCommonSubsequence(String s1, String s2) {
        int[][] memo = new int[s1.length()][s2.length()];
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                memo[i][j] = -1;
            }
        }
        return helper(s1, 0, s1, 0, memo);
    }

    private int helper(String s1, int i, String s2, int j, int[][] memo) {
        if (i == s1.length() || j == s2.length()) return 0;
        if (memo[i][j] != -1) return memo[i][j];
        if (s1.charAt(i) == s2.charAt(j)) {
            memo[i][j] = 1 + helper(s1, i + 1, s2, j + 1, memo);
        } else {
            memo[i][j] = Math.max(helper(s1, i + 1, s2, j, memo), helper(s1, i, s2, j + 1, memo));
        }
        return memo[i][j];
    }

    public int longestCommonSubsequenceDP(String s1, String s2) {
        int m = s1.length(), n = s2.length();

        // 定義：s1[0..i-1] 和 s2[0..j-1] 的 lcs 長度為 helper[i][j]
        int[][] dp = new int[m + 1][n + 1];

        // 目標：s1[0..m-1] 和 s2[0..n-1] 的 lcs 長度，即 helper[m][n]
        // base case: helper[0][..] = helper[..][0] = 0

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 現在 i 和 j 從 1 開始，所以要減一
                // 其實可以考慮在兩字串前先加個空格，例如：s1 = " " + s1; s2 = " " + s2;
                // 這樣就可以不用減一，以減少邊界判斷
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // s1[i-1] 和 s2[j-1] 必然在 lcs 中
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // s1[i-1] 和 s2[j-1] 至少有一個不在 lcs 中
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }

        return dp[m][n];
    }


//    https://leetcode.cn/problems/delete-operation-for-two-strings/
//    583. 兩個字符串的刪除操作
//    給定兩個單詞 word1 和 word2 ，返回使得 word1 和  word2 相同所需的最小步數。
//
//    每步 可以刪除任意一個字符串中的一個字符。

    class minDistance {
        public int minDistance(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // 復用前文計算 lcs 長度的函數
            int lcs = longestCommonSubsequence(s1, s2);
            return m - lcs + n - lcs;
        }

        // 計算最長公共子序列的長度
        int longestCommonSubsequence(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // 定義：s1[0..i-1] 和 s2[0..j-1] 的 lcs 長度為 helper[i][j]
            int[][] dp = new int[m + 1][n + 1];

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    // 現在 i 和 j 從 1 開始，所以要減一
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        // s1[i-1] 和 s2[j-1] 必然在 lcs 中
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    } else {
                        // s1[i-1] 和 s2[j-1] 至少有一個不在 lcs 中
                        dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                    }
                }
            }
            return dp[m][n];
        }


        public int minDistance2(String s1, String s2) {
            char[] cs1 = s1.toCharArray(), cs2 = s2.toCharArray();
            int n = s1.length(), m = s2.length();
            int[][] f = new int[n + 1][m + 1];

            for (int i = 0; i <= n; i++) f[i][0] = i;
            for (int j = 0; j <= m; j++) f[0][j] = j;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    f[i][j] = Math.min(f[i - 1][j] + 1, f[i][j - 1] + 1);
                    if (cs1[i - 1] == cs2[j - 1]) f[i][j] = Math.min(f[i][j], f[i - 1][j - 1]);
                }
            }
            return f[n][m];
        }
    }


//    https://leetcode.cn/problems/minimum-ascii-delete-sum-for-two-strings/
//    712. 兩個字符串的最小ASCII刪除和
//    給定兩個字符串s1 和 s2，返回 使兩個字符串相等所需刪除字符的 ASCII 值的最小和 。

    class minimumDeleteSum {

        // 備忘錄
        int memo[][];

        /* 主函數 */
        public int minimumDeleteSumMemo(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // 備忘錄值為 -1 代表未曾計算
            memo = new int[m][n];
            for (int[] row : memo) Arrays.fill(row, -1);

            return helper(s1, 0, s2, 0);
        }

        // 定義：將 s1[i..] 和 s2[j..] 刪除成相同字符串，
        // 最小的 ASCII 碼之和為 helper(s1, i, s2, j)。
        int helper(String s1, int i, String s2, int j) {
            int res = 0;
            // base case
            if (i == s1.length()) {
                // 如果 s1 到頭了，那麼 s2 剩下的都得刪除
                for (; j < s2.length(); j++)
                    res += s2.charAt(j);
                return res;
            }
            if (j == s2.length()) {
                // 如果 s2 到頭了，那麼 s1 剩下的都得刪除
                for (; i < s1.length(); i++)
                    res += s1.charAt(i);
                return res;
            }

            if (memo[i][j] != -1) {
                return memo[i][j];
            }

            if (s1.charAt(i) == s2.charAt(j)) {
                // s1[i] 和 s2[j] 都是在 lcs 中的，不用刪除
                memo[i][j] = helper(s1, i + 1, s2, j + 1);
            } else {
                // s1[i] 和 s2[j] 至少有一個不在 lcs 中，刪一個
                memo[i][j] = Math.min(
                        s1.charAt(i) + helper(s1, i + 1, s2, j),
                        s2.charAt(j) + helper(s1, i, s2, j + 1)
                );
            }
            return memo[i][j];
        }


        public int minimumDeleteSumDP(String s1, String s2) {
            int m = s1.length(), n = s2.length();

            // s1[...i]和s2[...j]若要成為相等的字符串所需要刪除的字符的ASCII值的最小和為dp[i][j]
            int[][] dp = new int[m + 1][n + 1];

            // base case
            for (int i = 1; i <= m; i++) {
                dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
            }
             
            for (int j = 1; j <= n; j++) {
                dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1),
                                dp[i][j - 1] + s2.charAt(j - 1));
                    }
                }
            }
            return dp[m][n];
        }
    }

}
