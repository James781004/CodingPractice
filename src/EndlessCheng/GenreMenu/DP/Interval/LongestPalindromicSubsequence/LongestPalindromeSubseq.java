package EndlessCheng.GenreMenu.DP.Interval.LongestPalindromicSubsequence;

import java.util.Arrays;

public class LongestPalindromeSubseq {

    // https://leetcode.cn/problems/longest-palindromic-subsequence/solutions/2203001/shi-pin-jiao-ni-yi-bu-bu-si-kao-dong-tai-kgkg/
    public int longestPalindromeSubseq(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示還沒有計算過
        }
        return dfs(0, n - 1, s, memo);
    }

    private int dfs(int i, int j, char[] s, int[][] memo) {
        if (i > j) {
            return 0; // 空串
        }
        if (i == j) {
            return 1; // 只有一個字母
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        if (s[i] == s[j]) {
            return memo[i][j] = dfs(i + 1, j - 1, s, memo) + 2; // 都選
        }
        return memo[i][j] = Math.max(dfs(i + 1, j, s, memo), dfs(i, j - 1, s, memo)); // 枚舉哪個不選
    }


    public int longestPalindromeSubseqDP(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) { // i 依賴於 i + 1 所以倒著排。
            f[i][i] = 1;
            for (int j = i + 1; j < n; j++) { // j 依賴於 j - 1 所以正著排 i > j 無效 i == j 是 0 所以從 j = i + 1 開始就行
                f[i][j] = s[i] == s[j] ? f[i + 1][j - 1] + 2 :
                        Math.max(f[i + 1][j], f[i][j - 1]);
            }
        }
        return f[0][n - 1];
    }


    public int longestPalindromeSubseqDP2(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] f = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            f[i] = 1;
            int pre = 0; // 初始值為 f[i+1][i]
            for (int j = i + 1; j < n; j++) {
                int tmp = f[j];
                f[j] = s[i] == s[j] ? pre + 2 : Math.max(f[j], f[j - 1]);
                pre = tmp;
            }
        }
        return f[n - 1];
    }


}
