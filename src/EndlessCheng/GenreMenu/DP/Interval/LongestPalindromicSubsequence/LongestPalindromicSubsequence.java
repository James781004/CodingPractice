package EndlessCheng.GenreMenu.DP.Interval.LongestPalindromicSubsequence;

import java.util.Arrays;

public class LongestPalindromicSubsequence {

    // https://leetcode.cn/problems/longest-palindromic-subsequence-after-at-most-k-operations/solutions/3591706/qu-jian-dppythonjavacgo-by-endlesscheng-sd78/
    public int longestPalindromicSubsequence(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][][] memo = new int[n][n][k + 1];
        for (int[][] mat : memo) {
            for (int[] row : mat) {
                Arrays.fill(row, -1);
            }
        }
        return dfs(0, n - 1, k, s, memo);
    }

    private int dfs(int i, int j, int k, char[] s, int[][][] memo) {
        if (i >= j) {
            return j - i + 1; // i=j+1 時返回 0，i=j 時返回 1
        }
        if (memo[i][j][k] != -1) {
            return memo[i][j][k];
        }
        int res = Math.max(dfs(i + 1, j, k, s, memo), dfs(i, j - 1, k, s, memo));
        int d = Math.abs(s[i] - s[j]);
        int op = Math.min(d, 26 - d);
        if (op <= k) {
            res = Math.max(res, dfs(i + 1, j - 1, k - op, s, memo) + 2);
        }
        return memo[i][j][k] = res;
    }


    public int longestPalindromicSubsequenceDP(String S, int K) {
        char[] s = S.toCharArray();
        int n = s.length;
        int cnt = 0;
        for (int i = 0; i < n / 2; i++) {
            int d = Math.abs(s[i] - s[n - 1 - i]);
            cnt += Math.min(d, 26 - d);
        }
        if (cnt <= K) {
            return n;
        }

        int[][][] f = new int[K + 1][n][n];
        for (int k = 0; k <= K; k++) {
            for (int i = n - 1; i >= 0; i--) {
                f[k][i][i] = 1;
                for (int j = i + 1; j < n; j++) {
                    int res = Math.max(f[k][i + 1][j], f[k][i][j - 1]);
                    int d = Math.abs(s[i] - s[j]);
                    int op = Math.min(d, 26 - d);
                    if (op <= k) {
                        res = Math.max(res, f[k - op][i + 1][j - 1] + 2);
                    }
                    f[k][i][j] = res;
                }
            }
        }
        return f[K][0][n - 1];
    }


}
